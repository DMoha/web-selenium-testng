package com.myproject.automationframework.tests;

import org.testng.annotations.Test;

import com.myproject.automationframework.pageobjects.AuthenticationPage;
import com.myproject.automationframework.pageobjects.EntitiesBranchPage;
import com.myproject.automationframework.pageobjects.HomePage;
import com.myproject.automationframework.pageobjects.LandingPage;

import com.myproject.automationframework.utilities.TestSetup;

public class EntitiesBranchTest extends TestSetup{

	LandingPage LandPage;
	AuthenticationPage AuthPage;
	EntitiesBranchPage BranchPage;
	HomePage HmPage;
	
	@Test(dataProvider = "iterator")
	public void addNewBranch(Integer iteration, Object inputData) throws Exception {
		LandPage = new LandingPage();
		AuthPage = new AuthenticationPage();
		BranchPage = new EntitiesBranchPage();
		HmPage = new HomePage();
		
		LandPage.navigateToApplication().loginToAccount();
		AuthPage.EnterAuthenticationDetails(use("LoginUser"), use("Password"));
		HmPage.navigateToBranch();
		BranchPage.createNewBranchDetails(use("BranchName"), use("BranchCode")).cancelBranchCreation();
		BranchPage.createNewBranchDetails(use("BranchName"), use("BranchCode")).saveBranchDetails();
		HmPage.logoutFromAccountEntitiesPage();
		
	}
	
	@Test(dataProvider = "iterator")
	public void deleteBranchDetails(Integer iteration, Object inputData) throws Exception {
		LandPage = new LandingPage();
		AuthPage = new AuthenticationPage();
		BranchPage = new EntitiesBranchPage();
		HmPage = new HomePage();
		
		LandPage.navigateToApplication().loginToAccount();
		AuthPage.EnterAuthenticationDetails(use("LoginUser"), use("Password"));
		HmPage.navigateToBranch();
		BranchPage.deleteBranchDetails(use("BranchName"),use("BranchCode"));
		HmPage.logoutFromAccountEntitiesPage();
		
		assertAll();
		
	}
	
	@Test(dataProvider = "iterator")
	public void editBranchDetails(Integer iteration, Object inputData) throws Exception {
		LandPage = new LandingPage();
		AuthPage = new AuthenticationPage();
		BranchPage = new EntitiesBranchPage();
		HmPage = new HomePage();
		
		LandPage.navigateToApplication().loginToAccount();
		AuthPage.EnterAuthenticationDetails(use("LoginUser"), use("Password"));
		HmPage.navigateToBranch();
		BranchPage.editBranchDetails(use("BranchName"),use("BranchCode"), use("NewBranchName"),use("NewBranchCode"));
		HmPage.logoutFromAccountEntitiesPage();
		
		assertAll();
		
	}
	
	@Test(dataProvider = "iterator")
	public void searchBranchesAndConfirm(Integer iteration, Object inputData) throws Exception {
		LandPage = new LandingPage();
		AuthPage = new AuthenticationPage();
		BranchPage = new EntitiesBranchPage();
		HmPage = new HomePage();
		
		LandPage.navigateToApplication().loginToAccount();
		AuthPage.EnterAuthenticationDetails(use("LoginUser"), use("Password"));
		HmPage.navigateToBranch();
		BranchPage.searchBranchDetails(use("BranchSearchCriteria"));
		HmPage.logoutFromAccountEntitiesPage();	
		
		assertAll();
		
	}
	
	@Test(dataProvider = "iterator")
	public void viewBranchesAndConfirm(Integer iteration, Object inputData) throws Exception {
		LandPage = new LandingPage();
		AuthPage = new AuthenticationPage();
		BranchPage = new EntitiesBranchPage();
		HmPage = new HomePage();
		
		LandPage.navigateToApplication().loginToAccount();
		AuthPage.EnterAuthenticationDetails(use("LoginUser"), use("Password"));
		HmPage.navigateToBranch();
		BranchPage.viewBranchDetailsandConfirm(use("BranchName"),use("BranchCode"));
		HmPage.logoutFromAccountEntitiesPage();	
		
		assertAll();
		
	}
	
}
