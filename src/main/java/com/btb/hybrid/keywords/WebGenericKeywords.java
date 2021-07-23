package com.btb.hybrid.keywords;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.btb.hybrid.report.ExtentManager;
import com.btb.hybrid.util.Xls_Reader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class WebGenericKeywords {
	public Properties envprop;
	public Properties genprop;
	public String objKey;
	public String dataKey;
	public String descVal;
	public String proceedOnFail;
	public Hashtable<String, String> data;
	public WebDriver driver;
	public AppiumDriver<MobileElement> m_driver;
	public JavascriptExecutor jsDriver;
	public ExtentTest test;
	public SoftAssert softAssert = new SoftAssert();
	public JavascriptExecutor js;
	
	
	public Xls_Reader xls;
	public String sheetName;
	

	public boolean hypval;
	public String hyphen;
	public int beforeAnswerCount;
	public int afterAnswerCount;
	public int beforeRejectCount;
	public int afterRejectCount;
	public int beforeSkipCount;
	public int afterSkipCount;
	public String qusId;
	
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public void setExcelData(Xls_Reader xls) {
		this.xls = xls;
	}

	public void setEnvprop(Properties envprop) {
		this.envprop = envprop;
	}

	public void setGenprop(Properties genprop) {
		this.genprop = genprop;
	}

	public void setObjKey(String objKey) {
		this.objKey = objKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public void setDescKey(String descVal) {
		this.descVal = descVal;
	}

	public void setData(Hashtable<String, String> data) {
		this.data = data;
	}
	public void setExtentTest(ExtentTest test) {
		this.test = test;
	}

	public String getProceedOnFail() {
		return proceedOnFail;
	}

	public Properties getEnvprop() {
		return envprop;
	}

	public void setProceedOnFail(String proceedOnFail) {
		this.proceedOnFail = proceedOnFail;
	}
	/**
	 * openBrowser -  Method opens the Respective browser - as per Data Sheet Browser value
	 */
	public void openBrowser(){
		try {
			String browser = data.get(dataKey);
			
			String[] target = browser.split(",");
			
			//test.log(Status.INFO, "Opening the browser: "+browser);
			test.log(Status.INFO, descVal + " : "+browser);
			//if(browser.equalsIgnoreCase("mozilla")){
				if(target[0].equalsIgnoreCase("mozilla")){
				test.assignCategory(target[0]);
				FirefoxOptions foption = new FirefoxOptions();
				if(envprop.getProperty("HeadlessRun").equalsIgnoreCase("yes")){
				foption.addArguments("--headless");
				}
				foption.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				
				if(System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
					System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+File.separator+"driver"+File.separator+"geckodriver_mac_85");
				}
				else {
					System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+File.separator+"driver"+File.separator+"geckodriver.exe");
				}
				driver = new FirefoxDriver(foption);
				driver.manage().window().maximize();
			}
			//else if(browser.equalsIgnoreCase("safari")){
				else if(target[0].equalsIgnoreCase("safari")){
					test.assignCategory(target[0]);

				//SafariOptions sops = new SafariOptions();
				//sops.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				
				
				//if(envprop.getProperty("HeadlessRun").equalsIgnoreCase("yes")){
				//	ops.addArguments("--headless");
				//}
				
//				if(System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
//					System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+File.separator+"driver"+File.separator+"chromedriver_mac_86");
//				}
//				else {
//					System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+File.separator+"driver"+File.separator+"chromedriver.exe");
//				}
				driver = new SafariDriver();
				driver.manage().window().maximize();
			}
			//else if(browser.equalsIgnoreCase("chrome_compass")){
				else if(target[0].equalsIgnoreCase("chrome_compass")){
					test.assignCategory(target[0]);

				ChromeOptions ops = new ChromeOptions();
				ops.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				//ops.addArguments("force-device-scale-factor=0.75");
				//ops.addArguments("high-dpi-support=0.75");
				
				if(envprop.getProperty("HeadlessRun").equalsIgnoreCase("yes")){
					ops.addArguments("--headless");
					//ops.setHeadless(true);
					ops.addArguments("window-size=1400,800");
				}
				
				if(System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
					System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+File.separator+"driver"+File.separator+"chromedriver_mac");
				}
				else {
					System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+File.separator+"driver"+File.separator+"chromedriver.exe");
				}
				driver = new ChromeDriver(ops);
				//((JavascriptExecutor)driver).executeScript("document.body.style.zoom='100%';");
				driver.manage().window().maximize();
			}
			//else if(browser.equalsIgnoreCase("chrome")){
				else if(target[0].equalsIgnoreCase("chrome")){
					test.assignCategory(target[0]);

				ChromeOptions ops = new ChromeOptions();

				if(envprop.getProperty("HeadlessRun").equalsIgnoreCase("yes")){
					ops.addArguments("--headless");
					ops.addArguments("force-device-scale-factor=0.50");
					ops.addArguments("high-dpi-support=0.50");
				}
				ops.addArguments("--disable-notifications");
				ops.addArguments("--disable-extensions");

				ops.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				if(System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
					System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+File.separator+"driver"+File.separator+"chromedriver_mac");
				}
				else {
					System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+File.separator+"driver"+File.separator+"chromedriver.exe");
				}
				driver = new ChromeDriver(ops);
				driver.manage().window().maximize();
			}
			
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
		catch(Exception e) {
			test.log(Status.FAIL, "Browser did NOT open");
			reportFailureNoScreenShot("Browser did NOT open");

		}
	}
	
	/**
	 * openApp -  Method open the Mobile App 
	 */
	
	public void openApp(){
		try {
			String device = data.get(dataKey);
			String[] target = device.split(",");
			test.log(Status.INFO, descVal + " : "+device);
			 if(target[0].equalsIgnoreCase("android_realdevice")){
				test.assignCategory(target[0]);
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, "vivo 1907");
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, envprop.getProperty("androidAutomationName"));
				cap.setCapability(MobileCapabilityType.UDID, target[1]);
				//cap.setCapability("appActivity", "com.bartleby.learn.MainActivity");
				//cap.setCapability("appPackage", "com.bartleby.learn");
				
				cap.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir")+File.separator+"android"+File.separator+"Bartleby.apk");	
				cap.setCapability("androidInstallTimeout", 200000);//miliseconds , default it is 20000
				cap.setCapability("appWaitActivity", "com.bartleby.learn.MainActivity");
				cap.setCapability("appWaitPackage", "com.bartleby.learn");
				URL url = new URL("http://0.0.0.0:4723/wd/hub");
				m_driver = new AppiumDriver<MobileElement>(url,cap);
				
			}
				else if(target[0].equalsIgnoreCase("android_emulator")){
				test.assignCategory(target[0]);
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel3");
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, envprop.getProperty("androidAutomationName"));
				cap.setCapability(MobileCapabilityType.UDID, target[1]);
				cap.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir")+File.separator+"android"+File.separator+"Bartleby.apk");	
				cap.setCapability("appWaitActivity", "com.bartleby.learn.MainActivity");
				URL url = new URL("http://0.0.0.0:4723/wd/hub");
				m_driver = new AppiumDriver<MobileElement>(url,cap);
			}
				else if(target[0].equalsIgnoreCase("ios_realdevice")){
				test.assignCategory(target[0]);
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6s");
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, envprop.getProperty("iOSAutomationName"));
				System.out.println(envprop.getProperty("iOSAutomationName"));
				cap.setCapability(MobileCapabilityType.UDID, target[1]);
				cap.setCapability("xcodeOrgId", "378UB437HK");
				cap.setCapability("xcodeSigningId", "iPhone Developer");
				cap.setCapability(MobileCapabilityType.NO_RESET, false);
				//cap.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir")+File.separator+"ios"+File.separator+"Bartleby.ipa");
				cap.setCapability("bundleId", "com.bartleby.learn");
				URL url = new URL("http://0.0.0.0:4723/wd/hub");
				m_driver = new AppiumDriver<MobileElement>(url,cap);
			}
				else if(target[0].equalsIgnoreCase("ios_simulator")){
				test.assignCategory(target[0]);
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 8");
				cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, envprop.getProperty("iOSAutomationName"));
				cap.setCapability(MobileCapabilityType.UDID, target[1]);
			    //cap.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir")+File.separator+"ios"+File.separator+"Bartleby.ipa");
				//cap.setCapability(MobileCapabilityType.APP, "/Users/hareesh/Documents/MobileAuto/ios-uicatalog-master/UIKitCatalog/build/Release-iphonesimulator/UIKitCatalog-iphonesimulator.app");
				//cap.setCapability("bundleId", "com.bartleby.learn");
				URL url = new URL("http://0.0.0.0:4723/wd/hub");
				m_driver = new AppiumDriver<MobileElement>(url,cap);
			}
			m_driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		catch(Exception e) {
			test.log(Status.FAIL, "App did NOT open "+e.getMessage());
			reportFailureNoScreenShot("App did NOT open ");
		}
	}
	
	public void waitUntilPerfValAppears() {
		@SuppressWarnings("deprecation")
		WebElement element = (new WebDriverWait(driver, 180))
				   .until(ExpectedConditions.visibilityOf(getObject(objKey)));
	}
	
	public void getPerfScore() throws InterruptedException {
		ArrayList<Integer> perfVal = new ArrayList<Integer>();
		
		ArrayList<Double> fcpVal = new ArrayList<Double>();
		ArrayList<Double> ttifVal = new ArrayList<Double>();
		ArrayList<Double> sifVal = new ArrayList<Double>();
		ArrayList<Double> tbtVal = new ArrayList<Double>();
		ArrayList<Double> lcpVal = new ArrayList<Double>();
		ArrayList<Double> clsVal = new ArrayList<Double>();
		
		int sum = 0;
		int perfAvg = 0;
		
		double fcpsum = 0;
		double fcpAvg = 0;
		double ttisum = 0;
		double ttiAvg = 0;
		double sisum = 0;
		double siAvg = 0;
		double tbtsum = 0;
		double tbtAvg = 0;
		double lcpsum = 0;
		double lcpAvg = 0;
		double clssum = 0;
		double clsAvg = 0;
		
		int count = Integer.parseInt(envprop.getProperty("iterationCount"));
		
		for(int i=1;i<=count;i++) {
			String perf = getObjectXpath(envprop.getProperty("perfval_xpath")).getText();
			
			String fcpperf = getObjectXpath(envprop.getProperty("labfcp_xpath")).getText().replaceAll(" s", "");
			String ttiperf = getObjectXpath(envprop.getProperty("labtti_xpath")).getText().replaceAll(" s", "");
			String siperf = getObjectXpath(envprop.getProperty("labsi_xpath")).getText().replaceAll(" s", "");
			String tbtperf = getObjectXpath(envprop.getProperty("labtbt_xpath")).getText().replaceAll(" ms", "").replaceAll(",", "");
			String lcpperf = getObjectXpath(envprop.getProperty("lablcp_xpath")).getText().replaceAll(" s", "");
			String clsperf = getObjectXpath(envprop.getProperty("labcls_xpath")).getText();
			
			
			System.out.println(perf);
			System.out.println(fcpperf);
			System.out.println(ttiperf);
			System.out.println(siperf);
			System.out.println(tbtperf);
			System.out.println(lcpperf);
			System.out.println(clsperf);
			
			
			test.log(Status.INFO, "ITERATION = "+i+"-->"+ "Perf Score "+"-->"+perf);
			test.log(Status.INFO, "ITERATION = "+i+"-->"+ "FCP Score "+"-->"+fcpperf);
			test.log(Status.INFO, "ITERATION = "+i+"-->"+ "Time To Interactive Score "+"-->"+ttiperf);
			test.log(Status.INFO, "ITERATION = "+i+"-->"+ "Speed Index Score "+"-->"+siperf);
			test.log(Status.INFO, "ITERATION = "+i+"-->"+ "Total Blocking Time Score "+"-->"+tbtperf);
			test.log(Status.INFO, "ITERATION = "+i+"-->"+ "LCP Score "+"-->"+lcpperf);
			test.log(Status.INFO, "ITERATION = "+i+"-->"+ "CLS Score "+"-->"+clsperf);
			
			
			Thread.sleep(10000);
			takeScreenshot();
			//Thread.sleep(10000);
			
			Thread.sleep(2000);
			CustomScrollToAnElement(getObjectXpath(envprop.getProperty("labdata_xpath")));
			Thread.sleep(2000);
			//CustomScrollToAnElement(getObjectXpath(envprop.getProperty("labtti_xpath")));
			takeScreenshot();
			Thread.sleep(2000);
			CustomScrollToAnElement(driver.findElement(By.tagName("header")));
			Thread.sleep(5000);
			
			
			
			perfVal.add(Integer.parseInt(perf));
			
			fcpVal.add(Double.parseDouble(fcpperf));
			ttifVal.add(Double.parseDouble(ttiperf));
			sifVal.add(Double.parseDouble(siperf));
			tbtVal.add(Double.parseDouble(tbtperf));
			lcpVal.add(Double.parseDouble(lcpperf));
			clsVal.add(Double.parseDouble(clsperf));
			
			if(count == perfVal.size()) {
				for(int j=0; j<count;j++) {
					sum = sum + perfVal.get(j);
					
					fcpsum = fcpsum + fcpVal.get(j);
					ttisum = ttisum + ttifVal.get(j);
					sisum = sisum + sifVal.get(j);
					tbtsum = tbtsum + tbtVal.get(j);
					lcpsum = lcpsum + lcpVal.get(j);
					clssum = clssum + clsVal.get(j);
				}
				perfAvg = sum/count;
				
				fcpAvg = fcpsum/count;				
				ttiAvg = ttisum/count;				
				siAvg = sisum/count;				
				tbtAvg = tbtsum/count;				
				lcpAvg = lcpsum/count;				
				clsAvg = clssum/count;				
			}
			
			if(i<count) {
			test.log(Status.INFO, "Click on Analyze button");
			getObjectXpath(envprop.getProperty("analyze_xpath")).click();
			}
		
		}
		
		System.out.println("Perf Avg Score -->"+perfAvg);
		test.log(Status.INFO, "Average Performance Score = "+perfAvg);
		
		System.out.println("FCP Avg Score -->"+fcpAvg);
		test.log(Status.INFO, "Average FCP Score = "+fcpAvg);
		
		System.out.println("TTI Avg Score -->"+ttiAvg);
		test.log(Status.INFO, "Average TTI Score = "+ttiAvg);
		
		System.out.println("Speed Index Avg Score -->"+siAvg);
		test.log(Status.INFO, "Average SI Score = "+siAvg);
		
		System.out.println("TBT Avg Score -->"+tbtAvg);
		test.log(Status.INFO, "Average TBT Score = "+tbtAvg);
		
		System.out.println("LCP Avg Score -->"+lcpAvg);
		test.log(Status.INFO, "Average LCP Score = "+lcpAvg);
		
		System.out.println("CLS Avg Score -->"+clsAvg);
		test.log(Status.INFO, "Average CLS Score = "+clsAvg);
		
		xls.setCellData(sheetName, "PERFORMANCE", Integer.parseInt(data.get(dataKey)), perfAvg);
		
		xls.setCellData(sheetName, "PERFORMANCE", Integer.parseInt(data.get(dataKey)), perfAvg);
		xls.setCellData(sheetName, "LAB_MOBILE_FCP", Integer.parseInt(data.get(dataKey)), fcpAvg);
		xls.setCellData(sheetName, "LAB_MOBILE_TTI", Integer.parseInt(data.get(dataKey)), ttiAvg);
		xls.setCellData(sheetName, "LAB_MOBILE_SI", Integer.parseInt(data.get(dataKey)), siAvg);
		xls.setCellData(sheetName, "LAB_MOBILE_TOTALBLOCKINGTIME", Integer.parseInt(data.get(dataKey)), tbtAvg);
		xls.setCellData(sheetName, "LAB_MOBILE_LCP", Integer.parseInt(data.get(dataKey)), lcpAvg);
		xls.setCellData(sheetName, "LAB_MOBILE_CLS", Integer.parseInt(data.get(dataKey)), clsAvg);
		
		
		
//Apply condition later
		//scrollbypixl(0,5000);
//		String fcp = getObjectXpath(envprop.getProperty("fcpval_xpath")).getText().replaceAll(" s", "");
//		Double fcp_val = Double.parseDouble(fcp);
//		xls.setCellData(sheetName, "FIELD_MOBILE_FCP", Integer.parseInt(data.get(dataKey)), fcp_val);
//		System.out.println("FCP Score -->"+fcp_val);
//		
//		//CustomScrollToAnElement(getObjectXpath(envprop.getProperty("fidval_xpath")));
//		String fid = getObjectXpath(envprop.getProperty("fidval_xpath")).getText().replaceAll(" ms", "");
//		Double fid_val = Double.parseDouble(fid);
//		xls.setCellData(sheetName, "FIELD_MOBILE_FID", Integer.parseInt(data.get(dataKey)), fid_val);
//		System.out.println("FID Score -->"+fid_val);
//		
//
//		String lcp = getObjectXpath(envprop.getProperty("lcpval_xpath")).getText().replaceAll(" s", "");
//		Double lcp_val = Double.parseDouble(lcp);
//		xls.setCellData(sheetName, "FIELD_MOBILE_LCP", Integer.parseInt(data.get(dataKey)), lcp_val);
//		System.out.println("LCP Score -->"+lcp_val);
//		
//		String cls = getObjectXpath(envprop.getProperty("clsval_xpath")).getText();
//		Double cls_val = Double.parseDouble(cls);
//		xls.setCellData(sheetName, "FIELD_MOBILE_CLS", Integer.parseInt(data.get(dataKey)), cls_val);
//		System.out.println("LCP Score -->"+cls_val);
	}

	public void getClientSideMetrics()
	{
		try {
			if(envprop.getProperty("perfmetrics").equalsIgnoreCase("yes")) {
				JavascriptExecutor js1=((JavascriptExecutor)driver);
				//				try {
				//					Thread.sleep(5000);
				//				}catch(Exception e) {e.printStackTrace();}
				long pageLoadTime= (Long)js1.executeScript("return (window.performance.timing.loadEventEnd-window.performance.timing.responseStart)");
				long TTFB= (Long)js1.executeScript("return (window.performance.timing.responseStart-window.performance.timing.navigationStart)");
				long endtoendRespTime= (Long)js1.executeScript("return (window.performance.timing.loadEventEnd-window.performance.timing.navigationStart)");
				test.log(Status.INFO,"URL= "+driver.getCurrentUrl()+ ", PageLoadTime= "+pageLoadTime+", TTFB= "+TTFB+", Customer perceived Time= "+endtoendRespTime);
			}
			else {
				test.log(Status.INFO ,"Perf Metrics Data flag set to NO : EXPECTED");
			}
		}
		catch(Exception e) {
			reportFailure("Unable to capture getClientSideMetrics");
		}
	}

	public void getClientSideMetricsTest()
	{
		try {
			if(envprop.getProperty("perfmetrics").equalsIgnoreCase("yes")) {
				//JavascriptExecutor js1=((JavascriptExecutor)driver);
				//				try {
				//					Thread.sleep(5000);
				//				}catch(Exception e) {e.printStackTrace();}
				//				long pageLoadTime= (Long)js1.executeScript("return (window.performance.timing.loadEventEnd-window.performance.timing.responseStart)");
				//				long TTFB= (Long)js1.executeScript("return (window.performance.timing.responseStart-window.performance.timing.navigationStart)");
				//				long endtoendRespTime= (Long)js1.executeScript("return (window.performance.timing.loadEventEnd-window.performance.timing.navigationStart)");
				//				test.log(Status.INFO,"URL= "+driver.getCurrentUrl()+ ", PageLoadTime= "+pageLoadTime+", TTFB= "+TTFB+", Customer perceived Time= "+endtoendRespTime);

				Object PerformanceData = ((JavascriptExecutor) driver).executeScript( "return JSON.stringify(window.performance.getEntries().map(function(item){ return {connectStart:item.connectStart, connectEnd:item.connectEnd, type:item.type, name:item.name, duration:item.duration,responseStart:item.responseStart,responseEnd:item.responseEnd,requestStart:item.requestStart};}));"); 
				System.out.println("Performance Logs data" + PerformanceData);
			}
			else {
				test.log(Status.INFO ,"Perf Metrics Data flag set to NO : EXPECTED");
			}
		}
		catch(Exception e) {
			reportFailure("Unable to capture getClientSideMetrics");
		}
	}

	public void openNewTab() throws InterruptedException {
		try {
			((JavascriptExecutor)driver).executeScript("window.open()");
			test.log(Status.INFO, descVal);
		}
		catch(Exception e) {
			reportFailure("openNewTab action failed");
		}
	}


	/**
	 * keyEsc method performs KeyBoard escape key action
	 * @throws InterruptedException
	 */
	public void keyEsc() throws InterruptedException {
		try {
			Actions action = new Actions(driver);
			action.sendKeys(Keys.TAB).perform();
			action.sendKeys(Keys.ESCAPE).perform();
			test.log(Status.INFO, descVal);
		}
		catch(Exception e) {
			test.log(Status.INFO, "Escape Keyboard action failed" );
		}
	}

	public void keyEnter() throws InterruptedException {
		try {
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ENTER).perform();
			test.log(Status.INFO, descVal);
		}
		catch(Exception e) {
			test.log(Status.INFO, "ENTER Keyboard action failed" );
		}
	}

	public void keyDown() throws InterruptedException {
		try {
			Actions action = new Actions(driver);
			action.sendKeys(Keys.ARROW_DOWN).build();
			action.sendKeys(Keys.ARROW_DOWN).perform();
			test.log(Status.INFO, descVal);
		}
		catch(Exception e) {
			test.log(Status.INFO, "ENTER Keyboard action failed" );
		}
	}

	public void waitForFiveSec() throws InterruptedException {
		Thread.sleep(5000);
	}


	/**
	 * selectQASText method is used to Select the Plain Text from QAS area selection WebElement
	 * @throws InterruptedException
	 */

	public void selectQASText() throws InterruptedException {
		try {
			WebElement elem = getObject(objKey);
			Actions actions = new Actions(driver);
			actions.doubleClick(elem).perform();
			test.log(Status.INFO, descVal);
		}
		catch(Exception e) {
			test.log(Status.FAIL, "QAS Selection Failed");
		}
	}

	/**
	 * navigate - Method used to enter url and navigate to portal
	 */

	public void navigate(){
		try {
			WebElement html = driver.findElement(By.tagName("html"));
			html.sendKeys(Keys.chord(Keys.CONTROL, Keys.ADD));
			driver.get(genprop.getProperty(objKey));
			test.log(Status.INFO,descVal + " : "+genprop.getProperty(objKey));
			//driver.switchTo().alert().accept();
			//driver.findElement(By.xpath("(//a[text()=' OK '])[1]")).click();
			
//			if(envprop.getProperty("env").equalsIgnoreCase("prod")) {
//			driver.findElement(By.xpath(envprop.getProperty("cookiesok_xpath"))).click();
//			}
		
		}
		catch(Exception e) {
			reportFailure("Navigate action failed");
		}
	}
	
	public void waitUntilFCPAppears() {
		@SuppressWarnings("deprecation")
		WebElement element = (new WebDriverWait(driver, 100))
				   .until(ExpectedConditions.visibilityOf(getObject(objKey)));
	}
	
	public void getFCPScore() {
		String fcp = getObject(objKey).getText();
		System.out.println(fcp);
		test.log(Status.INFO, descVal+fcp);
	}
	
	public void getFIDScore() throws InterruptedException {
		String fid = getObject(objKey).getText();
		System.out.println(fid);
		test.log(Status.INFO, descVal+fid);
		scrollToAnElement();
	}
	
	public void getLCPScore() {
		String lcp = getObject(objKey).getText();
		System.out.println(lcp);
		test.log(Status.INFO, descVal+lcp);
	}
	
	public void getCLSScore() {
		String cls = getObject(objKey).getText();
		System.out.println(cls);
		test.log(Status.INFO, descVal+cls);
	}
	
