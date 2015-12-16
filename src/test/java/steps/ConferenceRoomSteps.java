package steps;

import api.APILibrary;
import api.APIMethods;
import api.EndPoints;
import commons.DomainAppConstants;
import cucumber.api.java.After;
import mongodb.DataBaseMethods;
import org.json.JSONArray;
import cucumber.api.java.en.And;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.ConferenceRoom;
import entities.Location;
import org.json.JSONObject;
import ui.pages.admin.ConferenceRoomsPage;
import ui.pages.admin.LocationPage;

import entities.Resource;
import framework.UIMethods;
import junit.framework.Assert;
import ui.pages.admin.ResourceAssociatePage;

import ui.pages.admin.RoomSettingsPage;
import ui.pages.admin.HomePage;
import utils.LeftBarOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Jorge Avila
 * Date: 12/3/15
 */
public class ConferenceRoomSteps {

    private HomePage homePage;
    private ConferenceRoomsPage conferenceRoomsPage;
    private RoomSettingsPage roomInfoPage;
    private LocationPage locationPage;
    private ConferenceRoom conferenceRoom;
    private Location location;
    private Resource resource;
    private ResourceAssociatePage resourceAssociatePage;
    private JSONObject response;
    private List<String> locationIds;

    public ConferenceRoomSteps(ConferenceRoom conferenceRoom){
        homePage = new HomePage();
        resource = new Resource();
        this.conferenceRoom = conferenceRoom;
        this.location = new Location();
        locationIds = new ArrayList<String>();
    }

    @Given("^I open the Room \"(.*?)\" from the Conference Room$")
    public void openRoomFromConferenceRoom(String roomName) {
        conferenceRoom.setDisplayName(roomName);
        conferenceRoom.setCustomDisplayName(roomName);
        UIMethods.refreshPage();
        UIMethods.switchPages(LeftBarOptions.CONFERENCE_ROOMS.getToPage());
        conferenceRoomsPage = homePage.getLeftMenuPanel()
                                      .clickOnConferenceRooms();

        roomInfoPage = conferenceRoomsPage.openConferenceRoomSettings(conferenceRoom.getCustomDisplayName());
    }

    @When("^I assign the current Room to the Location \"(.*?)\"$")
    public void assignRoomToALocation(String locationName){
        assignLocationToRoom(locationName);
    }

    private void assignLocationToRoom(String locationName) {
        location.setName(locationName);
        location.setDisplayName(locationName);
        roomInfoPage.assignLocation(locationName);
    }


    @Then("^the current room is associated to the Location defined in the Locations page$")
    public void isAssociatedRoomToLocationRoomPage(){
        locationPage = homePage.getLeftMenuPanel()
                .clickOnLocationPage();
        boolean existAssociated = locationPage.verifyIfExistLocationAssociation(location, conferenceRoom);
        Assert.assertTrue(existAssociated);
    }

    @When("^I edit the following info: Display Name \"(.*?)\", code \"(.*?)\" and capacity \"(.*?)\"$")
    public void editInfoConferenceRoom(String displayName, String roomCode, String roomCapacity){
        conferenceRoom.setDisplayName(displayName);
        conferenceRoom.setCustomDisplayName(displayName);
        conferenceRoom.setCode(roomCode);
        conferenceRoom.setCapacity(roomCapacity);
        roomInfoPage.fillForm(conferenceRoom);
    }

    @Then("^the info edited should be obtained by API request for the Room \"(.*?)\"$")
    public void isTheInfoEditedObtainedByAPI(String roomName){
        String roomId = DataBaseMethods
                .obtainKeyValue(DomainAppConstants.COLLECT_ROOMS,
                        DomainAppConstants.KEY_CUSTOM_DISPLAY_NAME,
                        roomName,
                        DomainAppConstants.KEY_ID);
        String endPoint = EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, roomId);
        JSONObject response = APILibrary.getInstance().getById(endPoint);

