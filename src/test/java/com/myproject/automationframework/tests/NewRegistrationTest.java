package com.myproject.automationframework.tests;

import org.testng.annotations.Test;

import com.myproject.automationframework.pageobjects.LandingPage;
import com.myproject.automationframework.pageobjects.RegistrationPage;
import com.myproject.automationframework.utilities.TestSetup;

public class NewRegistrationTest extends TestSetup{
	LandingPage LandPage;
	RegistrationPage RegPage;
	
	@Test(dataProvider = "iterator", priority = 1)
	public void newUserRegistration(Integer iteration, Object inputData) throws Exception {
		LandPage = new LandingPage();
		RegPage = new RegistrationPage();
		
		LandPage.navigateToApplication().registerNewAccount();
		RegPage.EnterAuthenticationDetails(use("LoginUser"), use("Email"), use("Password"), use("ConfirmPassword"));
		
		assertAll();
	}
	
}
