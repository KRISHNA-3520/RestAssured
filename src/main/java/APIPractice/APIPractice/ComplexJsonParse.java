package APIPractice.APIPractice;

import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	
	
	public static void main(String args[]) {
		JsonPath js = new JsonPath(payload.CoursePrice());
		int count = js.getInt("courses.size()");
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		String course1 = js.getString("courses[0].title");
		
		//Print course count
		System.out.println(count);
		
		//Print purchase amount
		System.out.println(purchaseAmount);
		
		//Print title of first course
		System.out.println(course1);
		
		//Print all course title and their respective prices
		for(int i=0;i<count;i++) {
			System.out.println(js.getString("courses["+i+"].title"));
			System.out.println(js.getString("courses["+i+"].price"));
		}
		
		//Print number of copies sold by course RPA
		for(int i=0;i<count;i++) {
			if(js.getString("courses["+i+"].title").equals("RPA")) {
				System.out.println(js.getString("courses["+i+"].copies"));
				break;
			}
			
			
		}
	}
	

}
