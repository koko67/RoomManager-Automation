package api;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.json.JSONObject;

import static com.jayway.restassured.RestAssured.*;
/**
 * This class works with all http methods GET, POST, UPDATE and DELETE in the
 * API services of room manager
 * Created by Jorge Avila on 12/11/2015.
 */
public class APILibrary {

    public APILibrary() {
        RestAssured.baseURI = "https://172.20.208.241:4040";
        RestAssured.useRelaxedHTTPSValidation();
    }

    /**
     * Get a JSON Object given its id
     * @param endPoint is the URL that return the JSON Object
     * @return the JSON Object
     */
    public JSONObject getById(String endPoint) {
        Response response = given().when().get(endPoint);
        JSONObject jsonElement = new JSONObject(response.asString());
        return jsonElement;
    }
}
