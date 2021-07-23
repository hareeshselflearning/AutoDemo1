package com.btb.hybrid.report;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    
    private static ExtentReports extent;
    public static String screenshotFolderPath;
    
    public static ExtentReports getInstance(String reportPath) throws IOException {
    	if (extent == null){
    		String fileName="BTB_Automation_Report.html";
    		Date d = new Date();
    		String folderName=d.toString().replace(":", "_").replace(" ", "_");
    		
    		// directory of the report folder
    		new File(reportPath+folderName+"/screenshots").mkdirs();
    		reportPath=reportPath+folderName+"/";
    		screenshotFolderPath=reportPath+"screenshots/";
    		System.out.println(reportPath+fileName);
    		createInstance(reportPath+fileName);
    	}
    	
        return extent;
    }
    
    public static ExtentReports createInstance(String fileName) throws IOException {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);
        //htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        //htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle("Bartleby - Test Automation Report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("Bartleby - Test Automation Report");
       // htmlReporter.loadXMLConfig(new File("extentconfig.xml"));
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        return extent;
    }
}