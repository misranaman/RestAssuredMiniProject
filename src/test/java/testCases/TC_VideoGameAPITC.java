package testCases;

import org.etsi.uri.x01903.v13.ResponderIDType;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class TC_VideoGameAPITC {

	@Test(priority=1)
	public void getAllVideoGame() {

		given()
		.when().get("http://localhost:8080/app/videogames")
		.then().statusCode(200);
	}
	
	@Test(priority=2)
	public void test_addNewVideoGame() {
		
		HashMap data=new HashMap();
		data.put("id", "100");
		data.put("name", "SpiderMan");
		data.put("releaseDate", "2021-01-07T15:55:58.145Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");

		Response res=
				given()
				.contentType("application/json")
				.body(data)

				.when()
				.post("http://localhost:8080/app/videogames")

				.then()
				.statusCode(200)
				.log().body()
				.extract().response();

		String jsonString=res.asString();

		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
		
	}
	
	@Test(priority=3)
	public void test_getVideoGame() {

		given()
		.when()
		.get("http://localhost:8080/app/videogames/100")

		.then()
		.statusCode(200)
		.log().body()
		.body("videoGame.id", equalTo("100"))
		.body("videoGame.name",equalTo("SpiderMan"));

	}
	
	@Test(priority=4)
	public void test_UpdateVideoGame() {
		
		HashMap data=new HashMap();
		data.put("id", "100");
		data.put("name", "BatMan");
		data.put("releaseDate", "2021-01-07T15:55:58.145Z");
		data.put("reviewScore", "1000");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		given()
			.contentType("application/json")
			.body(data)
		
		.when()
			.put("http://localhost:8080/app/videogames/100")
			
		.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id", equalTo("100"))
			.body("videoGame.name",equalTo("BatMan"));
	}
	
	@Test(priority=5)
	public void test_DeleteVideoGame() {
		
		Response res=
		given()
		.when()
			.delete("http://localhost:8080/app/videogames/100")
			
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
			
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
	}

}