        Assert.assertEquals(conferenceRoom.getCustomDisplayName(), response.getString(DomainAppConstants.KEY_CUSTOM_DISPLAY_NAME));
        Assert.assertEquals(conferenceRoom.getCode(), response.getString(DomainAppConstants.KEY_CODE));
    }

    @Given("^I have created a resource with name \"(.*?)\", customName \"(.*?)\"$")
    public void createResource(String resourceName, String resourceDisplayName){
        resource.setName(resourceName);
        resource.setCustomName(resourceDisplayName);
        resource.setFontIcon("fa fa-desktop");

        JSONObject jsonResource = new JSONObject();
        jsonResource.put(DomainAppConstants.KEY_NAME, resourceName);
        jsonResource.put(DomainAppConstants.KEY_CUSTOM_NAME, resourceDisplayName);
        jsonResource.put(DomainAppConstants.KEY_FONTICON, resource.getFontIcon());
        jsonResource.put(DomainAppConstants.KEY_DESCRIPTION, "");
        jsonResource.put(DomainAppConstants.KEY_FROM, "");


        String endPoint = EndPoints.RESOURCE;
        JSONObject response = APILibrary.getInstance().post(jsonResource, endPoint);
        resource.setId(response.getString(DomainAppConstants.KEY_ID));

        conferenceRoom.addResource(resource);
        UIMethods.refreshPage();
    }

    @And("^I go to Conference Room page$")
    public void goToConferenceRoomPage(){
        conferenceRoomsPage = homePage.getLeftMenuPanel().clickOnConferenceRooms();
    }
    @And("^I select the resource button in the header page$")
    public void displayResourceInTableConferenceRoom(){
        conferenceRoomsPage.clickOnResourcesDisplayButton(resource);
    }
    @And("^I select the Resource Association Tab$")
    public void selectResourceAssociateTab(){
        resourceAssociatePage = roomInfoPage.clickOnResourceAssociateTab();
    }
    @And("^I add \"(.*?)\" Resource to the Room$")
    public void addResourcesToRoom(String quantity){
        resource.setQuantity(quantity);
        resourceAssociatePage.clickOnAddResources(resource)
                            .typeQuantityResources(resource);
        conferenceRoomsPage = resourceAssociatePage.clickOnSaveButton();
    }
    @Then("^the resource and quantity should be displayed for the room in the list$")
    public void verifyResourceAndQuantity(){
        conferenceRoomsPage.makeSureResourcesIsSelect(resource);
        boolean verificationIcon = conferenceRoomsPage.isTheResourceCorrect(resource, conferenceRoom);
        boolean verificationQuantity = conferenceRoomsPage.isTheSameQuantityOfResources(resource, conferenceRoom);
        Assert.assertTrue("The resource icon is the same that it's assigned ", verificationIcon);
        Assert.assertTrue("The quantity is the same that was assigned", verificationQuantity);
    }
    @When("^I pressing the disable button$")
    public void disableRoom(){
        conferenceRoom.setEnabled(false);
        conferenceRoomsPage = roomInfoPage.clickOnPowerOffRoomButton(conferenceRoom);
        UIMethods.switchPages(LeftBarOptions.CONFERENCE_ROOMS.getToPage());
    }
    @Then("^The current Room should be disable$")
    public void verifyRoomDisable(){
        Assert.assertTrue("The room was disable correctly", conferenceRoomsPage.isRoomEnable(conferenceRoom));
    }
    @And("^the information updated in the room should be obtained by API$")
    public void verifyRoomIsDisableByAPI(){
        String id = DataBaseMethods.obtainKeyValue(DomainAppConstants.COLLECT_ROOMS, DomainAppConstants.KEY_DISPLAY_NAME, conferenceRoom.getDisplayName(), DomainAppConstants.KEY_ID);
        conferenceRoom.setId(id);

        String endPoint = EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, id);
        JSONObject response = APILibrary.getInstance().getById(endPoint);

        Assert.assertFalse("the room is disabled", response.getBoolean("enabled"));
    }
    @And("^the Room obtain by api should be contain the resource and quantity$")
    public void verifyResourceInRoom(){

        String id = DataBaseMethods.obtainKeyValue(DomainAppConstants.COLLECT_ROOMS, DomainAppConstants.KEY_DISPLAY_NAME, conferenceRoom.getDisplayName(), DomainAppConstants.KEY_ID);
        conferenceRoom.setId(id);

        String endPoint = EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, id);

        JSONObject response = APIMethods.get(endPoint);
        JSONArray resources = response.getJSONArray("resources");

    }

    @Given("^I have a Room with name \"([^\"]*)\" that is associated with the Location \"([^\"]*)\"$")
    public void createResourceWithLocation(String roomName, String locationName) throws Throwable {
        JSONObject location = new JSONObject();
        location.put(DomainAppConstants.KEY_NAME, locationName);
        location.put(DomainAppConstants.KEY_CUSTOM_NAME, locationName);

        String endPoint = EndPoints.LOCATIONS;
        response = APILibrary.getInstance().post(location, endPoint);
        locationIds.add(response.getString(DomainAppConstants.KEY_ID));
        conferenceRoom.setId(DataBaseMethods
                            .obtainKeyValue(DomainAppConstants.COLLECT_ROOMS,
                                    DomainAppConstants.KEY_DISPLAY_NAME,
                                    roomName,
                                    DomainAppConstants.KEY_ID));
        JSONObject updateRoom = new JSONObject();
        updateRoom.put(DomainAppConstants.KEY_LOCATION_ID, response.getString(DomainAppConstants.KEY_ID));
        String roomsEndPoint = EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, conferenceRoom.getId());
        response = APILibrary.getInstance().put(updateRoom, roomsEndPoint);
    }

    @And("^I have a Location with name \"([^\"]*)\"$")
    public void createNewLocation(String name) throws Throwable {
        location.setName(name);
        location.setDisplayName(name);
        JSONObject request = new JSONObject();
        request.put(DomainAppConstants.KEY_NAME, name);
        request.put(DomainAppConstants.KEY_CUSTOM_NAME, name);
        String locationsEndPoint = EndPoints.LOCATIONS;
        response = APILibrary.getInstance().post(request, locationsEndPoint);
        conferenceRoom.setLocationId(response.getString(DomainAppConstants.KEY_ID));
        locationIds.add(response.getString(DomainAppConstants.KEY_ID));
    }

    @Then("^the current Room should be moved to the new Location$")
    public void isRoomAssociatedToLocation() throws Throwable {
        isAssociatedRoomToLocationRoomPage();
    }

    @And("^the current Room should be obtained by API request associated with the location$")
    public void roomAssociatedToLocation() throws Throwable {
        String id = DataBaseMethods.
                obtainKeyValue(DomainAppConstants.COLLECT_ROOMS,
                        DomainAppConstants.KEY_DISPLAY_NAME,
                        conferenceRoom.getDisplayName(),
                        DomainAppConstants.KEY_ID);
        String endPoint = EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, id);
        response = APILibrary.getInstance().getById(endPoint);
        Assert.assertEquals(conferenceRoom.getLocationId(), response.getString(DomainAppConstants.KEY_LOCATION_ID));
    }

    @And("^I assign the current Room to the new Location \"([^\"]*)\"$")
    public void I_assign_the_current_Room_to_the_new_Location(String locationName) throws Throwable {
        assignLocationToRoom(locationName);
    }

    @After(value = "@locationRoom")
    public void deleteLocation() throws Throwable {
        String endPoint = EndPoints.LOCATION_BY_ID.replace(DomainAppConstants.REPLACE_ID, conferenceRoom.getLocationId());
        APILibrary.getInstance().delete(endPoint);
    }

    @After(value = "@locationRoom2")
    public void deleteLocations() throws Throwable {
        for(String id : locationIds){
            String endPoint = EndPoints.LOCATION_BY_ID.replace(DomainAppConstants.REPLACE_ID, id);
            try {
                APILibrary.getInstance().delete(endPoint);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @After(value = "@disableRoom")
    public void enableConferenceRoom(){
        String roomId = DataBaseMethods
                .obtainKeyValue(DomainAppConstants.COLLECT_ROOMS,
                        DomainAppConstants.KEY_CUSTOM_DISPLAY_NAME,
                        conferenceRoom.getCustomDisplayName(),
                        DomainAppConstants.KEY_ID);
        String endPoint = EndPoints.ROOM_BY_ID.replace(DomainAppConstants.REPLACE_ID, roomId);

        JSONObject request = new JSONObject();
        request.put(DomainAppConstants.KEY_ENABLED, true);
        APILibrary.getInstance().put(request, endPoint);
    }
}
