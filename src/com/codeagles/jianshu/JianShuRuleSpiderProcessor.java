package com.codeagles.jianshu;

import java.util.ArrayList;
import java.util.List;

import com.codeagles.bean.JianShuBean;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class JianShuRuleSpiderProcessor implements PageProcessor {

	// 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
	private Site site = Site.me().setSleepTime(3000).setRetryTimes(3);
	// 文章列表URL
	private static String ListsURL = "http://www.jianshu.com/u/bbfef3982813?order_by=shared_at&page=";
	// 文章详情URL
	private static String ListsMatchURL = "http://www\\.jianshu\\.com/u/bbfef3982813\\?order_by=shared_at\\&page=\\d+";
	
	// 统计总数
	private static int total = 0;
	private static boolean flag = true;
	private List<String> lists = new ArrayList<String>();

	public static void main(String[] args) {
		for(int i = 1;i<100;i++ ){
			if(flag){
				Spider.create(new JianShuRuleSpiderProcessor()).addUrl(ListsURL+i).thread(23).run();
			}
			
		}
		System.out.println("爬取结束，共爬取"+total);
	}

	@Override
	public void process(Page page) {
			String type = page.getHtml().xpath("//ul[@class=\"trigger-menu\"]/li[@class=\"active\"]/a/text()").get();
			boolean isMatch = page.getUrl().regex(ListsMatchURL).match();
			if (null==type||"文章".equals(type.trim())) {
				if(isMatch){
					page.addTargetRequests(page.getHtml().xpath("//a[@class=\"title\"]").links()
							.regex("http://www\\.jianshu\\.com/p/.*").all());
				}else{
					total++;
					JianShuBean bean = new JianShuBean();
					bean.setTitle(page.getHtml().xpath("//div[@class =\"article\"]/h1/text()").get());
					System.out.println(bean.getTitle());
					System.out.println("爬取序列" + total);
				}
			} else{
				flag =false;
			}
	}

	@Override
	public Site getSite() {
		return site;
	}

}
