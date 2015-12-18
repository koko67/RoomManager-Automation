package steps;

import api.APILibrary;
import api.APIMethods;
import api.EndPoints;
import commons.DomainAppConstants;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.ConferenceRoom;
import entities.Location;
import framework.UIMethods;
import mongodb.DataBaseMethods;
import org.json.JSONObject;
import org.junit.Assert;
import ui.pages.admin.ConferenceRoomsPage;
import ui.pages.admin.LocationSettingsPage;
import ui.pages.admin.RoomSettingsPage;
import ui.pages.admin.HomePage;
import ui.pages.admin.LocationAssociationsPage;
import ui.pages.admin.LocationPage;
import utils.LeftBarOptions;


/**
 * User: RonaldButron
 * Date: 12/3/15
 */
public class LocationSteps {

    private LocationPage locationPage;
    private LocationSettingsPage locationInfoPage;
    private LocationAssociationsPage locationAssociationsPage;
    private ConferenceRoomsPage conferenceRoomPage;
    private RoomSettingsPage roomInfoPage;

    private Location location;
    private ConferenceRoom conferenceRoom;

    public LocationSteps(Location location){
        conferenceRoom = new ConferenceRoom();
        locationPage = new LocationPage();
        this.location = location;
    }
    @Given("^I go to the Location page$")
    public void goToAPage(){
        locationPage.getLeftMenuPanel().clickOnLocationPage();
    }

    @When("^I create a Location with Name \"(.*?)\" and Display Name \"(.*?)\"$")
    public void createLocation(String locationName, String displayName){
        location.setName(locationName);
        location.setDisplayName(displayName);
        locationInfoPage = locationPage.clickAddLocation();
        locationInfoPage.fillFormSuccessfully(location);
        UIMethods.switchPages(LeftBarOptions.LOCATIONS.getToPage());
    }
    @Then("^the Location  should be displayed in the Location page$")
    public void isTheLocationDisplayedInTheLocationPage(){
        Assert.assertTrue("this location was created successfully", locationPage.isLocationNameExists(location.getDisplayName()));
    }

    @And("^the Location should be obtained by API request$")
    public void isTheLocationObtainedByAPI(){
        String id = DataBaseMethods.obtainKeyValue(DomainAppConstants.LOCATIONS, DomainAppConstants.KEY_NAME, location.getName(), DomainAppConstants.KEY_ID);
        JSONObject response = APIMethods.get(EndPoints.LOCATION_BY_ID.replace(DomainAppConstants.REPLACE_ID, id));
        Assert.assertEquals("the location name is the same that was assigned", response.getString(DomainAppConstants.KEY_NAME), location.getName());
        Assert.assertEquals("the location display name is the same that was assigned", response.getString(DomainAppConstants.KEY_CUSTOM_NAME), location.getDisplayName());
    }

    @Given("^I open the Location details for Location \"(.*?)\"$")
    public void openDetailsLocation(String locationName){
        location.setName(locationName);
        location.setDisplayName(locationName);

        JSONObject response = APIMethods.post(locationName, locationName, EndPoints.LOCATIONS);

        location.setId(response.getString(DomainAppConstants.KEY_ID));
        locationPage.getLeftMenuPanel().clickOnLocationPage();

        UIMethods.refreshPage();
        UIMethods.switchPages(LeftBarOptions.LOCATIONS.getToPage());
        locationInfoPage = locationPage.clickEditLocation(location);
    }

    @When("^I associate the Room \"(.*?)\" with the Location$")
    public void associateRoomToLocation(String roomName){
        locationAssociationsPage = locationInfoPage.goLocationAssociationTab();
        locationAssociationsPage.clickOnRoomAssociateToLocation(roomName).saveLocation();
    }

    @Then("^the Room \"(.*?)\" should be associated to the Location in the Conference Rooms page$")
    public void isTheRoomDisplayInTheLocation(String roomName){
        conferenceRoomPage = locationPage.getLeftMenuPanel().clickOnConferenceRooms();
        conferenceRoom.setDisplayName(roomName);
        roomInfoPage = conferenceRoomPage.openConferenceRoomSettings(roomName);
        boolean isLocationInRoomInfo = roomInfoPage.isLocationPresent(location);
        roomInfoPage.clickOnSaveButton();
        Assert.assertTrue("the room has assigned to the location", isLocationInRoomInfo);
    }

