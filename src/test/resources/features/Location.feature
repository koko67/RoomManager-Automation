@Location
Feature: Location of the rooms
  In this feature will be able to create feature and associate rooms to a specific location

  Background:
    Given I log in successfully as "test" with password "Client123"

  Scenario: User should be able to create the location
    Given I go to the "Location" page
    When I create a Location with Name "Custom Name" and Display Name "display name"
    Then the location "Custom Name" should be displayed in the Location page
      And the location "Custom Name" should be obtained by API request

  Scenario: Assign rooms to a Location from the Location Association tab
    Given I open the Location details for Location "name"
    When I associate the Room "Room Name" with the Location "Location Name"
    Then the Room "Room Name" is associated to the Location "Location Name" in the Location page
      And the Location "Custom Name" should be obtained by API request for the Room "Room Name"

  Scenario: User should be able to remove the room associated to location
    Given I go to the "location" page
    And  I have a location "Custom Name" with display name " display name" created
    And  I open the location "Custom Name"
    And I select the "Locations Associations" tab
    When I dis-associate the room "Room Name" of the location "Custom Name"
    Then The room "Room Name" should be displayed in the column of "Available"




