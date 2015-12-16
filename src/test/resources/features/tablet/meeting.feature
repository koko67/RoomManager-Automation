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
   |Organizer              |Subject  |From |To   |Attendees                        |Body       |
   |ronald.salavatierra    |reunion  |14:00|14:30|                                 |Be on Time |
   |jose.cardozo           |organize |15:00|15:30|ronald.salvatierra@grupob01.local|Bring Paper|

  Scenario: Remove a meeting
    Given I have a meeting with the following information: "ronald.salvatierra", "reunion", "10:40", "11:10", "jose.cardozo@grupob01.local"
      And I navigate to Available section
    When I remove the meeting
    Then an information message should be displayed "Meeting successfully removed"
      And the meeting should not be displayed in the Schedule bar
      And the meeting information should not be displayed in the Next section of Main page
      And the meeting should not be listed in the meetings of Room using the API

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
    Given I have a meeting with the following information: "ronald.salvatierra", "reunion", "10:40", "11:10", "jose.cardozo@grupob01.local"
      And I navigate to Available section
    When I update the meeting information: "Planning Change Hour", "11:10", "11:40", "Close the Door 11:10"
    Then an information message should be displayed "Meeting successfully updated"
      And the meeting should be displayed in the Schedule bar
      And the meeting information should be displayed in the Next section of Main page
      And the meeting should be listed in the meetings of Room using the API

  @RemoveMeeting
  Scenario: Try to create a meeting at the same time than other meeting
    Given I have a meeting with the following information: "ronald.salvatierra", "reunion", "10:40", "11:10", "jose.cardozo@grupob01.local"
      And I navigate to Available section
    When I create a meeting at the same time than other with the following information: "jose.cardozo", "Demo", "10:40", "11:10", "ronald.salvatierra@grupob01.local", "Bring a Paper and Pencil"
    Then an information message should be displayed "There is a conflict with another meeting, please choose another time interval"
      And the meeting should not be displayed in the Schedule bar
      And the meeting information should not be displayed in the Next section of Main page
      And the meeting should not be listed in the meetings of Room using the API



