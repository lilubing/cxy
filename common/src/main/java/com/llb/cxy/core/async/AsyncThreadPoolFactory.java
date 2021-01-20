package com.llb.cxy.core.async;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步线程池 Created by llb on 14-7-10.
 */
public class AsyncThreadPoolFactory {

	public static final String CLIENT_REPORT_POOL ="client-report-pool";
    public static final ThreadPoolExecutor CLIENT_REPORT_THREAD_POOL =
            new ThreadPoolExecutor(100, 100, 0L, TimeUnit.MILLISECONDS,
                    new SynchronousQueue<Runnable>(), new NamedThreadFactory(
                            CLIENT_REPORT_POOL, true));
    
    public static final String REDIS_SLOWLOG_POOL = "redis-slowlog-pool";
    public static final ThreadPoolExecutor REDIS_SLOWLOG_THREAD_POOL =
            new ThreadPoolExecutor(30, 30, 0L, TimeUnit.MILLISECONDS,
                    new SynchronousQueue<Runnable>(), new NamedThreadFactory(
                            REDIS_SLOWLOG_POOL, true));
    
    public static final String MACHINE_POOL ="machine-pool";
    public static final ThreadPoolExecutor MACHINE_THREAD_POOL =
            new ThreadPoolExecutor(100, 100, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(1000), new NamedThreadFactory(
                    		MACHINE_POOL, true));
}
