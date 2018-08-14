package com.myproject.automationframework.resources;

import java.io.File;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class DriverFactory {


	static WebDriver Driver;
	
	static File file1 = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\ChromeDriver\\chromedriver.exe");
	static File file2 = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\GeckoDriver\\geckodriver.exe");
	static DesiredCapabilities dc;
	
	//Initialize WebDriver based on the Browser set in properties file
	public static WebDriver initializeDriver(String browser) {

		switch (browser) {
		case "firefox":
			
			//Setting gecko driver path
			System.setProperty("webdriver.gecko.driver",file2.getAbsolutePath());			
			Driver = new FirefoxDriver();		
			Driver.manage().window().maximize();
			
			break;
		
		case "chrome":
			
			//Setting chrome driver path
			System.setProperty("webdriver.chrome.driver", file1.getAbsolutePath());

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			
			Driver = new ChromeDriver(options);
			Driver.manage().window().maximize();

			break;
	
		}
		return Driver;
	}

	public static WebDriver getDriver() {
		return Driver;
	}

	public static void closeDriver() {
		
		Driver.getWindowHandle();
		Driver.close();

	}
}
