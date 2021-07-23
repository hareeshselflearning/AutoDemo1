package com.btb.hybrid.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.btb.hybrid.driver.DriverScript;
import com.btb.hybrid.keywords.WebGenericKeywords;
import com.btb.hybrid.report.ExtentManager;
import com.btb.hybrid.util.Constants;
import com.btb.hybrid.util.DataUtil;
import com.btb.hybrid.util.Xls_Reader;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class BaseTest {
	public Properties envprop;
	public Properties genprop;
	public Xls_Reader xls;
	public String testCaseName;
	public DriverScript ds;
	public ExtentReports rep ;
	public ExtentTest test ;
	
	public String sheetName;
	public WebGenericKeywords wg;
	private static AppiumDriverLocalService server;
	
	//@BeforeSuite
	public void beforeSuite() throws Exception, Exception {
		server = getAppiumService();
		if(!checkIfAppiumServerIsRunnning(4723)) {
			server.start();
			//server.clearOutPutStreams();
			test.log(Status.INFO,"Appium server started");
			System.out.println("Appium server started");
		} else {
			test.log(Status.INFO,"Appium server already running");
			System.out.println("Appium server already running");
		}	
	}
	
	public boolean checkIfAppiumServerIsRunnning(int port) throws Exception {
	    boolean isAppiumServerRunning = false;
	    ServerSocket socket;
	    try {
	        socket = new ServerSocket(port);
	        socket.close();
	    } catch (IOException e) {
	    	System.out.println("1");
	        isAppiumServerRunning = true;
	    } finally {
	        socket = null;
	    }
	    return isAppiumServerRunning;
	}
	
	public AppiumDriverLocalService getAppiumService() {
		HashMap<String, String> environment = new HashMap<String, String>();
		return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
				.usingDriverExecutable(new File("/usr/local/bin/node"))
				.withAppiumJS(new File("/Users/hareesh/.npm-global/lib/node_modules/appium/lib/main.js"))
				.usingPort(4723)
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
				.withEnvironment(environment));
	}


	@BeforeTest
	public void init(){
		try {
		testCaseName = this.getClass().getSimpleName();
		envprop=new Properties();
		genprop=new Properties();
		FileInputStream fs;
		try {
			fs = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//env.properties");
			envprop.load(fs);
			String propName=envprop.getProperty("env");
			fs =  new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//"+propName+".properties");
			genprop.load(fs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] arr  = this.getClass().getPackage().getName().split("\\.");
		String suiteName = arr[arr.length-1];
		xls = new Xls_Reader(System.getProperty("user.dir") + "//TestScripts//"+suiteName+".xlsx");
		
		
		if(envprop.getProperty("corewebvitalsrun").equals("yes")) {
			Date d = new Date();
			sheetName=d.toString().replace(":", "_").replace(" ", "_").replace("IST", "");
			//addSheet
			sheetName = envprop.getProperty("env")+"_"+sheetName;
			xls.addSheet(sheetName);


			//Add Columns
			
			xls.addColumn(sheetName,"PERFORMANCE");
//			xls.addColumn(sheetName,"FIELD_MOBILE_FCP");
//			xls.addColumn(sheetName,"FIELD_MOBILE_FID");
//			xls.addColumn(sheetName,"FIELD_MOBILE_LCP");
//			xls.addColumn(sheetName,"FIELD_MOBILE_CLS");

			xls.addColumn(sheetName,"LAB_MOBILE_FCP");
			xls.addColumn(sheetName,"LAB_MOBILE_TTI");
			xls.addColumn(sheetName,"LAB_MOBILE_SI");
			xls.addColumn(sheetName,"LAB_MOBILE_TOTALBLOCKINGTIME");
			xls.addColumn(sheetName,"LAB_MOBILE_LCP");
			xls.addColumn(sheetName,"LAB_MOBILE_CLS");
			
//
//			xls.addColumn(sheetName,"FIELD_DESKTOP_FCP");
//			xls.addColumn(sheetName,"FIELD_DESKTOP_FID");
//			xls.addColumn(sheetName,"FIELD_DESKTOP_LCP");
//			xls.addColumn(sheetName,"FIELD_DESKTOP_CLS");
//
//			xls.addColumn(sheetName,"LAB_DESKTOP_FCP");
//			xls.addColumn(sheetName,"LAB_DESKTOP_FID");
//			xls.addColumn(sheetName,"LAB_DESKTOP_LCP");
//			xls.addColumn(sheetName,"LAB_DESKTOP_CLS");
//			xls.addColumn(sheetName,"LAB_DESKTOP_SPEEDINDEX");
//			xls.addColumn(sheetName,"LAB_DESKTOP_TOTALBLOCKINGTIME");
			
			wg = new WebGenericKeywords();
			wg.setExcelData(xls);
			wg.setSheetName(sheetName);
		}
		
		
		
		ds = new DriverScript();
		ds.setEnvprop(envprop);
		ds.setGenprop(genprop);
		
		
		
		}
		catch(Exception e) {
			test.log(Status.FAIL, "Init Method Failed");
		}
	}

	@DataProvider
	public Object[][] getData(Method method){
		return DataUtil.getTestData(testCaseName, xls);
	}

	@BeforeMethod
	public void initReport() throws IOException{
		
		//rep = ExtentManager.getInstance(envprop.getProperty("reportPath"));
		rep = ExtentManager.getInstance(Constants.REPORT_PATH);
		test = rep.createTest(testCaseName);
		ds.setExtentTest(test);
	}

	@AfterMethod
	public void quit(){
		if(ds!=null){
			ds.quit();
		}

		if(rep!=null){
			rep.flush();
		}
	}
}
