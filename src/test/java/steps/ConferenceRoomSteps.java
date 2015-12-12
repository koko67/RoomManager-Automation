package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.ConferenceRoom;
import entities.Resource;
import junit.framework.Assert;
import ui.pages.admin.ConferenceRoomsPage;
import ui.pages.admin.ResourceAssociatePage;
import ui.pages.admin.RoomSettingsPage;
import ui.pages.admin.HomePage;

/**
 * Author: JorgeAvila
 * Date: 12/3/15
 */
public class ConferenceRoomSteps {

    private HomePage homePage;
    private ConferenceRoomsPage conferenceRoomsPage;
    private RoomSettingsPage roomInfoPage;

    private ConferenceRoom conferenceRoom;
    private Resource resource;
    private ResourceAssociatePage resoureAssociatePage;

    public ConferenceRoomSteps(ConferenceRoom conferenceRoom){
        homePage = new HomePage();
        resource = new Resource();
        this.conferenceRoom = conferenceRoom;
    }

    @Given("^I open the Room \"(.*?)\" from the Conference Room$")
    public void openRoomFromConferenceRoom(String roomName) {
        conferenceRoom.setCustomDisplayName(roomName);
        conferenceRoom.setDisplayName(roomName);
        conferenceRoomsPage = homePage.getLeftMenuPanel()
                                      .clickOnConferenceRooms("Conference Rooms");

        roomInfoPage = conferenceRoomsPage.openConferenceRoomSettings(roomName);
    }

    @When("^I assign the Room \"(.*?)\" to the Location \"(.*?)\"$")
    public void assignRoomToALocation(String roomName, String locationName){

    }

    @Then("^the Room \"(.*?)\" is associated to the Location \"(.*?)\" in the Location page$")
    public void isAssociatedRoomToLocationRoomPage(String roomName, String locationName){

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
        System.out.println("the element created was: " + resourceName);
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

}
