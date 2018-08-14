package com.myproject.automationframework.pageobjects;

import com.myproject.automationframework.utilities.Actions;
import com.myproject.automationframework.utilities.ObjectLocator;

public class HomePage {
	
	public static ObjectLocator menuEntities = new ObjectLocator("Xpath", "//li[@class='dropdown pointer ng-scope']/a", "Entities Menu");
	public static ObjectLocator menuAccount = new ObjectLocator("Xpath", "//li[@class='dropdown pointer']/a", "Account Menu");
	public static ObjectLocator menuEntitiesBranch = new ObjectLocator("Xpath", "//a[@href='#/branch']", "Entities Branch Menu");
	public static ObjectLocator menuEntitiesStaff = new ObjectLocator("Xpath", "//a[@href='#/staff']", "Entities Staff Menu");
	public static ObjectLocator menuAccountSettings = new ObjectLocator("Xpath", "//a[@href='#/settings']", "Account Seetings Menu");
	public static ObjectLocator menuAccountLogout = new ObjectLocator("Xpath", "//ul[@class='dropdown-menu']/li[4]/a", "Account Logout");
	public static ObjectLocator menuAccountUserSetting = new ObjectLocator("Xpath", "//li[@class='dropdown pointer active']/a", "Account Menu");
	public static ObjectLocator menuAccountEntities = new ObjectLocator("Xpath", "//li[@class='dropdown pointer active']/a", "Account Menu");
	
	Actions action = new Actions();
	
	//Navigating to Branches
	public HomePage navigateToBranch(){
		
		action.WaitForWebElement(menuEntities)
			  .Click(menuEntities)
			  .Click(menuEntitiesBranch);

		
		return this;
	}
	
	//Navigating to Staff's
	public HomePage navigateToStaff(){
			
			action.WaitForWebElement(menuEntities)
				  .Click(menuEntities)
				  .Click(menuEntitiesStaff);

			
			return this;
	}
	
	//Navigating to Account Settings
	public HomePage navigateToAccountSettings(){
				
				action.WaitForWebElement(menuAccount)
					  .Click(menuAccount)
					  .Click(menuAccountSettings);

				return this;
	}
	
	public HomePage logoutFromAccount(){
		
		action.WaitForWebElement(menuAccountUserSetting)
			  .Click(menuAccountUserSetting)
			  .Click(menuAccountLogout);

		return this;
	}
	public HomePage logoutFromAccountEntitiesPage(){
		
		action.WaitForWebElement(menuAccount)
			  .Click(menuAccount)
			  .Click(menuAccountLogout);

		return this;
	}
}
