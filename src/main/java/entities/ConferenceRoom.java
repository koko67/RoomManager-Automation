package entities;

import java.util.ArrayList;

/**
 * Created by jorgeavila on 12/9/2015.
 */
public class ConferenceRoom {
    private String id;
    private String emailAddress;
    private String displayName;
    private String serviceId;
    private ArrayList<Resource> resources;
    private boolean enabled;
    private String locationId;
    private String customDisplayName;
    private String code;

    public ConferenceRoom() {
        resources = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public void addResource(Resource resource){
        this.resources.add(resource);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCustomDisplayName() {
        return customDisplayName;
    }

    public void setCustomDisplayName(String customDiaplayName) {
        this.customDisplayName = customDiaplayName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
