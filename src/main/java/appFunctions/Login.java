package appFunctions;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import HSF.DriverClass;
public class Login extends Baseclass {

	@Test
	public static void launchApplication() throws Throwable {
		try {
			switch (objGM.dataValuereading("login", Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), al_login.indexOf("browserType"))) {
			case "Chrome":
				Thread.sleep(2000);
				System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
				ChromeOptions opt = new ChromeOptions();
				opt.setCapability("browserVersion", "98");
				opt.setCapability("platformName", "Windows 10");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, opt);
				webDriverThreadLocal.set(new ChromeDriver(opt));
				Thread.sleep(1000);
				getDriver().manage().window().maximize();
				break;
			case "Edge":
				Thread.sleep(2000);
				System.setProperty("webdriver.edge.driver", "msedgedriver.exe");
				webDriverThreadLocal.set(new EdgeDriver());
				Thread.sleep(1000);
				getDriver().manage().window().maximize();
				getDriver().manage().deleteAllCookies();
				break;
			case "Firefox":
				Thread.sleep(2000);
				System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
				webDriverThreadLocal.set(new FirefoxDriver());
				Thread.sleep(1000);
				getDriver().manage().window().maximize();
				getDriver().manage().deleteAllCookies();
				break;
			case "Headless":
				System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		        ChromeOptions options1 = new ChromeOptions();
		        options1.addArguments("headless");
		        webDriverThreadLocal.set(new ChromeDriver(options1));
		        getDriver().manage().window().maximize();
				getDriver().manage().deleteAllCookies();
				break;
			default:
				System.out.println("Browser:" + objGM.dataValuereading("login", Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), al_login.indexOf("browserType")) + " is invalid");
			}
			
			waitDriver.set(new WebDriverWait(getDriver(), 90));
			Thread.sleep(2000);
			String split[] = getTestCaseName().split("_");
			int count = split.length;
			String envName = split[count - 1];
			Thread.sleep(1000);
			getDriver().get(objGM.dataValuereading("login", Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), al_login.indexOf("env" + envName)));
			Thread.sleep(1000);
			resultClass.logInfo(getLogger(), "Application successfully launched: " + objGM.dataValuereading("login", Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), al_login.indexOf("env" + envName)));
		} catch (Exception e) {
			resultClass.logFail(getLogger(), "Unable to launch the application", getDriver());
		}
	}

	@Test
	public void login() throws Throwable {
		try {
			DriverClass.objIdentification(getDriver(), dictObj_or, al_login, Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), "clickLogin", getLogger());
			//ValidationMethod vs = new ValidationMethod();
			//vs.fieldPopUpMessage("login", al_login, "atrributes");
			DriverClass.objIdentification(getDriver(), dictObj_or, al_login, Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), "userName", getLogger());
			DriverClass.objIdentification(getDriver(), dictObj_or, al_login, Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), "password", getLogger());
			resultClass.logInfo(getLogger(), "Login Page Screenshot", getDriver());
			DriverClass.objIdentification(getDriver(), dictObj_or, al_login, Integer.parseInt(objGM.testCaseValueReading("login").split(",")[0]), "clickLogin", getLogger());
			Thread.sleep(10000);
			resultClass.logPass(getLogger(), "Successfully logged-in to Agent Role: ej");
			resultClass.logInfo(getLogger(), "ESurety login Page performance check: " + objGM.performanceCheckTransaction(getDriver(), "//a[@id='navBuyABond']", 2) + " seconds");
		} catch (Exception e) {

		}
	}
	
	@Test
	public void forgotPassword() {
		String splitRowNumber = objGM.testCaseValueReading("login");
		String[] rCount = splitRowNumber.split(",");
		int tcRow = Integer.parseInt(rCount[0]);
			try{
			DriverClass.objIdentification(getDriver(), dictObj_or, al_login, tcRow, "forgotpass", getLogger());
			Thread.sleep(2000);
			DriverClass.objIdentification(getDriver(), dictObj_or, al_login, tcRow, "forgotpassUserName", getLogger());
			DriverClass.objIdentification(getDriver(), dictObj_or, al_login, tcRow, "forgotpassSubmit", getLogger());
		}catch(Exception e) {
			resultClass.logFail(getLogger(), "unable to find method in Forgot password login page", getDriver());
		}
	}
}