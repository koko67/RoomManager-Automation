package api;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import commons.DomainAppConstants;
import org.json.JSONObject;
import utils.CredentialManager;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by jorgeavila on 12/12/2015.
 */
public class TokenAPI {

    private final static String URI_SERVICE = CredentialManager.getInstance().getRoomManagerService();

    public static TokenAPI instance = null;

    public TokenAPI(){
        init();
    }

    public static TokenAPI getInstance(){
        if(instance == null){
            instance = new TokenAPI();
        }
        return  instance;
    }

    private void init(){
        RestAssured.baseURI = URI_SERVICE;
        RestAssured.useRelaxedHTTPSValidation();
    }

    /**
     * Gets the token for a user account
     * @return the token for the user account
     */
    public String getToken(){
        String userName = CredentialManager.getInstance().getUserNameAdmin();
        String password = CredentialManager.getInstance().getPasswordAdmin();

        JSONObject request = new JSONObject();
        request.put(DomainAppConstants.USERNAME, userName);
        request.put(DomainAppConstants.PASSWORD, password);
        request.put(DomainAppConstants.AUTHENTICATION, DomainAppConstants.LOCAL_AUTHENTICATION);

        Response response = given()
                .contentType(APILibrary.CONTENT_TYPE)
                .body(request.toString())
                .when()
                .post(DomainAppConstants.LOGIN_SERVICE);

        JSONObject auth = new JSONObject(response.asString());
        return auth.getString(DomainAppConstants.TOKEN);
    }

}
