package com.eWebShop_PW.PgObj;

import com.microsoft.playwright.Page;

//OO encapsulation
public class HomePageObj {
	private Page page;
	
	//string locators 
	
	//css selector
	private String searchtextField = "input#small-searchterms";
	private String searchBTN = "//input[@value='Search']"; 
			//"html>body>div:nth-of-type(4)>div>div>div:nth-of-type(3)>form>input:nth-of-type(2)";
	
	
	private String searchPgLBL = "//h1[text()='Search']";
	private String prdLinkNameLBL = "html>body>div:nth-of-type(4)>div>div:nth-of-type(4)>div:nth-of-type(2)>div>div:nth-of-type(2)>div:nth-of-type(3)>div>div>div>div:nth-of-type(2)>h2>a";
	
	private String loginLink = "a:text('Log in')";
	
	
	//constructor
	public HomePageObj (Page page) {
		
		this.page = page;
				
	}
	
	//page methods
	public String getHomePageTitle() {
		
		return page.title();
		
	}
	
	public String getHomePageURL() {
		
		return page.url();
		
	}
	
	public String doSearch(String productName) {
		
		page.fill(searchtextField, productName); //page.fill method is same as driver.findelement.sendkeys as in selenium java
		page.click(searchBTN);
		page.locator(searchPgLBL).waitFor();
		String prdName = page.textContent(prdLinkNameLBL); //page.textContent is same as driver.gettext in selenium java 
		System.out.println("search head label: "+prdName);
		return prdName;
		
	}

	public LoginPageObj clickLoginLink() {
		page.click(loginLink);
		return new LoginPageObj(page);
		
		
		
	}
	
}


