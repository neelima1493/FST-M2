package restAssuredSession1;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.List;

import io.restassured.http.ContentType;
import io.restassured.http.*;
import io.restassured.response.Response;

public class TestPetAPI {

    @Test
    public void GetPetDetails() {
        // Specify the base URL to the RESTful web service
        baseURI = "https://petstore.swagger.io/v2/pet";

        // Make a request to the server by specifying the method Type and 
        // resource to send the request to.
        // Store the response received from the server for later use.
        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .when().get("/findByStatus?status=sold"); // Run GET request

        // Now let us print the body of the message to see what response
        // we have received from the server
        String responseBody = response.getBody().asString();
        String responseBodyPretty = response.getBody().prettyPrint();
        System.out.println("Response Body is =>  " + responseBody);
        System.out.println("Response Body is =>  " + responseBodyPretty);

        // Extract name from response
        String responseJSON_name = response.then().extract().path("[0].name");
        System.out.println("Name of the first pet => " + responseJSON_name);
        
        //get header details
        List<Header> responseHeaders = response.getHeaders().asList();
        System.out.println("Response Headers are => " + responseHeaders);
        Headers responseHeadersExtract = response.then().extract().headers();
        System.out.println("Extracted Response Headers are => " + responseHeadersExtract);
        
        //Asserting headers
        response.then().extract().headers().hasHeaderWithName("Content-Type");

        // Assertions
        response.then().statusCode(200);
        response.then().body("[0].status", equalTo("sold"));
    }
    
 // Set Base URL
    String ROOT_URI = "https://petstore.swagger.io/v2/pet";

    @Test
    public void AddNewPet() {
        // Write the request body
        String reqBody = "{\"id\": 149307, \"name\": \"Oreo\",\"status\": \"Cutie\"}";

        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .body(reqBody).when().post(ROOT_URI); // Send POST request

        // Print response of POST request
        String body = response.getBody().asPrettyString();
        System.out.println(body);
    }

}