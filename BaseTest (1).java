package generic;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public static ExtentReports extentReports;
	
	public WebDriver driver;
	public WebDriverWait wait;
	public ExtentTest extentTest;
	
		
	@BeforeSuite
	public void createReport() {
		extentReports=new ExtentReports();
		ExtentSparkReporter spark=new ExtentSparkReporter("./result/MyReport.html");
		extentReports.attachReporter(spark); 
	}
	
	@AfterSuite
	public void publishReport() {
		extentReports.flush();	
	}
	
	@BeforeMethod
	public void openApp(Method testMethod) throws MalformedURLException {
		String configPath="./config.properties";
		
		String testName=testMethod.getName();
		extentTest = extentReports.createTest(testName);
		
		String gird = Utility.getProperty(configPath,"GRID");
		extentTest.log(Status.INFO, "Use Grid to Execute?"+gird);
		
		
		String browser = Utility.getProperty(configPath,"BROWSER");
		extentTest.log(Status.INFO, "Browser is:"+browser);
		
		
		if(gird.equalsIgnoreCase("YES"))
		{
			String gridURL=Utility.getProperty(configPath, "GRIDURL");
			URL url=new URL(gridURL);
			
			DesiredCapabilities dc=new DesiredCapabilities();
			dc.setBrowserName(browser);
			
			driver = new RemoteWebDriver(url,dc);
		}
		else
		{
			if(browser.equals("chrome"))
			{
				WebDriverManager.chromedriver().setup();
				String path=System.getProperty("webdriver.chrome.driver");
				extentTest.log(Status.INFO, "Set the path of driver exe:"+path);
				extentTest.log(Status.INFO, "Open the browser");
				driver=new ChromeDriver();
			}
			else
			{
				WebDriverManager.firefoxdriver().setup();
				String path=System.getProperty("webdriver.gecko.driver");
				extentTest.log(Status.INFO, "Set the path of driver exe:"+path);
				extentTest.log(Status.INFO, "Open the browser");
				driver=new FirefoxDriver();
			}
		}
		
		
		
		String strITO = Utility.getProperty(configPath,"ITO");
		int iITO=Integer.parseInt(strITO);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(iITO));
		extentTest.log(Status.INFO, "Set the ITO:"+iITO);
		
		String strETO = Utility.getProperty(configPath,"ETO");
		int iETO=Integer.parseInt(strETO);
		wait=new WebDriverWait(driver, Duration.ofSeconds(iETO));
		extentTest.log(Status.INFO, "Set the ETO:"+iETO);
				
		String appURL = Utility.getProperty(configPath,"APPURL");
		driver.get(appURL);
		extentTest.log(Status.INFO, "Enter the URL:"+appURL);
	}
	
	@AfterMethod
	public void closeApp() {
		
		extentTest.log(Status.INFO, "Close the browser");
		driver.quit();
	}
}
