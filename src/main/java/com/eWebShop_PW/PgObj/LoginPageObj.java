package com.eWebShop_PW.PgObj;

import com.microsoft.playwright.Page;

public class LoginPageObj {

	private Page page;
	
	private String emailID_TextField = "//input[@class='email valid']";
	private String password_TextField = "//input[@class='password valid']";
	private String loginLNK = ".button-1.login-button";
	
	public LoginPageObj(Page page){
		this.page = page;
	}
	
	public String getLoginPageTitle() {
		return page.title();
	}

	public LandingPageObj doLogin(String username, String password) { 
		System.out.println("Entered login credentials: "+username+" "+password);
		page.fill(emailID_TextField, username);
		page.fill(password_TextField, password);
		page.click(loginLNK);
		return new LandingPageObj(page);
	}
	
}
