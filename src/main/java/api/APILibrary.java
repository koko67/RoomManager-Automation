package api;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONArray;
import utils.CredentialManager;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import static com.jayway.restassured.RestAssured.*;
/**
 * This class works with all http methods GET, POST, PUT and DELETE in the
 * API services of room manager
 * Created by Jorge Avila on 12/11/2015.
 * Updated by Ronald Butron on 12/14/2015
 */
public class APILibrary {

    private Logger log = Logger.getLogger("APILibrary");
    public final static String AUTHORIZATION_TYPE = "jwt ";
    public final static String AUTHORIZATION_HEADER = "Authorization";
    public final static String CONTENT_TYPE = "application/json";
    public final static String ENCODE_TYPE = "UTF-8";
    public final static String BASIC_AUTHENTICATION = "Basic ";
    public static APILibrary instance = null;

    public APILibrary(){
       init();
    }

    public static APILibrary getInstance(){
        if(instance == null){
            instance = new APILibrary();
        }
        return  instance;
    }

    private void init(){
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
        return new JSONObject(response.asString());
    }

    /**
     * This method return all JSON objects from a collection
     * @param endPoint the URL
     * @return an Array of JSON Object
     */
    public JSONArray getAll(String endPoint){
        Response response = given().when().get(endPoint);
        return new JSONArray(response.asString());
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

        return new JSONObject(response.asString());
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

        return new JSONObject(response.asString());
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

    /**
     * This method encode a String into a Base64
     * @return a String encoded
     */
    public String encodeBase64Authentication(){
        String toEncode = CredentialManager.getInstance().getUserExchange() + ":" + CredentialManager.getInstance().getPasswordExchange();
        String authEncode = null;
        try{
           authEncode = Base64.getEncoder().encodeToString(toEncode.getBytes(ENCODE_TYPE));
        } catch (UnsupportedEncodingException e){
            log.error("Unsupported Encoding Exception " + e);
        }
        return authEncode;
    }

    /**
     * This method delete a object that use Basic authentication
     * @param endPoint the URL
     * @return the body of the response
     */
    public JSONObject delete(String endPoint){
        Response response = given()
                            .header(AUTHORIZATION_HEADER, BASIC_AUTHENTICATION + encodeBase64Authentication())
                            .when()
                            .delete(endPoint);
        return new JSONObject(response.asString());
    }

    /**
     * This method post using Basic Authentication
     * @param jsonObject recieve a JSONObject to post
     * @param endPoint the URL endPoint
     * @return a JSON object with the response
     */
    public JSONObject post(JSONObject jsonObject, String endPoint){
        Response response = given()
                .contentType(CONTENT_TYPE)
                .header(AUTHORIZATION_HEADER, BASIC_AUTHENTICATION + encodeBase64Authentication())
                .body(jsonObject.toString())
                .when()
                .post(endPoint);
        return new JSONObject(response.asString());
    }

}
