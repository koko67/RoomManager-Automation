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

  Scenario: Out of order wrong in a Conference Room
    Given I open the Room "Room Name" from the Conference Room
      And I select the "Out Of Order Planning" Tab
    When I set a date "12/12/12" from today in the "From" field
      And I set a date "12/12/12" from today in the "To" field
    Then an error message should be displayed in the form
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
    When I open the Room "Room Name" from the Conference Room
      And I select the "Resource Association" Tab
      And I add "1" Resource "Computer" to the Room
    Then the Resource amount should be "1" added in the Room row table