//	public void getPerfScore() {
//		String cls = getObject(objKey).getText();
//		System.out.println(cls);
//		test.log(Status.INFO, descVal+cls);
//		takeScreenshot();
//	}

	/**
	 * click - Method used to click on the WebElement
	 */
	public void click(){
		getObject(objKey).click();
		//test.log(Status.INFO, "Clicked on the webelement : "+envprop.getProperty(objKey));
		test.log(Status.INFO, descVal);
	}
	
	/**
	 * click - Method used to click on the AndroidElement
	 */
	public void ai_click(){
		ai_getObject(objKey).click();
		test.log(Status.INFO, descVal);
	}

	public void jsClick(){
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", getObject(objKey));
		//test.log(Status.INFO, "Clicked on the webelement : "+envprop.getProperty(objKey));
		test.log(Status.INFO, descVal);
	}



	public void customClick(){
		try {
			String s = null;
			String loc_xpath = null;
			s = data.get(dataKey);
			loc_xpath = "//input[@id="+"'"+s+"'"+"]";
			objKey = loc_xpath;
			Actions actions = new Actions(driver);
			actions.click(getObjectWithoutExplicit(objKey)).perform();
			test.log(Status.INFO, descVal);
		}
		catch(Exception e) {
			reportFailure("Custom Click Method Failed");
		}
	}

	public void subjectClick(){
		try {
			Thread.sleep(2000);
			String s = null;
			String loc_xpath = null;
			s = data.get(dataKey);
			loc_xpath = "//div[@title="+"'"+s+"'"+"]";
			objKey = loc_xpath;
			getObjectXpath(objKey).click();
			test.log(Status.INFO, descVal+data.get(dataKey));
		}
		catch(Exception e) {
			reportFailure("Subject Selection Failed: "+data.get(dataKey));
		}
	}

	public void parentSubjectClick(){
		try {
			String s = null;
			String loc_xpath = null;
			s = data.get(dataKey);
			loc_xpath = "//img[@alt="+"'"+s+"'"+"]"+"/following-sibling::h5";
			objKey = loc_xpath;
			getObjectXpath(objKey).click();
			test.log(Status.INFO, descVal+data.get(dataKey));
		}
		catch(Exception e) {
			reportFailure("Parent Subject click Failed: "+data.get(dataKey));
		}
	}

	public void parentSubjClick(){
		try {
			String s = null;
			String loc_xpath = null;
			s = data.get(dataKey);
			//loc_xpath = "//img[@alt="+"'"+s+"'"+"]"+"/following-sibling::h5";
			loc_xpath = "(//a[text()="+"'"+s+"'"+"])"+"[1]";
			//'Business'])[1]
			objKey = loc_xpath;
			getObjectXpath(objKey).click();
			test.log(Status.INFO, descVal+data.get(dataKey));
		}
		catch(Exception e) {
			reportFailure("Parent Subject click Failed: "+data.get(dataKey));
		}
	}

	public void expandParentSub(){
		try {
			String s = null;
			String loc_xpath = null;
			s = data.get(dataKey);
			loc_xpath = "(//a[text()='"+s+"']/following-sibling::button/div[text()='+'])[1]";
			objKey = loc_xpath;
			getObjectXpath(objKey).click();
			test.log(Status.INFO, descVal+data.get(dataKey));
		}
		catch(Exception e) {
			reportFailure("Parent Subject Expand Failed: "+data.get(dataKey));
		}
	}

	public void childSubClick(){
		try {
			String s = null;
			String loc_xpath = null;
			s = data.get(dataKey);
			loc_xpath = "(//a[@role='menuitem' and text()='"+s+"'])[1]";
			objKey = loc_xpath;
			getObjectXpath(objKey).click();
			test.log(Status.INFO, descVal+data.get(dataKey));
		}
		catch(Exception e) {
			reportFailure("Parent Subject Expand Failed: "+data.get(dataKey));
		}
	}

	public void validateHomeworkHelpChldSub(){
		String s = data.get(dataKey);
		String[] s1 = s.split(",");
		boolean val = false;

		if(s1[0].equals("Business")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					//val = driver.findElements(By.xpath("(//div[text()="+"'"+s1[i]+"'"+"])")).size()!=0;
					val = driver.findElements(By.xpath("//h2[text()='Homework Help by "+s1[0]+" Subjects']/parent::div/parent::div/following-sibling::div//div[text()='"+s1[i]+"']")).size()!=0;
					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Engineering")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					//val = driver.findElements(By.xpath("(//div[text()="+"'"+s1[i]+"'"+"])")).size()!=0;
					val = driver.findElements(By.xpath("//h2[text()='Homework Help by "+s1[0]+" Subjects']/parent::div/parent::div/following-sibling::div//div[text()='"+s1[i]+"']")).size()!=0;
					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Language")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					//val = driver.findElements(By.xpath("(//div[text()="+"'"+s1[i]+"'"+"])")).size()!=0;
					val = driver.findElements(By.xpath("//h2[text()='Homework Help by "+s1[0]+" Subjects']/parent::div/parent::div/following-sibling::div//div[text()='"+s1[i]+"']")).size()!=0;
					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Math")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					//val = driver.findElements(By.xpath("(//div[text()="+"'"+s1[i]+"'"+"])")).size()!=0;
					val = driver.findElements(By.xpath("//h2[text()='Homework Help by "+s1[0]+" Subjects']/parent::div/parent::div/following-sibling::div//div[text()='"+s1[i]+"']")).size()!=0;
					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Science")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					//val = driver.findElements(By.xpath("(//div[text()="+"'"+s1[i]+"'"+"])")).size()!=0;
					val = driver.findElements(By.xpath("//h2[text()='Homework Help by "+s1[0]+" Subjects']/parent::div/parent::div/following-sibling::div//div[text()='"+s1[i]+"']")).size()!=0;
					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Social Science")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					//val = driver.findElements(By.xpath("(//div[text()="+"'"+s1[i]+"'"+"])")).size()!=0;
					val = driver.findElements(By.xpath("//h2[text()='Homework Help by "+s1[0]+" Subjects']/parent::div/parent::div/following-sibling::div//div[text()='"+s1[i]+"']")).size()!=0;
					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else {
			reportFailure("Check the Parent Sub Name ");
		}
	}	


	public void businessQALibParent(){
		String s = data.get(dataKey);
		String[] s1 = s.split(",");
		boolean val = false;

		if(s1[0].equals("Business")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h2[text()="+"'"+s1[0]+" Q&A Library']/parent::div/following-sibling::div/a[text()='"+s1[i]+"']")));
						Thread.sleep(2000);
						if(i==2) {
							takeScreenshot();
						}
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}


					val = driver.findElements(By.xpath("//h2[text()="+"'"+s1[0]+" Q&A Library']/parent::div/following-sibling::div/a[text()='"+s1[i]+"']")).size()!=0;
					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Engineering")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h2[text()="+"'"+s1[0]+" Q&A Library']/parent::div/following-sibling::div/a[text()='"+s1[i]+"']")));
						Thread.sleep(2000);
						if(i==2) {
							takeScreenshot();
						}
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}


					val = driver.findElements(By.xpath("//h2[text()="+"'"+s1[0]+" Q&A Library']/parent::div/following-sibling::div/a[text()='"+s1[i]+"']")).size()!=0;
					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Language")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h2[text()="+"'"+s1[0]+" Q&A Library']/parent::div/following-sibling::div/a[text()='"+s1[i]+"']")));
						Thread.sleep(2000);
						if(i==2) {
							takeScreenshot();
						}
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}


					val = driver.findElements(By.xpath("//h2[text()="+"'"+s1[0]+" Q&A Library']/parent::div/following-sibling::div/a[text()='"+s1[i]+"']")).size()!=0;
					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Math")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h2[text()="+"'"+s1[0]+" Q&A Library']/parent::div/following-sibling::div/a[text()='"+s1[i]+"']")));
						Thread.sleep(2000);
						if(i==2) {
							takeScreenshot();
						}
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}


					val = driver.findElements(By.xpath("//h2[text()="+"'"+s1[0]+" Q&A Library']/parent::div/following-sibling::div/a[text()='"+s1[i]+"']")).size()!=0;
					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Science")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h2[text()="+"'"+s1[0]+" Q&A Library']/parent::div/following-sibling::div/a[text()='"+s1[i]+"']")));
						Thread.sleep(2000);
						if(i==2) {
							takeScreenshot();
						}
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}


					val = driver.findElements(By.xpath("//h2[text()="+"'"+s1[0]+" Q&A Library']/parent::div/following-sibling::div/a[text()='"+s1[i]+"']")).size()!=0;
					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Social Science")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h2[text()="+"'"+s1[0]+" Q&A Library']/parent::div/following-sibling::div/a[text()='"+s1[i]+"']")));
						Thread.sleep(2000);
						if(i==2) {
							takeScreenshot();
						}
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}


					val = driver.findElements(By.xpath("//h2[text()="+"'"+s1[0]+" Q&A Library']/parent::div/following-sibling::div/a[text()='"+s1[i]+"']")).size()!=0;
					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else {
			reportFailure("Check the Parent Sub Name ");
		}
	}

	public void validatePopularSeeSampleCTA(){
		String s = data.get(dataKey);
		String[] s1 = s.split(",");
		boolean val = false;
		boolean seeAllVal = false;

		if(s1[0].equals("Business")) {
			for (int i = 1; i < s1.length; i++) {
				try{

					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}

					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;



					if(val & seeAllVal) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);

					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Engineering")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}

					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;
					if(val & seeAllVal) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Language")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}
					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;
					if(val & seeAllVal) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Math")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}
					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;
					if(val & seeAllVal) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Science")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}
					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;
					if(val & seeAllVal) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Social Science")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}
					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;

					if(val & seeAllVal) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else {
			reportFailure("Check the Parent Sub Name ");
		}
	}	


	public void validatePopularSubComp(){
		String s = data.get(dataKey);
		String[] s1 = s.split(",");
		boolean val = false;
		boolean seeAllVal = false;

		if(s1[0].equals("Business")) {
			for (int i = 1; i < s1.length; i++) {
				try{

					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}

					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;



					if(val & seeAllVal) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Engineering")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}

					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;
					if(val & seeAllVal) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Language")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}
					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;
					if(val & seeAllVal) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Math")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}
					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;
					if(val & seeAllVal) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Science")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}
					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;
					if(val & seeAllVal) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Social Science")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}
					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;

					if(val & seeAllVal) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else {
			reportFailure("Check the Parent Sub Name ");
		}
	}

	public void validateSeeSampleSolComp(){
		String s = data.get(dataKey);
		String[] s1 = s.split(",");
		boolean val = false;
		boolean seeSample = false;

		if(s1[0].equals("Business")) {
			for (int i = 1; i < s1.length; i++) {
				try{

					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//h2[text()='Popular "+s1[i]+" Textbooks ']/../../following-sibling::div[contains(@class,'MarketingBanner')]/div/div[text()='Still sussing out bartleby?'])")));						
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}

					val = driver.findElements(By.xpath("(//h2[text()='Popular "+s1[i]+" Textbooks ']/../../following-sibling::div[contains(@class,'MarketingBanner')]/div/div[text()='Check out a sample textbook solution.'])")).size()!=0;

					String seeSampleSolbtn = "(//h2[text()='Popular "+s1[i]+" Textbooks ']/../../following-sibling::div[contains(@class,'MarketingBanner')]/a[text()='See a sample solution'])";
					seeSample = driver.findElements(By.xpath(seeSampleSolbtn)).size()!=0;


					if(val && seeSample) {
						test.log(Status.PASS, "Marketing Text Found for: "+s1[i]);
						//						Actions act = new Actions(driver);
						//						act.contextClick(getObjectXpath(seeSampleSolbtn));
						//						act.click(getObjectXpath(seeSampleSolbtn));
						//						act.keyDown(Keys.CONTROL).click(getObjectXpath(seeSampleSolbtn));
						//						act.build().perform();

						String urlVal = getObjectXpath(seeSampleSolbtn).getAttribute("href");

						if(envprop.getProperty("env").equalsIgnoreCase("qa")) {
							//form url
							driver.get(genprop.getProperty(objKey));

							//open new tab
							//switch to new tab
							//navigate to url
							//validate ele

							String loc_xpath = "//button[text()="+"'"+s1[0]+"'"+"]";
							objKey = loc_xpath;
							getObjectXpath(objKey).click();
							test.log(Status.INFO, descVal + " : "+s1[0]);
						}
						else if(envprop.getProperty("env").equalsIgnoreCase("uat")) {
							String loc_xpath = "//button[text()="+"'"+s1[1]+"'"+"]";
							objKey = loc_xpath;
							getObjectXpath(objKey).click();
							test.log(Status.INFO, descVal + " : "+s1[1]);
						}
						else if(envprop.getProperty("env").equalsIgnoreCase("prod")) {
							String loc_xpath = "//button[text()="+"'"+s1[2]+"'"+"]";
							objKey = loc_xpath;
							getObjectXpath(objKey).click();
							test.log(Status.INFO, descVal + " : "+s1[2]);
						}
						else {
							test.log(Status.FAIL, "Environment Question ID String NOT Matched");
						}

						System.out.println("urlVal "+urlVal);


						//context_click(element).key_down(Keys.CONTROL).click(element).perform(
						//getObjectXpath(seeSampleSolbtn).sendKeys(Keys.CONTROL,Keys.RETURN);

						//String parentSubBrdCrum = "//div[text()='Business']";
						//String chlldSubBrdCrum = "(//a[text()='Marketing'])[2]";

						//switchNewTab();
						//boolean a = driver.findElements(By.xpath("//div[text()='"+s1[0]+"']")).size()!=0;
						//boolean b = driver.findElements(By.xpath("(//a[text()='"+s1[i]+"'])[2]")).size()!=0;

						//						if( a && b) {
						//							Thread.sleep(2000);
						//							switchOriginalTab();
						////							driver.navigate().back();
						////							Thread.sleep(2000);
						//						}
						//						else {
						//							reportFailure("BreadCrumbs NOT Found: "+s1[i]);	
						//						}
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Engineering")) {
			for (int i = 1; i < s1.length; i++) {
				try{

					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//h2[text()='Popular "+s1[i]+" Textbooks ']/../../following-sibling::div[contains(@class,'MarketingBanner')]/div/div[text()='Still sussing out bartleby?']")));						
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}

					val = driver.findElements(By.xpath("(//h2[text()='Popular "+s1[i]+" Textbooks ']/../../following-sibling::div[contains(@class,'MarketingBanner')]/div/div[text()='Check out a sample textbook solution.']")).size()!=0;
					//seeAllVal = driver.findElements(By.xpath("(//h2[text()='Popular "+s1[i]+" Textbooks ']/../../following-sibling::div[contains(@class,'MarketingBanner')]/a[text()='See a sample solution'])")).size()!=0;
					String seeSampleSolbtn = "(//h2[text()='Popular "+s1[i]+" Textbooks ']/../../following-sibling::div[contains(@class,'MarketingBanner')]/a[text()='See a sample solution'])";

					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
						getObjectXpath(seeSampleSolbtn).click();
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Language")) {
			for (int i = 1; i < s1.length; i++) {
				try{

					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//h2[text()='Popular "+s1[i]+" Textbooks ']/../../following-sibling::div[contains(@class,'MarketingBanner')]/div/div[text()='Still sussing out bartleby?']")));						
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}

					val = driver.findElements(By.xpath("(//h2[text()='Popular "+s1[i]+" Textbooks ']/../../following-sibling::div[contains(@class,'MarketingBanner')]/div/div[text()='Check out a sample textbook solution.']")).size()!=0;
					//seeAllVal = driver.findElements(By.xpath("(//h2[text()='Popular "+s1[i]+" Textbooks ']/../../following-sibling::div[contains(@class,'MarketingBanner')]/a[text()='See a sample solution'])")).size()!=0;
					String seeSampleSolbtn = "(//h2[text()='Popular "+s1[i]+" Textbooks ']/../../following-sibling::div[contains(@class,'MarketingBanner')]/a[text()='See a sample solution'])";

					if(val) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
						getObjectXpath(seeSampleSolbtn).click();
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Math")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}
					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					//seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;
					if(val ) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Science")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}
					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					//seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;
					if(val ) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else if(s1[0].equals("Social Science")) {
			for (int i = 1; i < s1.length; i++) {
				try{
					try {
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")));
						Thread.sleep(3000);
						takeScreenshot();
					}
					catch(Exception e) {
						reportFailure("Element NOT present "+objKey);
					}
					val = driver.findElements(By.xpath("(//h2[text()="+"'Popular "+s1[i]+" Textbooks '"+"])")).size()!=0;
					//seeAllVal = driver.findElements(By.xpath("(//a[text()="+"'See all "+s1[i]+" textbooks'"+"])")).size()!=0;

					if(val ) {
						test.log(Status.PASS, "Child Subject Element Found: "+s1[i]);
					}
					else {
						reportFailure("Child Subject Element NOT Found: "+s1[i]);	
					}
				}
				catch(Exception e) {
					reportFailure("Element NOT Found: "+val);
				}
			}
		}
		else {
			reportFailure("Check the Parent Sub Name ");
		}
	}



	public void userClick(){
		try {
			String s = null;
			String loc_xpath = null;
			s = data.get(dataKey);
			loc_xpath = "//*[text()="+"'"+s+"'"+"]";
			objKey = loc_xpath;
			getObjectXpath(objKey).click();
			test.log(Status.INFO, descVal+data.get(dataKey));
		}
		catch(Exception e) {
			reportFailure("Subject Selection Failed: "+data.get(dataKey));
		}
	}

	public void selectSubject(){
		try {
			String s = null;
			String loc_xpath = null;
			s = data.get(dataKey);
			loc_xpath = "//*[@title="+"'"+s+"'"+"]";
			objKey = loc_xpath;
			//		Actions actions = new Actions(driver);
			//		actions.click(getObjectWithoutExplicit(objKey)).perform();
			getObject(objKey).click();
			test.log(Status.INFO, descVal);
		}
		catch(Exception e) {
			reportFailure("Custom Click Method Failed");
		}
	}



	public void clickOnQusId() throws InterruptedException{
		try {
			String s = null;
			String[] s1 = null;	
			s = data.get(dataKey);
			s1 = s.split(",");

			if(envprop.getProperty("env").equalsIgnoreCase("qa")) {
				String loc_xpath = "//button[text()="+"'"+s1[0]+"'"+"]";
				objKey = loc_xpath;
				getObjectXpath(objKey).click();
				test.log(Status.INFO, descVal + " : "+s1[0]);
			}
			else if(envprop.getProperty("env").equalsIgnoreCase("uat")) {
				String loc_xpath = "//button[text()="+"'"+s1[1]+"'"+"]";
				objKey = loc_xpath;
				getObjectXpath(objKey).click();
				test.log(Status.INFO, descVal + " : "+s1[1]);
			}
			else if(envprop.getProperty("env").equalsIgnoreCase("prod")) {
				String loc_xpath = "//button[text()="+"'"+s1[2]+"'"+"]";
				objKey = loc_xpath;
				getObjectXpath(objKey).click();
				test.log(Status.INFO, descVal + " : "+s1[2]);
			}
			else {
				test.log(Status.FAIL, "Environment Question ID String NOT Matched");
			}
		}
		catch(Exception e) {
			test.log(Status.FAIL, "Clicking on Question ID Failed");
		}
	}

	/**
	 * waitUntil - Method is used to wait until visibility of element (Ex: Scroll)
	 * @throws InterruptedException
	 */
	public void waitUntil() throws InterruptedException{
		takeScreenshot();
		WebDriverWait wait = new WebDriverWait(driver,100);
		wait.until(ExpectedConditions.visibilityOf(getObject(objKey)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getObject(objKey));
		test.log(Status.INFO, descVal);
	}

	/**
	 * type - Method is used to enter values in a WebElement
	 */

	public void type(){
		getObject(objKey).sendKeys(data.get(dataKey));
		test.log(Status.INFO, descVal + " : "+data.get(dataKey));
	}
	
	/**
	 * type - Method is used to enter values in a AndroidElement
	 */

	public void ai_type(){
		ai_getObject(objKey).clear();
		ai_getObject(objKey).sendKeys(data.get(dataKey));
		test.log(Status.INFO, descVal + " : "+data.get(dataKey));
	}

	public void clearAndType(){
		getObject(objKey).clear();
		getObject(objKey).sendKeys(data.get(dataKey));
		//test.log(Status.INFO, "Entered the Value as: "+data.get(dataKey));
		test.log(Status.INFO, descVal + " : "+data.get(dataKey));
	}

	//---------------------*************	

	//	public void clearRandomTextType() {
	//		getObject(objKey).clear();
	//		String[] greeting={"Hello","Hey","Good morning","Good evening","Welcome","Hi","Good day"};
	//		String[] pref={"you","they","I'm","he","she","it","we","Test"};
	//		String[] verb={"swam","ran","said","heard","jumped","bought","buying","read","ate","kicked","hit","drove","hugged","slaped","held","called","messaged","knew","saw","TEST","TEST","TEST"};
	//		String[] advrb={"had","have","did","was","am","are","is"};
	//		String[] obj={"car","boat","street","house","town","food","burger","shop","book","clothes","code","java","beer","bank","bed","window","glass","tea","person","family","plane","bed","fish","man","chair","cup","coffee","dog","math","c++","something","TEST","TEST","TEST"};
	//
	//		String[] ref={"some","a","the"};
	//		String[] adjtv={"beautiful","red","tall","big","small","awesome","huge","test","delicious","smart","great","masive","ugly","colorful","yellow","blue","cool","intilegent","crazy","tiny","TEST","TEST","TEST"};
	//
	//		String randText = (greeting[radgrt()]+","+" "+pref[radpref()]+" "+verb[radverb()]+" "+ref[radref()]+" "+adjtv[radadjtv()]+" "+obj[radobj()]+" "+"and"+" "+pref[radpref()]+" "+verb[radverb()]+" "+ref[radref()]+" "+adjtv[radadjtv()]+" "+obj[radobj()]+","+" "+"because"+" "+pref[radpref()]+" "+verb[radverb()]+" "+ref[radref()]+" "+adjtv[radadjtv()]+" "+obj[radobj()]+".");
	//		getObject(objKey).sendKeys(randText);
	//		test.log(Status.INFO, descVal + " : "+randText);
	//	}
	//	public static int radgrt()
	//	{
	//		int x=(int)Math.ceil(Math.random()*6);
	//		return x;
	//	}
	//	public static int radpref()
	//	{
	//		int y=(int)Math.ceil(Math.random()*7);
	//		return y;
	//	}
	//	public static int radverb()
	//	{
	//		int z=(int)Math.ceil(Math.random()*16);
	//		return z;
	//	}
	//	public static int radadvrb()
	//	{
	//		int j=(int)Math.ceil(Math.random()*6);
	//		return j;
	//	}
	//	public static int radobj()
	//	{
	//		int a=(int)Math.ceil(Math.random()*30);
	//		return a;
	//	}
	//	public static int radadjtv()
	//	{
	//		int b=(int)Math.ceil(Math.random()*21);
	//		return b;
	//	}
	//	public static int radref()
	//	{
	//		int l=(int)Math.ceil(Math.random()*2);
	//		return l;
	//	}

	//	//enter char by char
	//	public void typeQusId() throws InterruptedException{
	//		String s = data.get(dataKey);
	//		String[] s1 = s.split(",");
	//
	//		if(envprop.getProperty("env").equalsIgnoreCase("qa")) {
	//			for(int i=0;i<s1[0].length();i++) {
	//				char ch1 = s1[0].charAt(i);
	//				String fs = new StringBuilder().append(ch1).toString();
	//				Thread.sleep(100);
	//				getObject(objKey).sendKeys(fs);
	//			}
	//			test.log(Status.INFO, descVal + " : "+s1[0]);
	//		}
	//		else if(envprop.getProperty("env").equalsIgnoreCase("uat")) {
	//			for(int i=0;i<s1[1].length();i++) {
	//				char ch1 = s1[1].charAt(i);
	//				String fs = new StringBuilder().append(ch1).toString();
	//				Thread.sleep(100);
	//				getObject(objKey).sendKeys(fs);
	//			}
	//			test.log(Status.INFO, descVal + " : "+s1[1]);
	//		}
	//		else if(envprop.getProperty("env").equalsIgnoreCase("prod")) {
	//			for(int i=0;i<s1[2].length();i++) {
	//				char ch1 = s1[2].charAt(i);
	//				String fs = new StringBuilder().append(ch1).toString();
	//				Thread.sleep(100);
	//				getObject(objKey).sendKeys(fs);
	//			}
	//			test.log(Status.INFO, descVal + " : "+s1[2]);
	//		}
	//		else {
	//			test.log(Status.FAIL, "Environment String NOT Matched");
	//		}
	//	}

	//---------------------*************
	//enter entire qus id
	public void typeQusId() throws InterruptedException{
		String s = data.get(dataKey);
		String[] s1 = s.split(",");

		if(envprop.getProperty("env").equalsIgnoreCase("qa")) {
			getObject(objKey).sendKeys(s1[0]);
			Thread.sleep(2000);
			getObject(objKey).sendKeys(" ");
			//Thread.sleep(2000);
			getObject(objKey).sendKeys(Keys.DELETE);
		}
		else if((envprop.getProperty("env").equalsIgnoreCase("uat"))) {
			getObject(objKey).sendKeys(s1[1]);
			Thread.sleep(2000);
			getObject(objKey).sendKeys(" ");
			//Thread.sleep(2000);
			getObject(objKey).sendKeys(Keys.DELETE);
		}
		else {
			test.log(Status.FAIL, "Unable to read/search with qus id - env mismatch");
		}
	}

	public void clearRandomTextType() {
		getObject(objKey).clear();
		//String[] greeting={"Hello","Hey","Good morning","Good evening","Welcome","Hi","Good day"};
		//		//String[] pref={"you","they","I'm","he","she","it","we","Test"};
		//		String[] verb={"swam","ran","said","heard","jumped","bought","buying","read","ate","kicked","hit","drove","hugged","slaped","held","called","messaged","knew","saw","TEST","TEST","TEST"};
		//		String[] advrb={"had","have","did","was","am","are","is"};
		//		String[] obj={"car","boat","street","house","town","food","burger","shop","book","clothes","code","java","beer","bank","bed","window","glass","tea","person","family","plane","bed","fish","man","chair","cup","coffee","dog","math","c++","something","TEST","TEST","TEST"};
		//
		//		String[] ref={"some","a","the"};
		//		String[] adjtv={"beautiful","red","tall","big","small","awesome","huge","test","delicious","smart","great","masive","ugly","colorful","yellow","blue","cool","intilegent","crazy","tiny","TEST","TEST","TEST"};

		//String randText = (greeting[radgrt()]+","+" "+pref[radpref()]+" "+verb[radverb()]+" "+ref[radref()]+" "+adjtv[radadjtv()]+" "+obj[radobj()]+" "+"and"+" "+pref[radpref()]+" "+verb[radverb()]+" "+ref[radref()]+" "+adjtv[radadjtv()]+" "+obj[radobj()]+","+" "+"because"+" "+pref[radpref()]+" "+verb[radverb()]+" "+ref[radref()]+" "+adjtv[radadjtv()]+" "+obj[radobj()]+".");
		getObject(objKey).sendKeys(Double.toString(Math.random()));
		test.log(Status.INFO, descVal + " : "+"Random Double Val Generated and Entered");
	}

	/**
	 * switchFrame - Method used to Switch to the frame
	 * @throws InterruptedException
	 */

	public void switchFrame() throws InterruptedException {
		Thread.sleep(5000);
		WebElement e = getObject(objKey);
		driver.switchTo().frame(e);
		test.log(Status.INFO, descVal);

	}

	/**
	 * switchFrame_1 - Method used to Switch to frame 1
	 * @throws InterruptedException
	 */
	public void switchFrame_1() throws InterruptedException {
		Thread.sleep(5000);
		driver.switchTo().frame(1);
		test.log(Status.INFO, descVal);
	}

	/**
	 * switchToDefaultContent - Method used to switch to the default content
	 */
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
		test.log(Status.INFO, descVal);
	}


	/**
	 * validateTitle - Method used to validate the Title of the page
	 */
	public void validateTitle(){
		String expectedTitle = envprop.getProperty(objKey);
		String actualTitle = driver.getTitle();
		if(!expectedTitle.equals(actualTitle)){
			reportFailure("Titles are NOT Matching-->"+"Actual Title: "+actualTitle+", Expected Title: "+expectedTitle);
		}
		takeScreenshot();
		//test.log(Status.INFO,"Validated the Title: "+envprop.getProperty(objKey));
		test.log(Status.INFO, descVal);
	}

	public void uploadFile() throws InterruptedException {
		try {

			String[] names = { "Image1.png", "Image2.jpeg","Image3.png","Image4.jpeg"};
			String name = names[(int) (Math.random() * names.length)];

			String filePath = System.getProperty("user.dir")+"/upload/"+name;
			System.out.println("filePath  "+filePath);
			getObjectWithoutExplicit(objKey).sendKeys(filePath);
			try {
				Thread.sleep(40000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			test.log(Status.INFO, descVal);
			takeScreenshot();
		} catch (Exception e) {
			reportFailure("FileUpload Failed");
		}
	}

	/**
	 * quit - Method is used to quit the browser
	 */
	public void quit(){
		if(driver !=null){
			driver.quit();
			test.log(Status.INFO, "Browser Closed");
		}
		
		if(m_driver !=null){
			m_driver.quit();
			test.log(Status.INFO, "App Closed");
		}
	}

	/**
	 * validateElementPresent - Method is used to validate WebElement presence
	 */
	//	public void validateElementPresent(){
	//		if(!isElementPresent(objKey)){
	//			reportFailure("Element NOT present "+objKey);
	//		}
	//		test.log(Status.PASS, descVal);
	//	}

	//	public void highlightEle() {
	//		try {
	//			JavascriptExecutor js = (JavascriptExecutor)driver;
	//	        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: yellow; border: 2px solid yellow;");
	//	        js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
	//		}
	//		catch(Exception e) {
	//			reportFailure("Highlight an Element Function Failed");
	//		}
	//	}

	public void validateElementPresent(){
		//		if(getObjectWithoutExplicit(objKey).isDisplayed()){
		//			test.log(Status.PASS, descVal);
		//		}
		//		else {
		//			reportFailure("Element NOT present "+objKey);
		//		}


		boolean val = false;
		try{
			if(objKey.endsWith("_xpath")){
				val = driver.findElements(By.xpath(envprop.getProperty(objKey))).size()!=0;

			}
			else if(objKey.endsWith("_id")){
				val = driver.findElements(By.id(envprop.getProperty(objKey))).size()!=0;

			}
			else if(objKey.endsWith("_css")){
				val = driver.findElements(By.cssSelector(envprop.getProperty(objKey))).size()!=0;

			}
			else if(objKey.endsWith("_name")){
				val = driver.findElements(By.name(envprop.getProperty(objKey))).size()!=0;

			}
			else if(objKey.endsWith("_tagname")){
				val = driver.findElements(By.tagName(envprop.getProperty(objKey))).size()!=0;

			}
		}
		catch(Exception e) {
			reportFailure("Element NOT Found: "+envprop.getProperty(objKey));
		}

		if(val) {
			test.log(Status.INFO, descVal);
		}
		else {
			reportFailure("Element NOT Found "+envprop.getProperty(objKey));
		}
	}
	
	public void ai_validateElementPresent(){
		if(ai_isElementPresent(objKey)) {
			//test.log(Status.INFO, descVal);
			ai_reportPass(descVal);
		}
		else {
			ai_reportFailure("Element NOT Found "+envprop.getProperty(objKey));
		}
	}

	public void validateElementPresentCustom(){
		boolean val = false;
		try{
			if(objKey.endsWith("_xpath")){
				val = driver.findElements(By.xpath("(//*[text()="+"'"+data.get(dataKey)+"'"+"])"+"[4]")).size()!=0;

			}
			else if(objKey.endsWith("_id")){
				val = driver.findElements(By.id(envprop.getProperty(objKey))).size()!=0;

			}
			else if(objKey.endsWith("_css")){
				val = driver.findElements(By.cssSelector(envprop.getProperty(objKey))).size()!=0;

			}
			else if(objKey.endsWith("_name")){
				val = driver.findElements(By.name(envprop.getProperty(objKey))).size()!=0;

			}
			else if(objKey.endsWith("_tagname")){
				val = driver.findElements(By.tagName(envprop.getProperty(objKey))).size()!=0;	
			}
			takeScreenshot();
		}
		catch(Exception e) {
			reportFailure("Element NOT Found: "+val);
		}

		if(val) {
			test.log(Status.INFO, descVal);
		}
		else {
			reportFailure("Element NOT Found "+val);
		}
	}


	public void customValidateElementPresent(){
		boolean val = false;
		String loc_xpath = "//*[text()="+"'"+data.get(dataKey)+"'"+"]";
		objKey = loc_xpath;
		try{
			val = driver.findElements(By.xpath(loc_xpath)).size()!=0;
		}
		catch(Exception e) {
			reportFailure("Element NOT found");
		}

		if(val) {
			test.log(Status.INFO, descVal+": "+data.get(dataKey));
		}
		else {
			reportFailure("Element NOT Found");
		}
	}

	public void customValidateElementPresentConfig(){
		boolean val = false;
		String loc_xpath = "//*[text()="+"'"+envprop.getProperty(data.get(objKey))+"'"+"]";
		objKey = loc_xpath;
		try{
			val = driver.findElements(By.xpath(loc_xpath)).size()!=0;
		}
		catch(Exception e) {
			reportFailure("Element NOT found");
		}

		if(val) {
			test.log(Status.INFO, descVal+": "+envprop.getProperty("free_qus_btn_txt"));
		}
		else {
			reportFailure("Element NOT Found: "+envprop.getProperty("free_qus_btn_txt"));
		}
	}

	public void freeQusBtnTxtValidate(){
		boolean val = false;
		String loc_xpath = "//*[text()="+"'"+envprop.getProperty("free_qus_btn_txt")+"'"+"]";
		objKey = loc_xpath;
		try{
			val = driver.findElements(By.xpath(loc_xpath)).size()!=0;
		}
		catch(Exception e) {
			reportFailure("Element NOT found");
		}

		if(val) {
			test.log(Status.INFO, descVal+": "+envprop.getProperty("free_qus_btn_txt"));
		}
		else {
			reportFailure("Element NOT Found");
		}
	}

	public void validateBannerText(){
		boolean val = false;
		String loc_xpath = "//*[text()="+"'"+envprop.getProperty("bannertext")+"'"+"]"+"|"+"//*[text()="+"'"+envprop.getProperty("bannertext1")+"'"+"]";
		objKey = loc_xpath;
		try{
			val = driver.findElements(By.xpath(loc_xpath)).size()!=0;
		}
		catch(Exception e) {
			reportFailure("Element NOT found");
		}

		if(val) {
			test.log(Status.INFO, descVal+": "+envprop.getProperty("bannertext"));
		}
		else {
			reportFailure("Element NOT Found: "+loc_xpath);
		}
	}

	public void validateStaticText(){
		boolean val = false;
		//String loc_xpath = "//*[text()="+"'"+envprop.getProperty(data.get(dataKey))+"'"+"]"+"|"+"//*[text()="+"\""+envprop.getProperty(data.get(dataKey))+"\""+"]";
		String loc_xpath = "//*[text()="+"\""+envprop.getProperty(data.get(dataKey))+"\""+"]";//+"|"+"//*[text()="+"\""+envprop.getProperty(data.get(dataKey))+"\""+"]";
		objKey = loc_xpath;
		try{
			val = driver.findElements(By.xpath(loc_xpath)).size()!=0;
		}
		catch(Exception e) {
			reportFailure("Element NOT found");
		}

		if(val) {
			test.log(Status.PASS, descVal+": "+envprop.getProperty(data.get(dataKey)));
		}
		else {
			reportFailure("Element NOT Found: "+loc_xpath);
		}
	}

	public void validateHeaderTitle(){
		boolean val = false;
		String loc_xpath = "//h1[text()="+"'"+data.get(dataKey)+" Homework Help'"+"]";
		objKey = loc_xpath;
		try{
			val = driver.findElements(By.xpath(loc_xpath)).size()!=0;
		}
		catch(Exception e) {
			reportFailure("Element NOT found");
		}

		if(val) {
			test.log(Status.INFO, descVal+": "+data.get(dataKey));
		}
		else {
			reportFailure("Element NOT Found");
		}
	}

	public void validateTitleTextH2(){
		boolean val = false;
		String loc_xpath = "//h2[text()="+"'"+"Homework Help by "+data.get(dataKey)+" Subjects"+"'"+"]";
		objKey = loc_xpath;
		try{
			val = driver.findElements(By.xpath(loc_xpath)).size()!=0;
		}
		catch(Exception e) {
			reportFailure("Element NOT found");
		}

		if(val) {
			test.log(Status.INFO, descVal+": "+data.get(dataKey));
		}
		else {
			reportFailure("Element NOT Found");
		}
	}

	public void validatePopularHeaderSubTitle(){
		boolean val = false;
		String loc_xpath = "//h2[text()="+"'"+"Popular "+data.get(dataKey)+" Textbooks "+"'"+"]";
		objKey = loc_xpath;
		try{
			val = driver.findElements(By.xpath(loc_xpath)).size()!=0;
		}
		catch(Exception e) {
			reportFailure("Element NOT found");
		}

		if(val) {
			test.log(Status.INFO, descVal+": "+data.get(dataKey));
		}
		else {
			reportFailure("Element NOT Found");
		}
	}

	public void validateQandALibHead(){
		boolean val = false;
		String loc_xpath = "//h2[text()="+"'"+data.get(dataKey)+" Q&A Library"+"'"+"]";
		objKey = loc_xpath;
		try{
			val = driver.findElements(By.xpath(loc_xpath)).size()!=0;
		}
		catch(Exception e) {
			reportFailure("Element NOT found");
		}

		if(val) {
			test.log(Status.INFO, descVal+": "+data.get(dataKey));
		}
		else {
			reportFailure("Element NOT Found: "+loc_xpath);
		}
	}


	public void ValidateLitGuideAuthorTitle(){
		boolean val = false;
		String[] strVal = data.get(dataKey).split(",");

		int k = 0;
		int l=  1;
		int len = strVal.length/2;
		
		for (int j = 0; j < len; j++) {
			String loc_xpath ="//div[text()="+"\"" +strVal[k+j]+"\"" +"]/following-sibling::div/span[text()="+"\""+strVal[l+j]+"\"]";

			objKey = loc_xpath;
			try{
				val = driver.findElements(By.xpath(loc_xpath)).size()!=0;
			}
			catch(Exception e) {
				reportFailure("Element NOT found");
			}

			if(val) {
				test.log(Status.INFO, descVal+": Title="+strVal[k+j]+ "\n and Author="+ strVal[l+j]);
			}
			else {
				reportFailure("Element NOT Found: "+loc_xpath);
			}
			
			if(l<=len) {
				
			k = k+1;
			l = l+1;
			
			}
		}
	}



	public void validateMultiElementPresent(){
		try {
			List<WebElement> list = null;
			if(objKey.endsWith("_xpath")){
				list = driver.findElements(By.xpath(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_id")){
				list = driver.findElements(By.id(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_css")){
				list = driver.findElements(By.cssSelector(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_name")){
				list = driver.findElements(By.name(envprop.getProperty(objKey)));
			}
			test.log(Status.INFO, "Total Rows of Data "+list.size());
			String expectedText = data.get(dataKey);
			int count = 0;
			String leftxpath = envprop.getProperty(objKey);

			//if(list.size()!=0) {
			for (int i=1; i<=list.size(); i++) {
				String loc_xpath = leftxpath+"["+i+"]"+"/div[@title="+"'"+expectedText+"'"+"]";
				objKey = loc_xpath;
				String actualText = getObjectXpath(objKey).getText();
				if(!actualText.equals(expectedText)) {
					test.log(Status.FAIL, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedText);
				}

				if(count == 1 | count ==0) {
					test.log(Status.PASS, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedText);
					count++;
				}
			}
			test.log(Status.PASS, descVal);
			list = null;
			//}
			//			else {
			//				test.log(Status.INFO, "Filtered Values Unavailable");	
			//			}
		}
		catch(Exception e) {
			reportFailure("Validating Multiple Value's Failed");
		}
	}

	public void validateColData(){
		try {
			List<WebElement> list=null;
			if(objKey.endsWith("_xpath")){
				list = driver.findElements(By.xpath(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_id")){
				list = driver.findElements(By.id(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_css")){
				list = driver.findElements(By.cssSelector(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_name")){
				list = driver.findElements(By.name(envprop.getProperty(objKey)));
			}
			test.log(Status.INFO, "Total Rows of Data "+list.size());
			String expectedText = data.get(dataKey);
			int count = 0;
			String leftxpath = envprop.getProperty(objKey);

			if(list.size()!=0) {
				for (int i=1; i<=list.size(); i++) {
					String loc_xpath = leftxpath+"["+i+"]"+"/div[@title="+"'"+expectedText+"'"+"]";
					objKey = loc_xpath;
					String actualText = getObjectXpath(objKey).getText();
					System.out.println("actualText--->"+actualText);
					if(!actualText.equals(expectedText)) {
						test.log(Status.FAIL, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedText);
					}

					if(count == 1 | count ==0) {
						test.log(Status.PASS, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedText);
						count++;
					}
				}
				test.log(Status.PASS, descVal);
				//list=null;
			}
			else {
				test.log(Status.INFO, "Filtered Values Un-Available");	
			}
		}
		catch(Exception e) {
			reportFailure("Validating Multiple Value's Failed");
		}
	}

	public void validateColDataMultiple(){
		try {
			List<WebElement> list=null;
			if(objKey.endsWith("_xpath")){
				list = driver.findElements(By.xpath(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_id")){
				list = driver.findElements(By.id(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_css")){
				list = driver.findElements(By.cssSelector(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_name")){
				list = driver.findElements(By.name(envprop.getProperty(objKey)));
			}
			test.log(Status.INFO, "Total Rows of Data "+list.size());
			String expectedText = data.get(dataKey);
			String[] listVal = expectedText.split(",");
			String e1 = listVal[0];
			String e2 = listVal[1];
			int count = 0;
			String leftxpath = envprop.getProperty(objKey);

			if(list.size()!=0) {
				for (int i=1; i<list.size(); i++) {
					String loc_xpath = leftxpath+"["+i+"]"+"/div[@title="+"'"+e1+"'"+"]"+"|"+leftxpath+"["+i+"]"+"/div[@title="+"'"+e2+"'"+"]"+"|"+leftxpath+"["+i+"]"+"/div[@title="+"'"+e1+","+e2+"'"+"]";
					objKey = loc_xpath;
					String actualText = getObjectXpath(objKey).getText();
					if(actualText.equals(e1)|actualText.equals(e2)) {
						test.log(Status.PASS, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+e1+","+e2);
					}
					else {
						test.log(Status.FAIL, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+e1+","+e2);
					}

					if(count == 2 | count ==1) {
						test.log(Status.PASS, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedText);
						count++;
					}
				}
				test.log(Status.PASS, descVal);
				//list=null;
			}
			else {
				test.log(Status.FAIL, "Filtered Values Un-Available");	
			}
		}
		catch(Exception e) {
			reportFailure("Validating Multiple Value's Failed");
		}
	}

	//	@SuppressWarnings("unlikely-arg-type")
	//	public void validateMultipleResSearchVal(){
	//		try {	
	//		int count = 0;
	//		String expectedVal= data.get(dataKey);
	//		System.out.println(getObjects(objKey).size());
	//			if(getObjects(objKey).size()!=0) {
	//				for (int i=1; i<getObjects(objKey).size(); i++) {
	//					String actualText = "//h4[text()="+"'"+data.get(dataKey)+"]"+"["+i+"]";
	//					if(getObjectXpath(actualText).equals(expectedVal)) {
	//						test.log(Status.PASS, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedVal);
	//					}
	//					else {
	//						test.log(Status.FAIL, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedVal);
	//					}
	//
	//					if(count == 2 | count ==1) {
	//						test.log(Status.PASS, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedVal);
	//						count++;
	//					}
	//				}
	//				test.log(Status.PASS, descVal);
	//				//list=null;
	//			}
	//			else {
	//				test.log(Status.FAIL, "Filtered Values Un-Available");	
	//			}
	//		}
	//		catch(Exception e) {
	//			reportFailure("Validating Multiple Value's Failed");
	//		}
	//	}

	public void validateMultipleResSearchVal(){
		try {
			String expectedText = data.get(dataKey);
			List<WebElement> list=null;

			list = driver.findElements(By.xpath("//*[text()="+"'"+expectedText+"'"+"]"));

			test.log(Status.INFO, "Total Number Of Res Found In One Page "+list.size());
			int count = 0;
			if(list.size()!=0) {
				for (int i=2; i<list.size(); i++) {
					String loc_xpath =  "(//*[text()="+"'"+expectedText+"'"+"]"+")["+i+"]";
					objKey = loc_xpath;
					String actualText = getObjectXpath(objKey).getText();
					if(actualText.equals(expectedText)) {
						test.log(Status.PASS, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedText);
					}
					else {
						test.log(Status.FAIL, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedText);
					}

					if(count == 2 | count ==1) {
						test.log(Status.PASS, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedText);
						count++;
					}
				}
				test.log(Status.INFO, descVal);
				//list=null;
			}
			else {
				test.log(Status.FAIL, "Filtered Values Un-Available");	
			}
		}
		catch(Exception e) {
			reportFailure("Validating Multiple Value's Failed");
		}
	}

	public void validateElementNotPresent(){
		if(!isElementPresent(objKey)){
			test.log(Status.PASS, descVal);
		}
		else {
			reportFailure("Element Found: "+objKey);
		}
		test.log(Status.PASS, descVal);
	}


	//App Related functions:	
	public void validateDeclineQusText(){
		String expectedText = null;
		String actualText = null;
		expectedText = data.get(dataKey);
		//String actualText = getObject(objKey).getText();
		actualText = getObjectWithoutExplicit(objKey).getText();
		if(actualText.equals(expectedText)) {
			test.log(Status.PASS, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedText);
		}
		else {
			reportFailure(descVal+" NOT Matched: \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedText);
		}
	}

	public void fetchAndValidateAnsCount(){
		hypval = false;
		if(objKey.equals("beforeanswered_xpath")) {
			if(!getObject(objKey).getText().equals("-")) {
				beforeAnswerCount= Integer.parseInt(getObject(objKey).getText());
				test.log(Status.INFO, descVal + beforeAnswerCount);
			}
			else {

				hyphen = getObject(objKey).getText();
				test.log(Status.INFO, descVal + hyphen);
				hypval =  true;
			}
		}
		else if(objKey.equals("afteranswered_xpath")){
			afterAnswerCount= Integer.parseInt(getObject(objKey).getText());
			if(!hypval) {
				if(afterAnswerCount==beforeAnswerCount+1) {
					test.log(Status.PASS, descVal + afterAnswerCount);
				}
				else {
					reportFailure("Answer count NOT increased by 1 "+" ACTUAL COUNT IS: "+afterAnswerCount);
				}
			}
			else {
				if(afterAnswerCount==1) {
					test.log(Status.PASS, descVal + afterAnswerCount);
				}
				else {
					reportFailure("ANSWER count NOT increased by 1 "+" ACTUAL COUNT IS: "+afterAnswerCount);
				}

			}
		}
	}

	public void fetchAndValidateRejectCount(){
		hypval = false;
		if(objKey.equals("beforerejected_xpath")) {
			if(!getObject(objKey).getText().equals("-")) {
				beforeRejectCount= Integer.parseInt(getObject(objKey).getText());
				test.log(Status.INFO, descVal + beforeRejectCount);
			}
			else {

				hyphen = getObject(objKey).getText();
				test.log(Status.INFO, descVal + hyphen);
				hypval =  true;
			}
		}
		else if(objKey.equals("afterrejected_xpath")){
			afterRejectCount= Integer.parseInt(getObject(objKey).getText());
			if(!hypval) {
				if(afterRejectCount==beforeRejectCount+1) {
					test.log(Status.PASS, descVal + afterRejectCount);
				}
				else {
					reportFailure("Reject count NOT increased by 1 "+" ACTUAL COUNT IS: "+afterRejectCount);
				}
			}
			else {
				if(afterAnswerCount==1) {
					test.log(Status.PASS, descVal + afterAnswerCount);
				}
				else {
					reportFailure("REJECT count NOT increased by 1 "+" ACTUAL COUNT IS: "+afterAnswerCount);
				}

			}
		}
	}

	public void fetchAndValidateSkipCount(){
		hypval = false;
		if(objKey.equals("beforeskipped_xpath")) {
			if(!getObject(objKey).getText().equals("-")) {
				beforeSkipCount= Integer.parseInt(getObject(objKey).getText());
				test.log(Status.INFO, descVal + beforeSkipCount);
			}
			else {

				hyphen = getObject(objKey).getText();
				test.log(Status.INFO, descVal + hyphen);
				hypval =  true;
			}
		}
		else if(objKey.equals("afterskipped_xpath")){
			afterSkipCount= Integer.parseInt(getObject(objKey).getText());
			if(!hypval) {
				if(afterSkipCount==beforeSkipCount+1) {
					test.log(Status.PASS, descVal + afterSkipCount);
				}
				else {
					reportFailure("SKIP count NOT increased by 1 "+" ACTUAL COUNT IS: "+afterSkipCount);
				}
			}
			else {
				if(afterAnswerCount==1) {
					test.log(Status.PASS, descVal + afterAnswerCount);
				}
				else {
					reportFailure("SKIP count NOT increased by 1 "+" ACTUAL COUNT IS: "+afterAnswerCount);
				}

			}
		}
	}

	public void fetchQusID(){
		try {
			String currentUrl = driver.getCurrentUrl();
			String[] splitUrl = currentUrl.split("/");
			qusId = splitUrl[4];
			test.log(Status.INFO,descVal+ qusId);
		}
		catch(Exception e) {
			reportFailure("Question ID Fetch Failed");
		}
	}

	public void switchNewTab(){
		try {
			ArrayList<String> newTab = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(newTab.get(1));
			test.log(Status.INFO,descVal);
			takeScreenshot();
		}
		catch(Exception e) {
			reportFailure("switchNewTab action failed ");
		}
	}

	public void switchNewTab2(){
		try {
			ArrayList<String> newTab = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(newTab.get(2));
			test.log(Status.INFO,descVal);
		}
		catch(Exception e) {
			reportFailure("switchNewTab action failed ");
		}
	}

	public void switchOriginalTab(){		
		ArrayList<String> newTab = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(newTab.get(0));
	}

	public void switchOriginalTab1(){		
		ArrayList<String> newTab = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(newTab.get(1));
	}

	public void closeTab(){
		try {
			driver.close();
			test.log(Status.INFO,"Closed the Tab");
		}
		catch(Exception e){
			reportFailure("Browser Tab NOT closed");
		}
	}


	public void validateURL(){
		String currentUrl = driver.getCurrentUrl();
		test.log(Status.INFO,"Current URL is "+currentUrl);
		String[] splitUrl = currentUrl.split("/");
		if(qusId.equals(splitUrl[4])){
			test.log(Status.PASS,"Question ID Matched: ACTUAL QUS ID is: "+splitUrl[4]+" and Expected Qus ID is: "+qusId);
		}
		else {
			reportFailure("Question Did NOT Match: ");
		}
	}

	public void validateQusId(){
		String qusVal = "//a[text()"+"="+"'"+qusId+"'"+"]";
		if(isElementPresentCustom(qusVal)) {
			test.log(Status.PASS,"Question ID found : NOT QC'd: "+qusId);
		}
		else {
			reportFailure("Question Id Did NOT Match: Qus Id is: "+qusId);
		}
	}

	public void validateQusIdNotPresent(){
		String qusVal = "//a[text()"+"="+"'"+qusId+"'"+"]";
		if(!isElementPresentCustom(qusVal)) {
			test.log(Status.PASS,"Question ID found : NOT QC'd: "+qusId);
		}
		else {
			reportFailure("Question Id Did NOT Match: Qus Id is: "+qusId);
		}
	}

	public void clickCustomQusId(){
		String qusVal = "//a[text()"+"="+"'"+qusId+"'"+"]";
		try {
			getObjectXpath(qusVal).click();
			test.log(Status.INFO, "Clicked on the question id: "+qusId);
		}
		catch (Exception e) {
			reportFailure("Clicking on Question ID Failed: "+qusId);
		}
	}
	
	public void clickPopularCarouselBook() throws InterruptedException{
		
		//int[] values = { 1,3,5,7};
		//int randVal = values[(int) (Math.random() * values.length)];
		String randPopularBook = "(//h2[text()="+"'Popular "+data.get(dataKey)+" Textbooks ']//parent::div//parent::div//following-sibling::div//child::div/span)["+1+"]";
		try {
			
			CustomScrollToAnElement(getObjectXpath(randPopularBook));
			Thread.sleep(2000);
			getObjectXpath(randPopularBook).click();
			test.log(Status.INFO, "Clicked on PopularText Book");
		}
		catch (Exception e) {
			reportFailure("Clicking on PopularText Book Failed");
		}
	}
	
	public void clickOnRandomSol(){
		//int[] values = {1,2,3};
		//int randVal = values[(int) (Math.random() * values.length)];
		String randSol = "(//div[contains(@class,'SampleSolutionZippies')])"+"/a["+1+"]";
		try {
			Thread.sleep(2000);
			getObjectXpath(randSol).click();
			test.log(Status.INFO, "Clicked on Sample Sol: "+getObjectXpath(randSol).getText());
		}
		catch (Exception e) {
			reportFailure("Clicking on Sample Sol Failed");
		}
	}



	//App Related functions:	
	public void validateLogOutText(){
		String expectedText = data.get(dataKey);
		//String actualText = getObject(objKey).getText();
		String actualText = getObjectWithoutExplicit(objKey).getText();
		if(actualText.equals(expectedText)) {
			test.log(Status.PASS, descVal+": \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedText);
		}
		else {
			reportFailure(descVal+" NOT Matched: \n "+ "--> ACTUAL_TEXT: "+actualText+" \n "+ "--> EXPECTED_TEXT: "+expectedText);
		}
	}


	public void clear() {
		getObject(objKey).clear();
	}

	//---------------------------------------------

	public WebElement getObject(String objKey){
		WebElement e = null;
		try{
			if(objKey.endsWith("_xpath")){
				e = driver.findElement(By.xpath(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_id")){
				e = driver.findElement(By.id(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_css")){
				e = driver.findElement(By.cssSelector(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_name")){
				e = driver.findElement(By.name(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_tagname")){
				e = driver.findElement(By.tagName(envprop.getProperty(objKey)));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			takeScreenshot();
			reportFailure("Element NOT found "+objKey);
		}
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(e));
		wait.until(ExpectedConditions.elementToBeClickable(e));
		return e;
	}
	
	public MobileElement ai_getObject(String objKey){
		MobileElement e = null;
		try{
			if(objKey.endsWith("_xpath")){
				e = m_driver.findElement(MobileBy.xpath(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_id")){
				e = m_driver.findElement(MobileBy.id(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_name")){
				e = m_driver.findElement(MobileBy.name(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_tagname")){
				e = m_driver.findElement(MobileBy.tagName(envprop.getProperty(objKey)));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			ai_reportFailure("Element NOT found "+ex.getCause());
		}
		return e;
	}


	public WebElement getObjectXpath(String objKey){
		WebElement e = null;
		try{
			e = driver.findElement(By.xpath(objKey));	
		}
		catch (Exception ex) {
			ex.printStackTrace();
			takeScreenshot();
			reportFailure("Element NOT found "+objKey);
		}
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(e));
		wait.until(ExpectedConditions.elementToBeClickable(e));
		return e;
	}

	public WebElement getObjectWithoutExplicit(String objKey){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		WebElement e = null;
		try{
			if(objKey.endsWith("_xpath")){
				e = driver.findElement(By.xpath(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_id")){
				e = driver.findElement(By.id(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_css")){
				e = driver.findElement(By.cssSelector(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_name")){
				e = driver.findElement(By.name(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_tagname")){
				e = driver.findElement(By.tagName(envprop.getProperty(objKey)));
			}
			else {
				e = driver.findElement(By.xpath(envprop.getProperty(objKey)));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			takeScreenshot();
			reportFailure("Element NOT found "+objKey);
		}
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		return e;
	}

	public List<WebElement> getObjects(String objKey){
		List<WebElement> list=null;
		try{
			if(objKey.endsWith("_xpath")){
				list = driver.findElements(By.xpath(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_id")){
				list = driver.findElements(By.id(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_css")){
				list = driver.findElements(By.cssSelector(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_name")){
				list = driver.findElements(By.name(envprop.getProperty(objKey)));
			}
			else if(objKey.endsWith("_tagname")){
				list = driver.findElements(By.tagName(envprop.getProperty(objKey)));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			takeScreenshot();
			reportFailure("Element NOT found "+objKey);
		}
		return list;
	}

	public boolean isElementPresent(String objKey){
		List<WebElement> list=null;
		if (objKey.endsWith("_xpath")){
			list = driver.findElements(By.xpath(envprop.getProperty(objKey)));
		}
		else if(objKey.endsWith("_id")){
			list = driver.findElements(By.id(envprop.getProperty(objKey)));
		}
		else if(objKey.endsWith("_css")){
			list = driver.findElements(By.cssSelector(envprop.getProperty(objKey)));
		}
		else if(objKey.endsWith("_name")){
			list = driver.findElements(By.name(envprop.getProperty(objKey)));
		}


		if(list.size() == 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean ai_isElementPresent(String objKey){
		List<MobileElement> list=null;
		if (objKey.endsWith("_xpath")){
			list = m_driver.findElements(MobileBy.xpath(envprop.getProperty(objKey)));
		}
		else if(objKey.endsWith("_id")){
			list = m_driver.findElements(MobileBy.id(envprop.getProperty(objKey)));
		}
		else if(objKey.endsWith("_css")){
			list =  m_driver.findElements(MobileBy.cssSelector(envprop.getProperty(objKey)));
		}
		else if(objKey.endsWith("_name")){
			list =  m_driver.findElements(MobileBy.name(envprop.getProperty(objKey)));
		}
		if(list.size() == 0){
			return false;
		}
		else{
			return true;
		}
	}

	public boolean isElementPresentCustom(String objKey){
		List<WebElement> list=null;

		list = driver.findElements(By.xpath(objKey));

		if(list.size() == 0){
			return false;
		}
		else{
			return true;
		}
	}

	public boolean isElementVisible(String objKey){
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getObject(objKey));
		if(getObject(objKey).isDisplayed()) {
			reportPass(descVal);
			return true;
		}
		else {
			reportFailure("Element NOT present "+objKey);
			return false;
		}
	}

	public void scrollToAnElement() throws InterruptedException{
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getObject(objKey));
			Thread.sleep(3000);
			takeScreenshot();
		}
		catch(Exception e) {
			reportFailure("Element NOT present "+objKey);
		}
	}
	
	public void CustomScrollToAnElement(WebElement element) throws InterruptedException{
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(3000);
			//takeScreenshot();
		}
		catch(Exception e) {
			reportFailure("Element NOT present "+objKey);
		}
	}
	
//	public void scrollToTop(WebElement element) throws InterruptedException{
//		try {
//			JavascriptExecutor jse = (JavascriptExecutor)driver;
//			jse.executeScript("window.scrollBy(x,y)");
//			Thread.sleep(3000);
//			takeScreenshot();
//		}
//		catch(Exception e) {
//			reportFailure("Element NOT present "+objKey);
//		}
//	}
	
	public void scrollbypixl(int x, int y) throws InterruptedException{
		try {
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(x,y)");
			Thread.sleep(3000);
			takeScreenshot();
		}
		catch(Exception e) {
			reportFailure("Element NOT present "+objKey);
		}
	}



	public void scrollToEndOfPage() throws InterruptedException{
		try {
			((JavascriptExecutor) driver)
			.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			takeScreenshot();
		}
		catch(Exception e) {
			reportFailure("Element NOT present "+objKey);
		}
	}

	public void scrollToHome() throws InterruptedException{
		try {
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
			((JavascriptExecutor) driver)
			.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
			Thread.sleep(3000);
			takeScreenshot();
		}
		catch(Exception e) {
			reportFailure("Element NOT present "+objKey);
		}
	}

	public void browserBack() {
		try {
			Thread.sleep(3000);
			driver.navigate().back();

			takeScreenshot();
		}
		catch(Exception e) {
			reportFailure("Element NOT present "+objKey);
		}
	}



	public void reportFailure(String failureMsg){
		test.log(Status.FAIL, failureMsg+" - URL -  "+driver.getCurrentUrl());//extent report failure
		takeScreenshot();
		if(proceedOnFail.equalsIgnoreCase("Y")){
			softAssert.fail(failureMsg);	
		}
		else{
			softAssert.fail(failureMsg);
			softAssert.assertAll();
		}
	}
	
	public void ai_reportFailure(String failureMsg){
		test.log(Status.FAIL, failureMsg);//extent report failure
		ai_takeScreenshot();
		if(proceedOnFail.equalsIgnoreCase("Y")){
			softAssert.fail(failureMsg);	
		}
		else{
			softAssert.fail(failureMsg);
			softAssert.assertAll();
		}
	}

	public void reportFailureNoScreenShot(String failureMsg){
		test.log(Status.FAIL, failureMsg);//extent report failure
		//takeScreenshot();
		if(proceedOnFail.equalsIgnoreCase("Y")){
			softAssert.fail(failureMsg);	
		}
		else{
			softAssert.fail(failureMsg);
			softAssert.assertAll();
		}
	}

	public void reportPass(String failureMsg){
		test.log(Status.PASS, failureMsg);//extent report failure
		takeScreenshot();
		if(proceedOnFail.equalsIgnoreCase("Y")){
			softAssert.fail(failureMsg);	
		}
		else{
			softAssert.fail(failureMsg);
			softAssert.assertAll();
		}
	}
	
	public void ai_reportPass(String failureMsg){
		test.log(Status.PASS, failureMsg);//extent report failure
		//ai_takeScreenshot();
		if(proceedOnFail.equalsIgnoreCase("Y")){
			softAssert.fail(failureMsg);	
		}
//		else{
//			softAssert.fail(failureMsg);
//			softAssert.assertAll();
//		}
	}

	public void assertAll(){
		softAssert.assertAll();
	}

	public void takeScreenshot(){
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+screenshotFile));
			test.log(Status.INFO,"Screenshot-> "+ test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+screenshotFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ai_takeScreenshot(){
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		File srcFile = ((TakesScreenshot)m_driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+screenshotFile));
			test.log(Status.INFO,"Screenshot-> "+ test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+screenshotFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeModal() {
		//		if(!driver.findElements(By.xpath("//button[contains(@class,'PlainButton')]/i[text()='close']")).isEmpty()){
		//			driver.findElement(By.xpath("//button[contains(@class,'PlainButton')]/i[text()='close']")).click();
		//			test.log(Status.INFO, "Closed the Modal Pop Up");
		//		}

		if(!getObjects(objKey).isEmpty()) {
			getObjectWithoutExplicit(objKey).click();
		}
	}

	public void gradedQusClick() {
		//visibility and click of element condition avoided
		//WebElement elem = driver.findElement(By.xpath("//input[@value='243d6df5-e9a5-11e9-b78c-00090ffe0001']"));
		Actions actions = new Actions(driver);
		actions.click(getObjectWithoutExplicit(objKey)).perform();
		test.log(Status.INFO, descVal);
	}

	public void incompleteQusClick() {
		//visibility and click of element condition avoided
		//WebElement elem = driver.findElement(By.xpath("//input[@value='3f575645-dbf5-47af-b6f0-8367dfc65dfc']"));
		Actions actions = new Actions(driver);
		actions.click(getObjectWithoutExplicit(objKey)).perform();
		test.log(Status.INFO, descVal);
	}

	public void actionClick() {
		Actions actions = new Actions(driver);
		actions.click(getObjectWithoutExplicit(objKey)).perform();
		test.log(Status.INFO, descVal);
	}

}
