package steps;

import api.APILibrary;
import api.EndPoints;
import com.mongodb.DBObject;
import cucumber.api.java.en.And;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.ConferenceRoom;
import entities.Location;
import mongodb.DataBaseDriver;
import org.json.JSONObject;
import ui.pages.admin.ConferenceRoomsPage;
import ui.pages.admin.LocationPage;

import entities.Resource;
import framework.UIMethods;
import junit.framework.Assert;
import ui.pages.admin.ConferenceRoomsPage;
import ui.pages.admin.ResourceAssociatePage;

import ui.pages.admin.RoomSettingsPage;
import ui.pages.admin.HomePage;
import utils.LeftBarOptions;

/**
 * Author: Jorge Avila
 * Date: 12/3/15
 */
public class ConferenceRoomSteps {

    private HomePage homePage;
    private ConferenceRoomsPage conferenceRoomsPage;
    private RoomSettingsPage roomInfoPage;
    private LocationPage locationPage;
    private APILibrary apiLibrary;
    private DataBaseDriver dataBaseDriver;
    private ConferenceRoom conferenceRoom;
    private Location location;
    private Resource resource;
    private ResourceAssociatePage resoureAssociatePage;

    public ConferenceRoomSteps(ConferenceRoom conferenceRoom){
        homePage = new HomePage();
        resource = new Resource();
        this.conferenceRoom = conferenceRoom;
        this.location = new Location();
        this.apiLibrary = new APILibrary();
        dataBaseDriver = new DataBaseDriver();
    }

    @Given("^I open the Room \"(.*?)\" from the Conference Room$")
    public void openRoomFromConferenceRoom(String roomName) {
        conferenceRoom.setDisplayName(roomName);
        conferenceRoom.setCustomDisplayName(roomName);
        conferenceRoomsPage = homePage.getLeftMenuPanel()
                                      .clickOnConferenceRooms("Conference Rooms");

        roomInfoPage = conferenceRoomsPage.openConferenceRoomSettings(conferenceRoom.getCustomDisplayName());
    }

    @When("^I assign the current Room to the Location \"(.*?)\"$")
    public void assignRoomToALocation(String locationName){
        location.setName(locationName);
        location.setDisplayName(locationName);
        roomInfoPage.expandLocations()
                .expandDefaultLocation()
                .selectLocationByName(locationName)
                .clickOnSaveButton();

    }


    @Then("^the Room \"(.*?)\" is associated to the Location \"(.*?)\" in the Locations page$")
    public void isAssociatedRoomToLocationRoomPage(String roomName, String locationName){
        locationPage = homePage.getLeftMenuPanel()
                .clickOnLocationPage("Locations");
        boolean existAssociated = locationPage.clickEditLocation(location)
                .goLocationAssociationTab()
                .existsRoomAssociated(conferenceRoom);
        Assert.assertTrue(existAssociated);

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

    }
    @Given("^I have created a resource with name \"(.*?)\", customName \"(.*?)\"$")
    public void createResource(String resourceName, String resourceDisplayName){
        resource.setName(resourceName);
        resource.setCustomName(resourceDisplayName);
        resource.setFontIcon("fa fa-desktop");
    }

    @And("^I go to \"(.*?)\" page$")
    public void goToConferenceRoomPage(String namePage){
        conferenceRoomsPage = homePage.getLeftMenuPanel().clickOnConferenceRooms(namePage);
    }
    @And("^I select the resource button in the header page$")
    public void displayResourceInTableConferenceRoom(){
        conferenceRoomsPage.clickOnResourcesDisplayButton(resource);
    }
    @And("^I select the Resource Association Tab$")
    public void selectResourceAssociateTab(){
        resoureAssociatePage = roomInfoPage.clickOnResourceAssociateTab();
    }
    @And("^I add \"(.*?)\" Resource to the Room$")
    public void addResourcesToRoom(String quantity){
        resource.setQuantity(quantity);
        resoureAssociatePage.clickOnAddResources(resource)
                            .typeQuantityResources(resource);
        conferenceRoomsPage = resoureAssociatePage.clickOnSaveButton();
    }
    @Then("^the resource and quantity should be displayed for the room in the list$")
    public void verifyResourceAndQuantity(){
        conferenceRoomsPage.makeSureResourcesIsSelect(resource);
        boolean verificationIcon = conferenceRoomsPage.isTheResourceCorrect(resource, conferenceRoom);
        boolean verificationQuantity = conferenceRoomsPage.isTheSameQuantityOfResources(resource, conferenceRoom);
        Assert.assertTrue("The resource icon is the same that it's assigned ",verificationIcon);
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

    }
}
