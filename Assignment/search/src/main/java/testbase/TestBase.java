package testbase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import api.helpers.SearchHelpers;
import io.github.bonigarcia.wdm.WebDriverManager;
import ui.constants.Constants;
import ui.library.Home;



public abstract class TestBase {

	Logger logger;
	protected WebDriver  driver;
	protected Wait<WebDriver> wait;
	protected String URL;
	private static String OS = System.getProperty("os.name").toLowerCase();

	//My Page class object declaration
	protected Home home;
	protected SearchHelpers searchHealper;

	public Constants constants=new Constants();
	protected String myURL;
	protected String timeNow;
	protected String timeResults;

	protected String SUT_URL_FILE_PATH=Constants.getFullPathOfFile("src/test/resources/SUT/URL.properties");
	protected WebDriverWait forceWait;
	String browserName="chrome";
	boolean isHeadless=false;


	@BeforeTest
	public void beforeTest(ITestContext testContext)
	{
		String logFileConfigurations;
		//log4g property configurations

		logger = Logger.getLogger(TestBase.class.getName());
		logFileConfigurations=Constants.getFullPathOfFile("src/test/resources/log4j.properties");

		PropertyConfigurator.configure(logFileConfigurations);
		URL=getPropertyValue(SUT_URL_FILE_PATH,"URL");//getting url from the CNP properties file
		if(URL==null)
		{
			logger.info("URL can not be empty, Please update the URL in '/SUT/CNP_URL.properties'");
			logger.info("TEST EXIT");
			System.exit(0);
		}
		logger.info("======================================");
		logger.info("BEFORE TEST");
		logger.info("======================================");


	}
	@AfterTest
	public void quitDriver()
	{
		try{
			logger.info("======================================");
			logger.info("AFTER TEST");
			logger.info("======================================");
			logger.info("Quiting driver");
			if(driver!=null)
			{
				driver.quit();
			}
			logger.info("EXECUTION COMPLETED");
		}catch(Exception ex)
		{
			logger.error(ex.getMessage());
		}
	}


	@AfterClass
	public void closeAllBrowser()
	{
		try {
			logger.info("======================================");
			logger.info("CLOSING BROWSER");
			logger.info("======================================");
			cleanUp();
			if(driver!=null)
			{
				driver.close();
				logger.info("CURRENT BROWSERS IS CLOSED");

			}else {
				logger.info("DRIVER IS ALREADY CLOSED");
			}
		}catch(NoSuchSessionException ex)
		{
			logger.info("Browser is not open");
		}
		catch(Exception  ex)
		{
			logger.info("Browser is not open");
			driver.close();
		}
	}


	/***
	 * @author sepal
	 * @param sourceDir
	 * @param dstDir
	 *Copy one directory contents to other directory
	 *
	 */
	public void copyDirectoryContents(String sourceDir,String dstDir)
	{
		try{
			File srcDir = new File(sourceDir);
			File destDir = new File(dstDir);
			try {
				FileUtils.copyDirectory(srcDir, destDir);
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				logger.info("Test Results Backup is completed");
			}
		}catch(Exception ex)
		{
			logger.error(ex.getMessage());
		}
	}



	public void cleanUp()
	{
		logger.info("Library CleanUP started ");
		home=null;
		logger.info("Library CleanUP finished ");
	}


	//Library class object Initialization

