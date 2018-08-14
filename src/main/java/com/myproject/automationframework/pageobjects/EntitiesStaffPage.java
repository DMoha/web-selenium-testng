package com.myproject.automationframework.pageobjects;

import com.myproject.automationframework.utilities.Actions;
import com.myproject.automationframework.utilities.ObjectLocator;

public class EntitiesStaffPage {
	
	public static ObjectLocator btnCreateStaff = new ObjectLocator("Xpath", "//button[@class='btn btn-primary' and @data-target='#saveStaffModal']", "Create New Staff Button");
	public static ObjectLocator btnSearchStaff = new ObjectLocator("Xpath", "//form[@name='searchForm']/button", "Search Staff Button");
	public static ObjectLocator txtboxSearch = new ObjectLocator("Xpath", "//input[@id='searchQuery']", "Search Staff textbox");
	public static ObjectLocator txtboxStaffName = new ObjectLocator("Xpath", "//input[@name='name']", "New Staff Name textbox");
	public static ObjectLocator dropdownStaffBranch = new ObjectLocator("Xpath", "//select[@name='related_branch']", "New Staff Branch dropdown");
	public static ObjectLocator btnSave = new ObjectLocator("Xpath", "//form[@name='editForm']//button[@type='submit']", "Save Button");
	public static ObjectLocator btnCancel = new ObjectLocator("Xpath", "//form[@name='editForm']//button[@type='button' and @class='btn btn-default']", "Cancel Button");
	public static ObjectLocator txtboxViewStaffName = new ObjectLocator("Xpath", "//tbody/tr[1]/td[2]/input[@class='input-sm form-control']", "View Staff Name textbox");
	public static ObjectLocator txtboxViewStaffBranch = new ObjectLocator("Xpath", "//tbody/tr[2]/td[2]/input[@class='input-sm form-control']", "View Staff Branch textbox");
	public static ObjectLocator btnBack = new ObjectLocator("Xpath", "//button[@href='#/staff']", "Back Button");
	public static ObjectLocator webtableStaff = new ObjectLocator("Xpath", "//table/tbody/tr", "Staffs Table");
	public static ObjectLocator btnViewStaff = new ObjectLocator("Xpath", "//table/tbody/tr[$]/td[4]/button[@class='btn btn-info btn-sm']", "View Staff Button");
	public static ObjectLocator btnEditStaff = new ObjectLocator("Xpath", "//table/tbody/tr[$]/td[4]/button[@class='btn btn-primary btn-sm']", "Edit Staff Button");
	public static ObjectLocator btnDeleteStaff = new ObjectLocator("Xpath", "//table/tbody/tr[$]/td[4]/button[@class='btn btn-danger btn-sm']", "Delete Staff Button");
	public static ObjectLocator btnConfirmDeleteBranch = new ObjectLocator("Xpath", "//button[@class='btn btn-danger']", "Confirm Delete Staff Button");
	public static ObjectLocator btnDeleteCancel = new ObjectLocator("Xpath", "//form[@name='deleteForm']//button[@class='btn btn-default']", "Cancel Delete Staff Button");
	
	Actions action = new Actions();
	
	//Enter New Branch Details
	public EntitiesStaffPage createNewStaffDetails(String StaffName, String StaffBranch){
		
		action.WaitForWebElement(btnCreateStaff)
		      .Click(btnCreateStaff);
		action.WaitForWebElement(txtboxStaffName)
			  .EnterText(txtboxStaffName, StaffName);
		action.SelectByText(dropdownStaffBranch, StaffBranch);
				
		return this;
	}
	
	//Save new branch details
	public EntitiesStaffPage saveStaffDetails(){
		
		if(action.verifyAttribtuePresent(btnSave, "disabled")) {
			action.AssertFail("Staff name and staff branch is not entered correctly");
		}else {
			
			action.Click(btnSave);
			action.threadWait();
		}
				
		return this;
	}
	
	//Cancel creation of new Branch
	public EntitiesStaffPage cancelStaffCreation(){
		
		action.Click(btnCancel);
		action.threadWait();	
		return this;
	}	
	
	//View details of a Branch
	public EntitiesStaffPage viewStaffDetailsandConfirm(String StaffName, String StaffBranch){
		
		action.WaitForWebElement(webtableStaff)
		      .clickViewButton(webtableStaff, StaffName, StaffBranch, btnViewStaff);
		action.WaitForWebElement(txtboxViewStaffName);
		
		if(action.GetAttribute(txtboxViewStaffName, "value").equalsIgnoreCase(StaffName)
				&& action.GetAttribute(txtboxViewStaffBranch, "value").equalsIgnoreCase(StaffBranch)) {
			action.ActionLog("Details are as expected when viewing Staff Details");
		}else {
			action.AssertFail("Details are not as expected when viewing Staff Details");
		}
		
		action.Click(btnBack);
		
		return this;
	}	
		
	public EntitiesStaffPage editStaffDetails(String StaffName, String StaffBranch, String NewStaffName, String NewStaffBranch){
		
		action.WaitForWebElement(webtableStaff)
		      .clickEditButton(webtableStaff, StaffName, StaffBranch, btnEditStaff);
		action.WaitForWebElement(txtboxStaffName);
		
		if(action.GetAttribute(txtboxStaffName, "value").equalsIgnoreCase(StaffName)
				&& action.getSelectedValueInDropDown(dropdownStaffBranch).equalsIgnoreCase(StaffBranch)) {
			action.ActionLog("Details are as expected when editing Staff Details");
		}else {
			action.AssertFail("Details are not as expected when editing Staff Details");
		}
		
		action.Clear(txtboxStaffName);
		action.EnterText(txtboxStaffName, NewStaffName);
		action.SelectByText(dropdownStaffBranch, NewStaffBranch);
		
		cancelStaffCreation();
		
		action.clickEditButton(webtableStaff, StaffName, StaffBranch, btnEditStaff);
		action.Clear(txtboxStaffName);
		action.EnterText(txtboxStaffName, NewStaffName);
		action.SelectByText(dropdownStaffBranch, NewStaffBranch);
		
		saveStaffDetails();
			
		return this;
	}	
		
	public EntitiesStaffPage deleteStaffDetails(String StaffName, String StaffBranch){
	
		action.WaitForWebElement(webtableStaff)
		      .clickDeleteButton(webtableStaff, StaffName, StaffBranch, btnDeleteStaff);
		
		action.WaitForWebElement(btnDeleteCancel)
		      .Click(btnDeleteCancel);
		
		action.threadWait();
		
		action.clickDeleteButton(webtableStaff, StaffName, StaffBranch, btnDeleteStaff);
		
		action.WaitForWebElement(btnConfirmDeleteBranch)
		      .Click(btnConfirmDeleteBranch);
		
		action.threadWait();
		
		return this;
	}
	
	public EntitiesStaffPage searchStaffDetails(String StaffSearchCriteria){
		
		action.WaitForWebElement(txtboxSearch)
		      .EnterText(txtboxSearch, StaffSearchCriteria)
		      .Click(btnSearchStaff);
		action.threadWait();
		
		action.verifyElementInWebTable(webtableStaff, StaffSearchCriteria);
		
		return this;
	}	
	
	
}
