package com.myproject.automationframework.pageobjects;

import com.myproject.automationframework.utilities.Actions;
import com.myproject.automationframework.utilities.ObjectLocator;

public class EntitiesBranchPage {
	public static ObjectLocator btnCreateBranch = new ObjectLocator("Xpath", "//button[@class='btn btn-primary' and @data-target='#saveBranchModal']", "Create New Branch Button");
	public static ObjectLocator btnSearchBranch = new ObjectLocator("Xpath", "//form[@name='searchForm']/button", "Search Branch Button");
	public static ObjectLocator txtboxSearch = new ObjectLocator("Xpath", "//input[@id='searchQuery']", "Search Branch textbox");
	public static ObjectLocator txtboxBranchName = new ObjectLocator("Xpath", "//input[@name='name']", "New Branch Name textbox");
	public static ObjectLocator txtboxBranchCode = new ObjectLocator("Xpath", "//input[@name='code']", "New Branch Code textbox");
	public static ObjectLocator btnSave = new ObjectLocator("Xpath", "//form[@name='editForm']//button[@type='submit']", "Save Button");
	public static ObjectLocator btnCancel = new ObjectLocator("Xpath", "//form[@name='editForm']//button[@type='button' and @class='btn btn-default']", "Cancel Button");
	public static ObjectLocator txtboxViewBranchName = new ObjectLocator("Xpath", "//tbody/tr[1]/td[2]/input[@class='input-sm form-control']", "View Branch Name textbox");
	public static ObjectLocator txtboxViewBranchCode = new ObjectLocator("Xpath", "//tbody/tr[2]/td[2]/input[@class='input-sm form-control']", "View Branch Code textbox");
	public static ObjectLocator btnBack = new ObjectLocator("Xpath", "//button[@href='#/branch']", "Back Button");
	public static ObjectLocator webtableBranch = new ObjectLocator("Xpath", "//table/tbody/tr", "Branches Table");
	public static ObjectLocator btnViewBranch = new ObjectLocator("Xpath", "//table/tbody/tr[$]/td[4]/button[@class='btn btn-info btn-sm']", "View Branch Button");
	public static ObjectLocator btnEditBranch = new ObjectLocator("Xpath", "//table/tbody/tr[$]/td[4]/button[@class='btn btn-primary btn-sm']", "Edit Branch Button");
	public static ObjectLocator btnDeleteBranch = new ObjectLocator("Xpath", "//table/tbody/tr[$]/td[4]/button[@class='btn btn-danger btn-sm']", "Delete Branch Button");
	public static ObjectLocator btnConfirmDeleteBranch = new ObjectLocator("Xpath", "//button[@class='btn btn-danger']", "Confirm Delete Branch Button");
	public static ObjectLocator btnDeleteCancel = new ObjectLocator("Xpath", "//form[@name='deleteForm']//button[@class='btn btn-default']", "Cancel Delete Branch Button");
	
	Actions action = new Actions();
	
	//Enter New Branch Details
	public EntitiesBranchPage createNewBranchDetails(String BranchName, String BranchCode){
		
		action.WaitForWebElement(btnCreateBranch)
		      .Click(btnCreateBranch);
		action.WaitForWebElement(txtboxBranchName)
			  .EnterText(txtboxBranchName, BranchName);
		action.EnterText(txtboxBranchCode, BranchCode);
				
		return this;
	}
	
	//Save new branch details
	public EntitiesBranchPage saveBranchDetails(){
		
		if(action.verifyAttribtuePresent(btnSave, "disabled")) {
			action.HardAssertFail("Branch name and code is not entered correctly");
		}else {
			
			action.WaitForWebElement(btnSave)
			      .Click(btnSave);
			action.threadWait();
		}
				
		return this;
	}
	
	//Cancel creation of new Branch
	public EntitiesBranchPage cancelBranchCreation(){
		
		action.WaitForWebElement(btnCancel)
		      .Click(btnCancel);
		action.threadWait();
				
		return this;
	}	
	
	//View details of a Branch
	public EntitiesBranchPage viewBranchDetailsandConfirm(String BranchName, String BranchCode){
		
		action.WaitForWebElement(webtableBranch)
		      .clickViewButton(webtableBranch, BranchName, BranchCode, btnViewBranch);
		action.WaitForWebElement(txtboxViewBranchName);
		
		if(action.GetAttribute(txtboxViewBranchName, "value").equalsIgnoreCase(BranchName)
				&& action.GetAttribute(txtboxViewBranchCode, "value").equalsIgnoreCase(BranchCode)) {
			action.ActionLog("Details are as expected when viewing Branch Details");
		}else {
			action.AssertFail("Details are not as expected when viewing Branch Details");
			action.threadWait();
		}
		
		action.WaitForWebElement(btnBack)
			  .Click(btnBack);
		action.threadWait();
		
		return this;
	}	
		
	public EntitiesBranchPage editBranchDetails(String BranchName, String BranchCode, String NewBranchName, String NewBranchCode){
		
		action.clickEditButton(webtableBranch, BranchName, BranchCode, btnEditBranch);
		action.WaitForWebElement(txtboxBranchName);
		
		if(action.GetAttribute(txtboxBranchName, "value").equalsIgnoreCase(BranchName)
				&& action.GetAttribute(txtboxBranchCode, "value").equalsIgnoreCase(BranchCode)) {
			action.ActionLog("Details are as expected when editing Branch Details");
		}else {
			action.AssertFail("Details are not as expected when editing Branch Details");
		}
		action.Clear(txtboxBranchName);
		action.Clear(txtboxBranchCode);
		action.EnterText(txtboxBranchName, NewBranchName);
		action.EnterText(txtboxBranchCode, NewBranchCode);
		
		cancelBranchCreation();
		
		action.clickEditButton(webtableBranch, BranchName, BranchCode, btnEditBranch);
		action.Clear(txtboxBranchName);
		action.Clear(txtboxBranchCode);
		action.EnterText(txtboxBranchName, NewBranchName);
		action.EnterText(txtboxBranchCode, NewBranchCode);
		
		saveBranchDetails();
			
		return this;
	}	
		
	public EntitiesBranchPage deleteBranchDetails(String BranchName, String BranchCode){
	
		action.clickDeleteButton(webtableBranch, BranchName, BranchCode, btnDeleteBranch);
		
		action.WaitForWebElement(btnDeleteCancel)
		      .Click(btnDeleteCancel);
		
		action.threadWait();
		
		action.clickDeleteButton(webtableBranch, BranchName, BranchCode, btnDeleteBranch);
		
		action.WaitForWebElement(btnConfirmDeleteBranch)
		      .Click(btnConfirmDeleteBranch);
		action.threadWait();
		
		return this;
	}
	
	public EntitiesBranchPage searchBranchDetails(String BranchSearchCriteria){
		
		action.WaitForWebElement(txtboxSearch)
		      .EnterText(txtboxSearch, BranchSearchCriteria)
		      .Click(btnSearchBranch);
		
		action.threadWait();
		
		action.verifyElementInWebTable(webtableBranch, BranchSearchCriteria);
		
		return this;
	}	
	
	
}
