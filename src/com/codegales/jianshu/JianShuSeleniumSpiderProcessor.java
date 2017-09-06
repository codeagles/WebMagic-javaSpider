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
 * @author hcn 功能:爬去简书文章并存储到Mysql数据中 数据来源：个人简书地址
 *         http://www.jianshu.com/u/bbfef3982813 由于简书没有分页，所以列表地址就是博客地址不用正则匹配
 */
public class JianShuSeleniumSpiderProcessor implements PageProcessor {

	// 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
	private Site site = Site.me().setSleepTime(3000).setRetryTimes(3);
	// 基础地址
	private String Base_URL = "http://www.jianshu.com";
	// 文章列表URL
	private String Lists_URL = "http://www\\.jianshu\\.com/u/bbfef3982813";
	// 文章详情URL
	private static String User_Url = "http://www.jianshu.com/u/bbfef3982813";
	// 统计总数
	private static int total = 0;

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
		driver.get(User_Url);
		for (int i = 0; i < 3; i++) {
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		WebElement webElement = driver.findElement(By.xpath("/html"));
		String str = webElement.getAttribute("outerHTML");
		Html html = new Html(str);
		List<String> lists = new ArrayList<String>();
		// 列表页
		if (page.getUrl().regex(Lists_URL).match()) {
			// 选中所有区域
			lists = html.xpath("//div[@class =\"content\"]//a[@class=\"title\"]").links().regex("/p/.*")
					.replace("/p/", Base_URL+"/p/").all();
			page.addTargetRequests(lists);
			
		} else {// 文章页
			total++;
			JianShuBean bean = new JianShuBean();
			bean.setTitle(page.getHtml().xpath("//div[@class =\"article\"]/h1/text()").get());
			System.out.println(bean.getTitle());
			System.out.println("爬取序列"+total);
		}
		driver.close();
	}

	public static void main(String[] args) {
		Spider.create(new JianShuSeleniumSpiderProcessor())
						.addUrl(User_Url)
						.thread(3)
						.run();

		System.out.println("爬取结束,共爬取文章数："+ total);

	}

}
