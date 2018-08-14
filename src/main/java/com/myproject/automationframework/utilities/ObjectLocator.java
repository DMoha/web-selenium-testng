package com.myproject.automationframework.utilities;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class ObjectLocator {

	public 	WebElement element;
	public String locatorType="";
	public String locatorValue="";
	public String locatorDescription;
	public static String xpath="";
	public static String id="";
	public static String Name="";
     By Locator;
	

  public  ObjectLocator ReplaceLocator(String ReplaceString)
	
	{
	  String Temp=locatorValue.replace("$", ReplaceString);
	  
	  Locator=GetObjectLocator(locatorType,Temp);
	
	  return this;
		
	}
 
      
    public By GetObjectLocator(String locatorType, String locatorValue)
    {
    	
	    switch(locatorType){
		case("linkText"):
			Locator= By.linkText(locatorValue);	
			break;
		case("partiallinkText"):
			Locator= By.partialLinkText(locatorValue);	
			break;
		case("ID"):
			Locator= By.id(locatorValue);
			break;
		case("CSS"):
			Locator = By.cssSelector(locatorValue);
			break;
		case("Name"):
			Locator = By.name(locatorValue);
			break;
		case("Xpath"):
			Locator = By.xpath(locatorValue);
			break;
		case("TagName"):
			Locator = By.tagName(locatorValue);	
			break;
		case ("ClassName"):	
			Locator = By.className(locatorValue);
			break;
		} 
	    return Locator;
    
    }
    
	public ObjectLocator(String LocatorType, String LocatorValue,String LocatorDescription)
	  
	{
	     this.locatorType=LocatorType;
	     this.locatorValue=LocatorValue;
		Locator=GetObjectLocator(LocatorType,LocatorValue);
		this.locatorDescription=LocatorDescription;

	}
	
}
