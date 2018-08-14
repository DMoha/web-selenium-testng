package com.myproject.automationframework.pageobjects;

import com.myproject.automationframework.utilities.Actions;
import com.myproject.automationframework.utilities.ObjectLocator;


public class UserSettingsPage {
	
	public static ObjectLocator txtboxFirstName = new ObjectLocator("Xpath", "//input[@name='firstName']", "First name textbox");
	public static ObjectLocator txtboxLastName = new ObjectLocator("Xpath", "//input[@name='lastName']", "Last name textbox");
	public static ObjectLocator txtboxEmail = new ObjectLocator("Xpath", "//input[@name='email']", "Email textbox");
	public static ObjectLocator btnSave = new ObjectLocator("Xpath", "//button[@class='btn btn-primary ng-scope']", "Save button");
	public static ObjectLocator txtSettingsSuccess = new ObjectLocator("Xpath", "//div[@class='alert alert-success ng-scope']/strong", "Authenticate Failure text");
	
	
	Actions action = new Actions();
	
	public UserSettingsPage verifyUserDetails(String FirstName, String LastName, String Email){
		
		action.WaitForWebElement(txtboxFirstName);
		
		if(action.GetAttribute(txtboxFirstName, "value").equalsIgnoreCase(FirstName)
				&& action.GetAttribute(txtboxLastName, "value").equalsIgnoreCase(LastName)
				&& action.GetAttribute(txtboxEmail, "value").equalsIgnoreCase(Email)) {
			
			action.ActionLog("User Details are as expected when viewing user Details");
		}else {
			action.AssertFail("User Details are not as expected when viewing user Details");
		}
		
		return this;
	}
	
	public UserSettingsPage editUserDetails(String NewFirstName, String NewLastName, String NewEmail){
		
		action.WaitForWebElement(txtboxFirstName);
		
		action.Clear(txtboxFirstName);
		action.EnterText(txtboxFirstName, NewFirstName);
		
		action.Clear(txtboxLastName);
		action.EnterText(txtboxLastName, NewLastName);
	
		action.Clear(txtboxEmail);
		action.EnterText(txtboxEmail, NewEmail);
		
		if(action.verifyAttribtuePresent(btnSave, "disabled")) {
			action.AssertFail("User Details are not entered correctly");
		}else {
			action.Click(btnSave);
		}
		
		if(action.verifyElementPresentinUI(txtSettingsSuccess)) {
			action.ActionLog("Settings are saved successfully.");
		}else {
			action.AssertFail("Settings are not saved successfully");
		}
		
		return this;
	}
}
