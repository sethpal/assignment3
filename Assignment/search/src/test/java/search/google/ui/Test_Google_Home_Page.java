package search.google.ui;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.qameta.allure.Step;
import testbase.TestBase;

public class Test_Google_Home_Page extends TestBase{


	@BeforeMethod
	public void beforeMethod(Method testCaseName) {
		driver.get(URL);
	}


	//Allure reporting tool is used , so each step details cases be recorded in final report
	@Step("Verify Google Logo Is Present On the Home Page")
	@Test
	public void Verify_Google_Logo_IsPresentOnPage()
	{
		//In actual cases this message will come from the test input file, for now i have hard-coded it here
		String failureMessage="Googgle Logo is not Present";

		if(home.isGoogleLogoPresent())
		{
			Assert.assertTrue(true);
		}
		else {
			Assert.assertFalse(true,failureMessage);
		}
	}


	//Allure reporting tool is used , so each step details cases be recorded in final report
	@Step("Verify Search Box Is Present On the Google Home Page")
	@Test
	public void Verify_Search_Box_Is_Present_On_the_Google_Home_Page()
	{
		//In actual cases this message will come from the test input file, for now i have hard-coded it here
		String failureMessage="Googgle Search Box is not Present";

		if(home.isSearchBoxPresent())
		{
			Assert.assertTrue(true);
		}
		else {
			Assert.assertFalse(true,failureMessage);
		}
	}

	//Allure reporting tool is used , so each step details cases be recorded in final report
	@Step("Verify user is able to search text in google Search Box")
	@Test(dataProvider="testdata")
	public void Verify_User_Is_Able_To_Search__Text(String textToSearch)
	{
		//In actual cases this message will come from the test input file, for now i have hard-coded it here
		String failureMessage="Google Search is not working as expected";

		home.enterTextToSearch(textToSearch);
		home.clickOnGoogleSearch();
		String current_Time=driver.getTitle();
		if(current_Time.contains(textToSearch))
		{
			Assert.assertTrue(true);
		}
		else {
			Assert.assertFalse(true,failureMessage);
		}
	}
	
	@DataProvider(name="testdata")
	public Object[][] TestDataToSearch(){

	Object [][] searchData=new Object[3][1];

	searchData[0][0]="Awesome";
	
	searchData[1][0]="cricket match schedule";
	
	searchData[2][0]="username@gmail.com";

	
	return searchData;
	}

}
