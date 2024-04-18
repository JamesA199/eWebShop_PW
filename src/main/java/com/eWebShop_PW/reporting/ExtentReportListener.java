package com.eWebShop_PW.reporting;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import static com.eWebShop_PW.FactoryPg.FactoryPg.takeScreenshot;
import com.eWebShop_PW.FactoryPg.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

public class ExtentReportListener implements ITestListener {
	private static final String OUTPUT_FOLDER = "./build/";
	private static final String FILE_NAME = "TestExecutionReport.html";
	
	private static ExtentReports extent = init();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	private static ExtentReports extentReports;
	
	private static ExtentReports init() {
		Path path = Paths.get(OUTPUT_FOLDER);
		//if directory exists
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				//fail to create directory
				e.printStackTrace();
			}
		}
	extentReports = new ExtentReports();
	ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
	
	reporter.config().setReportName("eWeb Shop Test Results");
	extentReports.attachReporter(reporter);
	extentReports.setSystemInfo("System", "win11");
	extentReports.setSystemInfo("Developer", "JamesA");
	extentReports.setSystemInfo("Build#", "1.1");
	
	return extentReports;
	
	}
	
	@Override
	public synchronized void onStart(ITestContext context) {
		System.out.println("Test Suite Started...");
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		System.out.println("Test Suite Ended...");
		extent.flush();
		test.remove();
	}	

	@Override
	public synchronized void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String qualifiedName = result.getMethod().getQualifiedName();
		int last = qualifiedName.lastIndexOf(".");
		int mid =  qualifiedName.substring(0, last).lastIndexOf(".");
		String className = qualifiedName.substring(mid + 1, last);
		
		System.out.println(methodName + "started");
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
				result.getMethod().getDescription());
		
		extentTest.assignCategory(result.getTestContext().getSuite().getName());
		/*
		 * Methodname = StringUtils.capitalize(StringUtils.join(StringUtils.
		 * splitByCharacterTypeCamelCase(methodName), StringUtils.SPACE));
		 */
		extentTest.assignCategory(className);
		test.set(extentTest);
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + " passed!");
		test.get().pass("Test Passed");
		
		if (FactoryPg.init_prop().getProperty("screenshotstep").equals("yes")) {
			test.get().pass(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
		}
		
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}
	
	public synchronized void onTestFailure(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + " Failed!");
		test.get().pass("Test Failed");
		test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	public synchronized void onTestSkipped(ITestResult result) {
		System.out.println(result.getMethod().getMethodName() + " Skipped!");
		test.get().pass("Test Skipped");
		test.get().skip(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}	
	
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}	
	
	
	private Date getTime(long Millis) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Millis);
		return calendar.getTime();
	}	
	
}
