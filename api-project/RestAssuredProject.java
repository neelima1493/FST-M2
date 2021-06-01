package restAssuredProject;

import static io.restassured.RestAssured.given;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestAssuredProject {
	
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    
    String sshkey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCdYHmLevIFsxnCLWA9x+oBPDkEUMsWuZw/sq9C8zBuwNYizB80YAgyEEBIC1RZltb8cHsT2xOwGYL2yATPnHWCuQ08w3D4zmNpuTF+herPess42u7UXIe4z6pI457cVd3z7RXl9xi8KxQzNjsQ3OV9W9It6QUdTUTrzHp/4KRitweRmHxlHftdfK6Y5tzp0T8rcMwa6eMhImEsyw/wVVDznkFkJO1JkbI17mpgG/E0rhpI8Jg9Q1YPoRJPh12Pg4gqpMlFDmn4zWfDvuXhXzqdlO9+1l4OfN3LA7d8Mi45AqKMUYVsvaIBVUT0z5bZ5y1TaZbvOYD1X6mwc5jQqUT9 azuread\\neelimakadali@LAPTOP-0J7NNLBG";
    int responseID;

    @BeforeClass
    public void setUp() {
        requestSpec = new RequestSpecBuilder()
                // Set content type
                .setContentType(ContentType.JSON)
                // Set Authorization header token
                .addHeader("Authorization", "token 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")
                // Set base URL
                .setBaseUri("https://api.github.com")
                // Build request specification
                .build();
    }
    
    
    @Test(priority=1)
    public void addSSHKey() {
        String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"" + sshkey + "\"}";
        Response response = given().spec(requestSpec) // Use requestSpec
                .body(reqBody) // Send request body
                .when().post("/user/keys"); // Send POST request

        // Assertions
        response.then().statusCode(201);
      //Print the response to the console or to the TestNG report using
        String StrResponse = response.asPrettyString();
        responseID = response.then().extract().path("id");
        Reporter.log(StrResponse);
        System.out.println("The SSH Key ID is: "+responseID);
        System.out.println("response of AddSSHKey: " + response.asPrettyString());
    }
    
    @Test(priority=2)
    public void getSSHKey() {
        Response response = given().spec(requestSpec) // Use requestSpec
                        .when().get("/user/keys"); // Send GET request

       
        String StrResponse = response.asPrettyString();
        // Assertions
        response.then().statusCode(200);
        //Print the response to the console or to the TestNG report using
        Reporter.log(StrResponse);
        System.out.println("response of GetSSHKey: " + response.asPrettyString());
 
    }
    
    @Test(priority=3)
    public void deleteSSHKey() {
        Response response = given().spec(requestSpec) // Use requestSpec
                .pathParam("keyId", responseID) // Add path parameter
                .when().delete("/user/keys/{keyId}"); // Send DELETE request

      //Print the response to the console or to the TestNG report using
        String StrResponse = response.asPrettyString();
        Reporter.log(StrResponse);
        System.out.println("response of DeleteSSHKey: " + response.asPrettyString());
        
        // Assertions
        response.then().statusCode(204);
    }
}
