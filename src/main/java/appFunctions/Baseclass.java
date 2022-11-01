package appFunctions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import HSF.AppConfiguration;
import HSF.DriverClass;
import HSF.GenericMethods;
import HSF.ResultClass;
import HSF.EmailConfiguration;

public class Baseclass {
	
	public WebDriver driver;
	
	public static GenericMethods objGM = new GenericMethods();
	public static AppConfiguration properties = new AppConfiguration();
	public static DriverClass driverClass = new DriverClass();
	public static ResultClass resultClass = new ResultClass();
	public static EmailConfiguration sendResultMail = new EmailConfiguration();
	public static Map<String, List<String>> dictObj_or = new HashMap<String, List<String>>();
	public static Map<String, List<String>> dictObj_form = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> obj_value = new HashMap<String, List<String>>();
	public static Map<String, String> dictObj_state = new HashMap<String, String>();
	public static Map<String, List<HashMap<String, String>>> dictObj_Questions = new HashMap<String, List<HashMap<String, String>>>();
	public static Map<String, HashMap<String, List<String>>> obj_FormsCompare = new HashMap<String, HashMap<String, List<String>>>();
	public static Map<String, List<HashMap<String, String>>> obj_DataCompare = new HashMap<String, List<HashMap<String, String>>>();
	
	public static ExtentTest logger;
	public static ExtentReports reports;
	
	// Modified by Dimpal - 20/03/2019
	String scenarioNames = "";
	public static String testCaseName = "";

	//Initialization of an Array
	public static ArrayList<String> al_login = new ArrayList<String>();
	public static ArrayList<String> al_findBondType = new ArrayList<String>();
	public static ArrayList<String> al_initialBondInformation = new ArrayList<String>();
	public static ArrayList<String> al_whoIsTheBondApplicant = new ArrayList<String>();
	public static ArrayList<String> al_bond = new ArrayList<String>();
	public static ArrayList<String> al_adminLogin = new ArrayList<String>();
	public static ArrayList<String> al_adminHomePage = new ArrayList<String>();
	public static ArrayList<String> al_quote = new ArrayList<String>();
	public static ArrayList<String> al_questions = new ArrayList<String>();
	public static ArrayList<String> al_underwritingInformation = new ArrayList<String>();
	public static ArrayList<String> al_reQuote = new ArrayList<String>();
	public static ArrayList<String> al_purchaseQuote = new ArrayList<String>();
	public static ArrayList<String> al_submitNPBR=new ArrayList<String>();
	public static ArrayList<String> al_declineQuote=new ArrayList<String>();
	public static ArrayList<String> al_nonRenew= new ArrayList<String>();
	public static ArrayList<String> al_submitRenewal=new ArrayList<String>();
	public static ArrayList<String> al_adminSubmitRenewalApplication=new ArrayList<String>();
	public static ArrayList<String> al_submitRenewalNPBR=new ArrayList<String>();
	public static ArrayList<String>	al_cancelRenewal=new ArrayList<String>();
	public static ArrayList<String> al_submitClaims = new ArrayList<String>();
	public static ArrayList<String> al_editClaims = new ArrayList<String>();
	public static ArrayList<String> al_chngeAccnt = new ArrayList<String>();
	public static ArrayList<String> al_applicationStatus = new ArrayList<String>();
	public static ArrayList<String> al_homepage = new ArrayList<String>();
	public static ArrayList<String> al_reinstate = new ArrayList<String>();
	public static ArrayList<String> al_premiumBearing=new ArrayList<String>();
	public static ArrayList<String> al_cancellation=new ArrayList<String>();
	public static ArrayList<String> list=new ArrayList<String>();
	
	public static String startTime;
	public static String endTime;
	public static String  fileNameCompare;
	public static String folderName;
	
	public static int sPass = 0;
	public static int sFail = 0;
	public static int count = 0;
	public static boolean sCount = true;
	
