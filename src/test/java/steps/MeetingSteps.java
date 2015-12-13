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
public class MeetingSteps {

    LoginPageTablet loginTabletPage;
    HomePageTablet homePageTablet;
    StatusPageTablet statusPageTablet;
    SchedulePageTablet schedulePageTablet;
    Meeting meeting;
    public MeetingSteps(){
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


    @When("^I create successfully a meeting with the following information: \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\"$")
    public void createAMeeting(String organizer, String subject, String from, String to, String attendees, String body){
        meeting.setAllForm(organizer, subject, from, to, attendees, body);
        meeting.setDeleteSubject(subject);
        schedulePageTablet.createSuccessfullyAMeeting(meeting);
        schedulePageTablet.fillSuccessfullyExchangeCredentialsForm();
    }

    @When("^I create unsuccessfully a meeting with the following information: \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\"$")
    public void createUnsuccessfullyAMeeting(String organizer, String subject, String from, String to, String attendees, String body){
        meeting.setAllForm(organizer, subject, from, to, attendees, body);
        schedulePageTablet.createUnsuccessfullyAMeeting(meeting);
    }

    @When("^I create a meeting at the same time than other with the following information: \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\"$")
    public void createMeetingAtTheSameTimeThanOther(String organizer, String subject, String from, String to, String attendees, String body){
        meeting.setAllForm(organizer, subject, from, to, attendees, body);
        schedulePageTablet.createSuccessfullyAMeeting(meeting);
        schedulePageTablet.fillUnsuccessfullyExchangeCredentialsForm();
        schedulePageTablet.cancelExchangeCredentials();
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
        schedulePageTablet.removeMeeting(meeting);
    }

    @And("^the meeting should not be displayed in the Schedule bar$")
    public void isNotTheMeetingDisplayedScheduleBar(){
        Assert.assertTrue(schedulePageTablet.isNotTheMeetingDisplayedInTheScheduleBar(meeting));
    }

    @And("^the meeting information should not be displayed in the Next section of Main page$")
    public void isNotTheMeetingDisplayedInTheNextSection(){
        homePageTablet = schedulePageTablet.goHome();
        Assert.assertTrue(homePageTablet.isNotDisplayedTheNextMeeting(meeting));
    }

    @Then("^an error \"(.*?)\" message should be displayed$")
    public void isTheErrorMessageDisplayed(String errorMessage){
        Assert.assertTrue(schedulePageTablet.isDisplayedErrorMessage(errorMessage));
    }

    @When("^I update the meeting information: \"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\"$")
    public void updateMeetingInformation(String subject, String from, String to, String body){
        schedulePageTablet.selectMeeting(meeting.getSubject());
        meeting.setUpdateForm(subject, from, to, body);
        schedulePageTablet.updateMeeting(meeting);
    }

    @After(value = "@RemoveMeeting")
    public void removeMT(){
        removeAMeeting();
        schedulePageTablet.goHome();
    }
}
