package steps;

import common.CommonMethods;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.Meeting;
import junit.framework.Assert;
import ui.PageTransporter;
import ui.pages.tablet.*;

/**
 * User: Ronald Butron
 * Date: 12/10/15
 */
public class meetingSteps {

    LoginPageTablet loginTabletPage;
    HomePageTablet homePageTablet;
    StatusPageTablet statusPageTablet;
    SchedulePageTablet schedulePageTablet;
    ExchangeCredentialsPage credentialsPage;
    Meeting meeting;
    public meetingSteps(){
         meeting = new Meeting();
    }

    @Given("^I'm sign in with the user \"(.*?)\" in the login page selecting the Room \"(.*?)\"$")
    public void lonInToTabletVersion(String userName, String roomName){
        if(!CommonMethods.isItInTheLoginPageTablet() && !CommonMethods.isItInTheHomePageTablet()){
            loginTabletPage = PageTransporter.getInstance().toTabletLoginPage();
            statusPageTablet = loginTabletPage.signInSuccessfully(userName);
            homePageTablet = statusPageTablet.selectRoomSuccessfully(roomName);
        } else {
            homePageTablet = new HomePageTablet();
        }
    }

    @And("^I select the room \"(.*?)\"$")
    public void selectARoom(String roomName){
        homePageTablet = statusPageTablet.selectRoomSuccessfully(roomName);
    }

    @Given("^I navigate to Available section$")
    public void goToScheduleSection(){
        schedulePageTablet = homePageTablet.goToSchedulePage();
    }


    @When("^I create a meeting with the following information: \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\"$")
    public void createAMetting(String organizer, String subject, String from, String to, String attendees, String body){
        meeting.setAllForm(organizer, subject, from, to, attendees, body);
        credentialsPage =  schedulePageTablet.createSuccessfullyAMeeting(meeting);
        credentialsPage.fillExchangeCredentialsForm();
    }

    @Then("^an information message should be displayed \"(.*?)\"$")
    public void isInformationMessageDisplayed(String configMessage){
        Assert.assertTrue(schedulePageTablet.isDisplayedTheConfigMessage(configMessage));
    }

    @And("^the meeting should be displayed in the Schedule bar$")
    public void isTheMeetingDisplayedScheduleBar(){
        Assert.assertTrue(schedulePageTablet.isTheMeetingDisplayedInTheScheduleBar(meeting));
    }

    @And("^the meeting information should be displayed in the Next section of Main page$")
    public void isTheMeetingDisplayedInTheNextSection(){
        homePageTablet = schedulePageTablet.goHome();
        Assert.assertTrue(homePageTablet.isDisplayedTheNextMeeting(meeting));
    }

    @When("^I remove the meeting$")
    public void removeAMeeting(){
        if(CommonMethods.isItInTheHomePageTablet()){
            schedulePageTablet = homePageTablet.goToSchedulePage();
        }
        credentialsPage = schedulePageTablet.removeMeeting(meeting);
        schedulePageTablet = credentialsPage.fillTheExchangePasswordToRemoveAMeeting();

    }

    @And("^the meeting should be removed from the the Schedule bar$")
    public void isNotTheMeetingDisplayedScheduleBar(){
        Assert.assertTrue(schedulePageTablet.isNotTheMeetingDisplayedInTheScheduleBar(meeting));
    }

    @And("^the meeting information should be removed from the Next section of Main page$")
    public void isNotTheMeetingDisplayedInTheNextSection(){
        homePageTablet = schedulePageTablet.goHome();
        Assert.assertTrue(homePageTablet.isNotDisplayedTheNextMeeting(meeting));
    }

    @After(value = "@RemoveMeeting")
    public void removeMT(){
        removeAMeeting();
        schedulePageTablet.goHome();
    }
}
