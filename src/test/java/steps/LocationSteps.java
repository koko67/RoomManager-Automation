package steps;

import api.APILibrary;
import api.EndPoints;
import api.MethodsAPI;
import api.TokenAPI;
import commons.DomainAppConstants;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.ConferenceRoom;
import entities.Location;
import framework.UIMethods;
import mongodb.DataBaseDriver;
import mongodb.DataBaseMethods;
import org.json.JSONObject;
import org.junit.Assert;
import ui.pages.admin.ConferenceRoomsPage;
import ui.pages.admin.LocationSettingsPage;
import ui.pages.admin.RoomSettingsPage;
import ui.pages.admin.HomePage;
import ui.pages.admin.LocationAssociationsPage;
import ui.pages.admin.LocationPage;
import utils.CredentialManager;
import utils.LeftBarOptions;


/**
 * User: RonaldButron
 * Date: 12/3/15
 */
public class LocationSteps {

    private LocationPage locationPage;
    private LocationSettingsPage locationInfoPage;
    private HomePage homePage;
    private LocationAssociationsPage locationAssociationsPage;
    private ConferenceRoomsPage conferenceRoomPage;
    private RoomSettingsPage roomInfoPage;

    private Location location;
    private ConferenceRoom conferenceRoom;

    public LocationSteps(Location location){
        homePage = new HomePage();
        this.location = location;
        conferenceRoom = new ConferenceRoom();
    }
    @Given("^I go to the \"(.*?)\" page$")
    public void goToAPage(String namePage){
        locationPage = homePage.getLeftMenuPanel().clickOnLocationPage(namePage);
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
        String id = DataBaseMethods.obtainKeyValue(DomainAppConstants.LOCATIONS, DomainAppConstants.NAME, location.getName(), DomainAppConstants.KEY_ID);
        JSONObject response = MethodsAPI.get(EndPoints.LOCATION_BY_ID.replace(DomainAppConstants.REPLACE_ID, id));
        Assert.assertEquals("the location name is the same that was assigned", response.getString(DomainAppConstants.NAME), location.getName());
        Assert.assertEquals("the location display name is the same that was assigned", response.getString(DomainAppConstants.KEY_DISPLAY_NAME), location.getDisplayName());
    }

    @Given("^I open the Location details for Location \"(.*?)\"$")
    public void openDetailsLocation(String locationName){
        location.setName(locationName);
        location.setDisplayName(locationName);

        JSONObject jsonLocation = new JSONObject();
        jsonLocation.put(DomainAppConstants.CUSTOM_NAME, locationName);
        jsonLocation.put(DomainAppConstants.NAME, locationName);

        JSONObject response = MethodsAPI.post(jsonLocation,EndPoints.LOCATIONS);

        location.setId(response.getString(DomainAppConstants.KEY_ID));

        locationPage = homePage.getLeftMenuPanel().clickOnLocationPage(LeftBarOptions.LOCATIONS.getToPage());
        UIMethods.refreshPage();

//        UIMethods.switchPages(LeftBarOptions.LOCATIONS.getToPage());

        locationInfoPage = locationPage.clickEditLocation(location);
    }

    @When("^I associate the Room \"(.*?)\" with the Location$")
    public void associateRoomToLocation(String roomName){
        locationAssociationsPage = locationInfoPage.goLocationAssociationTab();
        locationAssociationsPage.clickOnRoomAssociateToLocation(roomName).saveLocation();
    }

    @Then("^the Room \"(.*?)\" should be associated to the Location in the Conference Rooms page$")
    public void isTheRoomDisplayInTheLocation(String roomName){
        conferenceRoomPage = homePage.getLeftMenuPanel().clickOnConferenceRooms(LeftBarOptions.CONFERENCE_ROOMS.getToPage());
        conferenceRoom.setDisplayName(roomName);
        roomInfoPage = conferenceRoomPage.openConferenceRoomSettings(roomName);
        boolean isLocationInRoomInfo = roomInfoPage.isLocationPresent(location);
        roomInfoPage.clickOnSaveButton();
        Assert.assertTrue("the room has assigned to the location", isLocationInRoomInfo);
    }

