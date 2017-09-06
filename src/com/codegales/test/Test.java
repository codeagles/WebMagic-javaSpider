package com.codegales.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test {
	 public static void testSelenium() {
	        System.getProperties().setProperty("webdriver.chrome.driver", "/Users/codeagles/ProgramTools/chromedriver2");
	        WebDriver webDriver = new ChromeDriver();
	        webDriver.get("http://www.jianshu.com/u/bbfef3982813");
	        WebElement webElement = webDriver.findElement(By.xpath("/html"));
	        System.out.println(webElement.getAttribute("outerHTML"));
	        webDriver.close();
	    }
	 public static void main(String[] args) {
		 testSelenium();
	 }
}
