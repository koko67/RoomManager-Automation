package api;

import commons.DomainAppConstants;
import entities.Meeting;
import mongodb.DataBaseMeetingMethods;
import mongodb.DataBaseRoomMethods;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.CredentialManager;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * User: RonaldButron
 * Date: 12/15/15
 */
public class APIMeetingMethods {

    /**
     * This method build a JSON Object to create a Meeting
     * @param meeting entity with the information
     * @return a JSON object
     */
    private static JSONObject buildJSONObjectMeeting(Meeting meeting){
        String roomEmail = meeting.getRoomName().replace("-", ".");
        String domainEmail = CredentialManager.getInstance().getDomainEmail();
        JSONObject jsonObject =  new JSONObject();
         jsonObject.put(DomainAppConstants.KEY_ORGANIZER, meeting.getOrganizer())
                   .put(DomainAppConstants.KEY_TITLE, meeting.getSubject())
                   .put(DomainAppConstants.KEY_START, createISOHour(meeting.getHourFrom()))
                   .put(DomainAppConstants.KEY_END, createISOHour(meeting.getHourTo()))
                   .put(DomainAppConstants.KEY_LOCATION, meeting.getRoomName())
                   .put(DomainAppConstants.KEY_ROOM_EMAIL, (roomEmail + domainEmail))
                   .put(DomainAppConstants.KEY_RESOURCES, new JSONArray().put(roomEmail + domainEmail))
                   .put(DomainAppConstants.KEY_ATTENDEES, new JSONArray().put(meeting.getAttendees()));
        return jsonObject;
    }

    /**
     * This method create the hour in the ISO format need to build the JSON meeting
     * @param currentHour the current hour
     * @return a String with the hour in the format "yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'"
     */
    private static String createISOHour(String currentHour){
        int hour = Integer.parseInt(currentHour.split(":")[0])+4;
        String minute = currentHour.split(":")[1];
        SimpleDateFormat dateFormat= new SimpleDateFormat(DomainAppConstants.DATE_FORMAT);
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        if (hour<10){
            currentDate = currentDate+ "T" + "0" + Integer.toString(hour) + ":" + minute + DomainAppConstants.SECONDS;
        } else {
            currentDate = currentDate + "T" + Integer.toString(hour) + ":" + minute + DomainAppConstants.SECONDS;
        }
        return currentDate;
    }

    /**
     * This method build the endpoint /services/{:serviceId}/rooms/{:roomId}/meetings
     * @param roomName the name of the room needed
     * @return a string with the endPoint built
     */
    private static String buildEndPointAllMeeting(String roomName){
        String servicesId = DataBaseRoomMethods.obtainServicesId(roomName);
        String roomId = DataBaseRoomMethods.obtainRoomId(roomName);

        return EndPoints.ALL_MEETING.replace(DomainAppConstants.REPLACE_SERVICE_ID, servicesId)
                                    .replace(DomainAppConstants.REPLACE_ROOM_ID, roomId);

    }

    /**
     * This method allows to create a meeting by API
     * @param meeting the with the information needed
     */
    public static void createAMeetingByAPI(Meeting meeting){
        APILibrary.getInstance().postBasic(buildJSONObjectMeeting(meeting), buildEndPointAllMeeting(meeting.getRoomName()));
    }

    /**
     * This method verify if a meeting was created
     * @param meeting the meeting information
     * @return  true or false
     */
    public static boolean isTheMeetingCreated(Meeting meeting){
        String endpoint = buildEndPointAllMeeting(meeting.getRoomName());
        boolean isItCreated = false;
        JSONArray meetingsArray = APILibrary.getInstance().getAll(endpoint);
        for (int indice = 0; indice < meetingsArray.length(); indice++) {
            if(meetingsArray.getJSONObject(indice).getString(DomainAppConstants.KEY_TITLE).equals(meeting.getSubject())){
                isItCreated = true;
                break;
            }
        }
        return isItCreated;
    }


    /**
     * This method delete a meeting
     * @param meeting the meeting information
     */
    public static void deleteMeeting(Meeting meeting){
        String endpoint = buildEndPointAllMeeting(meeting.getRoomName());
        endpoint = endpoint + "/" + DataBaseMeetingMethods.obtainMeetingId(meeting.getDeleteSubject());
        APILibrary.getInstance().deleteBasic(endpoint);
    }

////////////////////////////

    /**
     *
     * @param endPoint
     * @return
     */
    public static JSONObject get(String endPoint){
       //Mas especificos los nombres y una clase con sus metodos, recibir la entidad para construir tus JSON y endPOints

        JSONObject response = APILibrary
                .getInstance()
                .getById(endPoint);
        return response;
    }

    /**
     *
     * @param name
     * @param displayName
     * @param endPoint
     * @return
     */
    public static JSONObject post(String name, String displayName, String endPoint) {
        JSONObject jsonLocation = new JSONObject();
        jsonLocation.put(DomainAppConstants.KEY_CUSTOM_NAME, displayName);
        jsonLocation.put(DomainAppConstants.KEY_NAME, name);

        JSONObject response = APILibrary.getInstance()
                .post(jsonLocation, endPoint);
        return  response;
    }


}
