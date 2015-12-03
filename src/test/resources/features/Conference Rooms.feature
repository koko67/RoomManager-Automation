@ConferenceRooms

Feature: Conference Rooms
  Allow to manage the rooms associated to Locations, Reserve a room

  Background:
    Given I log in successfully as "test" with password "Client123"

  Scenario: Assign a room to a locations from the Conference Room
    Given I open the Room "Room Name" from the Conference Room
    When I assign the Room "Room Name" to the Location "Location Name"
    Then the Room "Room Name" is associated to the Location "Location Name" in the Location page
      And the Location "Custom Name" should be obtained by API request for the Room "Room Name"

  Scenario: edit room info
    Given I open the Room "Room Name" from the Conference Room
    When I edit the following info: Display Name "Display Name", code "Code" and capacity "Capacity"
    Then the info edited should be obtained by API request for the Room "Room Name"