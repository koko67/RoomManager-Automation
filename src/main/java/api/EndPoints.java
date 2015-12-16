package api;

/**
 * Created by jorgeavila on 12/11/2015.
 */
public class EndPoints {
    public final static String LOCATIONS = "/locations";
    public final static String LOCATION_BY_ID = "/locations/#id#";
    public final static String RESOURCE = "/resources";
    public final static String RESOURCE_BY_ID = "/resources/#id#";
    public final static String ROOMS = "/rooms";
    public final static String ROOM_BY_ID = "/rooms/#id#";
    public final static String MEETING_BY_ID = "services/#serviceId#/rooms/#roomId#/meetings/#meetingId#";
    public final static String ALL_MEETING = "services/#serviceId#/rooms/#roomId#/meetings";
}
