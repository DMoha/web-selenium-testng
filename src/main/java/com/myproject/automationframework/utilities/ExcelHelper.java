package com.myproject.automationframework.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.MultiMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {

	private int getHeadingCellCount(Row row, String heading) {
		int totalNoOfCols = row.getLastCellNum();
		for (int j = 0; j < totalNoOfCols; j++) {
			if (null != row.getCell(j)) {
				if (row.getCell(j).toString().trim().equalsIgnoreCase(heading)) {
					return j;
				}
			}

		}
		if (-1 == totalNoOfCols) {
			return 0;
		}
		return totalNoOfCols;
	}

	List<String> getHeadingList(Row row) throws IOException {
		List<String> subList = new ArrayList<String>();
		int totalNoOfCols = row.getLastCellNum();
		for (int j = 0; j < totalNoOfCols; j++) {
			if (null != row.getCell(j)) {
				subList.add(row.getCell(j).toString().trim());
			}

		}
		return subList;
	}

	boolean checkIfSheetExist(String sheetName, XSSFWorkbook wbEx) {
		for (int l = 0; l < wbEx.getNumberOfSheets(); l++) {
			XSSFSheet sheet = wbEx.getSheetAt(l);
			if (sheet.getSheetName().equalsIgnoreCase(sheetName)) {
				return true;
			}

		}
		return false;
	}

	public void createExcelAndWorkSheet(String fileName, String sheetName) {
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(fileName);
			XSSFWorkbook workbook = new XSSFWorkbook();

			workbook.createSheet(sheetName);

			workbook.write(fos);
			fos.flush();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
//Excel Based Reports
	@SuppressWarnings("unchecked")
	public void createEXCELReport(HashMap<String, MultiMap> testResultMultiMapDatas, String fileName,
			String sheetName) {

		FileInputStream fi = null;
		FileOutputStream outputStream = null;
		XSSFSheet sheet = null;

		try {

			fi = new FileInputStream(fileName);

			XSSFWorkbook workbook = new XSSFWorkbook(fi);
			if (!checkIfSheetExist(sheetName, workbook)) {
				sheet = workbook.createSheet(sheetName); 
			} else {
				sheet = workbook.getSheet(sheetName);
			}

			XSSFCellStyle style = workbook.createCellStyle();// Create style
			Font font = workbook.createFont();// Create font
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);// Make font bold
			style.setFont(font);// set it to bold
			style.setWrapText(true);

			Row row = null;
			Row rowHeader = null;
			Cell cellHeader = null;
			Cell cell = null;
			
			//Store the key set from Map
			Set<String> datas = testResultMultiMapDatas.keySet();
			int runCount = 0;
			int rowCount = 1;
			
			//Iterate through each key set
			for (String rowVal : datas) {
				MultiMap mapData = testResultMultiMapDatas.get(rowVal);

				for (Object key : mapData.keySet()) {
					List<HashMap<String, String>> list = (ArrayList<HashMap<String, String>>) mapData.get(key);
					if (runCount == 0) {
						rowHeader = sheet.createRow(0);
						
					}
					row = sheet.createRow(rowCount);

					int colCount = 0;
					int headerColCount = 0;
					for (HashMap<String, String> hashData : list) {
						for (HashMap.Entry<String, String> entry : hashData.entrySet()) {
							String cellval = "";
							String heading = "";

							heading = entry.getKey();
							cellval = entry.getValue();

							if (!getHeadingList(rowHeader).contains(heading)) {
								headerColCount = getHeadingCellCount(rowHeader, heading);
								cellHeader = rowHeader.createCell(headerColCount);
								cellHeader.setCellStyle(style);
								cellHeader.setCellValue(heading);

							}
							colCount = getHeadingCellCount(rowHeader, heading);
							if (null != heading && null != cellval) {
								cell = row.createCell(colCount);
								cell.setCellStyle(style);
								cell.setCellValue(cellval);
							}

						}
					}
					rowCount++;
					runCount++;

				}

			}
			outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != fi) {
					fi.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				if (null != outputStream) {
					outputStream.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
