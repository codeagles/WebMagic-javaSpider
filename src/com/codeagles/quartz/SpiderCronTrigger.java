package com.codeagles.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import us.codecraft.webmagic.Spider;

public class SpiderCronTrigger {
	
	public static void main(String[] args){
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler= null;
		try {
			//获得调度器
			scheduler = sf.getScheduler();
			//创建jobDetail实例，绑定Job
			JobDetail jobDetail = JobBuilder.newJob(SpiderJob.class).withIdentity("job-1", "jgroup-1").build(); 
			//定义触发器
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger", "trigger")
					.withSchedule(CronScheduleBuilder.cronSchedule("*/20 * * * * ?"))  
		              .startNow()
		              .build();
			
			//把作业和触发器注册到调度器中
			scheduler.scheduleJob(jobDetail,trigger);
			//启动触发器
			scheduler.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
