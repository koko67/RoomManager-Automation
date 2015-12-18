package api;

import commons.DomainAppConstants;
import entities.ConferenceRoom;
import entities.Location;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: josecardozo
 * Date: 12/17/15
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class APIRoomMethods {
    /**
     * This method search the room by ID
     * @param conferenceRoom receive the entity that is search
     * @return the room in a json
     */
    public static JSONObject obtainRoom(ConferenceRoom conferenceRoom) {
        String endPoint = buildEndPoint(conferenceRoom.getId());
        JSONObject response = APILibrary.getInstance().getById(endPoint);
        return response;
    }

    /**
     * this method build the endPoint to search the room by Id
     * @param roomId is the id of room that search
     * @return
     */
    private static String buildEndPoint(String roomId) {
        return EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, roomId);
    }

    public static void associateLocationToRoom(Location location, ConferenceRoom conferenceRoom) {
        JSONObject updateRoom = buildJsonLocationToRoom(location.getId());
        APILibrary.getInstance().put(updateRoom,EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, conferenceRoom.getId()));
    }

    /**
     * build the json to associate the location with room
     * @param locationId receive that id of location that will associate
     * @return
     */
    private static JSONObject buildJsonLocationToRoom(String locationId) {
        JSONObject updateRoom = new JSONObject();
        updateRoom.put(DomainAppConstants.KEY_LOCATION_ID, locationId);
        return updateRoom;
    }

    /**
     * this method change the status disable to enable the a specific room
     * @param roomId receive the id of the room that want change the status.
     */
    public static void enableRoom(String roomId) {
        JSONObject request = new JSONObject();
        String endPoint = buildEndPoint(roomId);
        request.put(DomainAppConstants.KEY_ENABLED, true);
        APILibrary.getInstance().put(request, endPoint);
    }
}
