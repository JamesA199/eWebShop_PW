package com.eWebShop_PW.Base;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.eWebShop_PW.FactoryPg.FactoryPg;
import com.eWebShop_PW.PgObj.HomePageObj;
import com.eWebShop_PW.PgObj.LoginPageObj;
import com.microsoft.playwright.Page;

public class BaseTest {
	FactoryPg pf;
	Page page;
	protected Properties prop;
	protected HomePageObj homePageOBJ; //make protected so all the child classes can use it...inherit the parent properties
	protected LoginPageObj loginPageObj; //make protected so all the child classes can use it...inherit the parent properties	
	
	public static Logger Logger;
	
	
	@Parameters("browser")
	@BeforeMethod
	public void setup(String browser) {
		pf = new FactoryPg();
		prop = FactoryPg.init_prop();
		if (prop.getProperty("paralleltest").equals("yes")) {
			page = pf.initBrowser_paralleltest(browser);
		}
		else if (prop.getProperty("paralleltest").equals("no")) {
			page = pf.initBrowser(browser);
		}

		homePageOBJ = new HomePageObj(page);
		
	}
	@BeforeSuite
	public void config() {
		System.out.println("Load log4j");
		Logger = org.apache.log4j.Logger.getLogger("eWebShop_PW");
		PropertyConfigurator.configure("Log4j.properties");
		DOMConfigurator.configure("log4j.xml");
	}
	
	
	@AfterMethod
	public void tearDown() {
		page.context().browser().close();
	}	
	
}
