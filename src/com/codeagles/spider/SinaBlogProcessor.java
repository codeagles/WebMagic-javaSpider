/**
 * 
 */
package com.codeagles.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author hcn
 * 官方Demo爬去新浪博客地址
 */
public class SinaBlogProcessor implements PageProcessor {

	private String LISURLS = "http://blog\\.sina\\.com\\.cn/s/articlelist_1487828712_0_\\d\\.html";

	private String URLPOST = "http://blog\\.sina\\.com\\.cn/s/blog_\\w+\\.html";

	private Site site = Site.me().setDomain("blog.sina.com.cn").setSleepTime(3000).setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
	
	private static int size = 0;
	
//	public static void main(String[] args) {
//		  
//		  System.out.println(size);
//	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		//列表页
		if (page.getUrl().regex(LISURLS).match()) {
			page.addTargetRequests(page.getHtml().xpath("//div[@class=\"articleList\"]").links().regex(URLPOST).all());
				
		} else {//文章页
			size++;//计数器
			page.putField("title", page.getHtml().xpath("//div[@class=\"articalTitle\"/h2]"));
			page.putField("articalContent", page.getHtml().xpath("//div[@articalContent=\"articalContent\"]"));
			page.putField("date",
			page.getHtml().xpath("//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
		}
		
	}
	
}
