package api;

import commons.DomainAppConstants;
import entities.Resource;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: josecardozo
 * Date: 12/17/15
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class APIResourceMethods {
    /**
     * this method create the resources by API
     * @param resource information of resource to create.
     * @return
     */
    public static JSONObject createResource(Resource resource) {
        JSONObject jsonResource = buildJsonResource(resource);
        String endPoint = EndPoints.RESOURCE;
        JSONObject response = APILibrary.getInstance().post(jsonResource, endPoint);

        return response;
    }

    /**
     * this method create the json with data of the resource
     * @param resource
     * @return
     */
    private static JSONObject buildJsonResource(Resource resource) {
        JSONObject jsonResource = new JSONObject();
        jsonResource.put(DomainAppConstants.KEY_NAME, resource.getName());
        jsonResource.put(DomainAppConstants.KEY_CUSTOM_NAME, resource.getCustomName());
        jsonResource.put(DomainAppConstants.KEY_FONTICON, resource.getFontIcon());
        jsonResource.put(DomainAppConstants.KEY_DESCRIPTION, "");
        jsonResource.put(DomainAppConstants.KEY_FROM, "");
        return jsonResource;
    }


    public static void deleteResources(Resource resource) {
        String endPoint = buildEndPoint(resource.getId());
        APILibrary.getInstance().delete(endPoint);
    }

    /**
     * this method build the end point for specific location
     * @param resourceId is the id of the location
     * @return the endpoint to specific location
     */
    private static String buildEndPoint(String resourceId) {
        return EndPoints.RESOURCE_BY_ID.replace(DomainAppConstants.REPLACE_ID, resourceId);
    }
}
