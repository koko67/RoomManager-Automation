package api;

import commons.DomainAppConstants;
import entities.Meeting;
import mongodb.DataBaseMethods;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * User: RonaldButron
 * Date: 12/15/15
 */
public class APIMethods {

    /**
     *
     * @param meeting
     * @return
     */
    public static JSONObject buildJSONObjectMeeting(Meeting meeting){
        String roomEmail = meeting.getRoomName().replace("-", ".");
        System.out.println(roomEmail);
        JSONObject jsonObject =  new JSONObject();
         jsonObject.put("organizer", meeting.getOrganizer())
                   .put("title", meeting.getSubject())
                   .put("start", createISOHour(meeting.getHourFrom()))
                   .put("end", createISOHour(meeting.getHourTo()))
                   .put("location", meeting.getRoomName())
                   .put("roomEmail", roomEmail + "@grupob01.local")
                   .put("resources", new JSONArray().put(roomEmail + "@grupob01.local"))
                   .put("attendees", new JSONArray().put(meeting.getAttendees()));
        return jsonObject;
    }

    /**
     *
     * @param currentHour
     * @return
     */
    public static String createISOHour(String currentHour){
        int hour = Integer.parseInt(currentHour.split(":")[0])+4;
        String minute = currentHour.split(":")[1];
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        if (hour<9){
            currentDate = currentDate+ "T" + "0" + Integer.toString(hour) + ":" + minute + ":00.000Z";
        } else {
            currentDate = currentDate + "T" + Integer.toString(hour) + ":" + minute + ":00.000Z";
        }
        return currentDate;
    }

    /**
     *
     * @param roomName
     * @return
     */
    public static String buildEndPoint(String roomName){
        String servicesId = DataBaseMethods.obtainKeyValue(DomainAppConstants.COLLECT_ROOMS, DomainAppConstants.KEY_DISPLAY_NAME, roomName, DomainAppConstants.KEY_SERVICE_ID);
        String roomId = DataBaseMethods.obtainKeyValue(DomainAppConstants.COLLECT_ROOMS, DomainAppConstants.KEY_DISPLAY_NAME, roomName, DomainAppConstants.KEY_ID);

        return EndPoints.ALL_MEETING.replace(DomainAppConstants.REPLACE_SERVICE_ID, servicesId)
                                    .replace(DomainAppConstants.REPLACE_ROOM_ID, roomId);

    }

    /**
     *
     * @param meeting
     */
    public static void createAMeetingByAPI(Meeting meeting){
        APILibrary.getInstance().post(buildJSONObjectMeeting(meeting), buildEndPoint(meeting.getRoomName()));
    }
}
