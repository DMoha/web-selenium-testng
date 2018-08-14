package com.myproject.automationframework.tests;

import org.testng.annotations.Test;

import com.myproject.automationframework.pageobjects.AuthenticationPage;
import com.myproject.automationframework.pageobjects.HomePage;
import com.myproject.automationframework.pageobjects.LandingPage;
import com.myproject.automationframework.pageobjects.UserSettingsPage;
import com.myproject.automationframework.utilities.TestSetup;


public class LoginUserDetailsTest extends TestSetup{
	LandingPage LandPage;
	AuthenticationPage AuthPage;
	HomePage HmPage;
	UserSettingsPage UserPage;

	@Test(dataProvider = "iterator", priority = 1)
	public void verifyLoginLogoutUserDeatils(Integer iteration, Object inputData) throws Exception {
		LandPage = new LandingPage();
		AuthPage = new AuthenticationPage();
		HmPage = new HomePage();
		UserPage = new UserSettingsPage();
		
		LandPage.navigateToApplication().loginToAccount();
		AuthPage.EnterAuthenticationDetails(use("LoginUser"), use("Password"));
		HmPage.navigateToAccountSettings();
		UserPage.verifyUserDetails(use("FirstName"), use("LastName"), use("Email"));
		UserPage.editUserDetails(use("NewFirstName"), use("NewLastName"), use("NewEmail"));
		HmPage.logoutFromAccount();
		
		assertAll();
		
	}
	

	
	
}
