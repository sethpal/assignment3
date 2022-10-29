package search.google.api;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.contants.EndPoints;
import io.qameta.allure.Step;
import testbase.TestBase;

public class Test_Google_Search extends TestBase{


	//Allure reporting tool is used , so each step details cases be recorded in final report
	@Step("Verify user is able to search text in google Search Box")
	@Test(dataProvider="testdata")
	public void Verify_User_Is_Able_To_Search__Text(String textToSearch)
	{
		//In actual cases this message will come from the test input file, for now i have hard-coded it here
		String failureMessage="Google Search is not working as expected";
		HashMap<String, String> results=new HashMap<String, String>();
		results=searchHealper.getSearchContents(textToSearch);
		if(results.get(EndPoints.STATUS).equals("200"))
		{
			Assert.assertTrue(true);
		}else {
			Assert.assertFalse(true,failureMessage);
		}
		System.out.println("=============================");
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
