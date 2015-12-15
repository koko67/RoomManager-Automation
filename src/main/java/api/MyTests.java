package api;

import org.json.JSONObject;

/**
 * Created by jorgeavila on 12/15/2015.
 */
public class MyTests {
    public static void main(String[] args){
        JSONObject location = new JSONObject();
        location.put("name", "fundacion-jala");
        location.put("customName", "fundacion-jala");
        String locationsEndPoint = EndPoints.LOCATIONS;

        //APILibrary.getInstance().post(location, TokenAPI.getInstance().getToken("test", "Client123", "local"), locationsEndPoint);
    }
}
