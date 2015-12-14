package api;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.json.JSONObject;
import utils.CredentialManager;

import static com.jayway.restassured.RestAssured.*;
/**
 * This class works with all http methods GET, POST, PUT and DELETE in the
 * API services of room manager
 * Created by Jorge Avila on 12/11/2015.
 */
public class APILibrary {

    public final static String AUTHORIZATION_TYPE = "jwt ";
    public final static String AUTHORIZATION_HEADER = "Authorization";
    public final static String CONTENT_TYPE = "application/json";


    public APILibrary() {
        RestAssured.baseURI = CredentialManager.getInstance().getRoomManagerService();
        RestAssured.useRelaxedHTTPSValidation();
    }

    /**
     * Get a JSON Object given its id
     * @param endPoint is the API Url endpoint to use for getting
     * @return the JSON Object
     */
    public JSONObject getById(String endPoint) {
        Response response = given().when().get(endPoint);
        JSONObject jsonElement = new JSONObject(response.asString());
        return jsonElement;
    }

    /**
     * Create a new Object in the database
     * @param jsonObject is the request object to send
     * @param token is the string Authorization
     * @param endPoint is the API Url endpoint to use for creating
     * @return
     */
    public JSONObject post(JSONObject jsonObject, String token, String endPoint){
        Response response = given()
                            .contentType(CONTENT_TYPE)
                            .header(AUTHORIZATION_HEADER, AUTHORIZATION_TYPE + token)
                            .body(jsonObject.toString())
                            .when()
                            .post(endPoint);

        JSONObject res = new JSONObject(response.asString());
        return res;
    }

    /**
     * Update a Object in the database
     * @param jsonObject is the request object to send
     * @param token is the string Authorization
     * @param endPoint is the API Url endpoint to use for updating
     * @return
     */
    public JSONObject put(JSONObject jsonObject, String token, String endPoint){
        Response response = given()
                .contentType(CONTENT_TYPE)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_TYPE + token)
                .body(jsonObject.toString())
                .when()
                .put(endPoint);

        JSONObject res = new JSONObject(response.asString());
        return res;
    }

    /**
     * delete a object from the database
     * @param token is the string Authorization
     * @param endPoint is the API Url endpoint to use for deleting
     * @return
     */
    public JSONObject delete(String token, String endPoint){
        Response response = given()
                .contentType(CONTENT_TYPE)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_TYPE + token)
                .when()
                .delete(endPoint);

        JSONObject res = new JSONObject(response.asString());
        return res;
    }
}
