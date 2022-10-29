package ui.utils;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import ui.constants.Constants;

public class LibUtils {

	final static Logger logger = Logger.getLogger(LibUtils.class.getName());
	private WebDriver driver;
	protected static Wait<WebDriver> wait;
	Actions action;

	protected WebDriverWait forceWait;
	protected int implicitWaitTime=30;
	protected int implicitWait_2Sec=2;
	protected String URL;
	protected String SUT_URL=Constants.getFullPathOfFile("SUT/URL.properties");


	public void initLibUtils(WebDriver myDriver)
	{	
		this.driver=myDriver;
		wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(Constants.TIMEOUTS)) 
				.pollingEvery(Duration.ofSeconds(Constants.POLLING_INTERVAL)) 
				.ignoring(NoSuchElementException.class);
		forceWait = new WebDriverWait(driver, Duration.ofSeconds(60));
	}

	/***
	 * This is used to click on the webelement
	 * @param element
	 */
	public void click(WebElement element) {
		try {
			if(element.isEnabled()) {
				element.click();
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
	}

	/***
	 * This is used to set input text in the text box
	 * @param element
	 */
	public void setText(WebElement element,String text) {
		try {
			if(element.isDisplayed() && element.isEnabled()) {
				element.sendKeys(text);
			}
		}
		catch(Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	public boolean isElementPresent(WebElement element)
	{
		boolean isPresent=false;
		try {
			if(element.isDisplayed())
			{
				isPresent=true;
			}
		}catch(Exception ex)
		{
			return false;
		}
		return isPresent;
	}

}
