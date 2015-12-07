package utils;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * User: RonaldButron
 * Date: 12/7/15
 */
public class JSONReader {

    private String filePath = "";

    public String readJsonFile(String node, String tag) {

        JSONParser parser = new JSONParser();
        JSONObject son = new JSONObject();
        String value = null;
        try {
            Object obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;
            son = (JSONObject) jsonObject.get(node);
            value = (String) son.get(tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