	static ThreadLocal<WebDriver> webDriverThreadLocal= new InheritableThreadLocal<>();
	static ThreadLocal<ResultClass> resultThreadLocal= new InheritableThreadLocal<>();
	static ThreadLocal<ExtentTest> loggerThreadLocal= new InheritableThreadLocal<>();
	static ThreadLocal<ExtentReports> reportThreadLocal= new InheritableThreadLocal<>();
	static ThreadLocal<String> caseName = new InheritableThreadLocal<>();
	static ThreadLocal<WebDriverWait> waitDriver = new InheritableThreadLocal<>();
	static ThreadLocal<String> threadCount = new InheritableThreadLocal<>();
	public static ThreadLocal<String> trackingID = new InheritableThreadLocal<>();
	public static ThreadLocal<String> bondNumber = new InheritableThreadLocal<>();
	public static ThreadLocal<WebElement> activeElement = new InheritableThreadLocal<>();
	static ThreadLocal<String> getCapturedValue = new InheritableThreadLocal<>();
	public static ThreadLocal<Integer> envNumber = new InheritableThreadLocal<>();
	public static ThreadLocal<Integer> resultNumber = new InheritableThreadLocal<>();
	public static ThreadLocal<String> valDict = new InheritableThreadLocal<>();
	public static ThreadLocal<Map<String, List<HashMap<String, String>>>> dictObj_DataCompare = new InheritableThreadLocal<>();
	public static ThreadLocal<Map<String, HashMap<String, List<String>>>> dictObj_FormsCompare = new InheritableThreadLocal<>();
	public static ThreadLocal<HashMap<String, List<String>>> dictObj_value = new InheritableThreadLocal<>();
	public static ThreadLocal<FileWriter> myWriter = new InheritableThreadLocal<>();
	public static ThreadLocal<ExtentReports> objReport = new InheritableThreadLocal<>();
	public static ThreadLocal<ResultClass> objResultClass = new InheritableThreadLocal<>();
	public static ThreadLocal<String> subReportFile = new InheritableThreadLocal<>();
	public static ThreadLocal<Integer> counter = new InheritableThreadLocal<>();
	public static ThreadLocal<Integer> serialNumber = new InheritableThreadLocal<>();
	public static ThreadLocal<JavascriptExecutor> javaScriptExecutor = new InheritableThreadLocal<>();
	public static ThreadLocal<Boolean> vFlag = new InheritableThreadLocal<>();
	
	@BeforeSuite()
	public void Report() throws IOException, EncryptedDocumentException, InvalidFormatException, InterruptedException {
		objResultClass.set(resultClass);
		// Creating a HMTL report file
		//reports.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
		properties.putPropValues("projectPath", System.getProperty("user.dir"));
		// Start time capture of batch execution
		startTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss aa").format(new GregorianCalendar().getTime());
		// Creating a copy of comparison template
		folderName = new SimpleDateFormat("MM-dd-yyyy").format(new GregorianCalendar().getTime());
		fileNameCompare = new SimpleDateFormat("MM-dd-yyyy_HHmmssaa").format(new GregorianCalendar().getTime());
		
		Thread.sleep(1000);
		properties.putPropValues("reportPath","\\Reports\\Report");

		//Manually need to update the parameter in which environment that we executed
		resultClass.createReportSummary("UAT");
		
		properties.putPropValues("envCompare","\\Reports\\Template for Env Data Comparison.xlsm");
		String fileCompare = properties.getPropValues("projectPath") + properties.getPropValues("subFolderName") + "\\DataCompare_" + fileNameCompare + ".xlsm";
		properties.putPropValues("compareRegressionData", fileCompare);
		String evnCompareTemplate = System.getProperty("user.dir") + properties.getPropValues("envCompare");
		// Copy Forms Comparison Template
		objGM.makeCopyOfComparisonTemplate(evnCompareTemplate, fileCompare);
		
		String templatePath = properties.getPropValues("projectPath") + "\\Reports\\ESurety-Template.xlsx";
		String renewaltemplatePath = properties.getPropValues("projectPath") + "\\Reports\\Renewal-ESurety-Template.xlsx";
		
		String newFileName = properties.getPropValues("projectPath") + properties.getPropValues("subFolderName").replace("./", "\\") + "\\FormCompare-" + fileNameCompare + ".xlsx";
		String newFileName1 = properties.getPropValues("subFolderName") + "\\FormCompare-" + fileNameCompare + ".xlsx";
		
		String renewalFileName = properties.getPropValues("projectPath") + properties.getPropValues("subFolderName").replace("./", "\\") + "\\RenewalFormCompare-" + fileNameCompare + ".xlsx";
		String newFileName2 = properties.getPropValues("subFolderName") + "\\RenewalFormCompare-" + fileNameCompare + ".xlsx";
		properties.putPropValues("formsComparisonTemplate", newFileName);
		properties.putPropValues("formsComparisonResult", newFileName1);
		objGM.makeCopyOfComparisonTemplate(templatePath, newFileName);
		// Adding renewal forms
		properties.putPropValues("RenewalformsComparisonTemplate", renewalFileName);
		properties.putPropValues("RenewalformsComparisonResult", newFileName2);
		objGM.makeCopyOfComparisonTemplate(renewaltemplatePath, renewalFileName);
		// Scenario Handling
		properties.putPropValues("regressionScenario", "");
		
		GenericMethods.readObjectRepository(properties.getPropValues("orFilePath"), "ESurety", dictObj_or);
		
		// Read the data's from the input data sheet
			objGM.datareading(al_login, "login");
			objGM.datareading(al_findBondType, "findBondType");
			objGM.datareading(al_initialBondInformation, "initialBondInformation");
			objGM.datareading(al_whoIsTheBondApplicant, "whoIsTheBondApplicant");
			objGM.datareading(al_adminLogin, "adminLogin");
			objGM.datareading(al_adminHomePage, "adminHomePage");
			objGM.datareading(al_quote, "quote");
			objGM.datareading(al_questions, "questions");
			objGM.datareading(al_underwritingInformation, "underwritingInformation");
			objGM.datareading(al_purchaseQuote, "purchaseQuote");
			objGM.datareading(al_reQuote, "reQuote");
			objGM.datareading(al_submitNPBR, "submitNPBR");
			objGM.datareading(al_declineQuote, "declineQuote");
			objGM.datareading(al_nonRenew, "nonRenew");
			objGM.datareading(al_submitRenewal, "submitRenewalApp");
			objGM.datareading(al_submitRenewalNPBR, "submitRenewalNPBR");
			objGM.datareading(al_submitClaims, "submitClaims");
			objGM.datareading(al_editClaims, "editClaim");
			objGM.datareading(al_chngeAccnt, "changeAccount");
			objGM.datareading(al_cancelRenewal, "cancelRenewal");
			objGM.datareading(al_reinstate, "reinstate");
			objGM.datareading(al_premiumBearing, "submitPBR");
			objGM.datareading(al_cancellation, "cancellation");
			
		//Clear up the Local Threads
			webDriverThreadLocal.set(null);
			waitDriver.set(null);
			activeElement.set(null);
			getCapturedValue.set(null);
	}
	
