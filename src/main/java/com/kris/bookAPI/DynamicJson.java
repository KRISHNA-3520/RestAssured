package com.kris.bookAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReusableMethods;
import files.payload;

public class DynamicJson {

	@Test(dataProvider = "BooksData")
	public void addBook(String isbn,String aisle) {

		RestAssured.baseURI = "https://216.10.245.166";

		// ADD BOOK
		String response = given().log().all().header("Content-Type", "application/json")
				.body(payload.AddBook(isbn, aisle)).relaxedHTTPSValidation().when().post("/Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().asString();

		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);

	}
	
	
	@Test
	public void deleteBook(String id) {
		RestAssured.baseURI = "https://216.10.245.166";

		// ADD BOOK
		String response = given().log().all().header("Content-Type", "application/json")
				.body(payload.deleteBook(id)).relaxedHTTPSValidation().when().post("/Library/DeleteBook.php ")
				.then().log().all().assertThat().statusCode(200).extract().asString();

	//	JsonPath js = ReusableMethods.rawToJson(response);
	//	String id = js.get("ID");
	//	System.out.println(id);
	}

	@DataProvider(name = "BooksData")
	public Object[][] getData() {
		return new Object[][] { { "abcd", "1001" }, { "efgh", "1002" }, { "ijkl", "1003" } };
	}
	
	
}
