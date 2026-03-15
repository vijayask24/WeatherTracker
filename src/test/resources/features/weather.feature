Feature: Weather API Testing

  @smoke @weather
  Scenario: Retrieve weather data for multiple major international cities
    Given I load the list of international cities from metadata file
    When I retrieve weather data for these cities
    Then the system should validate the responses for all cities
    And the cities should match the requested list

  @smoke @weathercoordinates @regression
  Scenario: Verify weather data for coordinates
    Given the weather API is available
    When I request weather data for latitude -33.865143 and longitude 151.209900
    Then the response status should be 200
    And the response should contain valid weather data

  @smoke @weatherAUcity @regression
  Scenario: Identify warmest Australian capital city
    Given the weather API is available
    When I retrieve weather data for all Australian capital cities
    Then the system should identify the warmest capital city

  @smoke @weatherUSstate @USstate @regression
  Scenario: Identify coldest US state from metadata file
    Given the weather API is available
    Given I load the list of US states from metadata file
    Then I determine the coldest US state
    Then the coldest state should be displayed
