package ui.library;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ui.utils.LibUtils;



public class Home extends LibUtils {


	@FindBy(xpath="//*[@alt='Google']")
	WebElement googleLogo;
	@FindBy(xpath="//input[@name='q' and @type='text']")
	WebElement searchBox;
	@FindBy(xpath="(//input[@name='btnK' and @role='button'])[2]")
	WebElement googleSearchButton;

	@FindBy(xpath="//*[@jsname='Tg7LZd']/div")
	WebElement searchMagnifyingGlass;

	/***
	 * constructor
	 */
	public Home(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
		initLibUtils(driver);

	}

	/**
	 * This is responsible to enter the text in search box;
	 * @param txt
	 */
	public void enterTextToSearch(String txt)
	{
		searchBox.clear();
		setText(searchBox,txt);
	}


	/**
	 * This is responsible to enter the text in search box;
	 * @param txt
	 */
	public void clickOnGoogleSearch()
	{
		click(googleSearchButton);
	}


	/**
	 * This is responsible to enter the text in search box;
	 * @param txt
	 */
	public void clickOnsearchMagnifyingGlass()
	{
		click(searchMagnifyingGlass);
	}

	public boolean isGoogleLogoPresent()
	{
		boolean isPresent=false;
		if(isElementPresent(googleLogo))
		{
			isPresent=true;
		}
		return isPresent;
	}

	public boolean isSearchBoxPresent()
	{
		boolean isPresent=false;
		if(isElementPresent(searchBox))
		{
			isPresent=true;
		}
		return isPresent;
	}
	
}
