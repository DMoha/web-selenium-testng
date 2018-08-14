package com.myproject.automationframework.pageobjects;

import com.myproject.automationframework.utilities.Actions;
import com.myproject.automationframework.utilities.ObjectLocator;

public class RegistrationPage {
	
	public static ObjectLocator txtboxLogin = new ObjectLocator("Xpath", "//input[@name ='login']", "Login textbox");
	public static ObjectLocator txtboxEmail = new ObjectLocator("Xpath", "//input[@name ='email']", "Email textbox");
	public static ObjectLocator txtboxNewPassword = new ObjectLocator("Xpath", "//input[@name ='password']", "Password textbox");
	public static ObjectLocator txtboxNewPasswordConfirm = new ObjectLocator("Xpath", "//input[@name ='confirmPassword']", "Confirm Password textbox");
	public static ObjectLocator btnRegister = new ObjectLocator("Xpath", "//button[@type='submit']", "Authenticate button");
	public static ObjectLocator txtAuthenticationFail = new ObjectLocator("Xpath", "//div[@class='alert alert-danger ng-scope']/strong", "Authenticate Failure text");
	
	Actions action = new Actions();
	
	//Entering New account registration details
	public RegistrationPage EnterAuthenticationDetails(String LoginUser, String email, String Password, String ConfirmPassword){
		
		action.WaitForWebElement(txtboxLogin)
			  .EnterText(txtboxLogin, LoginUser);
		action.EnterText(txtboxEmail, email);
		action.EnterText(txtboxNewPassword, Password);
		action.EnterText(txtboxNewPasswordConfirm, ConfirmPassword);
			
			if(action.verifyAttribtuePresent(btnRegister, "disabled")) {
				action.HardAssertFail("Register Button is not enabled");	
				
			}else {
				action.Click(btnRegister);
			}
		
		if(action.verifyElementPresentinUI(txtAuthenticationFail)) {
				action.HardAssertFail("New User Registration has Failed");
				
			}
			
		return this;
	}
}
