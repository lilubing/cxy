package com.llb.cxy.demo.controller;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author lilubing
 * @email 8366419@qq.com
 * @description:spring的事件监听器的处理机制：在启动服务器的时候，插入默认数据
 */
@Component
public class InitApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	public void onApplicationEvent(ContextRefreshedEvent event) {
		/*ApplicationContext context = event.getApplicationContext();
		DemoService userRepository = context.getBean("demoService", DemoService.class);
		for (int i = 1; i < 21; i++) {
			Demo user = new Demo("user" + i, 25 + i);
			userRepository.save(user);
		}*/
	}

}
