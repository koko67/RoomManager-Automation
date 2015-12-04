@Location
Feature: Location of the rooms
  In this feature will be able to create feature and associate rooms to a specific location

  Background:
    Given I log in successfully as "test" with password "Client123"

  Scenario: User should be able to create the Location
    Given I go to the "Location" page
    When I create a Location with Name "Custom Name" and Display Name "display name"
    Then the Location "Custom Name" should be displayed in the Location page
      And the Location "Custom Name" should be obtained by API request

  Scenario: Assign rooms to a Location from the Location Association tab
    Given I open the Location details for Location "name"
    When I associate the Room "Room Name" with the Location "Location Name"
    Then the Room "Room Name" is associated to the Location "Location Name" in the Location page
      And the Location "Custom Name" should be obtained by API request for the Room "Room Name"

  Scenario: User should be able to dis-associate room from a Location
    Given I have a Location "Custom Name" with display name "display name" associate to Room with name "Room name"
      And I open the Location "Custom Name" and I select the "Locations Associations" tab
    When I dis-associate the Room "Room Name" of the Location "Custom Name"
    Then The Room "Room Name" should be displayed in the column of "Available"
      And the Location "Custom Name" obtained by API request should not contains the Room "Room Name"
