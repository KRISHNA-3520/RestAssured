package com.kris.jiraAPI;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import files.ReusableMethods;

public class JiraTest {

	public static void main(String[] args) {

		RestAssured.baseURI = "http://localhost:8080";

		// Login Scenario
		SessionFilter session = new SessionFilter();
		String response = given().header("Content-Type", "application/json")
				.body("{\n" + "    \"username\": \"kjamadar26\",\n" + "    \"password\": \"sachin@200\"\n" + "}")
				.filter(session).when().post("/rest/auth/1/session").then().log().all().extract().asString();

		// Add comment
		String message="this comment is created from POSTMAN";
		String addCommentResponse = given().pathParam("key", "10026").header("Content-Type", "application/json")
				.body("{\n" + "    \"body\": \""+message+"\",\n" + "    \"visibility\": {\n"
						+ "        \"type\": \"role\",\n" + "        \"value\": \"Administrators\"\n" + "    }\n" + "}")
				.filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat()
				.statusCode(201).extract().response().asString();
		
		JsonPath js1=ReusableMethods.rawToJson(addCommentResponse);
		String commentId=js1.getString("id");

		// Add attachment
		given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("key", "10026")
				.header("Content-Type", "multipart/form-data").multiPart("file", new File("APIText.txt")).when()
				.post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);

		// Get issue
		String issueDetails = given().filter(session).pathParam("key", "10026").queryParam("fields", "comment").log().all().when()
				.get("/rest/api/2/issue/{key}").then().log().all().extract().response().asString();
		System.out.println(issueDetails);

		
		JsonPath js = ReusableMethods.rawToJson(issueDetails);
		int commentCount =  js.getInt("fields.comment.comments.size()");
		
		for(int i=0;i<commentCount;i++) {
			if(js.get("fields.comment.comments["+i+"].id").equals(commentId)) {
				Assert.assertEquals(js.get("fields.comment.comments["+i+"].body"),message);
			}
		}
	}

}
