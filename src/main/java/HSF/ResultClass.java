package HSF;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ResultClass extends appFunctions.Baseclass {
	AppConfiguration properties = new AppConfiguration();
	/**	 Prints the custom message in the Extend reports output - Pass 
		 * @param logger - ExtendTest Logger
		 * @param msg - Message
		 */
		public void logPass(ExtentTest logger,String msg)
		{
			getLogger().log(LogStatus.PASS, msg);
		}
		
		/**
		 * Prints the custom message in the Extend reports output with screenshot - Pass
		 * @param logger- Extend logger
		 * @param msg  - Message
		 * @param driver - WebDriver
		 */
		public void logPass(ExtentTest logger,String msg,WebDriver driver)
		{
			logPass(getLogger(), msg);
			String screeshotPath = captureScreenshot(driver);
			String image = logger.addScreenCapture(screeshotPath);
			getLogger().log(LogStatus.PASS, msg,image);
		}

		/**	 Prints the custom message in the Extend reports output - Info 
		 * @param logger - ExtendTest Logger
		 * @param msg - Message
		 */
		public void logInfo(ExtentTest logger,String msg)
		{
			getLogger().log(LogStatus.INFO, msg);
		}

		/**
		 * Prints the custom message in the Extend reports output with screenshot - Info
		 * @param logger- Extend logger
		 * @param msg  - Message
		 * @param driver - WebDriver
		 */
		public void logInfo(ExtentTest logger,String msg,WebDriver driver)
		{
			logInfo(getLogger(), msg);
			String screeshotPath = captureScreenshot(driver);
			String image = getLogger().addScreenCapture(screeshotPath);
			getLogger().log(LogStatus.INFO, msg,image);
		}

		/**
		 * Prints the custom message in the Extend reports output with screenshot - Fail
		 * @param logger- Extend logger
		 * @param msg  - Message
		 * @param driver - WebDriver
		 */
		public void logFail(ExtentTest logger,String msg, WebDriver driver)
		{
			logfail(getLogger(), msg);
			String screeshotPath = captureScreenshot(driver);
			String image = getLogger().addScreenCapture(screeshotPath);		
			getLogger().log(LogStatus.FAIL, msg,image);		
		}		

		/**	 Prints the custom message in the Extend reports output - Fail 
		 * @param logger - ExtendTest Logger
		 * @param msg - Message
		 */
		public void logfail(ExtentTest logger,String msg)
		{
			getLogger().log(LogStatus.FAIL, msg);
		}
		
		/**
		 *  Captures the screenshot of the page
		 * @param driver - WebDriver
		 * @return
		 */
		public String captureScreenshot(WebDriver driver)
		{	
			String userDirector = System.getProperty("user.dir");
			String s1 = null;
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			if(true)
			{
				try {
					String failureImageFileName =  new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss").format(new GregorianCalendar().getTime())+ ".png"; 
					String imageFolder = new SimpleDateFormat("MM-dd-yyyy").format(new GregorianCalendar().getTime());
					File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(scrFile, new File("Screenshot\\"+imageFolder+"\\"+failureImageFileName));
					s1 =  userDirector +"\\Screenshot\\" +imageFolder+"\\" + failureImageFileName ;							
				} catch (IOException e1) {
					e1.printStackTrace();
				}		
			}
			return s1;
		}
		
		/**
		 *  HTML report path
		 * @param driver - WebDriver
		 * @throws IOException 
		 * @throws InterruptedException 
		 */
		public String htmlReportPath(WebDriver driver) throws IOException, InterruptedException {	
			try {
				Thread.sleep(3000);
				subReportFile.set(properties.getPropValues("projectPath") + "\\Reports\\Report" + folderName + "\\" + fileNameCompare + "\\" + getTestCaseName() + "_" + new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss aa").format(new GregorianCalendar().getTime()) +".html");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return properties.getPropValues("projectPath") + properties.getPropValues("reportPath").replace("./", "\\") + folderName + "\\" + fileNameCompare + "\\" + getTestCaseName() + "_" + new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss aa").format(new GregorianCalendar().getTime()) +".html";
		}
		
		public void createReportSummary(String environment) throws IOException
		{
			//SubFolder Creation with Respect to Batch Execution
			properties.putPropValues("subFolderName", properties.getPropValues("reportPath")+ folderName + "\\" + fileNameCompare);
			new File(properties.getPropValues("projectPath") + "\\Reports\\Report"+ folderName).mkdir();
			new File(properties.getPropValues("projectPath") + "\\Reports\\Report"+ folderName + "\\" + fileNameCompare).mkdir();
			File objFile = new File(properties.getPropValues("projectPath") + properties.getPropValues("subFolderName").replace("./", "\\")  + "\\Report_Summary_" + fileNameCompare + ".html");
			if (objFile.createNewFile()) {
				 String reportPath1 = (properties.getPropValues("reportPath") + folderName + "\\" + fileNameCompare + "\\Report_Summary_" + appFunctions.Baseclass.fileNameCompare + ".html").replaceAll("\\s+", "");
				 properties.putPropValues("EmailResultPath", reportPath1);
				 appFunctions.Baseclass.myWriter.set(new FileWriter(properties.getPropValues("projectPath") + properties.getPropValues("subFolderName").replace("./", "\\")  + "\\Report_Summary_" + fileNameCompare + ".html"));
				 getMyWriter().write("<html>");
			     getMyWriter().write("<title>" + "Test Execution Results</title>");
			     getMyWriter().write("<head></head>");
			     getMyWriter().write("<body>");
			     getMyWriter().write("<font face='Tahoma'size='2'>");
			     getMyWriter().write("<h1>eSurety - Automation Batch Results</h1>");
			     getMyWriter().write("<h4>Execution of automation scripts in "+ environment +" environment on " + startTime + "</h4>");	 
			     getMyWriter().write("<table border='1' width='100%' height='47' bodercolor='#ff7f7f'>");
			     getMyWriter().write("<tr>");
			     getMyWriter().write("<td width='8%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>#</font></b></td>");
			     getMyWriter().write("<td width='10%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>Config ID</font></b</td>");
			     getMyWriter().write("<td width='24%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>Bond Category</font></b></td>");
			     getMyWriter().write("<td width='25%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>Scenario Name</font></b></td>");
			     getMyWriter().write("<td width='10%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>Bond Number</font></b</td>");
			     getMyWriter().write("<td width='10%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>Execution Status</font></b></td>");
			     getMyWriter().write("<td width='18%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>Result Link</font></b></td>");
			     getMyWriter().write("</tr>");
		    }
		}
		
		/**
			HTML Summary Report
		*/
		public void reportSummary(String serialNo, String bondCategory, String ScenarioName, String Status) throws IOException
		{
			String splitRowNumber = objGM.testCaseValueReading("findBondType");
			String[] rCount = splitRowNumber.split(",");
			int tcRow = Integer.parseInt(rCount[0]);
			
			File objFile = new File(properties.getPropValues("projectPath") + properties.getPropValues("subFolderName").replace("./", "\\") + "\\Report_Summary_" + appFunctions.Baseclass.fileNameCompare + ".html");
			if (objFile.createNewFile()) {
				 appFunctions.Baseclass.myWriter.set(new FileWriter(properties.getPropValues("projectPath") + properties.getPropValues("subFolderName").replace("./", "\\") + "\\Report_Summary_" + appFunctions.Baseclass.fileNameCompare + ".html"));
				 getMyWriter().write("<html>");
			     getMyWriter().write("<title>" + "Test Execution Results</title>");
			     getMyWriter().write("<head></head>");
			     getMyWriter().write("<body>");
			     getMyWriter().write("<font face='Tahoma'size='2'>");
			     getMyWriter().write("<h1>" + "eSurety - Automation Batch Results</h1>");
			     getMyWriter().write("<table border='1' width='100%' height='47' bodercolor='#ff7f7f'>");
			     getMyWriter().write("<tr>");
			     getMyWriter().write("<td width='8%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>#</font></b></td>");
			     getMyWriter().write("<td width='10%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>Config ID</font></b</td>");
			     getMyWriter().write("<td width='24%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>Bond Category</font></b></td>");
			     getMyWriter().write("<td width='25%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>Scenario Name</font></b></td>");
			     getMyWriter().write("<td width='10%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>Bond Number</font></b</td>");
			     getMyWriter().write("<td width='10%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>Execution Status</font></b></td>");
			     getMyWriter().write("<td width='18%' bgcolor='#000000' align='center'><b><font color='#FFFFFF' face='Tahoma' size='2'>Result Link</font></b></td>");
			     getMyWriter().write("</tr>");
		    }
			getMyWriter().write("<tr>");
			serialNumber.set(Integer.parseInt(serialNo));
			if ((getSerialNumber() % 2) != 0) { 
				getMyWriter().write("<td width='8%' bgcolor='#E8FFE8' valign='top' align='center'>" + serialNo + "</td>");
				getMyWriter().write("<td width='10%' bgcolor='#E8FFE8' valign='top' align='justify'>" + objGM.dataValuereading("findBondType", tcRow, al_findBondType.indexOf("configID")) + "</td>");
				getMyWriter().write("<td width='24%' bgcolor='#E8FFE8' valign='top' align='justify'>" + bondCategory + "</td>");
				getMyWriter().write("<td width='25%' bgcolor='#E8FFE8' valign='top' align='justify'>" + ScenarioName + "</td>");
				getMyWriter().write("<td width='10%' bgcolor='#E8FFE8' valign='top' align='justify'>" + getBondNumber() + "</td>");
				if (Status.contains("Pass")) {
					getMyWriter().write("<td width='10%' bgcolor='#E8FFE8' valign='top' align='center'><b><font color='#008000' face='Tahoma' size='2'>" + Status + "</font></b></td>");
				}else {
					getMyWriter().write("<td width='10%' bgcolor='#E8FFE8' valign='top' align='center'><b><font color='#ff0000' face='Tahoma' size='2'>" + Status + "</font></b></td>");
				}
				getMyWriter().write("<td width='20%' colspan='2' valign='top' bgcolor='#E8FFE8' align='center'><a href='" + getSubReportFile() + "' target='_blank'>Click for html log</a></td>");
			}else {
				getMyWriter().write("<td width='8%' bgcolor='#b9e2f5' valign='top' align='center'>" + serialNo + "</td>");
				getMyWriter().write("<td width='10%' bgcolor='#b9e2f5' valign='top' align='justify'>" + objGM.dataValuereading("findBondType", tcRow, al_findBondType.indexOf("configID")) + "</td>");
				getMyWriter().write("<td width='24%' bgcolor='#b9e2f5' valign='top' align='justify'>" + bondCategory + "</td>");
				getMyWriter().write("<td width='25%' bgcolor='#b9e2f5' valign='top' align='justify'>" + ScenarioName + "</td>");
				getMyWriter().write("<td width='10%' bgcolor='#b9e2f5' valign='top' align='justify'>" + getBondNumber() + "</td>");
				if (Status.contains("Pass")) {
					getMyWriter().write("<td width='10%' bgcolor='#b9e2f5' valign='top' align='center'><b><font color='Green' face='Tahoma' size='2'>" + Status + "</font></b></td>");
				}else {
					getMyWriter().write("<td width='10%' bgcolor='#b9e2f5' valign='top' align='center'><b><font color='Red' face='Tahoma' size='2'>" + Status + "</font></b></td>");
				}
				getMyWriter().write("<td width='18%' colspan='2' valign='top' bgcolor='#b9e2f5' align='center'><a href='" + getSubReportFile() + "' target='_blank'>Click for html log</a></td>");
			}
			getMyWriter().write("</tr>");
		}
		
		
		public void reportSummary() throws IOException {
			getMyWriter().write("</table>");
			getMyWriter().write("</font>");
			getMyWriter().write("</body>");
			getMyWriter().write("</html>");
			getMyWriter().close();
		}
}