    @And("^the current Room obtained by API should contains the reference of the Location$")
    public void isTheRoomAObtainedByAPI(){
        String roomId = DataBaseMethods.obtainKeyValue(DomainAppConstants.COLLECT_ROOMS, DomainAppConstants.KEY_DISPLAY_NAME, conferenceRoom.getDisplayName(), DomainAppConstants.KEY_ID);
        conferenceRoom.setId(roomId);

        JSONObject room = APIMethods.get(EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, conferenceRoom.getId()));
        Assert.assertEquals("the location id is the same",room.get(DomainAppConstants.KEY_LOCATION_ID), location.getId());
    }

    @Given("^I have a Location \"(.*?)\" with display name \"(.*?)\" associate to Room with name \"(.*?)\"$")
    public void haveToLocation(String customName, String displayName, String roomName){
        location.setName(customName);
        location.setDisplayName(displayName);
        conferenceRoom.setDisplayName(roomName);
        conferenceRoom.setCustomDisplayName(roomName);
        conferenceRoom.setLocation(location);

        JSONObject response = APIMethods.post(customName, displayName, EndPoints.LOCATIONS);
        location.setId(response.getString(DomainAppConstants.KEY_ID));

        String roomId = DataBaseMethods.
                obtainKeyValue(DomainAppConstants.COLLECT_ROOMS,
                        DomainAppConstants.KEY_DISPLAY_NAME,
                        conferenceRoom.getDisplayName(),
                        DomainAppConstants.KEY_ID);
        conferenceRoom.setId(roomId);

        JSONObject updateRoom = new JSONObject();
        updateRoom.put(DomainAppConstants.KEY_LOCATION_ID, response.getString(DomainAppConstants.KEY_ID));

        APILibrary.getInstance().put(updateRoom,EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, conferenceRoom.getId()));

        UIMethods.refreshPage();
        locationPage.getLeftMenuPanel().clickOnLocationPage();
    }
    @And("^I open the Location and I select the Locations Associations tab$")
    public void openLocation(){
        locationInfoPage = locationPage.clickEditLocation(location);
        locationAssociationsPage = locationInfoPage.goLocationAssociationTab();
    }
    @When("^I dis-associate the Room \"(.*?)\" of the Location$")
    public void disAssociateRoomToLocation(String roomName){
        locationAssociationsPage.clickOnRoomDisassociateLocation(roomName);
    }
    @Then("^The Room \"(.*?)\" should be displayed in the column of \"(.*?)\"$")
    public void isRoomDisplayedAssocite(String roomName, String state){
        boolean isLocationAvailable = locationAssociationsPage.searchRoomInAvailableColumn(roomName, state);
        locationAssociationsPage.saveLocation();

        conferenceRoomPage = locationPage.getLeftMenuPanel().clickOnConferenceRooms();
        roomInfoPage = conferenceRoomPage.openConferenceRoomSettings(roomName);
        boolean isLocationInRoomInfo = !roomInfoPage.isLocationPresent(location);
        roomInfoPage.clickOnSaveButton();

        Assert.assertTrue("The location is displayed in the Available Column", isLocationAvailable);
        Assert.assertTrue("the room has assigned to the location", isLocationInRoomInfo);
    }
    @And("^the current Room obtained by API should not contains the reference of the Location$")
    public void isContainsRoomInLocation(){
        String roomId = DataBaseMethods.obtainKeyValue(DomainAppConstants.COLLECT_ROOMS, DomainAppConstants.KEY_DISPLAY_NAME, conferenceRoom.getDisplayName(), DomainAppConstants.KEY_ID);
        conferenceRoom.setId(roomId);

        JSONObject room = APILibrary.getInstance().getById(EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, conferenceRoom.getId()));
        Assert.assertEquals("The room has not location id ", room.get(DomainAppConstants.KEY_LOCATION_ID), null);
    }
    @After(value = "@LocationForUI")
    public void deleteLocation(){
        String id = DataBaseMethods.obtainKeyValue(DomainAppConstants.LOCATIONS, DomainAppConstants.KEY_NAME , location.getName(), DomainAppConstants.KEY_ID);
        APILibrary.getInstance().delete(EndPoints.LOCATION_BY_ID.replace(DomainAppConstants.REPLACE_ID, id));
    }
    @After(value = "@AssociateLocation")
    public void deleteLocationAssociate(){
        APILibrary.getInstance().delete(EndPoints.LOCATION_BY_ID.replace(DomainAppConstants.REPLACE_ID, location.getId()));
    }
    @After(value = "@DisassociateLocation")
    public void deleteLocationDisAssociate(){
        APILibrary.getInstance().delete(EndPoints.LOCATION_BY_ID.replace(DomainAppConstants.REPLACE_ID, location.getId()));
    }
}