	@BeforeTest()
	public void Start(ITestContext context) throws Throwable {
		threadCount.set(Integer.toString(context.getSuite().getXmlSuite().getThreadCount()));
		Thread.sleep(1000);
		caseName.set(context.getName());
		Thread.sleep(2000);
		
		// Creating a HMTL report file
		objReport.set(new ExtentReports(getResultClass().htmlReportPath(driver), true));
		getReports().loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
		loggerThreadLocal.set(getReports().startTest(getTestCaseName()));
	}

	
	@AfterTest()
	public void tearDown(ITestContext context) throws IOException, InvalidFormatException, InterruptedException {
		//Read Bond Category Name from Data sheet
		getDriver().quit();
		Thread.sleep(2000);
		if (context.getFailedTests().size() > 0 || DriverClass.getFlag3() == false) {
			getReports().flush();
			count = count + 1;
			counter.set(count);
			properties.putPropValues("testDataScenario", "");
			sFail = sFail + 1;
			getResultClass().reportSummary(Integer.toString(getCounter()), objGM.dataValuereading("findBondType", Integer.parseInt(objGM.testCaseValueReading("findBondType").split(",")[0]), al_findBondType.indexOf("bondCategories")), getTestCaseName(), "Fail");
		} else {
			getReports().flush();
			count = count + 1;
			counter.set(count);
			sPass = sPass + 1;
			properties.putPropValues("testDataScenario", "");
			getResultClass().reportSummary(Integer.toString(getCounter()), objGM.dataValuereading("findBondType", Integer.parseInt(objGM.testCaseValueReading("findBondType").split(",")[0]), al_findBondType.indexOf("bondCategories")), getTestCaseName(), "Pass");
		}
	}
	
	@AfterSuite()
	public void close() throws IOException, InvalidFormatException, InterruptedException {
		webDriverThreadLocal.set(null);
		waitDriver.set(null);
		caseName.set(null);
		threadCount.set(null);
		properties.putPropValues("regressionScenario", "");
		getResultClass().reportSummary();
		endTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss aa").format(new GregorianCalendar().getTime());
		//sendResultMail.sendResultMail(sPass, sFail, startTime, endTime);
	}
	
	public static WebDriver getDriver() {
		return webDriverThreadLocal.get();
	}
	
	public static ExtentTest getLogger() {
		return loggerThreadLocal.get();
	}
	
	public static String getTestCaseName() {
		return caseName.get();
	}
	
	public static WebDriverWait getWebDriverWait() {
		return waitDriver.get();
	}
	
	public static WebElement getActiveElement() {
		return activeElement.get();
	}
	
	public static String getThreadCount() {
		return trackingID.get();
	}
	
	public static String getCapturedValue() {
		return getCapturedValue.get();
	}
	
	public static String getDictVal() {
		return valDict.get();
	}
	
	public static ExtentReports getReports() {
		return objReport.get();
	}
	
	public static ResultClass getResultClass() {
		return objResultClass.get();
	}
	
	public static FileWriter getMyWriter() {
		return myWriter.get();
	}
	
	public static String getSubReportFile() {
		return subReportFile.get();
	}
	
	public static Integer getCounter() {
		return counter.get();
	}
	
	public static Integer getSerialNumber() {
		return serialNumber.get();
	}
	
	public static JavascriptExecutor getJS() {
		return javaScriptExecutor.get();
	}
	
	public static String getBondNumber() {
		return bondNumber.get();
	}
}