package com.eWebShop_PW.PgObj;

import com.microsoft.playwright.Page;

public class LandingPageObj {
	private Page page;
	
	private String logoutLNK = "//a[@class='ico-logout']";
	
	public LandingPageObj(Page page){
		this.page = page;
	}

	public boolean verifyLogoutDisplayed() { 
		if(page.isVisible(logoutLNK)) {
			System.out.println("Logout link is displayed");
			return true;
		}
		return false;
		
	}	
	
	public void clickLogout() { 
		System.out.println("click logout.");
		page.click(logoutLNK);
	}
}
