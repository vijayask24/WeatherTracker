package stepdefinitions;

import io.cucumber.java.en.*;
import steps.WeatherSteps;
import utils.ConfigReader;
import utils.FileReaderUtil;
import static org.junit.Assert.assertNotNull;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class WeatherStepDefinitions {

    WeatherSteps weatherSteps = new WeatherSteps();

    List<String> australianCapitals =
            Arrays.asList("Sydney","Melbourne","Brisbane","Perth","Adelaide","Canberra","Hobart","Darwin");

    String warmestCity;

    @Given("the weather API is available")
    public void api_available(){}

    // =============================
    // AC1: Weather for multiple international cities
    // =============================
    // =============================
    // Load metadata
    // =============================
@Given("I load the list of international cities from metadata file")
public void load_international_cities() {
    cities = FileReaderUtil.readInternationalCities();
    System.out.println("International cities loaded and verified");
    for (String city : cities) {
        System.out.println(city);
    }
}

    // =============================
    // Retrieve weather
    // =============================
    private String getApiKey() {
        return ConfigReader.getProperty("weather.api.key");
    }

    private String getBaseUrl() {
        return ConfigReader.getProperty("weather.base.url");
    }
    private List<String> responseCities;

    @When("I retrieve weather data for these cities")
    public void retrieve_weather_for_cities() {
        responseCities = weatherSteps.findWeatherForCities(cities, getApiKey(), getBaseUrl());
    }

    // =============================
    // Validate responses
    // =============================
    @Then("the system should validate the responses for all cities")
    public void validate_responses() {
        assertEquals("Number of responses doesn't match cities requested", cities.size(), responseCities.size());
    }

    @Then("the cities should match the requested list")
    public void verify_city_names() {
        for (int i = 0; i < cities.size(); i++) {
            assertEquals("City mismatch at index " + i, cities.get(i), responseCities.get(i));

        }
    }

    // =============================
    // AC2: COORDINATES WEATHER
    // =============================

    @When("I request weather data for latitude {double} and longitude {double}")
    public void request_coordinates(double lat,double lon){
        weatherSteps.getWeatherByCoordinates(lat,lon);
    }

    @Then("the response status should be {int}")
    public void verify_status(int code){

        weatherSteps.verifyStatusCode(code);
    }

    @Then("the response should contain city {string}")
    public void verify_city(String city){
        weatherSteps.verifyCity("City of " + city);

    }

    @Then("the response should contain valid weather data")
    public void verify_weather(){

        weatherSteps.verifyValidWeatherData();
    }

    // =============================
    // AC3: Warmest AU capital city
    // =============================

    @When("I retrieve weather data for all Australian capital cities")
    public void retrieve_capital_weather(){

        warmestCity = weatherSteps.findWarmestCity(australianCapitals);
    }

    @Then("the system should identify the warmest capital city")
    public void verify_warmest_city(){

        System.out.println("Warmest Australian Capital: " + warmestCity);
    }

    // =============================
    // AC4: Load metadata file
    // =============================

    private List<String> cities;
    private String coldestState;

    @Given("I load the list of US states from metadata file")
    public void load_metadata_file() {

        cities = FileReaderUtil.readStates();

        System.out.println("Cities loaded from metadata:");
        for(String city : cities){
            System.out.println(city);
        }
    }

    // =============================
    // AC4: Find coldest state
    // =============================

    @Then("I determine the coldest US state")
    public void determine_coldest_state() {

        coldestState = weatherSteps.findColdestState(cities);

        assertNotNull(coldestState);
    }

    // =============================
    // AC4: Print result
    // =============================

    @Then("the coldest state should be displayed")
    public void display_coldest_state() {

        System.out.println("Final Result -> Coldest State is '" + coldestState + "'");
    }

}