package com.myproject.automationframework.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.myproject.automationframework.resources.DriverFactory;
import com.myproject.automationframework.utilities.ExcelHelper;


public class TestSetup {

	Actions actions = new Actions();
	public static HashMap<String, String> TestDataCollection = null;
	public static String currentMethodName = "";
	public static String currentMethodNameData = "";
	public static String currentClassName = "";
	public static String currentPackagePath = "";
	public static String currentPackageName = "";
	private static int invokeCounter = 0;
	private static int invokeCounterData = 0;
	public HSSFWorkbook workbook;
	public HSSFSheet sheet;
	DataLoader loadData = new DataLoader();

	public static MultiMap currentRowMultiMapData;
	public static HashMap<String, MultiMap> testResultMultiMapDatas;
	public static int totalInputRows = 0;
	public static int currentInputRow = 0;
	public static int totalTestMethodeCount = 0;
	public static String currentReportFileName = "";
	ExcelHelper excelHelper = new ExcelHelper();

	@BeforeSuite(alwaysRun = true)
	public void setupApplication() {
	try {
	
		String line;
		Process p = Runtime.getRuntime().exec("cmd /c StartApp.bat", null, new File(System.getProperty("user.dir") + "\\staff-master\\staff-master"));
		//Process p = Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\staff-master\\staff-master\\StartApp.bat");
		BufferedReader input =
		        new BufferedReader
		          (new InputStreamReader(p.getInputStream()));
		while ((line = input.readLine()) != null && !(line.contains("127.0.0.1:8080"))) {
	        System.out.println(line);
	      }
		System.out.println("Stream Closed");
	      input.close();
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	@BeforeTest
	public void setup(ITestContext testName) {
		try {
			System.out.println("In Before Test");
			ITestNGMethod[] methodArr = testName.getAllTestMethods();
			currentMethodName = methodArr[invokeCounter].getMethodName();

			if (currentPackageName.equals("")) {
				currentPackagePath = methodArr[invokeCounter].getRealClass().getName();
				currentPackageName = currentPackagePath.split("\\.")[1].toString();
				currentClassName = currentPackagePath.split("\\.")[4].toString();
				currentPackagePath = currentPackagePath.substring(0,
						currentPackagePath.lastIndexOf(currentClassName) - 1);

			}
			++invokeCounter;
			TestDataCollection = new HashMap<String, String>();

		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	@BeforeMethod
	public void startIteration(Method method, Object[] testArgs) {
		System.out.println("In Before Method");
		actions.initializeSoftAssert();
		DriverFactory.initializeDriver(actions.getProperties("BrowserName"));
		loadTestData(testArgs[1]);
		currentRowMultiMapData = new MultiValueMap();
		currentInputRow++;
		setOutData("MethodName", method.getName());
		setOutData("Test Scenario", testArgs[0] + "");
		
		

	}

	@AfterMethod(alwaysRun = true)
	public void endIteration(ITestResult currentResult, Object[] testArgs) {
		System.out.println("In After Method");
		boolean isLastIteration = false;
		
		if (currentInputRow == totalInputRows) {
			totalTestMethodeCount++;
			isLastIteration = true;
			++invokeCounterData;
		}

		setOutData("Result", getCurrentTestStatus(currentResult));
		setOutData("ErrorMessage", getCurrentErrorMessage(currentResult));

		
		DriverFactory.closeDriver();

		if (null != currentRowMultiMapData && currentRowMultiMapData.size() > 0) {
			testResultMultiMapDatas.put(testArgs[0] + "", currentRowMultiMapData);
		}
		
		if (isLastIteration) {
			setReport(currentResult, testArgs, totalTestMethodeCount);
		}

	}

	@DataProvider(name = "iterator")
	public Object[][] createData(ITestContext testName) {
		System.out.println("Data Provider");
		ITestNGMethod[] methodArr = testName.getAllTestMethods();
		currentMethodNameData = methodArr[invokeCounterData].getMethodName();
		
		testResultMultiMapDatas = new LinkedHashMap<String, MultiMap>();
		totalInputRows = 0;
		currentInputRow = 0;
		Object[][] data = loadData.readExcel();
		totalInputRows = data.length;
		return data;
	}

	@AfterTest(alwaysRun = true)
	public void collectResults() {
		System.out.println("In After Test");
		TestDataCollection = null;


	}


	public void loadTestData(Object testData) {
		Object[][] testDataArray = (Object[][]) testData;
		for (Object[] dataRow : testDataArray) {
			if (null != dataRow) {
	//To handle blank values for Fields in Datasheet
					if (dataRow[1].toString().trim().equals("<blank>")) {
						TestDataCollection.put(dataRow[0].toString(), "");
					} else {
						TestDataCollection.put(dataRow[0].toString(), dataRow[1].toString());
					}
				}
			}

		}
	
	//To get the test data based on the identifier (key value pair)
	public String use(String identifier) {
		if (TestDataCollection.containsKey(identifier)) {
			return TestDataCollection.get(identifier);
		} else {
			return "<Data Not Found>";
		}
	}


	public void assertAll() {
		actions.assertAll();
	}

	public void AssertFail(String message) {
		actions.AssertFail(message);
	}

	public void setOutData(String key, String value) {
		actions.setOutData(key, value);
	}

	//Creating Excel Reports after the run
	public void setReport(ITestResult result, Object[] testArgs, int totalTestMethodeCount) {

		if (null == testResultMultiMapDatas || testResultMultiMapDatas.size() == 0) {
			return;
		}

		if (actions.getProperties("EXCEL_REPORT_TEST_BASED").equalsIgnoreCase("True")) {

			String sheetName = "";
			String fileName = "";
			// sheetName = currentClassName;
			sheetName = "sheet" + totalTestMethodeCount;

			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
			fileName = System.getProperty("user.dir") + "\\Results\\" + currentClassName + "_"
					+ result.getMethod().getMethodName() + formatter.format(new Date()) + ".xlsx";

			excelHelper.createExcelAndWorkSheet(fileName, sheetName);
			excelHelper.createEXCELReport(testResultMultiMapDatas, fileName, sheetName);

		}

	}

	public String getCurrentTestStatus(ITestResult result) {

		if (result.getStatus() == ITestResult.SUCCESS) {
			return "Passed";
		}

		else if (result.getStatus() == ITestResult.FAILURE) {
			return "Failed";

		}

		else if (result.getStatus() == ITestResult.SKIP) {
			return "Skipped";

		}
		return "";
	}

	private String getCurrentErrorMessage(ITestResult currentResult) {
		String originalMessage = "";
		Throwable throwable = currentResult.getThrowable();
		if (null != throwable && null != throwable.getMessage()) {
			originalMessage = throwable.getMessage().replaceAll("\n", ",");
		}
		System.out.println("originalMessage" + originalMessage);
		if (originalMessage.length() > 100) {
			originalMessage = originalMessage.substring(0, 100);
			originalMessage = originalMessage + ".....";
		}
		return originalMessage;
	}

}
