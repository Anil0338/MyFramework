package HSF;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class EmailConfiguration {
	
	AppConfiguration properties = new AppConfiguration();
	GenericMethods objGM = new GenericMethods();
	public static Map<String, String> dictObj1 = new HashMap<String, String>();
	
	public void sendResultMail(int sPass, int sFail, String startTime, String endTime) throws IOException, InvalidFormatException
		{
	        String resultLink = System.getProperty("user.dir")  + properties.getPropValues("EmailResultPath").replace("./", "\\");
	        String envCompare = System.getProperty("user.dir")  + properties.getPropValues("compareRegressionData").replace("./", "\\");
	        String formCompare = System.getProperty("user.dir")  + properties.getPropValues("formsComparisonResult").replace("./", "\\");
	        
	        String scenarioFileName = System.getProperty("user.dir") +"\\Regression_Suite.xlsx";
			
			try {
		        FileInputStream inputStream = new FileInputStream(new File(scenarioFileName));
		        Workbook workbook = WorkbookFactory.create(inputStream);
		        Sheet sheet = workbook.getSheet("Email");
		        Cell cell17 = sheet.getRow(0).createCell(17);
		        cell17.setCellValue(resultLink);
		        Cell cell18 = sheet.getRow(0).createCell(18);
		        cell18.setCellValue(envCompare);
		        Cell cell19 = sheet.getRow(0).createCell(19);
		        cell19.setCellValue(sPass);
		        Cell cell20 = sheet.getRow(0).createCell(20);
		        cell20.setCellValue(sFail);
		        Cell cell21 = sheet.getRow(0).createCell(21);
		        cell21.setCellValue(startTime);
		        Cell cell22 = sheet.getRow(0).createCell(22);
		        cell22.setCellValue(endTime);
		        Cell cell23 = sheet.getRow(0).createCell(23);
		        cell23.setCellValue(formCompare);
		        
		        inputStream.close();
				FileOutputStream outputStream = new FileOutputStream(scenarioFileName);
		        workbook.write(outputStream);
		        workbook.close();
		        outputStream.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
         //Execute the Email Configuration .VBS File
			String script = System.getProperty("user.dir") +"\\Reports\\Email Configuration.vbs";
	  	    // search for real path:
	  	    String executable = "C:\\Windows\\System32\\wscript.exe"; 
	  	    String cmdArr [] = {executable, script};
	  	    Runtime.getRuntime ().exec (cmdArr);
	        
		}   
}



 






