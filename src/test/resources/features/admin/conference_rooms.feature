@ConferenceRooms

Feature: Conference Rooms
  Allow to manage the rooms associated to Locations, Reserve a room

  Background:
    Given I log in successfully as "test" with password "Client123"

  Scenario: Assign a room to a locations from the Conference Room
    Given I open the Room "Floor1-Room10" from the Conference Room
    When I assign the current Room to the Location "fundacion-jala"
    Then the Room "Floor1-Room10" is associated to the Location "fundacion-jala" in the Locations page
      And the Location "Custom Name" should be obtained by API request for the Room "Room Name"

  Scenario Outline: edit room info
    Given I open the Room "<RoomName>" from the Conference Room
    When I edit the following info: Display Name "<NewRoomName>", code "<Code>" and capacity "<Capacity>"
    Then the info edited should be obtained by API request for the Room "Room Name"
  Examples:
    | RoomName       | NewRoomName    | Code   | Capacity |
    | Floor1-Room10  | Floor1-Room001 | MyCode | 50       |
    | Floor1-Room001 | Floor1-Room10  | MyCode | 50       |

  Scenario: Out of order wrong in a Conference Room
    Given I open the Room "Room Name" from the Conference Room
      And I select the "Out Of Order Planning" Tab
    When I set a date "12/12/12" from today in the "From" field
      And I set a date "12/12/12" from today in the "To" field
    Then an error message "To field must be greater than From field Cannot establish out of order as an past event" should be displayed in the form
      And the room reserve should not be stored

  Scenario: Schedule icon is added in the conference room
    Given I Open the Room "Room Name" from the Conference Room
      And I select the "Out Of Order Planning" Tab
    When I assign a date "20" seconds late from now in the "From" field
      And I assign a date "40" seconds late from now in the "To" field
    Then a schedule icon should be displayed for the conference room
      And the info edited should be obtained by API request for the Room "Room Name"

  Scenario: Associate a resource to a conference room
    Given I have created a resource with name "Computer"
      And I go to the "Conference Room" page
      And I select the resource "Computer" button in the header page
    When I open the Room "Room Name" from the Conference Room
      And I select the "Resource Association" Tab
      And I add "1" Resource "Computer" to the Room
    Then the resource and quantity should be displayed for the room in the list
