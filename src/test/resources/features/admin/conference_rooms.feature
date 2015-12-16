@ConferenceRooms

Feature: Conference Rooms
  Allow to manage the rooms associated to Locations, Reserve a room

  Background:
    Given I log in successfully as "test" with password "Client123"

  @locationRoom
  Scenario: Assign a room to a locations from the Conference Room
    Given I have a Location with name "fundacion-jala"
      And I open the Room "Floor1-Room10" from the Conference Room
    When I assign the current Room to the Location "fundacion-jala"
    Then the current room is associated to the Location defined in the Locations page
      And the current Room should be obtained by API request associated with the location

  Scenario Outline: edit room info
    Given I open the Room "<RoomName>" from the Conference Room
    When I edit the following info: Display Name "<NewRoomName>", code "<Code>" and capacity "<Capacity>"
    Then the info edited should be obtained by API request for the Room "<NewRoomName>"
  Examples:
    | RoomName       | NewRoomName    | Code   | Capacity |
    | Floor1-Room10  | Floor1-Room001 | MyCode | 50       |
    | Floor1-Room001 | Floor1-Room10  |        |          |

  @locationRoom2
  Scenario: Associate Location to a Room that already is associated with another Location
    Given I have a Room with name "Floor1-Room15" that is associated with the Location "fundacion"
      And I have a Location with name "SACABA"
    When I open the Room "Floor1-Room16" from the Conference Room
      And I assign the current Room to the Location "SACABA"
    Then the current room is associated to the Location defined in the Locations page
      And the current Room should be obtained by API request associated with the location

  @disableRoom
  Scenario: Disable a room pressing the button enable/disable
    Given I open the Room "Floor1-Room13" from the Conference Room
    When I pressing the disable button
    Then The current Room should be disable
      And the information updated in the room should be obtained by API

  Scenario: Associate a resource to a conference room
    Given I have created a resource with name "Computer", customName "Computer"
      And I go to Conference Room page
      And I select the resource button in the header page
    When I open the Room "Floor1-Room1" from the Conference Room
      And I select the Resource Association Tab
      And I add "2" Resource to the Room
    Then the resource and quantity should be displayed for the room in the list
      And the Room obtain by api should be contain the resource id