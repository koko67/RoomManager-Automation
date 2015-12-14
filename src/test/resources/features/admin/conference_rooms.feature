@ConferenceRooms

Feature: Conference Rooms
  Allow to manage the rooms associated to Locations, Reserve a room

  Background:
    Given I log in successfully as "test" with password "Client123"

  Scenario: Assign a room to a locations from the Conference Room
    Given I open the Room "Room Name" from the Conference Room
    When I assign the Room "Room Name" to the Location "Location Name"
    Then the Room "Room Name" is associated to the Location "name" in the Location page
    And the Location "Custom Name" should be obtained by API request for the Room "Room Name"

  Scenario: edit room info
    Given I open the Room "Floor1-Room10" from the Conference Room
    When I edit the following info: Display Name "Room001", code "MyCode" and capacity "50"
    Then the info edited should be obtained by API request for the Room "Room Name"

  Scenario: Out of order wrong in a Conference Room
    Given I open the Room "Room Name" from the Conference Room
    And I select the "Out Of Order Planning" Tab
    When I set a date "12/12/12" from today in the "From" field
    And I set a date "12/12/12" from today in the "To" field
    Then an error message "To field must be greater than From field Cannot establish out of order as an past event" should be displayed in the form
    And the room reserve should not be stored

  Scenario: Disable a room pressing the button enable/disable
    Given I open the Room "Floor1-Room11" from the Conference Room
    When I pressing the disable button
    Then The current Room should be disable
      And the information updated in the room should be obtained by API

  Scenario: Associate a resource to a conference room
    Given I have created a resource with name "Computer", customName "Computer2"
      And I go to "Conference Room" page
      And I select the resource button in the header page
    When I open the Room "Floor1-Room1" from the Conference Room
      And I select the Resource Association Tab
      And I add "2" Resource to the Room
    Then the resource and quantity should be displayed for the room in the list