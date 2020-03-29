package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class TestStatus implements ITestListener {


    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("Test Started: "+ iTestResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("Test is SUCCESSFUL: "+ iTestResult.getName());
        System.out.println("Test DATA: "+ Arrays.toString(iTestResult.getParameters()));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("Test FAILED: "+ iTestResult.getName());
        System.out.println("Test DATA: "+ Arrays.toString(iTestResult.getParameters()));
        System.out.println("Test CLASS: "+ iTestResult.getTestClass());


    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("Test Skipped: "+ iTestResult.getName());
        System.out.println("Test DATA: "+ Arrays.toString(iTestResult.getParameters()));
        System.out.println("Test CLASS: "+ iTestResult.getTestClass());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
