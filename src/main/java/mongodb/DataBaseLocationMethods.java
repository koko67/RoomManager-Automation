package mongodb;

import commons.DomainAppConstants;
import entities.Location;

/**
 * Created with IntelliJ IDEA.
 * User: josecardozo
 * Date: 12/17/15
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataBaseLocationMethods {

    public static String obtainId(Location location) {
        DataBaseDriver.getInstance().createConnectionToDB();
        String locationId = DataBaseDriver.getInstance().getKeyValue(DomainAppConstants.LOCATIONS, DomainAppConstants.KEY_NAME, location.getName(), DomainAppConstants.KEY_ID);
        DataBaseDriver.getInstance().closeConnectionToDB();
        return locationId;
    }

}
