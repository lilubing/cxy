package com.llb.cxy.core.text;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 参考Netty的InternalThreadLocalMap 与 BigDecimal, 放在threadLocal中重用的StringBuilder,
 * 节约StringBuilder内部的char[]
 * 
 * 参考文章：《StringBuilder在高性能场景下的正确用法》http://calvin1978.blogcn.com/articles/stringbuilder.html
 * 
 * 不过仅在String对象较大时才有明显效果，否则抵不上访问ThreadLocal的消耗.
 * 
 * 当StringBuilder在使用过程中，会调用其他可能也使用StringBuilderHolder的子函数时，需要创建独立的Holder,
 * 否则使用公共的Holder
 * 
 * 注意：在Netty环境中，使用Netty提供的基于FastThreadLocal的版本。
 * 
 * 如果String的长度不大，那从ThreadLocal里取一次值的代价还更大的多，
 * 所以也不能把这个ThreadLocalStringBuilder搞出来后，见到StringBuilder就替换。。。
 *
 */
public class StringBuilderHolder {

	// 公共的Holder
	private static ThreadLocal<StringBuilder> globalStringBuilder = new ThreadLocal<StringBuilder>() {
		@Override
		protected StringBuilder initialValue() {
			return new StringBuilder(512);
		}
	};

	// 独立的Holder
	private ThreadLocal<StringBuilder> stringBuilder = new ThreadLocal<StringBuilder>() {
		@Override
		protected StringBuilder initialValue() {
			return new StringBuilder(initSize);
		}
	};

	private int initSize;

	/**
	 * 创建独立的Holder.
	 * 
	 * 用于StringBuilder在使用过程中，会调用其他可能也使用StringBuilderHolder的子函数.
	 * 
	 * @param initSize
	 *            StringBulder的初始大小, 建议512,如果容量不足将进行扩容，扩容后的数组将一直保留.
	 */
	public StringBuilderHolder(int initSize) {
		this.initSize = initSize;
	}

	/**
	 * 获取公共Holder的StringBuilder.
	 * 
	 * 当StringBuilder会被连续使用，期间不会调用其他可能也使用StringBuilderHolder的子函数时使用.
	 * 
	 * 重置StringBuilder内部的writerIndex, 而char[]保留不动.
	 */
	public static StringBuilder getGlobal() {
		StringBuilder sb = globalStringBuilder.get();
		sb.setLength(0);
		return sb;
	}

	/**
	 * 获取独立Holder的StringBuilder.
	 * 
	 * 重置StringBuilder内部的writerIndex, 而char[]保留不动.
	 */
	public StringBuilder get() {
		StringBuilder sb = stringBuilder.get();
		sb.setLength(0);
		return sb;
	}
	
	
	public void test() throws InterruptedException {

		final CountDownLatch countdown = new CountDownLatch(10);
		final CyclicBarrier barrier = new CyclicBarrier(10);

		Runnable runnable = new Runnable() {
			
			StringBuilderHolder holder = new StringBuilderHolder(512);
			@Override
			public void run() {
				try {
					barrier.await();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				StringBuilder builder = StringBuilderHolder.getGlobal();
				builder.append(Thread.currentThread().getName() + "-1");
				System.out.println(builder.toString());

				builder = StringBuilderHolder.getGlobal();
				builder.append(Thread.currentThread().getName() + "-2");
				System.out.println(builder.toString());

				StringBuilder builder2 = holder.get();
				builder2.append(Thread.currentThread().getName() + "-11");
				System.out.println(builder2.toString());

				builder2 = holder.get();
				builder2.append(Thread.currentThread().getName() + "-22");
				System.out.println(builder2.toString());

				countdown.countDown();
			}
		};

		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(runnable);
			thread.start();
		}

		countdown.await();

	}
}