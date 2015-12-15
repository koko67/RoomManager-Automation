package api;

import commons.DomainAppConstants;
import entities.Location;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: josecardozo
 * Date: 12/15/15
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class MethodsAPI {

    public static JSONObject get(String endPoint){
        JSONObject response = APILibrary
                                        .getInstance()
                                        .getById(endPoint);
        return response;
    }

    public static JSONObject post(JSONObject jsonLocation, String endPoint) {
        JSONObject response = APILibrary.getInstance()
                                        .post(jsonLocation,endPoint);
        return  response;
    }
}
