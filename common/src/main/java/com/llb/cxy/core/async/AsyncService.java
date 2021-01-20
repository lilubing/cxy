package com.llb.cxy.core.async;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步服务类
 * Created by llb on 14-6-18.
 */
public interface AsyncService {

    /**
     * 提交任务
     *
     * @param callable
     * @return 返回是否提交成功
     */
    public boolean submitFuture(KeyCallable<?> callable);

    /**
     * 提交任务
     * asyncService.submitFuture(AsyncThreadPoolFactory.MACHINE_POOL, new KeyCallable<Boolean>(key) {
            public Boolean execute() {
                try {
                	System.out.println("==================执行保存" + jsonRow);
    				sysMenuButtonService.saveMenuButtons(jsonRow);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        });
     * @param threadPoolKey
     * @param callable
     * @return 返回是否提交成功
     */
    public boolean submitFuture(String threadPoolKey, KeyCallable<?> callable);

    /**
     * 提交任务
     *
     *Future<?> f = asyncService.submitFuture(new Callable<SysMenuButton>(){
			public SysMenuButton call() {
				System.out.println("==================执行保存" + jsonRow);
				return sysMenuButtonService.saveMenuButtons(jsonRow);
			}
		});
		return (SysMenuButton) f.get();
		
     * @param callable
     * @return 返回成功结果
     */
    public Future<?> submitFuture(Callable<?> callable);

    /**
     * 装配key对应的线程池
     *
     *asyncService.assemblePool(AsyncThreadPoolFactory.MACHINE_POOL, 
				AsyncThreadPoolFactory.MACHINE_THREAD_POOL);
				
     * @param threadPoolKey
     * @param threadPool
     */
    public void assemblePool(String threadPoolKey, ThreadPoolExecutor threadPool);

}