	@BeforeClass
	public void init()
	{
		//log4g property configuration
		logger = Logger.getLogger(TestBase.class.getName());
		URL=getPropertyValue(SUT_URL_FILE_PATH,"URL");//getting url from the CNP properties file

		selectMyBrowser(browserName);
		//explicit wait
		wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(Constants.TIMEOUTS))
				.pollingEvery(Duration.ofSeconds(Constants.POLLING_INTERVAL))
				.ignoring(NoSuchElementException.class);
		forceWait = new WebDriverWait(driver,Duration.ofSeconds(5000));
		logger.info("Library Initialization");
		home=new Home(driver);
		searchHealper=new SearchHelpers();

	}

	/***
	 * This method is used to start browser based based on user selection
	 * @author sepal
	 * @param browserName
	 */

	private void selectMyBrowser(String browserName)
	{
		try {

			browserName=getPropertyValue(SUT_URL_FILE_PATH, "BrowserType");
			browserSelection(browserName);
			borwserPropertySetting(browserName,isHeadless);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLECIT_WAIT));
		}

		catch(Exception ex)
		{
			logger.info("Exception in Select Browser");
			logger.error(ex.getMessage());
		}

	}



	/**** 
	 * @return driver
	 */
	public WebDriver getDriver()
	{
		return driver;
	}


	public void closeAllBrowser(WebDriver driv)
	{
		try {
			if(driv!=null)
			{
				for (String handle:driv.getWindowHandles())
				{
					driv.switchTo().window(handle);
					driver.close();
					System.out.println("ALL BROWSERS ARE CLOSED");
				}
			}else {
				logger.info("Driver is already closed");
			}
		}
		catch(Exception e)
		{
			logger.info("Excepiton is coming when closing browser");
			e.printStackTrace();
		}
	}




	private void borwserPropertySetting(String Browsername, boolean isHeadless) {
		Proxy proxy=new Proxy();
		//String proxy_url="proxy-wsa.esl.cisco.com:80";
		if (Browsername.equalsIgnoreCase("chrome")) {
			try {
				Map<String, Object> chromePrefs = new HashMap<String, Object>();
				ChromeOptions options = new ChromeOptions();
				//WebDriverManager.chromedriver().win().setup();
				if (isHeadless) {
					logger.info("Running headless mode");
					options.addArguments("--headless");
				}
				options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

				options.addArguments("--no-sandbox");
				options.addArguments("--disable-gpu");
				options.addArguments("--disable-software-rasterizer");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--dont-require-litepage-redirect-infobar");
				options.addArguments("--disable-extensions");
				options.addArguments("--trusted-download-sources");
				options.addArguments("--disable-client-side-phishing-detection");
				options.addArguments("--safebrowsing-enable-enhanced-protection");
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("safebrowsing.enabled", "true");
				chromePrefs.put("download.prompt_for_download", "false");
				options.setAcceptInsecureCerts(true);
				options.setExperimentalOption("prefs", chromePrefs);
				driver = new ChromeDriver(options);
				System.out.print("Proxy:"+proxy.getProxyType());
				logger.info("driver in base=" + driver);
				logger.info("driver creation is success");
			} catch (Exception ex) {
				logger.info(ex.getMessage());
			}
		}
		if (Browsername.equalsIgnoreCase("firefox")) {

			logger.info("driver creation is success");
		}
	}

	protected void browserSelection(String browserLabel)
	{
		try {
			if(isWindowsOS())
			{
				System.out.println("Windows");
				if(browserLabel.equalsIgnoreCase("chrome"))
				{
					WebDriverManager.chromedriver().setup();//100.0.4896.60
					//System.setProperty("webdriver.chrome.driver",constants.getFullPathOfFile("resource/drivers/chromedriver_win32/chromedriver.exe"));
				}
				if(browserLabel.equalsIgnoreCase("firefox"))
				{
					System.setProperty("webdriver.gecko.driver",Constants.getFullPathOfFile("resource/drivers/geckodriver-v0.30.0-win64/geckodriver.exe"));
				}
				if(browserLabel.equalsIgnoreCase("IE"))
				{
					System.out.println("Need to Define for IE");
				}
			}
			else if (isMacOS())
			{
				System.out.println("Mac");
				if(browserLabel.equalsIgnoreCase("chrome"))
				{
					WebDriverManager.chromedriver().setup();//100.0.4896.60
				}
				if(browserLabel.equalsIgnoreCase("firefox"))
				{
					System.out.println("Need to Define for firefox");
				}
				if(browserLabel.equalsIgnoreCase("IE"))
				{
					System.out.println("Need to Define for IE");
				}
			}
			else {
				System.out.println("Linux/Ubuntu");
				if(browserLabel.equalsIgnoreCase("chrome"))
				{
					WebDriverManager.chromedriver().setup();//100.0.4896.60
					//System.setProperty("webdriver.chrome.driver",constants.getFullPathOfFile("resource/drivers/chromedriver_linux64/chromedriver"));
				}
				if(browserLabel.equalsIgnoreCase("firefox"))
				{
					System.out.println("Need to Define for firefox");
				}
				if(browserLabel.equalsIgnoreCase("IE"))
				{
					System.out.println("Need to Define for IE");
				}
			}
		}catch(Exception ex)
		{
			System.out.println("Expection is in browser Selection"+ex.getMessage());
		}
	}


	public String getPropertyValue(String fileName,String propertyName)
	{
		String propValue="";
		try{
			FileReader reader=new FileReader(fileName);
			Properties p=new Properties();
			p.load(reader);
			propValue=p.getProperty(propertyName);
			System.out.println(propertyName+"===>"+propValue);
			reader.close();


		}catch(Exception Ex)
		{
			System.out.println("Error is coming while reading property file");
		}
		return propValue;
	}

	public boolean isWindowsOS() {

		return (OS.indexOf("win") >= 0);

	}

	public boolean isMacOS() {

		return (OS.indexOf("mac") >= 0);

	}

	public boolean isUnixOS() {

		return (OS.indexOf("ntu") > 0 || OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

	}

}
