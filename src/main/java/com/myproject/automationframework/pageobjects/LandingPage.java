package com.myproject.automationframework.pageobjects;

import com.myproject.automationframework.utilities.Actions;
import com.myproject.automationframework.utilities.ObjectLocator;

public class LandingPage {
	
	public static ObjectLocator linkLogin = new ObjectLocator("Xpath", "//div[@class = 'alert alert-warning ng-scope']/a[@href = '#/login']", "Login Link");
	public static ObjectLocator linkRegisterNewAccount = new ObjectLocator("Xpath", "//div[@class = 'alert alert-warning ng-scope']/a[@href = '#/register']", "Register a New Account Link");
	public static ObjectLocator linkHome = new ObjectLocator("Xpath", "//ul[@class = 'nav navbar-nav nav-pills navbar-right']/li/a[@ui-sref='home' and @href = '#/']", "Home Link");

	Actions action = new Actions();
	
	//Navigating to the application
	public LandingPage navigateToApplication(){
		String  url = action.getProperties("URL");
		action.OpenURl(url).Waitforpageload();
		return this;
	}
	
	//Clicking on Login link
	public LandingPage loginToAccount(){
		action.WaitForWebElement(linkLogin)
			  .Click(linkLogin);
		return this;
	}
	//Clicking on Register New Account link
	public LandingPage registerNewAccount(){
		action.WaitForWebElement(linkRegisterNewAccount)
			  .Click(linkRegisterNewAccount);
		return this;
	}
	
}
