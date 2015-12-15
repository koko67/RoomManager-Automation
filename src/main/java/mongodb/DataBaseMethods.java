package mongodb;

import utils.CredentialManager;

/**
 * User: RonaldButron
 * Date: 12/15/15
 */
public class DataBaseMethods {

    /**
     * This method connect to the database and return a value searched then close the database
     * @param collection name of the collection
     * @param keyId name of the key
     * @param valueId value of the key
     * @param value and value searched
     * @return
     */
    public static String obtainKeyValue(String collection, String keyId, String valueId, String value){
        String ip = CredentialManager.getInstance().getIp();
        DataBaseDriver.getInstance().createConnectionToDB(ip);
        String valueToReturn = DataBaseDriver.getInstance().getKeyValue(collection, keyId, valueId, value);
        DataBaseDriver.getInstance().closeConnectionToDB();
        return valueToReturn;
    }
}
