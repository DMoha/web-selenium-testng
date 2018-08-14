package com.myproject.automationframework.pageobjects;

import com.myproject.automationframework.utilities.Actions;
import com.myproject.automationframework.utilities.ObjectLocator;


public class AuthenticationPage {
	
	public static ObjectLocator txtboxLogin = new ObjectLocator("Xpath", "//input[@id ='username']", "Login textbox");
	public static ObjectLocator txtboxPassword = new ObjectLocator("Xpath", "//input[@id ='password']", "Password textbox");
	public static ObjectLocator btnAuthenticate = new ObjectLocator("Xpath", "//button[@type='submit']", "Authenticate button");
	public static ObjectLocator txtAuthenticationFail = new ObjectLocator("Xpath", "//div[@class='alert alert-danger ng-scope']/strong", "Authenticate Failure text");
	public static ObjectLocator linkHome = new ObjectLocator("Xpath", "//a[@ui-sref='home']", "Home link");
	
	Actions action = new Actions();
	
	//Entering Login details and authenticating
	public AuthenticationPage EnterAuthenticationDetails(String LoginUser, String Password){
		
		action.WaitForWebElement(txtboxLogin)
			  .EnterText(txtboxLogin, LoginUser);
		action.EnterText(txtboxPassword, Password);
		action.Click(btnAuthenticate);
		
		if(action.verifyElementPresentinUI(txtAuthenticationFail)) {
			action.HardAssertFail("Login has Failed");
			
		}
		
		return this;
	}
	
}
