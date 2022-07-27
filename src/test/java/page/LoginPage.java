package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	//CacheLookup - find element only once and reuse same address every time-better performace
	
	@FindBy(id="username")
	@CacheLookup				 
	private WebElement unTB;
	
	@FindBy(name="pwd")
	private WebElement pwTB;
	
	
//	@FindBy(how = How.XPATH,using ="//div[.='Login ']")
	
	@FindBy(xpath="//div[.='Login ']")
	private WebElement loginBTN;
	
	@FindBy(xpath="//span[contains(.,'invalid')]")
	private WebElement errMsg;
	
	public LoginPage(WebDriver driver ) {
		PageFactory.initElements(driver, this);
	}
	
	public void setUserName(String un) {
		unTB.sendKeys(un); 
	}
	
	public void setPassword(String pw) {
		pwTB.sendKeys(pw);
	}
	
	public void clickLoginButton() {
		loginBTN.click();
	}
	
	public boolean verifyErrMsgDisplayed(WebDriverWait wait) {
		try
		{
			wait.until(ExpectedConditions.visibilityOf(errMsg));
			return true;
		}
		catch (Exception e) 
		{
			return false;
		}
	}
	
}
