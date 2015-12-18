package api;

import commons.DomainAppConstants;
import entities.Location;
import org.json.JSONObject;

public class APILocationMethods {
    /**
     * This method search a specific location by ID
     * @param location is a entity that contains information of location
     * @return
     */
    public static JSONObject getLocationByID(Location location) {
        String endPoint = buildEndPoint(location.getId());

        JSONObject response = APILibrary
                .getInstance()
                .getById(endPoint);
        return response;
    }

    /**
     * this method build the end point for specific location
     * @param locationId is the id of the location
     * @return the endpoint to specific location
     */
    private static String buildEndPoint(String locationId) {
        return EndPoints.LOCATION_BY_ID.replace(DomainAppConstants.REPLACE_ID, locationId);
    }

    /**
     * this method realize the insert of the location by API
     * @param location is a entity that contains data about the location
     * @return
     */
    public static JSONObject postLocation(Location location) {
        JSONObject jsonLocation = buildLocation(location);
        String endPoint = EndPoints.LOCATIONS;

        JSONObject response = APILibrary.getInstance().post(jsonLocation, endPoint);
        return response;
    }

    private static JSONObject buildLocation(Location location) {
        JSONObject jsonLocation = new JSONObject();
        jsonLocation.put(DomainAppConstants.KEY_CUSTOM_NAME, location.getDisplayName());
        jsonLocation.put(DomainAppConstants.KEY_NAME, location.getName());
        return jsonLocation;
    }

    public static void deleteLocation(String locationId) {
        APILibrary.getInstance().delete(buildEndPoint(locationId));
    }
}