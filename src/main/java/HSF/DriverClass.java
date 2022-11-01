package HSF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class DriverClass {
	public static WebElement activeElement;
	static ResultClass objRC = new ResultClass();
	static GenericMethods objGM = new GenericMethods();
	public static String data ="";
	public static ExtentReports reports;
	public static ThreadLocal<Boolean> flag3 = new InheritableThreadLocal<>();
	/*This method allows us to identify the object and perform desired action on objects
	 * Objects are identified by xpath/id/name
	 * Operations performed like click, enter, select and get values
	 */
	public static void objIdentification(WebDriver driver, Map<String, List<String>> orDict, ArrayList<String> al_dataSheet,int tcRow,String objName, ExtentTest logger) throws InterruptedException, IOException {
		//locator value has to be taken from OR dict pending
		values.set(orDict.get(objName));
		javaScript.set((JavascriptExecutor)appFunctions.Baseclass.getDriver());
		
		if((lstStringValue().get(0).substring(lstStringValue().get(0).length()-3).equals("txt")) || (lstStringValue().get(0).substring(lstStringValue().get(0).length()-3).equals("txg")) || (lstStringValue().get(0).substring(lstStringValue().get(0).length()-3).equals("txe")) ||(lstStringValue().get(0).substring(lstStringValue().get(0).length()-3).equals("lst")) ||(lstStringValue().get(0).substring(lstStringValue().get(0).length()-3).equals("pwd"))) {   
			GenericMethods objGM = new GenericMethods();
			readData.set(objGM.dataValuereading(al_dataSheet.get(0), tcRow, al_dataSheet.indexOf(objName)).trim());
		}
		if (getData() != null) {
			try {
			switch (lstStringValue().get(0).substring(0, 1)) {
			case "x":
				try {
				flag3.set(false);
				if (!lstStringValue().get(0).substring(lstStringValue().get(0).length()-3).contains("get")) {
					appFunctions.Baseclass.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(lstStringValue().get(1))));
					Thread.sleep(1000);
					appFunctions.Baseclass.activeElement.set(appFunctions.Baseclass.getDriver().findElement(By.xpath(lstStringValue().get(1))));
				}
					flag3.set(true);
					break;
				}catch(Exception e) {
					objRC.logFail(appFunctions.Baseclass.getLogger(), "Object not found : "+ e.getMessage(), appFunctions.Baseclass.getDriver());
				}
			case "i":
				flag3.set(false);
				appFunctions.Baseclass.getWebDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(lstStringValue().get(1))));
				appFunctions.Baseclass.activeElement.set(appFunctions.Baseclass.getDriver().findElement(By.id(lstStringValue().get(1))));
				flag3.set(true);
				break;
			case "n":
				flag3.set(false);
				appFunctions.Baseclass.getWebDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(lstStringValue().get(1))));
				appFunctions.Baseclass.activeElement.set(appFunctions.Baseclass.getDriver().findElement(By.name(lstStringValue().get(1))));
				flag3.set(false);
				break;
			}
			/*Performs actions like click, enter and selects item from the drop down list
			 * Modified by Dimpal - 03/09/2022
			 */
			switch (lstStringValue().get(0).substring(lstStringValue().get(0).length()-3)) {
			case "clk":
				try {
					appFunctions.Baseclass.getActiveElement().click();
					objRC.logPass(appFunctions.Baseclass.getLogger(), "Clicked on "+lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4));
					flag3.set(true);
					break;
				} catch (Exception e) {
					flag3.set(false);
					objRC.logfail(appFunctions.Baseclass.getLogger(), lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4) + " is not identified: "+ e.getMessage());
					break;
				}
			case "jsc":
				try {
					Thread.sleep(1000);
					getJavaScript().executeScript("arguments[0].click();", appFunctions.Baseclass.getActiveElement());
					objRC.logPass(appFunctions.Baseclass.getLogger(), "Clicked on "+lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4));
					flag3.set(true);
					break;
				} catch (Exception e) {
					flag3.set(false);
					objRC.logfail(appFunctions.Baseclass.getLogger(), lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4) + " is not identified: "+ e.getMessage());
					break;
				}
			case "skl":
					 Screen sikuli = new Screen();
					 String inputFilePath = data;
					 Pattern closeGrid = new Pattern (inputFilePath);
					 Thread.sleep(2000);
					 sikuli.click(closeGrid);
					 flag3.set(true);
					 break;
			case "txt":
				try {
					getJavaScript().executeScript("arguments[0].click();", appFunctions.Baseclass.getActiveElement());
					appFunctions.Baseclass.getActiveElement().clear();
					Thread.sleep(1000);
					getJavaScript().executeScript("arguments[0].value='"+ getData() +"';", appFunctions.Baseclass.getActiveElement());
					appFunctions.Baseclass.getActiveElement().sendKeys(Keys.TAB);
					objRC.logPass(appFunctions.Baseclass.getLogger(), getData() + " entered on "+lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4));
					flag3.set(true);
					break;
				} catch (Exception e) {
					objRC.logfail(appFunctions.Baseclass.getLogger(), lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4) + " is not identified: "+ e.getMessage());
					flag3.set(false);
					break;
			}
			case "txg":
				try {
					WebElement a= appFunctions.Baseclass.getDriver().switchTo().activeElement();
					 for (int i = 0; i < getData().length(); i++){
					        char c = getData().charAt(i);
					        String s = new StringBuilder().append(c).toString();
					        a.sendKeys(s);
					 }  
					objRC.logPass(appFunctions.Baseclass.getLogger(), getData() + " entered on "+lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4));
					flag3.set(true);
					break;
				} catch (Exception e) {
					flag3.set(false);
					objRC.logfail(appFunctions.Baseclass.getLogger(), lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4) + " is not identified: "+ e.getMessage());
					break;
				}
			case "pwd":
				try {
					getJavaScript().executeScript("arguments[0].click();", appFunctions.Baseclass.getActiveElement());
					appFunctions.Baseclass.getActiveElement().clear();
					appFunctions.Baseclass.getActiveElement().sendKeys(getData());
					Thread.sleep(1000);
					appFunctions.Baseclass.getActiveElement().sendKeys(Keys.TAB);
					objRC.logPass(appFunctions.Baseclass.getLogger(), "******** entered on "+lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4));
					flag3.set(true);
					break;
					}
					catch (Exception e) {
						flag3.set(false);
						objRC.logfail(appFunctions.Baseclass.getLogger(), lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4) + " is not identified: "+ e.getMessage());
						break;
				}
			case "txe":
				try {
					Thread.sleep(1000);
					getJavaScript().executeScript("arguments[0].click();", appFunctions.Baseclass.getActiveElement());
					appFunctions.Baseclass.getActiveElement().sendKeys(getData());
					Thread.sleep(1000);
					appFunctions.Baseclass.getActiveElement().sendKeys(Keys.ENTER);
					Thread.sleep(1000);
					objRC.logPass(appFunctions.Baseclass.getLogger(), getData() + " entered on "+lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4));
					break;
				} catch (Exception e) {
					flag3.set(false);
					objRC.logfail(appFunctions.Baseclass.getLogger(), lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4) + " is not identified: "+ e.getMessage());
					break;
				}
			case "lst":
			boolean	flag = false;
				Select slt = new Select(appFunctions.Baseclass.getActiveElement());
				try {
					if (flag==false) {
					slt.selectByValue(getData());
					flag = true;
					break;
					}
				} catch(Exception e) {
					objRC.logInfo(appFunctions.Baseclass.getLogger(), lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4) + " is not identified by value :"+ e.getMessage());
					break;
				}
				finally 
				{	
					if (flag==false) {
						Thread.sleep(1000);
						slt.selectByVisibleText(data);
						objRC.logFail(appFunctions.Baseclass.getLogger(), lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4) +" - "+"Failed to identify the object",driver);
						flag3.set(false);
					}else {
						objRC.logPass(appFunctions.Baseclass.getLogger(), getData() +" is selection from "+lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4));
						break;
					}
				}
			case "itm":
				boolean flag2 = false;
				Select allItems = new Select(appFunctions.Baseclass.getActiveElement());
				try {
					if (flag2==false) {
						Thread.sleep(1000);
						java.util.List<WebElement> options = allItems.getOptions();
						for(WebElement item:options) { 
				             System.out.println("Dropdown values are: "+ item.getText());
						}
						flag2 = true;
						break;
					}
				} catch(Exception e) {
					objRC.logInfo(appFunctions.Baseclass.getLogger(), lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4) + " is not identified by value :"+ e.getMessage());
					flag3.set(false);
					break;
				}
			case "get":
				try {
					if ((appFunctions.Baseclass.getDriver().findElements(By.xpath(lstStringValue().get(1)))).size() != 0) {
						appFunctions.Baseclass.activeElement.set(appFunctions.Baseclass.getDriver().findElement(By.xpath(lstStringValue().get(1))));
						appFunctions.Baseclass.valDict.set(appFunctions.Baseclass.getActiveElement().getText().toString().trim());
						AppConfiguration properties = new AppConfiguration();
						properties.putPropValues("capturedValue", appFunctions.Baseclass.getActiveElement().getText().toString().trim());
						break;
					}else {
						appFunctions.Baseclass.valDict.set("");
						break;
					}
				}catch(Exception e) { 
				//	objRC.logInfo(appFunctions.Baseclass.getLogger(), varObjectName + " is not identified by value :"+ e.getMessage());
					break;
				}
			case "val":
				try {
					if ((appFunctions.Baseclass.getDriver().findElements(By.xpath(lstStringValue().get(1)))).size() != 0) {
						AppConfiguration properties1 = new AppConfiguration();
						properties1.putPropValues("capturedValue", appFunctions.Baseclass.getActiveElement().getAttribute("value").toString().trim());
						break;
					}else {
						break;
					}
				}
				catch(Exception e) {
					flag3.set(false);
					break;
				}
			  }
			}
			catch(ElementNotVisibleException e) { 
				objRC.logFail(appFunctions.Baseclass.getLogger(), "Exception is thrown : "+ e.getMessage(),appFunctions.Baseclass.getDriver());
				flag3.set(false);
				objRC.logInfo(appFunctions.Baseclass.getLogger(),"Execution not successfully completed");
			} 
			catch(Exception e) { 
				objRC.logFail(appFunctions.Baseclass.getLogger(), "Exception is thrown : "+ e.getMessage(),appFunctions.Baseclass.getDriver());
				flag3.set(false);
				objRC.logInfo(appFunctions.Baseclass.getLogger(),"Execution not successfully completed");
			} 
		finally {
				if(getFlag3() == false) {
					objRC.logFail(appFunctions.Baseclass.getLogger(), lstStringValue().get(0).substring(2, lstStringValue().get(0).length()-4) +" - "+"Failed to identify the object", appFunctions.Baseclass.getDriver());
					appFunctions.Baseclass.getDriver().quit();
					flag3.set(false);
					reports.endTest(appFunctions.Baseclass.getLogger());
				} else
				{
					flag3.set(true);
				}
			}
		} 
	}
	
	public static ThreadLocal<String> readData = new InheritableThreadLocal<>();
	public static String getData() {
		return readData.get();
	}
	
	public static ThreadLocal<JavascriptExecutor> javaScript = new InheritableThreadLocal<>();
	public static JavascriptExecutor getJavaScript() {
		return javaScript.get();
	}
	
	public static ThreadLocal<List<String>> values = new InheritableThreadLocal<>();
	public static List<String> lstStringValue() {
		return values.get();
	}
	
	public static Boolean getFlag3() {
		return flag3.get();
	}
	
} 