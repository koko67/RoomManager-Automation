package conditions;

import api.APILibrary;
import api.EndPoints;
import commons.DomainAppConstants;
import mongodb.DataBaseMethods;
import org.json.JSONArray;
import utils.CredentialManager;

/**
 * User: RonaldButron
 * Date: 12/14/15
 */
public class MeetingConditions {



    /**
     * Verify if the Meeting was created
     * @param valueId the name of the room
     * @param value the name of the meeting
     * @return true or false
     */
    public static boolean isTheMeetingCreated(String valueId, String value){
        String servicesId = DataBaseMethods.obtainKeyValue(DomainAppConstants.COLLECT_ROOMS, DomainAppConstants.KEY_DISPLAY_NAME, valueId, DomainAppConstants.KEY_SERVICE_ID);
        String roomId = DataBaseMethods.obtainKeyValue(DomainAppConstants.COLLECT_ROOMS, DomainAppConstants.KEY_DISPLAY_NAME, valueId, DomainAppConstants.KEY_ID);
        String endpoint = EndPoints.ALL_MEETING.replace(DomainAppConstants.REPLACE_SERVICE_ID, servicesId)
                                               .replace(DomainAppConstants.REPLACE_ROOM_ID, roomId);
        boolean isItCreated = false;
        JSONArray meetingsArray = APILibrary.getInstance().getAll(endpoint);
        for (int indice = 0; indice < meetingsArray.length(); indice++) {
            if(meetingsArray.getJSONObject(indice).getString(DomainAppConstants.KEY_TITLE).equals(value)){
                isItCreated = true;
                break;
            }
       }
        return isItCreated;

    }


    /**
     * This method delete a meeting
     * @param valueId the name of the meeting subject
     */
    public static void deleteMeeting(String valueId){
        String servicesId = DataBaseMethods.obtainKeyValue(DomainAppConstants.COLLECT_MEETING, DomainAppConstants.KEY_TITLE, valueId, DomainAppConstants.KEY_SERVICE_ID);
        String roomId = DataBaseMethods.obtainKeyValue(DomainAppConstants.COLLECT_MEETING, DomainAppConstants.KEY_TITLE, valueId, DomainAppConstants.KEY_ROOM_ID);
        String meetingId = DataBaseMethods.obtainKeyValue(DomainAppConstants.COLLECT_MEETING, DomainAppConstants.KEY_TITLE, valueId, DomainAppConstants.KEY_ID);
        String endpoint = EndPoints.MEETING_BY_ID.replace(DomainAppConstants.REPLACE_SERVICE_ID, servicesId)
                                                 .replace(DomainAppConstants.REPLACE_ROOM_ID, roomId)
                                                 .replace(DomainAppConstants.REPLACE_MEETING_ID, meetingId);
        APILibrary.getInstance().delete(endpoint);
    }

}


