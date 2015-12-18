package mongodb;

import commons.DomainAppConstants;


/**
 * User: RonaldButron
 * Date: 12/15/15
 */
public class DataBaseRoomMethods {


    /**
     * This method obtain the Id of the Room
     * @param roomName the name of the room
     * @return Id of the room
     */
    public static String obtainRoomId(String roomName){
        DataBaseDriver.getInstance().createConnectionToDB();
        String valueToReturn = DataBaseDriver.getInstance().getKeyValue(DomainAppConstants.COLLECT_ROOMS, DomainAppConstants.KEY_CUSTOM_DISPLAY_NAME, roomName, DomainAppConstants.KEY_ID);
        DataBaseDriver.getInstance().closeConnectionToDB();
        return valueToReturn;
    }

    /**
     * This method obtain the services of the Id
     * @param roomName the name of the room
     * @return services Id of the room
     */
    public static String obtainServicesId(String roomName){
        DataBaseDriver.getInstance().createConnectionToDB();
        String valueToReturn = DataBaseDriver.getInstance().getKeyValue(DomainAppConstants.COLLECT_ROOMS, DomainAppConstants.KEY_DISPLAY_NAME, roomName, DomainAppConstants.KEY_SERVICE_ID);
        DataBaseDriver.getInstance().closeConnectionToDB();
        return valueToReturn;
    }
}
