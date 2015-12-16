@Location
Feature: Location of the rooms
  In this feature will be able to create feature and associate rooms to a specific location

  Background:
    Given I log in successfully as "test" with password "Client123"

  Scenario: User should be able to create the Location
    Given I go to the "Location" page
    When I create a Location with Name "B2-05" and Display Name "B2"
    Then the Location  should be displayed in the Location page
      And the Location should be obtained by API request

  Scenario: Assign rooms to a Location from the Location Association tab
    Given I open the Location details for Location "D4-03"
    When I associate the Room "Floor1-Room1" with the Location
    Then the Room "Floor1-Room1" should be associated to the Location in the Conference Rooms page
      And the current Room should be obtained by api contains the LocationId of this Location

  Scenario: User should be able to dis-associate room from a Location
    Given I have a Location "Custom Name" with display name "display name" associate to Room with name "ConfPrueba5"
      And I open the Location and I select the Locations Associations tab
    When I dis-associate the Room "ConfPrueba5" of the Location
    Then The Room "ConfPrueba5" should be displayed in the column of "Available"
      And the current Room should not obtained by api contains the LocationId of this Location