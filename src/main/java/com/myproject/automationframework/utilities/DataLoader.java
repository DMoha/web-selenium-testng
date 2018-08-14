package com.myproject.automationframework.utilities;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class DataLoader {

	private Actions actions;
	

	public DataLoader() {
		actions=new Actions();
	}

	//Get the file path
	private String getDataFilePath()
	{
		String appName=TestSetup.currentPackageName;
		String className=TestSetup.currentClassName;
		String methodName=TestSetup.currentMethodNameData;
		
		String rootDir=System.getProperty("user.dir");
		
		String dataFile=rootDir+"\\src\\main\\resources\\"+appName+"\\"+methodName+".xlsx";
		

		if(!checkFileExits(dataFile)) {
			dataFile=rootDir+"\\src\\main\\resources\\"+appName+"\\"+className+".xlsx";
		

		if(!checkFileExits(dataFile)) {
			
				System.out.println(dataFile);				
				dataFile="Data file does not exist";						
					}					
		}
		System.out.println(dataFile);
		return dataFile;
		
	}

	
	private boolean checkFileExits(String filePath)
	{
		File checkFile = new File(filePath);
		return checkFile.exists();
	}

	//Read the Test data sheet and return an object Array of Row and another Array of test data
	public Object [][] readExcel()
	{
		FileInputStream fsDataExcelStream = null;
		
		try {

			String dataFile= getDataFilePath();

			if(!dataFile.equals("Data file does not exist"))
			{			
				String dataSheetName=actions.getProperties("DataSheetName");
				if(dataSheetName==null ^ dataSheetName.isEmpty())
				{
					dataSheetName="Sheet1";
				}	
				

				fsDataExcelStream = new FileInputStream(dataFile);

				XSSFWorkbook wbTestData = new XSSFWorkbook(fsDataExcelStream);
				
				XSSFSheet shInputDataSheet = wbTestData.getSheet(dataSheetName);

				int totalNoOfCols = shInputDataSheet.getRow(0).getLastCellNum();
				int totalNoOfRows = shInputDataSheet.getPhysicalNumberOfRows();

				Object [][] dataRowsArray=null;
	
				boolean dataRowAdded=false;

				for (int rowCounter = 1; rowCounter < totalNoOfRows; rowCounter++) 
				{	
					Object [][] dataFieldsArray=new Object [totalNoOfCols][];
						for (int colCounter = 0; colCounter <= totalNoOfCols; colCounter++) 
						{
							if (null != shInputDataSheet.getRow(rowCounter).getCell(colCounter)) 
							{
								if(!shInputDataSheet.getRow(rowCounter).getCell(colCounter).toString().isEmpty()){
										Object [] dataArray=new Object[]{ shInputDataSheet.getRow(0).getCell(colCounter).toString().trim(),shInputDataSheet.getRow(rowCounter).getCell(colCounter).toString().trim()};
										dataFieldsArray[colCounter]=dataArray;
									}
								
							}
						}						
	
						dataRowsArray=copyandAddtoArray(dataRowsArray,2,new Object[]{rowCounter,dataFieldsArray});
						dataRowAdded=true;
					
				}

				shInputDataSheet=null;
				wbTestData=null;
				fsDataExcelStream=null;
				

				if(dataRowAdded)
				{
					return dataRowsArray;
				}
				else
				{
					return new Object [0][0];
				}
			}
			else
			{
				return new Object [0][0];
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	//Copies each new array into the source array and returns the source array
	private Object [][] copyandAddtoArray(Object[][] sourceArray,int colSize,Object[] targetArray )
	{
		Object [][] localDataArray;
		int currArrSize=0;
		if(sourceArray !=null)
		{
			currArrSize=sourceArray.length;	
			localDataArray=new Object[currArrSize+1][colSize];			
			java.lang.System.arraycopy(sourceArray,0,localDataArray,0,currArrSize);
		}
		else
		{
			localDataArray=new Object[currArrSize+1][colSize];
		}

		for(int colCounter=0;colCounter < colSize; colCounter++)
		{
			localDataArray[currArrSize][colCounter]=targetArray[colCounter];
		}					
		sourceArray=localDataArray;
		return sourceArray;
	}

	
}
