package com.myproject.automationframework.tests;

import org.testng.annotations.Test;

import com.myproject.automationframework.pageobjects.AuthenticationPage;
import com.myproject.automationframework.pageobjects.EntitiesStaffPage;
import com.myproject.automationframework.pageobjects.HomePage;
import com.myproject.automationframework.pageobjects.LandingPage;

import com.myproject.automationframework.utilities.TestSetup;

public class EntitiesStaffTest extends TestSetup{

	LandingPage LandPage;
	AuthenticationPage AuthPage;
	EntitiesStaffPage StaffPage;
	HomePage HmPage;
	
	
	@Test(dataProvider = "iterator")
	public void addNewStaff(Integer iteration, Object inputData) throws Exception {
		LandPage = new LandingPage();
		AuthPage = new AuthenticationPage();
		StaffPage = new EntitiesStaffPage();
		HmPage = new HomePage();
		
		LandPage.navigateToApplication().loginToAccount();
		AuthPage.EnterAuthenticationDetails(use("LoginUser"), use("Password"));
		HmPage.navigateToStaff();
		StaffPage.createNewStaffDetails(use("StaffName"), use("StaffBranch")).cancelStaffCreation();
		StaffPage.createNewStaffDetails(use("StaffName"), use("StaffBranch")).saveStaffDetails();
		HmPage.logoutFromAccountEntitiesPage();
	}
	
	@Test(dataProvider = "iterator")
	public void deleteStaffDetails(Integer iteration, Object inputData) throws Exception {
		LandPage = new LandingPage();
		AuthPage = new AuthenticationPage();
		StaffPage = new EntitiesStaffPage();
		HmPage = new HomePage();
		
		LandPage.navigateToApplication().loginToAccount();
		AuthPage.EnterAuthenticationDetails(use("LoginUser"), use("Password"));
		HmPage.navigateToStaff();
		StaffPage.deleteStaffDetails(use("StaffName"),use("StaffBranch"));
		HmPage.logoutFromAccountEntitiesPage();
		
		assertAll();
		
	}
	
		
	@Test(dataProvider = "iterator")
	public void editStaffDetails(Integer iteration, Object inputData) throws Exception {
		LandPage = new LandingPage();
		AuthPage = new AuthenticationPage();
		StaffPage = new EntitiesStaffPage();
		HmPage = new HomePage();
		
		LandPage.navigateToApplication().loginToAccount();
		AuthPage.EnterAuthenticationDetails(use("LoginUser"), use("Password"));
		HmPage.navigateToStaff();
		StaffPage.editStaffDetails(use("StaffName"),use("StaffBranch"), use("NewStaffName"),use("NewStaffBranch"));
		HmPage.logoutFromAccountEntitiesPage();
		
		assertAll();
		
	}
	
	
	@Test(dataProvider = "iterator")
	public void searchStaffAndConfirm(Integer iteration, Object inputData) throws Exception {
		LandPage = new LandingPage();
		AuthPage = new AuthenticationPage();
		StaffPage = new EntitiesStaffPage();
		HmPage = new HomePage();
		
		LandPage.navigateToApplication().loginToAccount();
		AuthPage.EnterAuthenticationDetails(use("LoginUser"), use("Password"));
		HmPage.navigateToStaff();
		StaffPage.searchStaffDetails(use("StaffSearchCriteria"));
		HmPage.logoutFromAccountEntitiesPage();	
		
		assertAll();
		
	}
	
	@Test(dataProvider = "iterator")
	public void viewStaffDetails(Integer iteration, Object inputData) throws Exception {
		LandPage = new LandingPage();
		AuthPage = new AuthenticationPage();
		StaffPage = new EntitiesStaffPage();
		HmPage = new HomePage();
		
		LandPage.navigateToApplication().loginToAccount();
		AuthPage.EnterAuthenticationDetails(use("LoginUser"), use("Password"));
		HmPage.navigateToStaff();
		StaffPage.viewStaffDetailsandConfirm(use("StaffName"),use("StaffBranch"));
		StaffPage.editStaffDetails(use("StaffName"),use("StaffBranch"), use("NewStaffName"),use("NewStaffBranch"));
		HmPage.logoutFromAccountEntitiesPage();	
		
	}
	
}
