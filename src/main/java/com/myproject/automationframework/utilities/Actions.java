package com.myproject.automationframework.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.myproject.automationframework.resources.DriverFactory;


public class Actions {

	WebDriver driver;
	public WebElement element1;
	public static SoftAssert SoftAssert;
	final HashMap<String, String> usersMap = new HashMap<String, String>();
	private static File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\" + "config.properties");

	// Constructor
	public Actions() {

		this.driver = DriverFactory.getDriver();
		if (driver != null) {
			this.driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
		}
	}

	public Actions OpenURl(String URL) {
		driver.navigate().to(URL);
		
		return this;
	}

	
	// Wait
	public Actions driverwait(long time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		return this;
	}

	// Wait
	public Actions Waitforpageload() {
		driverwait(Integer.parseInt(getProperties("Pageload_Wait")));
		return this;

	}

	
	// Click on any locator
	public Actions Click(ObjectLocator locator) {

		FindElement(locator).click();
		ActionLog("Clicked on " + locator.locatorDescription);
		return this;
	}


	// Log message to TestNG report
	public Actions ActionLog(String message) {
		Reporter.log(message + "||");
		System.out.println(message);
		return this;
	}

	// Find Element
	public WebElement FindElement(ObjectLocator locator) {

		WebElement retElement = null;

		WaitForWebElement(locator);
		try {

			retElement = driver.findElement(locator.Locator);
		} catch (NoSuchElementException ex) {
			// Handle exception if the element is not found
			ActionLog("NoSuchElementException: The Object " + locator.locatorDescription + " not found! "
					+ ex.getMessage());
			
		} catch (org.openqa.selenium.StaleElementReferenceException ex) {
			Waitforpageload();
			retElement = driver.findElement(locator.Locator);
		} catch (ElementNotVisibleException ex) {
			// Handle exception if the element is not found
			ActionLog("ElementNotVisibleException: The Object " + locator.locatorDescription + " not found! ");
		} catch (WebDriverException ex) {
			// Handle exception if the element is not found
			ActionLog("NoSuchElementException: The Object " + locator.locatorDescription + " not found! "
					+ ex.getMessage());
		}
		return retElement;
	}



	public void initializeSoftAssert() {
		SoftAssert = new SoftAssert();
	}

	public void AssertFail(String message) {

		ActionLog("[FAILURE] " + message);
		SoftAssert.fail(message);

	}

	public void HardAssertFail(String message) {

		ActionLog("[FAILURE] " + message);
		Assert.fail(message);

	}
	
	//Asserts all failed asserts
	public void assertAll() {
		Actions.SoftAssert.assertAll();
	}


	// Enter the text
	public Actions EnterText(ObjectLocator locator, String text) {
		driverwait(15);
		FindElement(locator).sendKeys(text);
		ActionLog("<EnterText on " + locator.locatorDescription + " as " + text + ">");
		return this;
	}

	
	public static Properties prop;

	public String getProperties(String key) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
	
