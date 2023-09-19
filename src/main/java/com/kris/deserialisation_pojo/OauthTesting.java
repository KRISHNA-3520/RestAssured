package com.kris.deserialisation_pojo;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import files.ReusableMethods;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OauthTesting {

	public static void main(String[] args) throws InterruptedException {

		String[] courseTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };

		String code = "4%2F0Adeu5BW_78ndSf9kPTkBXykZLpRtNuIVKxH4Vz2D6joM2pDvm1Eudh0V_9k3QanmkYicJw";

		// Get accessToken
		String accessTokenResponse = given().urlEncodingEnabled(false).queryParam("code", code)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		JsonPath js = ReusableMethods.rawToJson(accessTokenResponse);
		String accessToken = js.getString("access_token");

		// get Courses
		GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

		List<api> courseTitle = gc.getCourses().getApi();

		for (int i = 0; i < courseTitle.size(); i++) {
			if (courseTitle.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(courseTitle.get(i).getPrice());
			}
		}

		List<webAutomation> w = gc.getCourses().getWebAutomation();
		ArrayList<String> a = new ArrayList<String>();
		for (int i = 0; i < w.size(); i++) {
			a.add(w.get(i).getCourseTitle());
		}

		List<String> expected = Arrays.asList(courseTitles);
		Assert.assertTrue(a.equals(expected));
	}

}
