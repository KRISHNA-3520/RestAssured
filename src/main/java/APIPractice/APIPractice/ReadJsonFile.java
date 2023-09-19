package APIPractice.APIPractice;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReusableMethods;
import files.payload;

public class ReadJsonFile {
	public static void main(String[] args) throws IOException {
		JsonPath js, js1;
		// POST(Add) place
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(
						Paths.get("/Users/krishna.j/eclipse-workspace/APIPractice/src/main/java/files/addPlace.json"))))
				.when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().asString();

		js = ReusableMethods.rawToJson(response);
		String place_id = js.getString("place_id");
		System.out.println(place_id);

		String new_address = "Boston House";

		// PUT(Update) place
		given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.updatePlace(place_id, new_address)).when().put("maps/api/place/update/json").then().log()
				.all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

		// GET place

		String getPlaceResponse = given().queryParam("key", "qaclick123").queryParam("place_id", place_id).when()
				.get("maps/api/place/get/json").then().assertThat().statusCode(200).extract().asString();

		js1 = ReusableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		Assert.assertEquals(actualAddress, new_address);
	}
}
