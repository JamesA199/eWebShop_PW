package com.eWebShop_PW.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.eWebShop_PW.Base.BaseTest;
import com.eWebShop_PW.Constants.AppConstants;

public class LoginPageTests  extends BaseTest{

	@Test
	public void LoginNavigation_Test() {
		
		
		loginPageObj = homePageOBJ.clickLoginLink();
		String actualLoginPgTitle = loginPageObj.getLoginPageTitle();
		Assert.assertEquals(actualLoginPgTitle, AppConstants.LOGIN_PAGE_TITLE);
		
		

	}
}
