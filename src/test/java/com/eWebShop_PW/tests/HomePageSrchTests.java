package com.eWebShop_PW.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.eWebShop_PW.Base.BaseTest;
import com.eWebShop_PW.Constants.AppConstants;

public class HomePageSrchTests extends BaseTest {

	@Test(priority = 2)
	public void homePageTitle_Test() {
		String actualTitle = homePageOBJ.getHomePageTitle();
		Assert.assertEquals(actualTitle, AppConstants.HOME_PAGE_TITLE);
	}
		
	@Test(priority = 3)
	public void homePageURL_Test() {
		String actualURL = homePageOBJ.getHomePageURL();
		Assert.assertEquals(actualURL, prop.getProperty("url"));
		Logger.info("homePageURL_Test");
	}

	@Test(priority = 1, dataProvider = "getProductData")
	public void searchPrd_Test(String productName){
		String actualsearchHdrLbl = homePageOBJ.doSearch(productName);
		Assert.assertEquals(actualsearchHdrLbl, productName);
	}	

	
	@DataProvider
	public Object [][] getProductData(){
		return new Object[][]{
			{"Blue Jeans"},
			{"50's Rockabilly Polka Dot Top JR Plus Size"},			
		};
	}
	
}
