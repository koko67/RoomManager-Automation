package mongodb;

import commons.DomainAppConstants;

/**
 * User: RonaldButron
 * Date: 12/17/15
 */
public class DataBaseMeetingMethods {


    /**
     * This method connect to the database and search the id of the meeting then close the database
     * @param subjectName the subject of the meeting
     * @return a String with the value
     */
    public static String obtainMeetingId(String subjectName){
        DataBaseDriver.getInstance().createConnectionToDB();
        String valueToReturn = DataBaseDriver.getInstance().getKeyValue(DomainAppConstants.COLLECT_MEETING, DomainAppConstants.KEY_TITLE, subjectName, DomainAppConstants.KEY_ID);
        DataBaseDriver.getInstance().closeConnectionToDB();
        return valueToReturn;
    }

}
