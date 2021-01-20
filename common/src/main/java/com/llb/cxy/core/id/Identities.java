/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.llb.cxy.core.id;

import com.llb.cxy.core.snowflake.SnowflakeIdWorker;
import com.llb.cxy.core.utils.PropertiesUtilsApplicationXML;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 
 * @ClassName: Identities
 * @Description: TODO(封装各种生成唯一性ID算法的工具类.)
 * @author LiLuBing
 * @date 2015年4月3日 下午2:05:54
 *
 */
public class Identities {

	private static SnowflakeIdWorker idWorker = null;

	private static SecureRandom random = new SecureRandom();

	private static Identities instance = new Identities();

	private Identities() {
		idWorker = new SnowflakeIdWorker(
				Integer.valueOf(PropertiesUtilsApplicationXML.getProperty("SnowflakeIdWorkerWorkerId")),
				Integer.valueOf(PropertiesUtilsApplicationXML.getProperty("SnowflakeIdWorkerDatacenterId")));
	}

	public static Identities getInstance() {
		return instance;
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	public static String IDByTime13() {
		return String.valueOf(System.currentTimeMillis());
	}

	public static long getId() {
		return idWorker.nextId();
	}

}
