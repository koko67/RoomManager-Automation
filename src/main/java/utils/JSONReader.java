package utils;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * User: RonaldButron
 * Date: 12/7/15
 */
public class JSONReader {

    public String filePath = "C:\\RoomManager-Automation\\src\\main\\resources\\config.json";
    private static JSONReader instance = null;
    private String id = null;
    private String adminURL = null;
    private String tabletURL = null;

    protected  JSONReader(){
        init();
    }

    public static JSONReader getInstance(){
        if (instance == null){
            instance = new JSONReader();
        }

        return instance;
    }

    private void init() {

        JSONParser parser = new JSONParser();
        JSONObject node;

        try {
            Object obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;
            node = (JSONObject) jsonObject.get("Environment");
            id = (String) node.get("id");
            adminURL = (String) node.get("adminURL");
            tabletURL = (String) node.get("tabletURL");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method obtain the Id of the environment
     * @return
     */
    public String getId(){

        return id;
    }

    /**
     * This method obtain the admin URL
     * @return admin the URL
     */
    public String getAdminURL(){

        return adminURL;
    }

    /**
     * This method obtain the tablet URL
     * @return tablet URL
     */
    public String getTabletURL(){

        return tabletURL;
    }

}
