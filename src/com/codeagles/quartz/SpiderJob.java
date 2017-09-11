package com.codeagles.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.codeagles.spider.SinaBlogProcessor;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;

public class SpiderJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Spider.create(new SinaBlogProcessor()).addUrl("http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html")
		  .addPipeline(new ConsolePipeline())
        .run();
		System.out.println("爬取结束");
		
	}

}
