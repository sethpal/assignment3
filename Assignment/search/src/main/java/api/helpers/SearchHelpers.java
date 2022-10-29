package api.helpers;

import java.util.HashMap;

import api.contants.EndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SearchHelpers {

	private String BASE_URL=EndPoints.BASE_URL;
	private String API_KEY=EndPoints.API_KEY;
	private String CSE_ID=EndPoints.SEARCH_ENGINE_ID;
	//RestAssured

	public SearchHelpers()
	{
		RestAssured.baseURI= BASE_URL;
		RestAssured.useRelaxedHTTPSValidation();
	}

	public HashMap<String, String> getSearchContents(String textToSearch)
	{
		String endPoint="key="+API_KEY+"&cx="+CSE_ID+"&q="+textToSearch;
		Response response=RestAssured
				.given()
				.contentType(EndPoints.CONTENT_TYPE)
				.get(BASE_URL+endPoint).andReturn();
		HashMap<String, String> results=new HashMap<String, String>();
		//For now I am just validating status code.
		results.put(EndPoints.STATUS,String.valueOf(response.statusCode()));
		return results;
	}


}