			e.printStackTrace();
		} 
		// Loading File into inputStream
		prop = new Properties();
		try {
			prop.load(fis);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}

	
	// waits for webelement to be present and clickable on the screen
	public Actions WaitForWebElement(ObjectLocator locator) {
        try {
        	@SuppressWarnings("unused")
			WebElement element = (new WebDriverWait(driver, 30)
				.until(ExpectedConditions.elementToBeClickable(locator.Locator)));

        }catch (org.openqa.selenium.NoSuchElementException ex) {
			// Handle exception if the element is not found
			ActionLog("NoSuchElementException: The Object " + locator.locatorDescription + " not found! "
					+ ex.getMessage());
			throw ex;
		}
		return this;
	}

			
	public Actions SelectByText(ObjectLocator locator, String value) {

		try {
			Select cmbSelect = new Select(FindElement(locator));
			cmbSelect.selectByVisibleText(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			HardAssertFail(locator.locatorDescription + "does not have the provided value ");
		}
		
		return this;
	}

	public String getSelectedValueInDropDown(ObjectLocator locator) {

		Select cmbSelect = new Select(FindElement(locator));
		String selectedText = cmbSelect.getFirstSelectedOption().getText();
		return selectedText;

	}


	public String GetAttribute(ObjectLocator locator, String attributeName) {
		String value = null;

		value = FindElement(locator).getAttribute(attributeName);

		return value;
	}


	public Actions threadWait() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		return this;
	}

	//Setting the result of each row of data provider into a multi map
	public void setOutData(String key, String value) {
		HashMap<String, String> dataMap = new HashMap<String, String>();
		dataMap.put(key, value);
		TestSetup.currentRowMultiMapData.put(TestSetup.currentInputRow, dataMap);

	}
	
	//Verify if an element is present on screen and return a boolean
	public boolean verifyElementPresentinUI(ObjectLocator locator) {
		
		try {
			this.driver.findElement(locator.Locator).isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
	
			return false;
			}
		}
	
	//Verify if an attribute is present for a Web Element and return a boolean
	public boolean verifyAttribtuePresent(ObjectLocator locator, String attribute) {
			Boolean result = false;
			try {
				String value = this.driver.findElement(locator.Locator).getAttribute(attribute);
				if(value!=null) {
					result = true;
				}
			} catch (Exception e) {
		
				result = false;
				}
			return result;
			}

	public List<WebElement> FindElements(ObjectLocator locator) {
		return driver.findElements(locator.Locator);
		}
	
	public void clickViewButton(ObjectLocator Webtable, String Criteria1, String Criteria2, ObjectLocator View) {
		int counter=0;
		Integer rowcount = 1;
		List<WebElement> tableRows = FindElements(Webtable);
		
		if(tableRows.isEmpty()) {
			ActionLog("There are no records to View");
		}else {
		
		for (WebElement singleRow : tableRows) {
			List<WebElement> tableTDs = singleRow.findElements(By.tagName("td"));
			String value1 = tableTDs.get(1).getAttribute("innerText");
			String value2 = tableTDs.get(2).getAttribute("innerText");
			if(value1.equalsIgnoreCase(Criteria1) && value2.equalsIgnoreCase(Criteria2)) {
				Click(View.ReplaceLocator(rowcount.toString()));
				counter =1;
				break;
			}
			++rowcount;
		  }	
		}
		if(counter!=1) {
			HardAssertFail("Requested record is not present in the list");
		}
	}
	
	public void clickEditButton(ObjectLocator Webtable, String Criteria1, String Criteria2, ObjectLocator Edit) {
		int counter=0;
		Integer rowcount = 1;
		List<WebElement> tableRows = FindElements(Webtable);
		
		if(tableRows.isEmpty()) {
			ActionLog("There are no records to View");
		}else {
		
		for (WebElement singleRow : tableRows) {
			List<WebElement> tableTDs = singleRow.findElements(By.tagName("td"));
			String value1 = tableTDs.get(1).getAttribute("innerText");
			String value2 = tableTDs.get(2).getAttribute("innerText");
			if(value1.equalsIgnoreCase(Criteria1) && value2.equalsIgnoreCase(Criteria2)) {
				Click(Edit.ReplaceLocator(rowcount.toString()));
				counter =1;
				break;
			}
			++rowcount;
		  }	
		}
		if(counter!=1) {
			HardAssertFail("Requested record is not present in the list");
		}
	}

	public void clickDeleteButton(ObjectLocator Webtable, String Criteria1, String Criteria2, ObjectLocator Delete) {
		int counter=0;
		Integer rowcount = 1;
		List<WebElement> tableRows = FindElements(Webtable);
		
		if(tableRows.isEmpty()) {
			ActionLog("There are no records to View");
		}else {
		
		for (WebElement singleRow : tableRows) {
			List<WebElement> tableTDs = singleRow.findElements(By.tagName("td"));
			String value1 = tableTDs.get(1).getAttribute("innerText");
			String value2 = tableTDs.get(2).getAttribute("innerText");
			if(value1.equalsIgnoreCase(Criteria1) && value2.equalsIgnoreCase(Criteria2)) {
				Click(Delete.ReplaceLocator(rowcount.toString()));
				counter =1;
				break;
			}
			++rowcount;
		  }	
		}
		if(counter!=1) {
			HardAssertFail("Requested record is not present in the list");
		}
	}
	
	//Clear method for removing existing data in a text field
	public Actions Clear(ObjectLocator locator) {

		FindElement(locator).clear();
		return this;
	}
	
	//Verify an element in web table
	public Boolean verifyElementInWebTable(ObjectLocator locator, String Criteria) {
		List<WebElement> tableRows = FindElements(locator);
		
		if(tableRows.isEmpty()) {
			ActionLog("There are no records available");
		}
		
		for (WebElement singleRow : tableRows) {
			List<WebElement> tableTDs = singleRow.findElements(By.tagName("td"));
			String value1 = tableTDs.get(1).getAttribute("innerText");
			String value2 = tableTDs.get(2).getAttribute("innerText");
			if(value1.equalsIgnoreCase(Criteria)||value2.equalsIgnoreCase(Criteria)) {
				return true;
			}
			
		}	
		return false;
	}
}
	
	

