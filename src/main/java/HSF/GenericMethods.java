package HSF;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.relevantcodes.extentreports.ExtentTest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class GenericMethods
{     
	public static String s= System.getProperty("user.dir") ;
	public static String fileName = s+"\\DataRepository\\DataSheet-ESurety.xlsx";
	public boolean flag3;
	public static AppConfiguration properties = new AppConfiguration();
	static ResultClass objRC = new ResultClass();
	public static DriverClass driverClass = new DriverClass();
	/*	This method is to read the data from the Excel file
	 *  @param fileName - Path of excel file 
	 *  @param sheetName - Name of a sheet with respect to script
	 *  @param key - Pass the key name which returns the property value corresponding to object
	 *	@Modified by Dimpal - 12/12/2018
	 */
	
	//private static GenericMethods instanceOfSingletonClass =null;
	//private String regressionScenarioNames1;
/*
    public GenericMethods()
      {
         // System.out.println("Object created.");
      } 
  
    public static GenericMethods getInstanceOfSingletonClass() 
      {
         if(instanceOfSingletonClass==null){
                     instanceOfSingletonClass = new GenericMethods();
          } 
          return instanceOfSingletonClass;
      }
*/
	public static Map<String, List<String>> readObjectRepository( String fileName, String sheetName, Map<String, List<String>> dictObj ) throws IOException, InvalidFormatException
    {
        Workbook workbookObj = WorkbookFactory.create(new File(fileName));
    	Sheet sheet = workbookObj.getSheet(sheetName);
    	for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++)
    	{
        	 Row row = sheet.getRow(rowIndex);
        	  if (row != null) 
        	  {
        		Cell cell = row.getCell(1);
        	    Cell cell1 = row.getCell(3);
        	    Cell objProperty = row.getCell(6);
        	    if (cell != null) 
        	    {
        	      // Found column and there is value in the cell.
        	    	List<String> Value = new ArrayList<String>();
        	    	Value.add(cell.getStringCellValue());
        	    	Value.add(objProperty.getStringCellValue());
        	    	dictObj.put(cell1.getStringCellValue(),Value);
        	    }
        	}
        }
        workbookObj.close();
		return dictObj;
    }
	
	
	/** This method used to read the excel data and store it two dimensional Array
	 * 
	 * @param fileName - Where the file located in project
	 * @param sheetName - Sheet, where data needs to be extracted
	 * @return
	 */
	public String[][] getExcelData(String fileName, String sheetName) throws IOException 
	{
		String[][] arrayExcelData = null;
		Workbook workbook = null;
		try {
			if(fileName.contains(".xlsx")){
				FileInputStream inputStream = new FileInputStream(new File(fileName));
		        workbook = WorkbookFactory.create(inputStream);		
			}
			else{				
				FileInputStream inputStream = new FileInputStream(new File(fileName));
		        workbook = WorkbookFactory.create(new POIFSFileSystem(inputStream));		
			}
			Sheet sheet = workbook.getSheet(sheetName);

			// Total rows counts the top heading row
			int totalNoOfRows = sheet.getLastRowNum();
			Row row = sheet.getRow(0);
			int totalNoOfCols = row.getLastCellNum();

			arrayExcelData = new String[1][totalNoOfCols];

			try {
				for (int i= 1 ; i < totalNoOfRows; i++) {
					row = sheet.getRow(i);
					
					String compareTestDataScenario = row.getCell(1).toString().trim();
					if (appFunctions.Baseclass.getTestCaseName().equals(compareTestDataScenario) ) {
						for (int j=0; j < totalNoOfCols; j++) 
						{
							try{
								arrayExcelData[0][j] = row.getCell(j).toString().trim();
							}
							catch(Exception e){
								arrayExcelData[0][j] = "";
							}
						}
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		workbook.close();
		return arrayExcelData;
	}
	
	
	
	public static void DropFile(WebDriver driver, File filePath, WebElement target, int offsetX, int offsetY) throws InterruptedException {
	    if(!filePath.exists())
	    throw new WebDriverException("File not found: " + filePath.toString());

	    JavascriptExecutor jse = (JavascriptExecutor)driver;
	    WebDriverWait wait = new WebDriverWait(driver, 30);

	    String JS_DROP_FILE =
	        "var target = arguments[0]," +
	        "    offsetX = arguments[1]," +
	        "    offsetY = arguments[2]," +
	        "    document = target.ownerDocument || document," +
	        "    window = document.defaultView || window;" +
	        "" +
	        "var input = document.createElement('INPUT');" +
	        "input.type = 'file';" +
	        "input.style.display = 'none';" +
	        "input.onchange = function () {" +
	        "  var rect = target.getBoundingClientRect()," +
	        "      x = rect.left + (offsetX || (rect.width >> 1))," +
	        "      y = rect.top + (offsetY || (rect.height >> 1))," +
	        "      dataTransfer = { files: this.files };" +
	        "" +
	        "  ['dragenter', 'dragover', 'drop'].forEach(function (name) {" +
	        "    var evt = document.createEvent('MouseEvent');" +
	        "    evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);" +
	        "    evt.dataTransfer = dataTransfer;" +
	        "    target.dispatchEvent(evt);" +
	        "  });" +
	        "" +
	        "  setTimeout(function () { document.body.removeChild(input); }, 25);" +
	        "};" +
	        "document.body.appendChild(input);" +
	        "return input;";

	    WebElement input =  (WebElement)jse.executeScript(JS_DROP_FILE, target, offsetX, offsetY);
	    input.sendKeys(filePath.getAbsoluteFile().toString());
	    wait.until(ExpectedConditions.stalenessOf(input));
	    Thread.sleep(2000);
	    
	}
	

	
	public Map<String, String> readEmailID(String fileName1, String sheetName1, Map<String, String> dictObj1) throws IOException, InvalidFormatException
	{
		  Workbook workbookObj1 = WorkbookFactory.create(new File(fileName1));
	      Sheet sheet1 = workbookObj1.getSheet(sheetName1);
	      for (int emailIndex = 0; emailIndex <= sheet1.getLastRowNum(); emailIndex++) {
	        	 Row row = sheet1.getRow(emailIndex);
	        	  if (row != null) {
	        	    Cell cell = row.getCell(0);
	        	    Cell objProperty = row.getCell(1);
	        	    if (cell != null) {
	        	      // Found column and there is value in the cell.
	        	    	dictObj1.put(cell.getStringCellValue(), objProperty.getStringCellValue());
	        	    }
	        	}
	        }
	        workbookObj1.close();
			return dictObj1;
	}
	
	public ArrayList<String> datareading(ArrayList<String> list2 , String sheetName) throws IOException
	{
		//org.apache.poi.ss.usermodel.Workbook tempWB = null;
		Workbook workbookObj = null;
		InputStream inp = new FileInputStream(fileName);
		BufferedInputStream bufferStream = new BufferedInputStream(inp);
		ZipSecureFile.setMinInflateRatio(0);
		try {
			if(fileName.contains(".xlsx")){
				workbookObj = WorkbookFactory.create(bufferStream);
			}
			else{				
				workbookObj = WorkbookFactory.create(new POIFSFileSystem(bufferStream));
				//tempWB = (org.apache.poi.ss.usermodel.Workbook) new HSSFWorkbook(new POIFSFileSystem(inp));					
			}
			//org.apache.poi.ss.usermodel.Sheet sheet = tempWB.getSheet(sheetName);
			Sheet sheet = workbookObj.getSheet(sheetName);
			list2.add(0, sheetName);
			Row row = sheet.getRow(0);
			int totalNoOfCols = row.getLastCellNum();
			for( int i=1; i<=totalNoOfCols-1;i++)
			{
				Cell value =sheet.getRow(0).getCell(i);
				String values= value.toString();
				list2.add(i, values);
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		inp.close();
		workbookObj.close();
		return list2;
	}
	
	/** This method used to read the excel data and in order to pick the Program ID
	 * @param fileName - Where the file located in project
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	*/
	public static String readProgramID() throws EncryptedDocumentException, InvalidFormatException, IOException
    {
		String fileReadProgramID = s + "\\Regression_Suite.xlsx";
		String programID = "";
        Workbook workbookObj = WorkbookFactory.create(new File(fileReadProgramID));
    	Sheet sheet = workbookObj.getSheet("RegressionSuite");
    	for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++)
    	{
        	Row row = sheet.getRow(rowIndex);
        	Cell scenarioName = row.getCell(0);
        	if (appFunctions.Baseclass.getTestCaseName().contains(scenarioName.getStringCellValue())) {
        		programID = row.getCell(1).getStringCellValue();
        		break;
        	}
        }
    	workbookObj.close();
    	return programID;
      }
	
	
	public ArrayList<String> dataReadingEvnCompare(ArrayList<String> list2 , String envFileName, String sheetName) throws IOException
	{
		Workbook workbook = null;
		try {
			if(fileName.contains(".xlsx")){
				FileInputStream inputStream = new FileInputStream(new File(envFileName));
		        workbook = WorkbookFactory.create(inputStream);
			}
			else{				
				FileInputStream inputStream = new FileInputStream(new File(envFileName));
		        workbook = WorkbookFactory.create(inputStream);			
			}
			Sheet sheet = workbook.getSheet(sheetName);
			list2.add(0, sheetName);
			Row row = sheet.getRow(0);
			int totalNoOfCols = row.getLastCellNum();
			for( int i=1; i<=totalNoOfCols-1;i++)
			{
				Cell value =sheet.getRow(0).getCell(i);
				String values= value.toString();
				list2.add(i, values);
			}
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		workbook.close();
		return list2;
	}
	
	
	public String dataValuereading (String sheetName, int tcRow ,int columncount ) throws IOException
	{ 	
		Cell data = null;
		Workbook workbook = null;
		try {
			FileInputStream inputStream = new FileInputStream(new File(fileName));
			BufferedInputStream bufferStream = new BufferedInputStream(inputStream);
	        workbook = WorkbookFactory.create(bufferStream);
	        Sheet sheet = workbook.getSheet(sheetName);
			data =sheet.getRow(tcRow).getCell(columncount);
			bufferStream.close();
			inputStream.close();
			workbook.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		return data.toString();
	}
	
	@Test
	public String testCaseValueReading (String sheetName) throws NumberFormatException
    {      
           int k=0;
           String rowNumber = "";
           String columnvalue="";
           int pointer=0;
           try 
           {
           FileInputStream inputStream = new FileInputStream(new File(fileName));
           Workbook workbook = WorkbookFactory.create(inputStream);
           Sheet sheet = workbook.getSheet(sheetName);
           ArrayList<String> columndata =new ArrayList<>();
           int row =sheet.getLastRowNum();
           for(int i=0 ;i<=row; i++)
           {      
                  Cell value= sheet.getRow(i).getCell(1);
                  columnvalue=value.toString();
                  columndata.add(i , columnvalue);
           }
           int arraylistcount = columndata.size();
           
           String tcName = appFunctions.Baseclass.getTestCaseName();
           while(pointer<arraylistcount)
           { 
	          for(int i=k;i<arraylistcount;i++){          
	        	  if( tcName.contains(columndata.get(i)) && i>pointer){
	                  rowNumber = rowNumber + k + ",";
	                  break;
	        	  }
	        	  k =i+1;
	          }   
	          pointer = k;
           }
           workbook.close();
       }catch(Exception e) {
              e.printStackTrace();
       }
       return rowNumber;
    }

	
	public String testCaseRowNumberEnvCompare (String fileEnvName, String sheetName, String testCaseName) throws NumberFormatException, IOException
    {      
		  int k=0;
          String rowNumber = "";
          String columnvalue="";
          int pointer=0;
          Workbook workbook = null;
          try 
          {
          FileInputStream inputStream = new FileInputStream(new File(fileName));
          workbook = WorkbookFactory.create(inputStream);
          Sheet sheet = workbook.getSheet(sheetName);
          ArrayList<String> columndata =new ArrayList<>();
          
          int row =sheet.getLastRowNum();
          for(int i=0 ;i<=row; i++)
          {      
                 Cell value= sheet.getRow(i).getCell(1);
                 columnvalue=value.toString();
                 columndata.add(i , columnvalue);
          }
          int arraylistcount = columndata.size();
          
          String tcName = testCaseName;
          while(pointer<arraylistcount)
          { 
	          for(int i=k;i<arraylistcount;i++){          
	        	  if( tcName.contains(columndata.get(i)) && i>pointer){
	                  rowNumber = rowNumber + k + ",";
	                  break;
	        	  }
	        	  k =i+1;
	          }   
     pointer = k;
          }
          workbook.close();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
      return rowNumber;
   }

	public String getestScenarioNames () throws IOException  {
		String regressionScenarioNames1 = "";
		String scenarioFileName = s+"\\Regression_Suite.xlsx";
		org.apache.poi.ss.usermodel.Workbook tempRS = null;
		try {
			tempRS = new XSSFWorkbook(scenarioFileName);
			org.apache.poi.ss.usermodel.Sheet sheet = tempRS.getSheet("Email");
			Row row = sheet.getRow(0);
			regressionScenarioNames1 = String.valueOf(row.getCell(14));
			tempRS.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return regressionScenarioNames1;
	}

	
	public void putCompareFilePathToExcel(String compareFilePath) throws EncryptedDocumentException, InvalidFormatException  {
		
		String scenarioFileName = System.getProperty("user.dir") +"\\Regression_Suite.xlsx";
		try {
	        FileInputStream inputStream = new FileInputStream(new File(scenarioFileName));
	        Workbook workbook = WorkbookFactory.create(inputStream);
	        Sheet sheet = workbook.getSheet("Email");
	        Cell cell = sheet.getRow(0).createCell(15);
	        cell.setCellValue(compareFilePath);
			
	        inputStream.close();
			FileOutputStream outputStream = new FileOutputStream(scenarioFileName);
	        workbook.write(outputStream);
	        workbook.close();
	        outputStream.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public long performanceCheck(WebDriver driver) throws ParseException
    {
           DateFormat startTimeformat = new SimpleDateFormat(" HH:mm:ss");
           Date time = new Date();
           String startTime= startTimeformat.format(time);
           System.out.println("Start time is " + startTime);
            try {
            		WebDriverWait wait = new WebDriverWait(driver, 120);
            		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//nav[@id='headerNav']/div//ul[@id='nav-desktop']/li[@id='ScribeTab']/a")));
           }
           catch(Exception e)
           {
              e.printStackTrace();
           }
           
          DateFormat endTimeformat = new SimpleDateFormat(" HH:mm:ss");
          Date time3 = new Date();
          String endtime= endTimeformat.format(time3);
          System.out.println("End time is " + endtime);
           
          Date date5 = startTimeformat.parse(startTime);
          Date date6 = endTimeformat.parse(endtime);
          
          long difference = date6.getTime() - date5.getTime();
          System.out.println(difference/1000);
          long l =difference/1000;
          return l;
    }

	public long performanceCheckTransaction(WebDriver driver, String objectProperty, long timeSeconds) throws ParseException
    {
           DateFormat startTimeformat = new SimpleDateFormat(" HH:mm:ss");
           Date time = new Date();
           String startTime= startTimeformat.format(time);
           System.out.println("Start time is " + startTime);
            try {
            		appFunctions.Baseclass.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(objectProperty)));
           }
           catch(Exception e)
           {
        	  System.out.println(e.getMessage());
           }
           
           DateFormat endTimeformat = new SimpleDateFormat(" HH:mm:ss");
           Date time3 = new Date();
           String endtime= endTimeformat.format(time3);
           System.out.println("End time is " +endtime);
           
          Date date5 = startTimeformat.parse(startTime);
          Date date6 = endTimeformat.parse(endtime);
          
          long difference = date6.getTime() - date5.getTime();
          System.out.println(difference/1000);
          long l =(difference/1000) + timeSeconds ;
          return l ;
    }
	
	public boolean setCellData(int tcRow , int columncount, String data){
		
		try {
			String excelFile = properties.getPropValues("envCompare");
			
			FileInputStream inputStream = new FileInputStream(new File(excelFile));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet("");
            
            sheet.createRow(tcRow).createCell(columncount).setCellValue(data);
			
			FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
  	
	
	public void makeCopyOfComparisonTemplate(String templateName, String copyFileName) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		FileInputStream inputStream = new FileInputStream(new File(templateName));
        Workbook workbook = WorkbookFactory.create(inputStream);
        inputStream.close();
        
		FileOutputStream outputStream = new FileOutputStream(copyFileName);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
	}
	
	
	public String getLatestFilefromDir(String dirPath){
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }

	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile.toString();
	}
	
	public void downloadLettersFromHSF(WebDriver driver, String transactionName, ExtentTest logger) throws Exception
	{
		Thread.sleep(1000);
		String transaction = transactionName;
		//Download the Letter from DDM
		List<WebElement> iFrameSize = driver.findElements(By.cssSelector("#div_2 > div:nth-child(6) > iframe"));
		if (iFrameSize.size() != 0) {
			WebElement iframe = driver.findElement(By.cssSelector("#div_2 > div:nth-child(6) > iframe"));
			driver.switchTo().frame(iframe);
			String downloadLetter = "//tbody[@id='resultRows']/tr[1]/td[3]/a";
			WebElement objDownload = driver.findElement(By.xpath(downloadLetter));
			JavascriptExecutor jsu = (JavascriptExecutor)driver;
			jsu.executeScript("arguments[0].click();", objDownload);
		}else {
			List<WebElement> iFrameSize1 = driver.findElements(By.cssSelector("#div_2 > div:nth-child(8) > iframe"));
			if (iFrameSize1.size() != 0) {
				WebElement iframe = driver.findElement(By.cssSelector("#div_2 > div:nth-child(8) > iframe"));
				driver.switchTo().frame(iframe);
				String downloadLetter = "//tbody[@id='resultRows']/tr[1]/td[3]/a";
				WebElement objDownload = driver.findElement(By.xpath(downloadLetter));
				JavascriptExecutor jsu = (JavascriptExecutor)driver;
				jsu.executeScript("arguments[0].click();", objDownload);
			}else {
				List<WebElement> iFrameSize2 = driver.findElements(By.cssSelector("#div_2 > div:nth-child(7) > iframe"));
				if (iFrameSize2.size() != 0) {
					WebElement iframe = driver.findElement(By.cssSelector("#div_2 > div:nth-child(7) > iframe"));
					driver.switchTo().frame(iframe);
					String downloadLetter = "//tbody[@id='resultRows']/tr[1]/td[3]/a";
					WebElement objDownload = driver.findElement(By.xpath(downloadLetter));
					JavascriptExecutor jsu = (JavascriptExecutor)driver;
					jsu.executeScript("arguments[0].click();", objDownload);
				}
			}
		}
			Thread.sleep(5000);
		
		//Get the lastest file from the download folder and move in to project folder
		String dynamicPath = "C:\\Users\\" + System.getProperty("user.name") + "\\Downloads";
		String inputFile = getLatestFilefromDir(dynamicPath);
		String outputFile;
		if (inputFile.contains(".docx")) {
			outputFile = properties.getPropValues("subFolderName") + "\\"  + transaction + "_" + appFunctions.Baseclass.getTestCaseName() + ".docx";
		}else {
			outputFile = properties.getPropValues("subFolderName") + "\\"  + transaction + "_" + appFunctions.Baseclass.getTestCaseName() + ".pdf";
		}
		Files.move (Paths.get(inputFile),  Paths.get(outputFile));
       	Thread.sleep(2000);
       	objRC.logInfo(logger, "Letter present in this path: " + System.getProperty("user.dir") + "\\" + outputFile.replace("./", ""));
     
       	driver.switchTo().parentFrame();
	}
	
	public void convertPdfToWordDocument(String transactionName) throws Exception
	{
		//Get the 2 files from result folder
       	File dir = new File(properties.getPropValues("subFolderName"));
        File[] dir_contents = dir.listFiles();
        int counter = 0;
        String temp1 = "";
        for(int i = 0; i<dir_contents.length;i++) {
        	int indexNumber = dir_contents[i].getName().indexOf(transactionName);
            if(indexNumber >= 0) {
            	counter = counter + 1;
            	temp1 = temp1 + properties.getPropValues("subFolderName") + "\\" + dir_contents[i].getName() + "#";
            } 
        }
        if (temp1.contains(".pdf")) {
	        //Write these names in to Spreadsheet
	        putCompareFilePathToExcel(temp1);
	        Thread.sleep(3000);
	        //Call VBS file to convert PDF to Word Document
	        String script = s+"\\Reports\\PDF To Word Conversion.vbs";
	  	    // search for real path:
	  	    String executable = "C:\\Windows\\System32\\wscript.exe"; 
	  	    String cmdArr [] = {executable, script};
	  	    Runtime.getRuntime ().exec (cmdArr);
	  	    Thread.sleep(5000); 
        }
	}
	
	//Get Status of Document Comparison
	public String getDocumentComparisonStatus () throws IOException  {
		//Kill the Process 
		//Call VBS script for Killing the Process
        String script = s+"\\Reports\\Kill Process.vbs";
        // search for real path:
        String executable = "C:\\Windows\\System32\\wscript.exe"; 
        String cmdArr [] = {executable, script};
        Runtime.getRuntime ().exec (cmdArr);
        
		String regressionScenarioNames1 = "";
		String scenarioFileName = s+"\\Regression_Suite.xlsx";
		org.apache.poi.ss.usermodel.Workbook tempRS;
		try {
			tempRS = new XSSFWorkbook(scenarioFileName);
			org.apache.poi.ss.usermodel.Sheet sheet = tempRS.getSheet("Email");
			Row row = sheet.getRow(0);
			regressionScenarioNames1 = String.valueOf(row.getCell(16));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return regressionScenarioNames1;
	}

		//get data count
		public String gridDataCountValidation(String fileName, String colName, ExtentTest logger) throws IOException
		{  
			
			String transTypeValue= "";
			 try {
				   Thread.sleep(2000);
		           File inputfile = new File(fileName);
		           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		           DocumentBuilder docbuilder = dbFactory.newDocumentBuilder();
		           org.w3c.dom.Document doc = docbuilder.parse(inputfile);
		           doc.getDocumentElement().normalize();
		           NodeList monthlyPremium = doc.getElementsByTagName("P241Transaction");
		                for (int tmp = 0; tmp < monthlyPremium.getLength(); tmp++) {
			                Node nNode = monthlyPremium.item(tmp);
			                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			                    Element eElement = (Element) nNode;
			                    NodeList nl = eElement.getElementsByTagName(colName);
			                    if (nl.getLength() > 0) {
			                    	String transTypeValues = nl.item(0).getTextContent();
			                    	transTypeValue =transTypeValue+transTypeValues+",";
			                    }
			                    else {
			                    	transTypeValue =transTypeValue+null+",";
			                    }
			                }
		                } 
					}
			 catch (Exception e) {
		            e.getMessage();
		            e.printStackTrace();
		            objRC.logfail(logger, "Error Message: " + e.getMessage());
		        }
			 return transTypeValue ;
		}
	
	//Copy data from Excel for Import the data into grid
		public void copyDataFromExcel(String fileName, String sheetName, WebDriver driver, ExtentTest logger) throws InterruptedException, IOException, InvalidFormatException 
		 {
			 String temp = "";
			 Thread.sleep(2000);
			 //Read Data from Excel which needs to be pasted into Grid
			 FileInputStream fis= new FileInputStream(fileName);
			 Workbook wb = WorkbookFactory.create(fis);
			 Sheet sh = wb.getSheet(sheetName);
			 //Pick the row numbers with respect to the scenarios
			 for(int i=0; i <= sh.getLastRowNum(); i++) {
				 for(int j=0; j <= sh.getRow(i).getLastCellNum()-1 ; j++) {
					 Row row=sh.getRow(i);
					 String cellData = row.getCell(j,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
					 temp = temp +  cellData + "\t";
				 }
				 temp = temp + "\n" ;
			}
			System.out.println(temp);
			JavascriptExecutor jsu = (JavascriptExecutor)driver;
			String myString = temp;
			StringSelection stringSelection = new StringSelection(myString);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
			String pasteText = "//input[@id='txtPaste']";
			WebElement objPasteText = driver.findElement(By.xpath(pasteText));
			jsu.executeScript("arguments[0].click();", objPasteText);
			objPasteText.sendKeys(Keys.CONTROL,"v");
			Thread.sleep(2000);
			
			//Uncheck the first row for Column Header
			Thread.sleep(2000);
			String uncheckRow = "//tbody/tr[1]/td[1]/label";
			WebElement objUncheckRow = driver.findElement(By.xpath(uncheckRow));
			jsu.executeScript("arguments[0].click();", objUncheckRow);
			Thread.sleep(3000);
			ResultClass resultClass = new ResultClass();
			resultClass.logInfo(logger,"Data Imported to Grid by Import Excel: " + sheetName, driver);
			
			String clickReplaceButton = "//div//input[@id='replace']/parent::div//following-sibling::label[text()='Replace']";
			WebElement objclickReplaceButton = driver.findElement(By.xpath(clickReplaceButton));
			jsu.executeScript("arguments[0].click();", objclickReplaceButton);
			
			String clickImportButton = "//button[text()='Import']";
			WebElement objClickImportButton = driver.findElement(By.xpath(clickImportButton));
			jsu.executeScript("arguments[0].click();", objClickImportButton);
			
		 }
		
		public void copyDataFromRaterSheet(String fileName, String sheetName, WebDriver driver, ExtentTest logger) throws InterruptedException, IOException, InvalidFormatException 
		 {
			 String temp = "";
			 String cellData="";
			 //Read Data from Excel which needs to be pasted into Grid
			 FileInputStream fis= new FileInputStream(fileName);
			 Workbook wb = WorkbookFactory.create(fis);
			 Sheet sh = wb.getSheet(sheetName);
			 for(int i=0; i <= sh.getLastRowNum(); i++) {
				 for(int j=0; j <= sh.getRow(i).getLastCellNum()-1 ; j++) {
					 Row row=sh.getRow(i);
					 Cell cell = row.getCell(j);
					 if(cell.getCellType() == CellType.STRING) {
						 cellData=cell.getStringCellValue();
					 }
					 else if(cell.getCellType() == CellType.NUMERIC) {
						 DataFormatter fmt = new DataFormatter();
					     cellData=fmt.formatCellValue(cell); 
					 }
					 else if(cell.getCellType() == CellType.BLANK) {
						 cellData=row.getCell(j,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
					 }
	                  
					 temp = temp +  cellData + "\t";
				 }
				 temp = temp + "\n" ;
			}
			System.out.println(temp);
			JavascriptExecutor jsu = (JavascriptExecutor)driver;
			String myString = temp;
			StringSelection stringSelection = new StringSelection(myString);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
			String pasteText = "//input[@id='txtPaste']";
			WebElement objPasteText = driver.findElement(By.xpath(pasteText));
			jsu.executeScript("arguments[0].click();", objPasteText);
			objPasteText.sendKeys(Keys.CONTROL,"v");
			Thread.sleep(4000);
			//Uncheck the first row for Column Header
			String uncheckRow = "//tbody/tr[1]/td[1]/label";
			WebElement objUncheckRow = driver.findElement(By.xpath(uncheckRow));
			jsu.executeScript("arguments[0].click();", objUncheckRow);
			Thread.sleep(2000);
			ResultClass resultClass = new ResultClass();
			resultClass.logInfo(logger,"Data Imported to Grid by Import Excel: " + sheetName, driver);
			
			String clickImportButton = "//button[text()='Import']";
			WebElement objClickImportButton = driver.findElement(By.xpath(clickImportButton));
			jsu.executeScript("arguments[0].click();", objClickImportButton);
			
		 }
}	
