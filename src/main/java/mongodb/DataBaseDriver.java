package mongodb;

import com.mongodb.*;
import commons.DomainAppConstants;
import org.apache.log4j.Logger;
import utils.CredentialManager;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Ronald Butron
 * Date: 12/9/15
 */
public class DataBaseDriver {

    private Logger log = Logger.getLogger("DataBaseDriver");
    private MongoClient mongoClient;
    private DB db;
    private DBCollection collection;
    private static DataBaseDriver instance;

    public DataBaseDriver(){
        init();
    }

    public static DataBaseDriver getInstance(){
        if(instance == null){
            instance = new DataBaseDriver();
        }
        return  instance;
    }

    private void init(){

        //IPNumber y el puerto

    }

    /**
     * This method connect remote to a mongo data base
     * @param IPNumber number of the Ip where is the database
    */
    public void createConnectionToDB(String IPNumber){
       try{
          log.info("Connecting to the mongo DB");
          mongoClient = new MongoClient(IPNumber, 27017);
          db = mongoClient.getDB(DomainAppConstants.DB_SERVER_NAME);
       } catch (UnknownHostException e){
          log.error("Unknown Host Exception" + e);
       }
    }

    /**
     * This method return a specific DBObject
     * @param collect value to obtain the collection
     * @param keyId reference of the Key to obtain
     * @param valueId the value of the
     * @return dababase object
     */
    public DBObject getADBObjectFromACollection(String collect, String keyId, String valueId){
        DBObject object = null;
        BasicDBObject query = new BasicDBObject(keyId, valueId);
        collection = db.getCollection(collect);
        DBCursor cursor = collection.find(query);
        try {
            while(cursor.hasNext()) {
                object = cursor.next();
            }
        } finally {
            cursor.close();
        }
        return object;
    }

    /**
     * This method return the value of the a specific key
     * @param collect the name of the collection
     * @param keyId   Key id reference to look
     * @param valueId the value of the reference
     * @param key the key of the value searched
     * @return the value of the searched
     */
    public String getKeyValue(String collect, String keyId, String valueId, String key){
        DBObject dbObject = getADBObjectFromACollection(collect, keyId, valueId);
        return  dbObject.get(key).toString();
    }

    /**
     * This method update a value of the of a document
     * @param collect name of the collection
     * @param keyId the name of the key searched
     * @param valueId the value of the key
     * @param keyToUpdate the name of the key to update
     * @param valueToUpdate the new value of the key
     */
    public void updateValueOfAKey(String collect, String keyId, String valueId, String keyToUpdate, String valueToUpdate){
        BasicDBObject dbObject = (BasicDBObject) getADBObjectFromACollection(collect, keyId, valueId);
        BasicDBObject updateKeyValue = new BasicDBObject().append("$set", new BasicDBObject().append(keyToUpdate, valueToUpdate));
        collection.update(dbObject, updateKeyValue);
    }

    /**
     * This method delete a document obtained from DBobject
     * @param collect name of the collection
     * @param keyId the name of the key searched
     * @param valueId the value of the key
     */
    public void deleteDocumentFromDBObject(String collect, String keyId, String valueId){
        DBObject dbObject = getADBObjectFromACollection(collect, keyId, valueId);
        collection.remove(dbObject);
    }

    /**
     * Close connection of the data base
     */
    public void closeConnectionToDB(){
        log.info("Close data base connection");
        mongoClient.close();
    }

    /**
     * This method obtain a List of DBObject
     * @param collect the name of the collection
     * @param keyId the Id of the Key to search
     * @param valueId the value of the key id
     * @return return a list of DBObject that satisfy the search
     */
    public List<DBObject> getAListOfDBObject(String collect, String keyId, String valueId){
        List<DBObject> documents = new ArrayList<DBObject>();
        DBObject object = null;
        BasicDBObject query = new BasicDBObject(keyId, valueId);
        collection = db.getCollection(collect);
        DBCursor cursor = collection.find(query);
        try {
            while(cursor.hasNext()) {
                object = cursor.next();
                documents.add(object);
            }
        } finally {
            cursor.close();
        }
        System.out.println(documents);
        return documents;
    }
}