    @And("^the current Room should be obtained by api contains the LocationId of this Location$")
    public void isTheRoomAObtainedByAPI(){
        String ip = CredentialManager.getInstance().getIp();
        System.out.println("rescatando el ip"+ip);
        DataBaseDriver.getInstance().createConnectionToDB(ip);
        System.out.println("Abre la conexion");
        String roomId = DataBaseMethods.obtainKeyValue(DomainAppConstants.COLLECT_ROOMS,DomainAppConstants.DISPLAY_NAME,conferenceRoom.getDisplayName(),DomainAppConstants.KEY_ID);
        System.out.println(roomId);
        conferenceRoom.setId(roomId);

        JSONObject room = MethodsAPI.get(EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, conferenceRoom.getId()));
        Assert.assertEquals("the location id is the same",room.get(DomainAppConstants.KEY_LOCATION_ID), location.getId());
    }
    @Given("^I have a Location \"(.*?)\" with display name \"(.*?)\" associate to Room with name \"(.*?)\"$")
    public void haveToLocation(String customName, String displayName, String roomName){
        location.setName(customName);
        location.setDisplayName(displayName);
        conferenceRoom.setDisplayName(roomName);
        conferenceRoom.setCustomDisplayName(roomName);
        conferenceRoom.setLocation(location);

        JSONObject newLocation = new JSONObject();
        newLocation.put(DomainAppConstants.CUSTOM_NAME, location.getDisplayName());
        newLocation.put(DomainAppConstants.NAME, location.getName());

        String token = TokenAPI.getToken(CredentialManager.getInstance().getUserNameAdmin(), CredentialManager.getInstance().getPasswordAdmin(), DomainAppConstants.LOCAL);
        String endPoint = EndPoints.LOCATIONS;

        JSONObject response = APILibrary.getInstance().post(newLocation, token, endPoint);
        System.out.println(response);
        location.setId(response.getString(DomainAppConstants.KEY_ID));

        DataBaseDriver.getInstance().createConnectionToDB(CredentialManager.getInstance().getIp());
        String roomId = DataBaseDriver.getInstance().getKeyValue(DomainAppConstants.COLLECT_ROOMS, DomainAppConstants.DISPLAY_NAME, conferenceRoom.getCustomDisplayName(), DomainAppConstants.KEY_ID);
        conferenceRoom.setId(roomId);
        DataBaseDriver.getInstance().closeConnectionToDB();

        JSONObject updateRoom = new JSONObject();
        updateRoom.put(DomainAppConstants.KEY_LOCATION_ID, response.getString(DomainAppConstants.KEY_ID));

        locationPage = homePage.getLeftMenuPanel().clickOnLocationPage(LeftBarOptions.LOCATIONS.getToPage());
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
        conferenceRoomPage = homePage.getLeftMenuPanel().clickOnConferenceRooms(LeftBarOptions.CONFERENCE_ROOMS.getToPage());
        roomInfoPage = conferenceRoomPage.openConferenceRoomSettings(roomName);
        boolean isLocationInRoomInfo = !roomInfoPage.isLocationPresent(location);
        roomInfoPage.clickOnSaveButton();
        Assert.assertTrue("The location is displayed in the Available Column", isLocationAvailable);
        Assert.assertTrue("the room has assigned to the location", isLocationInRoomInfo);
    }
    @And("^the current Room should not obtained by api contains the LocationId of this Location$")
    public void isContainsRoomInLocation(){
        DataBaseDriver.getInstance().createConnectionToDB(CredentialManager.getInstance().getIp());
        String roomId = DataBaseDriver.getInstance().getKeyValue(DomainAppConstants.COLLECT_ROOMS, DomainAppConstants.DISPLAY_NAME, conferenceRoom.getDisplayName(), DomainAppConstants.KEY_ID);
        conferenceRoom.setId(roomId);
        DataBaseDriver.getInstance().closeConnectionToDB();

        JSONObject room = APILibrary.getInstance().getById(EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, conferenceRoom.getId()));
        Assert.assertNull("the location id is null", room.get(DomainAppConstants.KEY_LOCATION_ID));
    }
}
