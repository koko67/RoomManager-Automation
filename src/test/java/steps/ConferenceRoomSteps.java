package steps;

import api.*;
import commons.DomainAppConstants;
import cucumber.api.java.After;
import mongodb.DataBaseRoomMethods;
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
        Assert.assertTrue("The room is displayed in the association table", existAssociated);
    }

    @When("^I edit the following info: Display Name \"(.*?)\", code \"(.*?)\" and capacity \"(.*?)\"$")
    public void editInfoConferenceRoom(String displayName, String roomCode, String roomCapacity){
        conferenceRoom.setCustomDisplayName(displayName);
        conferenceRoom.setCode(roomCode);
        conferenceRoom.setCapacity(roomCapacity);
        roomInfoPage.fillForm(conferenceRoom);
    }

    @Then("^the info edited should be obtained by API request for the Room \"(.*?)\"$")
    public void isTheInfoEditedObtainedByAPI(String roomName){
        conferenceRoom.setDisplayName(conferenceRoom.getCustomDisplayName());
        String roomId = DataBaseRoomMethods.obtainRoomId(roomName);
        conferenceRoom.setId(roomId);
        JSONObject response = APIRoomMethods.obtainRoom(conferenceRoom);

        Assert.assertEquals("The rooms have the same name", conferenceRoom.getCustomDisplayName(), response.getString(DomainAppConstants.KEY_CUSTOM_DISPLAY_NAME));
        Assert.assertEquals("The rooms have the same code", conferenceRoom.getCode(), response.getString(DomainAppConstants.KEY_CODE));
    }

    @Given("^I have created a resource with name \"(.*?)\", customName \"(.*?)\"$")
    public void createResource(String resourceName, String resourceDisplayName){
        resource.setName(resourceName);
        resource.setCustomName(resourceDisplayName);
        resource.setFontIcon(DomainAppConstants.DEFAULT_RESOURCES_ICON);

        JSONObject responseResource = APIResourceMethods.createResource(resource);
        resource.setId(responseResource.getString(DomainAppConstants.KEY_ID));

        conferenceRoom.addResource(resource);

        UIMethods.refreshPage();
        UIMethods.switchPages(LeftBarOptions.CONFERENCE_ROOMS.getToPage());
    }

    @And("^I go to Conference Room page$")
    public void goToConferenceRoomPage(){
        conferenceRoomsPage = homePage.getLeftMenuPanel().clickOnConferenceRooms();
    }
    @And("^I select the resource button in the header page$")
    public void displayResourceInTableConferenceRoom(){
        conferenceRoomsPage.clickOnResourcesDisplayButton(resource);
    }

    @When("^I assign a Resource with quantity \"(.*?)\" to room \"(.*?)\"$")
    public void assignedResource(String quantity, String roomName){
        conferenceRoom.setDisplayName(roomName);
        conferenceRoom.setCustomDisplayName(roomName);

        UIMethods.refreshPage();
        UIMethods.switchPages(LeftBarOptions.CONFERENCE_ROOMS.getToPage());

        conferenceRoomsPage = homePage.getLeftMenuPanel().clickOnConferenceRooms();
        roomInfoPage = conferenceRoomsPage.openConferenceRoomSettings(conferenceRoom.getCustomDisplayName());

        resourceAssociatePage = roomInfoPage.clickOnResourceAssociateTab();
        resource.setQuantity(quantity);

        resourceAssociatePage.clickOnAddResources(resource).typeQuantityResources(resource);
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
        String roomId = DataBaseRoomMethods.obtainRoomId(conferenceRoom.getDisplayName());
        conferenceRoom.setId(roomId);

        JSONObject responseRoom = APIRoomMethods.obtainRoom(conferenceRoom);

        Assert.assertFalse("the room is disabled", responseRoom.getBoolean("enabled"));
    }
    @And("^the Room obtain by api should be contain the resource and quantity$")
    public void verifyResourceInRoom(){
        String id = DataBaseRoomMethods.obtainRoomId(conferenceRoom.getDisplayName());
        conferenceRoom.setId(id);

        JSONObject responseRoom = APIRoomMethods.obtainRoom(conferenceRoom);

        JSONArray resources = responseRoom.getJSONArray(DomainAppConstants.KEY_RESOURCES);
        String resId = resources.getJSONObject(0).getString(DomainAppConstants.KEY_RESOURCE_ID);
        int quantity = resources.getJSONObject(0).getInt(DomainAppConstants.KEY_QUANTITY);

        Assert.assertEquals("The resources is the same that was assigned",resource.getId() , resId);
        Assert.assertEquals("The quantity was assigned successfully", Integer.parseInt(resource.getQuantity()), quantity);
    }

    @Given("^I have a Room with name \"([^\"]*)\" that is associated with the Location \"([^\"]*)\"$")
    public void createResourceWithLocation(String roomName, String locationName) throws Throwable {
        Location associatedLocation = new Location();
        associatedLocation.setName(locationName);
        associatedLocation.setDisplayName(locationName);
        conferenceRoom.setDisplayName(roomName);

        JSONObject newLocation = APILocationMethods.createLocation(associatedLocation);

        locationIds.add(newLocation.getString(DomainAppConstants.KEY_ID));
        associatedLocation.setId(newLocation.getString(DomainAppConstants.KEY_ID));

        String roomId = DataBaseRoomMethods.obtainRoomId(conferenceRoom.getDisplayName());
        conferenceRoom.setId(roomId);

        APIRoomMethods.associateLocationToRoom(associatedLocation, conferenceRoom);
    }

    @And("^I have a Location with name \"([^\"]*)\"$")
    public void createNewLocation(String name) throws Throwable {
        location.setName(name);
        location.setDisplayName(name);
        JSONObject request = APILocationMethods.createLocation(location);
        conferenceRoom.setLocationId(request.getString(DomainAppConstants.KEY_ID));
        locationIds.add(request.getString(DomainAppConstants.KEY_ID));
    }

    @Then("^the current Room should be moved to the new Location$")
    public void isRoomAssociatedToLocation() throws Throwable {
        isAssociatedRoomToLocationRoomPage();
    }

    @And("^the current Room should be obtained by API request associated with the location$")
    public void roomAssociatedToLocation() throws Throwable {
        String roomId = DataBaseRoomMethods.obtainRoomId(conferenceRoom.getDisplayName());
        conferenceRoom.setId(roomId);
        JSONObject association = APIRoomMethods.obtainRoom(conferenceRoom);
        Assert.assertEquals("The room is associated with the location", conferenceRoom.getLocationId(), association.getString(DomainAppConstants.KEY_LOCATION_ID));
    }

    @And("^I assign the current Room to the new Location \"([^\"]*)\"$")
    public void I_assign_the_current_Room_to_the_new_Location(String locationName) throws Throwable {
        assignLocationToRoom(locationName);
    }

    @After(value = "@locationRoom")
    public void deleteLocation() throws Throwable {
        APILocationMethods.deleteLocation(conferenceRoom.getLocationId());
    }

    @After(value = "@locationRoom2")
    public void deleteLocations() throws Throwable {
        for(String id : locationIds){
            try {
                APILocationMethods.deleteLocation(id);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @After(value = "@disableRoom")
    public void enableConferenceRoom(){
        String roomId = DataBaseRoomMethods.obtainRoomId(conferenceRoom.getDisplayName());
        APIRoomMethods.enableRoom(roomId);

    }
    @After(value = "@AssociateResource")
    public void removeResource(){
        APIResourceMethods.deleteResources(resource);
    }
}
