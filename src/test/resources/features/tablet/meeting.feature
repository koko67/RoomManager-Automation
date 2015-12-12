@Meetings
Feature: Meetings
  Background:
    Given I'm sign in with the user "test" in the login page selecting the Room "Floor1-Room11"


  @RemoveMeeting
  Scenario Outline: : Create a meeting
    Given I navigate to Available section
   When I create successfully a meeting with the following information: "<Organizer>", "<Subject>", "<From>", "<To>", "<Attendees>", "<Body>"
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
      And I create successfully a meeting with the following information: "Ronald", "meeting", "08:00", "09:00", "jose.cardozo", "Close the door on time"
    When I remove the meeting
    Then an information message should be displayed "Meeting successfully removed"
    And the meeting should not be displayed in the Schedule bar
    And the meeting information should not be displayed in the Next section of Main page
      And the meeting should be listed in the meetings of Room using the API


  Scenario Outline: Try to create a meeting with missing information
    Given I navigate to Available section
    When I create unsuccessfully a meeting with the following information: "<Organizer>", "<Subject>", "<From>", "<To>", "<Attendees>", "<Body>"
    Then an error "<Error>" message should be displayed
      And the meeting should not be displayed in the Schedule bar
      And the meeting information should not be displayed in the Next section of Main page
      And the meeting should not be listed in the meetings of Room using the API

  Examples:
    |Organizer |Subject  |From |To   |Attendees         |Body       |Error                   |
    |          |meeting  |14:00|14:30|ronald.salvatierra|Be on Time |Organizer is required   |
    |jose      |         |15:00|15:30|                  |Bring Paper|Subject is required     |
    |jorge     |planning |16:00|15:30|                  |Bring Paper|Start time must be smaller than end time|

  @RemoveMeeting
  Scenario: Update a meeting
    Given I navigate to Available section
    And I create successfully a meeting with the following information: "Ronald", "Planning", "08:00", "09:00", "ronald.salvatierra ", "Close the Door 08:05"
    When I update the meeting information: "Planning Change Hour", "11:00", "11:30", "Close the Door 11:05"
    Then an information message should be displayed "Meeting successfully updated"
    And the meeting should be displayed in the Schedule bar
    And the meeting information should be displayed in the Next section of Main page
    And the meeting should be listed in the meetings of Room using the API

  Scenario: Try to create a meeting at the same time than other meeting
    Given I navigate to Available section
    And I create successfully a meeting with the following information: "Ronald", "Planning", "08:00", "09:00", "ronald.salvatierra ", "Close the Door 08:05"
    When I create a meeting with the following information: "Jose", "Planning Meeting", "08:00", "09:00", "jose.cardozo", "Bring a pencil and paper"
    Then an information error message should be displayed in Credentials Page
    And the meeting should not be displayed in the Schedule bar
    And the meeting information should not be displayed in the Next section of Main page
      And the meeting should not be listed in the meetings of Room using the API



