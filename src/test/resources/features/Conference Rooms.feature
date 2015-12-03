@ConferenceRooms

Feature: Conference Rooms
  Allow to manage the rooms associated to Locations, Reserve a room

  Background:
    Given I log in successfully as "test" with password "Client123"

  Scenario: Assign a room to a locations from the Conference Room
    Given I open the Room "Room Name" from the Conference Room
    When I assign the room "Room Name" to the location "Location Name"
    Then the room "Room Name" is associated to the location "Location Name" in the Location page
