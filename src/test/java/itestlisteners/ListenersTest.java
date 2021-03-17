package itestlisteners;
import resources.Base;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenersTest extends Base implements ITestListener {

	 public void onFinish(ITestContext arg0) {					
	        				
	        		
	    }		

	    public void onStart(ITestContext arg0) {					
	       			
	        		
	    }		

	    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {					
	        // TODO Auto-generated method stub				
	        		
	    }		

	    public void onTestFailure(ITestResult result) {					
	        
	        try {
				takeScreenshot(driver, result.getMethod().getMethodName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        		
	    }		

	    public void onTestSkipped(ITestResult arg0) {					
	        // TODO Auto-generated method stub				
	        		
	    }		

	    public void onTestStart(ITestResult arg0) {					
	       			
	        		
	    }		

	    public void onTestSuccess(ITestResult arg0) {					
	        // TODO Auto-generated method stub				
	        		
	    }
}
