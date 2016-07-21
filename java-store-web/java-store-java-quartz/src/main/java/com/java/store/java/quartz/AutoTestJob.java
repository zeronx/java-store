package com.java.store.java.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AutoTestJob {

	 @Scheduled(cron = "0/1 * * * * ?")
	 public void run() {
	     System.out.println("注解形式任务调度调用");    
	 }
}
