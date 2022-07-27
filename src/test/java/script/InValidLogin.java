package script;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import generic.BaseTest;
import generic.Excel;
import page.LoginPage;

public class InValidLogin extends BaseTest{
	@Test(priority = 2)
	public void testInvalidLogin() {
		String un=Excel.getCellData(INPUTXL_PATH, "InvalidLogin",1, 0);
		String pw=Excel.getCellData(INPUTXL_PATH, "InvalidLogin",1, 1);
//		1. Enter invalid user name
		LoginPage loginPage=new LoginPage(driver);
		loginPage.setUserName(un);
		extentTest.log(Status.INFO, "Enter invalid user name:"+un);
		
		
//		2. Enter invalid password
		loginPage.setPassword(pw);
		extentTest.log(Status.INFO, "Enter invalid password:"+pw);
		
//		3. click on login button
		loginPage.clickLoginButton();
		extentTest.log(Status.INFO, "Click on login Button");
		
//		4. verify that err msg is displayed
		boolean result = loginPage.verifyErrMsgDisplayed(wait);
		Assert.assertTrue(result,"Err Msg is not displayed");
		extentTest.log(Status.INFO, "Verify Err Msg is displyed");
	}
}
