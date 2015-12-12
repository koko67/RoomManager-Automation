@Meetings
Feature: Meetings
  Background:
    Given I'm sign in with the user "test" in the login page selecting the Room "Floor1-Room11"


  @RemoveMeeting
  Scenario Outline: : Create a meeting
    Given I navigate to Available section
   When I create a meeting with the following information: "<Organizer>", "<Subject>", "<From>", "<To>", "<Attendees>", "<Body>"
   Then an information message should be displayed "Meeting successfully created"
      And the meeting should be displayed in the Schedule bar
      And the meeting information should be displayed in the Next section of Main page
      And the meeting should be listed in the meetings of Room using the API
  Examples:
   |Organizer |Subject  |From |To   |Attendees         |Body       |
   |ronald    |meeting  |14:00|14:30|ronald.salvatierra|Be on Time |
   |jose      |organize |15:00|15:30|                  |Bring Paper|

  Scenario: Remove a meeting
    Given I navigate to Available section
      And I create a meeting with the following information: "Ronald", "meeting", "08:00", "09:00", "jose.cardozo", "Close the door on time"
    When I remove the meeting
    Then an information message should be displayed "Meeting successfully removed"
      And the meeting should be removed from the the Schedule bar
      And the meeting information should be removed from the Next section of Main page
      And the meeting should be listed in the meetings of Room using the API


  Scenario Outline: Try to create a meeting with missing information
    Given I navigate to Schedule section
    When I create a meeting with the following information: "<Organizer>", "<Subject>", "<From>", "<To>", "<Attendees>", "<Body>"
    Then an error "<Error>" message should be displayed
      And the meesting should not be displayed in the Schedule bar
      And the meeting information should not be displayed in the Next section of Main page
      And the meeting should not be listed in the meetings of Room using the API

  Examples:
    |Organizer |Subject  |From |To   |Attendees         |Body       |Error  |
    |ronald    |meeting  |14:00|14:30|ronald.salvatierra|Be on Time |       |
    |jose      |organize |15:00|15:30|                  |Bring Paper|       |


  Scenario: Try to create a meeting at the same time than other meeting
    Given I navigate to Schedule section
      And I create a meeting with the following information: "fblajbf", "kdf", "8:00", "9:00", "jidflg", "jdslf"
    When I create a meeting with the following information: "adsk", "kdf", "8:00", "9:00", "jidflg", "jdslf"
    Then an error "Error" message should be displayed
      And the meeting should not be displayed in the Schedule bar
      And the meeting information should not be displayed in the Next section of Main page
      And the meeting should not be listed in the meetings of Room using the API


  Scenario: Try to create a meeting in the room out of order
    Given I have a room "" with a state Out Of Order between the hours "" to ""
      And I navigate to Schedule section
    When I create a meeting with the following information: "fblajbf", "kdf", "8:00", "9:00", "jidflg", "jdslf"
    Then an error "Error Message" message should be displayed
      And the meeting should not be displayed in the Schedule bar
      And the meeting information should not be displayed in the Next section of Main page
      And the meeting should not be listed in the meetings of Room using the API


  Scenario: Update a meeting
    Given I navigate to Available section
      And I create a meeting with the following information: "fblajbf", "kdf", "8:00", "9:00", "jidflg", "jdslf"
    When I update the meeting information: "fblajbf", "kdf", "8:00", "9:00", "jidflg", "jdslf"
    Then an information message should be displayed
      And the meeting should be displayed in the Schedule bar
      And the meeting information should be displayed in the Next section of Main page
      And the meeting should be listed in the meetings of Room using the API


