package APIPractice.APIPractice;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidations {

	@Test
	public void Sum() {

		// Verify if sum of all course prices matches with Purachase amount
		int sum = 0;

		JsonPath js = new JsonPath(payload.CoursePrice());
		int course = js.getInt("courses.size()");
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(course);

		for (int i = 0; i < course; i++) {
			int price = js.getInt("courses[" + i + "].price");
			int copies = js.getInt("courses[" + i + "].copies");

			int amount = price * copies;
			sum=sum+amount;
			System.out.println(sum);
			System.out.println(purchaseAmount);
			
		}
		Assert.assertEquals(sum, purchaseAmount);
	}

}
