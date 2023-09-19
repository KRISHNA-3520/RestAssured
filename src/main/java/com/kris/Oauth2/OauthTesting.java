package com.kris.Oauth2;

import static io.restassured.RestAssured.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import files.ReusableMethods;
import io.restassured.path.json.JsonPath;

public class OauthTesting {

	public static void main(String[] args) throws InterruptedException {

		// Get code
	/*	System.setProperty("webdriver.chrome.driver",
				"/Users/krishna.j/eclipse-workspace/APIPractice/driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(
				"https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");

		driver.findElement(By.id("identifierId")).sendKeys("kjamadar26@gmail.com");
		driver.findElement(By.id("identifierNext")).click();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("reachable");
		driver.findElement(By.id("passwordNext")).click();
		Thread.sleep(3000);
		String url = driver.getCurrentUrl();
		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&scope")[0];*/
		
		String code="4%2F0Adeu5BWzvCGu4agDZ1vBz0Ml1fbVrD1FBsZEpJ_R7y_urQ_RU6p7ijJP7lcecMJJ6NYAMQ";

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
		String response = given().queryParam("access_token", accessToken).when().log().all()
				.get("https://rahulshettyacademy.com/getCourse.php").asString();

		System.out.println(response);

	}

}
