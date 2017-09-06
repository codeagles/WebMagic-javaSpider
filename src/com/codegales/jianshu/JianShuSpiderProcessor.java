/**
 * 
 */
package com.codegales.jianshu;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.codegales.bean.JianShuBean;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * @author hcn
 *  功能:爬去简书文章并存储到Mysql数据中
 *	数据来源：个人简书地址 http://www.jianshu.com/u/bbfef3982813
 *	由于简书没有分页，所以列表地址就是博客地址不用正则匹配
 */
public class JianShuSpiderProcessor implements PageProcessor{
	
	// 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
	private Site site = Site.me().setSleepTime(3000).setRetryTimes(3);
	//基础地址
	private String Base_URL = "http://www.jianshu.com";
	// 文章列表URL
	private String Lists_URL = "http://www\\.jianshu\\.com/u/bbfef3982813";
	// 文章详情URL
	private String Post_URL ="/p/*";
	//统计总数
	private int total = 0;
	
	@Override
	public Site getSite() {
		return site;
	}

	/**
	 * 爬虫核心抽取逻辑
	 */
	@Override
	public void process(Page page) {
		System.getProperties().setProperty("webdriver.chrome.driver", "/Users/codeagles/ProgramTools/chromedriver2");
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.jianshu.com/u/bbfef3982813");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		WebElement webElement = driver.findElement(By.xpath("/html"));
		String str = webElement.getAttribute("outerHTML");
        System.out.println(str);

        Html html = new Html(str);
		//列表页
		if(page.getUrl().regex(Lists_URL).match()){
			//选中所有区域
			List<String> lists = html.xpath("//div[@class =\"content\"]//a[@class=\"title\"]")
					.links()
					.regex("/p/.*")
					.replace("/p/", "http://www.jianshu.com/p/")
					.all();
			page.addTargetRequests(lists);
		}else{//文章页
			total++;
			JianShuBean bean = new JianShuBean();
			bean.setTitle(page.getHtml().xpath("//div[@class =\"article\"]/h1/text()").get());
			System.out.println(bean.getTitle());
			System.out.println(total);
		}
		driver.close();
	}
	
	public static void main(String[] args) {
		Spider.create(new JianShuSpiderProcessor()).addUrl("http://www.jianshu.com/u/bbfef3982813")
		.run();
		
		System.out.println("爬取结束");
		
	}


}
