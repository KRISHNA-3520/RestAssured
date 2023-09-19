package com.kris.ecomAPI;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import files.ReusableMethods;

public class EcommerceAPITest {

	public static void main(String[] args) {

		// Login
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("kjamadar26@gmail.com");
		loginRequest.setUserPassword("sachin@200");

		RequestSpecification reqLogin = given().spec(req).body(loginRequest);

		LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().extract().response()
				.as(LoginResponse.class);

		// Add Product
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", loginResponse.getToken()).build();

		RequestSpecification reqAddproduct = given().spec(addProductBaseReq).param("productName", "Zebpay")
				.param("productAddedBy", loginResponse.getUserId()).param("productCategory", "fashion")
				.param("productSubCategory", "shirts").param("productPrice", "11500")
				.param("productDescription", "Addias Originals").param("productFor", "women")
				.multiPart("productImage", new File("productImage.png"));

		String addProductResponse = reqAddproduct.when().post("/api/ecom/product/add-product").then().log().all()
				.extract().response().asString();

		JsonPath js = ReusableMethods.rawToJson(addProductResponse);
		String productId = js.getString("productId");

		// Create Order
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", loginResponse.getToken()).setContentType(ContentType.JSON).build();

		OrderDetails orderDetail = new OrderDetails();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);

		List<OrderDetails> orderDetailList = new ArrayList<OrderDetails>();
		orderDetailList.add(orderDetail);

		Orders order = new Orders();
		order.setOrders(orderDetailList);

		RequestSpecification createOrderReq = given().spec(createOrderBaseReq).body(order);

		String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all()
				.extract().response().asString();
		System.out.println(responseAddOrder);
		
		JsonPath js2=ReusableMethods.rawToJson(responseAddOrder);
		String orderId = js2.getString("orders[0]");

		// Delete Product
		RequestSpecification deleteProdBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", loginResponse.getToken()).setContentType(ContentType.JSON).build();

		RequestSpecification deleteProdReq = given().spec(deleteProdBaseReq).pathParam("productId", productId);

		String deleteProdResponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then()
				.log().all().extract().response().asString();
		JsonPath js1 = ReusableMethods.rawToJson(deleteProdResponse);
		String deleteMessage = js1.getString("message");
		Assert.assertEquals("Product Deleted Successfully", deleteMessage);
		System.out.println(deleteMessage);
		
		//Delete Order
		RequestSpecification deleteOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", loginResponse.getToken()).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteOrderReq=given().spec(deleteOrder).pathParam("orderID", orderId);
		
		String orderDeleteResponse=deleteOrderReq.when().delete("/api/ecom/order/delete-order/{orderID}").then().extract().response().asString();
		
		JsonPath js3=ReusableMethods.rawToJson(orderDeleteResponse);
		System.out.println(js3.getString("message"));
	}

}
