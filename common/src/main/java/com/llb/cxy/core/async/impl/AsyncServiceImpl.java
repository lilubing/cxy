package com.llb.cxy.core.async.impl;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.llb.cxy.core.async.AsyncService;
import com.llb.cxy.core.async.KeyCallable;
import com.llb.cxy.core.async.KeyFuture;
import com.llb.cxy.core.async.NamedThreadFactory;

/**
 * Created by llb on 14-6-18.
 */
@Service("asyncService")
public class AsyncServiceImpl implements AsyncService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int WARNING_TIMEOUT = 2000;

    private final int INTERRUPT_TIMEOUT = 10000;

    private final String DEFAULT_THREAD_POOL = "default_thread_pool";

    private final ExecutorService defaultThreadPool;

    private final ThreadPoolExecutor observeFuturePool;

    public ConcurrentMap<String, ExecutorService> threadPoolMap;

    private final BlockingQueue<KeyFuture<?>> futureQueue;

    public AsyncServiceImpl() {
        defaultThreadPool = new ThreadPoolExecutor(256, 256,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(),
                new NamedThreadFactory("async", true));

        observeFuturePool = new ThreadPoolExecutor(3, 3,
                0L, TimeUnit.MILLISECONDS,
                new SynchronousQueue<Runnable>(),
                new NamedThreadFactory("future-observe", true));
        threadPoolMap = new ConcurrentSkipListMap<String, ExecutorService>();
        futureQueue = new LinkedBlockingQueue<KeyFuture<?>>();

        threadPoolMap.put(DEFAULT_THREAD_POOL, defaultThreadPool);
        startObserveFuture();
    }

    private final Runnable observeTask = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    KeyFuture<?> keyFuture = futureQueue.take();
                    String key = keyFuture.getKey();
                    Future<?> future = keyFuture.getFuture();
                    try {
                        long begin = System.currentTimeMillis();
                        future.get(INTERRUPT_TIMEOUT, TimeUnit.MILLISECONDS);
                        long costTime = System.currentTimeMillis() - begin;
                        if (costTime >= WARNING_TIMEOUT) {
                            logger.warn("WARNING:future={} costTime={}", key, costTime);
                        } else {
                            logger.info("future={} costTime={}", key, costTime);
                        }
                    } catch (TimeoutException te) {
                        logger.error("ERROR:Timeout:future={},costTime={}", key, INTERRUPT_TIMEOUT);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    };

    private void startObserveFuture() {
        observeFuturePool.execute(observeTask);
        observeFuturePool.execute(observeTask);
    }

    @Override
    public boolean submitFuture(KeyCallable<?> callable) {
        return submitFuture(DEFAULT_THREAD_POOL, callable);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public boolean submitFuture(String threadPoolKey, KeyCallable<?> callable) {
        try {
            Future<?> future = getExecutorService(threadPoolKey).submit(callable);
            //忽略queue溢出
            futureQueue.put(new KeyFuture(callable.getKey(), future));
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage() + callable.getKey(), e);
            return false;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public Future<?> submitFuture(Callable<?> callable) {
        try {
            Future<?> future = defaultThreadPool.submit(callable);
            //忽略queue溢出
            futureQueue.put(new KeyFuture(callable.getClass().getName(), future));
            return future;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private ExecutorService getExecutorService(String key) {
        return threadPoolMap.get(key);
    }

    @Override
    public void assemblePool(String threadPoolKey, ThreadPoolExecutor threadPool) {
        ExecutorService executorService = threadPoolMap.putIfAbsent(threadPoolKey, threadPool);
        if (executorService != null) {
            logger.error("{} is assembled", threadPoolKey);
        }
    }

    @PreDestroy
    public void destory() {
        for (ExecutorService executorService : threadPoolMap.values()) {
            if (!executorService.isShutdown()) {
                executorService.shutdown();
            }
        }
        threadPoolMap.clear();
    }

}
