package utils;


import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



/**
 * User: RonaldButron
 * Date: 12/8/15
 */
public class JSONReader {

    private JSONObject jsonObjectMain;
    private Logger log = Logger.getLogger("JSONReader");
    public JSONReader(String filePath) {
        parseJSON(filePath);
    }

    private void parseJSON(String filePath) {
        try{
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            jsonObjectMain = (JSONObject) jsonParser.parse(reader);
        } catch (FileNotFoundException e){
            log.error("File Not Found Exception when reading the configuration file " + e);
        } catch (ParseException ex){
            log.error("Parse Exception Error when reading the configuration file");
        } catch (IOException ex){
            log.error("IO Exception Error when reading the configuration file");
        } catch (NullPointerException e){
            log.error("Null Point Exception Error when reading the configuration file");
        }
    }

    /**
     * This method get the value of a Key from a Json file
     * @param objectName name of the environment
     * @param idKey id key of the environment
     * @param idValue id value of the environment
     * @param key reference of the value searched inside a Json file
     * @return the value of the key searched
     */
    public String getKeyValue(String objectName, String idKey, String idValue, String key) {
        JSONObject jsonObject = getJSONObjectFromArrayById(objectName, idKey, idValue);
        return getKeyValueFromJSONObject(jsonObject, key);
    }

    /**
     * This method obtain the value of a node inside a Json File
     * @param objectName name of the environment
     * @param idKey id key of the environment
     * @param idValue id value of the environment
     * @param keyNode JSON object searched inside a Json file
     * @param key reference of the value searched inside a Json file
     * @return the
     */
    public String getKeyValue(String objectName, String idKey, String idValue, String keyNode, String key){
        JSONObject jsonObject = getJSONObjectFromArrayById(objectName, idKey, idValue);
        JSONObject jsonObject1 = getJSONObjectFromAJSONObject(jsonObject, keyNode);
        return getKeyValueFromJSONObject(jsonObject1, key);
    }

    /**
     * This method obtain a JSon from another JSon object
     * @param jsonObject reference to search the Json object
     * @param idKey reference of the node searched
     * @return the Json object searched
     */
    private JSONObject getJSONObjectFromAJSONObject(JSONObject jsonObject, String idKey ){

      return  (JSONObject) jsonObject.get(idKey) ;
    }

    /**
     * This method obtain the key value from a Json object
     * @param jsonObject to search the key value
     * @param key searched
     * @return return the value of the key
     */
    private String getKeyValueFromJSONObject(JSONObject jsonObject, String key) {

        return (String) jsonObject.get(key);
    }

    /**
     * This method obtain a specific Json object from array of Json
     * @param objectName name of the object
     * @param idKey id key searched
     * @param idValue value of the id key searched
     * @return a specific Json object
     */
    private JSONObject getJSONObjectFromArrayById(String objectName, String idKey, String idValue) {
        JSONObject jsonObject = null;
        JSONArray array = (JSONArray) jsonObjectMain.get(objectName);
        for (Object arr : array){
            jsonObject = (JSONObject) arr;
            if (jsonObject.get(idKey).equals(idValue)){
                break;
            }
        }
        return jsonObject;
    }

}
