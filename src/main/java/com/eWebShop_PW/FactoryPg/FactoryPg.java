package com.eWebShop_PW.FactoryPg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;

public class FactoryPg 
{
	Playwright playwright;
	Browser browser;
	BrowserContext browserContext;
	static Page page;
	static Properties prop;

	private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
	private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
	private static ThreadLocal<Page> tlPage = new ThreadLocal<>();
	private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
	
	
	//get local copy of thread to be used with 'set' 
	public static Browser getBrowser() {
		return tlBrowser.get();
	}	
	
	public static BrowserContext getBrowserContext() {
		return tlBrowserContext.get();
	}

	public static Page getPage() {
		return tlPage.get();
	}	

	public static Playwright getPlaywright() {
		return tlPlaywright.get();
	}

	public Page initBrowser_paralleltest(String Browser){
		String browserName = Browser;
	
		System.out.println("Browser name is: "+browserName);
		
		//playwright = Playwright.create();
		tlPlaywright.set(Playwright.create());

		LaunchOptions lo = new LaunchOptions();

		lo.setHeadless(false);
		
		switch (browserName.toLowerCase()){
		case "chrome":
			lo.setChannel("chrome");
			//browser = playwright.chromium().launch(lo);
			tlBrowser.set(getPlaywright().chromium().launch(lo));
		    //BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920,920));

			break;				
		case "firefox":
			lo.setChannel("firefox");
			//browser = playwright.firefox().launch(lo);
			tlBrowser.set(getPlaywright().firefox().launch(lo));
			break;			
		case "safari":
			lo.setChannel("safari");
			//browser = playwright.webkit().launch(lo);
			tlBrowser.set(getPlaywright().webkit().launch(lo));
			break;				
		case "msedge":
			lo.setChannel("msedge");
			//browser = playwright.chromium().launch(lo);
			tlBrowser.set(getPlaywright().chromium().launch(lo));
			break;	
		default:
			System.out.println("No browser has been set....");
			break;
		}
		tlBrowserContext.set(getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(1920,920)));
		System.out.println("set Browser w and h");
		//browserContext = browser.newContext();
		tlBrowserContext.set(getBrowser().newContext());
		//page = browserContext.newPage();
		tlPage.set(getBrowser().newPage());
		//page.navigate(prop.getProperty("url"));
		getPage().navigate(prop.getProperty("url").trim());
		
		//return page;
		return getPage();
		
	}	
	
	
	public Page initBrowser(String Browser){
		String browserName = Browser;
	
		System.out.println("Browser name is: "+browserName);
		
		playwright = Playwright.create();
		//tlPlaywright.set(Playwright.create());
	
		LaunchOptions lo = new LaunchOptions();

		lo.setHeadless(false);
		
		switch (browserName.toLowerCase()){
		case "chrome":
			lo.setChannel("chrome");
			browser = playwright.chromium().launch(lo);
			//tlBrowser.set(getPlaywright().chromium().launch(lo));
		    //BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920,920));
			//System.out.println("set Browser w and h");
			break;				
		case "firefox":
			lo.setChannel("firefox");
			browser = playwright.firefox().launch(lo);
			//tlBrowser.set(getPlaywright().firefox().launch(lo));
			break;			
		case "safari":
			lo.setChannel("safari");
			browser = playwright.webkit().launch(lo);
			//tlBrowser.set(getPlaywright().webkit().launch(lo));
			break;				
		case "msedge":
			lo.setChannel("msedge");
			browser = playwright.chromium().launch(lo);
			//tlBrowser.set(getPlaywright().chromium().launch(lo));
			break;	
		default:
			System.out.println("No browser has been set....");
			break;
		}

		BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920,920));
		System.out.println("set Browser w and h");

		//tlBrowserContext.set(getBrowser().newContext());
		//Page page = browserContext.newPage();

		page = browserContext.newPage();
		//tlPage.set(getBrowser().newPage());
		page.navigate(prop.getProperty("url").trim());
		//getPage().navigate(prop.getProperty("url").trim());
		
		return page;
		//return getPage();
		
	}
	//initialize the config.properties file containing static text
	public static Properties init_prop() {
		
		try {
			FileInputStream ip = new FileInputStream("E:\\My Projects\\Development\\automation\\eclipsev2\\eWebShop_PW\\src\\test\\resources\\configuration\\config.properties");
			prop = new Properties();
			prop.load(ip);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return prop;
		
	}
	
	public static String takeScreenshot() {
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		String paralleltest = prop.getProperty("paralleltest");
		System.out.println(paralleltest);
		if (prop.getProperty("paralleltest").equals("yes")) {
		
			getPage().screenshot(new Page.ScreenshotOptions() //getPage threadlocal...allows screenshots on multiple browser
					   .setPath(Paths.get(path))
					   .setFullPage(false));

		}
		else if (prop.getProperty("paralleltest").equals("no"))
		{
			System.out.println("screenshot taken");
			try {
				page.screenshot(new Page.ScreenshotOptions()
						   .setPath(Paths.get(path))
						   .setFullPage(false));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("no screenshot taken");
		}
		return path;
	}
	
}
