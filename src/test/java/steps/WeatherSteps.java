package steps;

import net.serenitybdd.rest.SerenityRest;
import io.restassured.response.Response;
import utils.ConfigReader;
import net.serenitybdd.core.Serenity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class WeatherSteps {

    private Response response;

    private String getApiKey() {
        return ConfigReader.getProperty("weather.api.key");
    }

    private String getBaseUrl() {
        return ConfigReader.getProperty("weather.base.url");
    }

// =============================
// AC1: Weather for multiple international cities
// =============================
    public List<String> findWeatherForCities(List<String> cities, String apiKey, String baseUrl) {

        List<String> responseCities = new ArrayList<>();

        for (String city : cities) {

            Response res = SerenityRest.given()
                    .queryParam("city", city)
                    .queryParam("key", getApiKey())
                    .when()
                    .get(getBaseUrl());

            res.then().statusCode(200);

            String responseCity = res.jsonPath().getString("location.name");
            responseCities.add(responseCity);

            // Optional: Log for Serenity report
            Serenity.recordReportData().withTitle("Weather API response for " + city)
                    .andContents(res.asString());

            System.out.println("Weather API response for " + city + ": " + responseCity);
        }

        return responseCities;
    }

    // =============================
    // AC2: WEATHER DATA USING COORDINATES
    // =============================

    public void getWeatherByCoordinates(double lat, double lon) {

        response =
                SerenityRest.given()
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("key", getApiKey())
                        .when()
                        .get(getBaseUrl());
    }

    // =============================
    // COMMON VALIDATIONS
    // =============================

    public void verifyStatusCode(int code) {
        response.then().statusCode(code);
    }

    public void verifyCity(String city) {
        response.then().body("data[0].city_name", equalTo(city));
    }

    public void verifyValidWeatherData() {
        response.then().body("data[0].temp", notNullValue());
    }

    // =============================
    // AC3: WARMEST AUSTRALIAN CITY
    // =============================

    public String findWarmestCity(List<String> cities) {

        double maxTemp = Double.NEGATIVE_INFINITY;
        String warmestCity = "";

        for (String city : cities) {

            Response res =
                    SerenityRest.given()
                            .queryParam("city", city)
                            .queryParam("key", getApiKey())
                            .when()
                            .get(getBaseUrl());

            res.then().statusCode(200);

            double temp = res.jsonPath().getDouble("data[0].temp");

            if (temp > maxTemp) {
                maxTemp = temp;
                warmestCity = city;
            }
        }

        //System.out.println("Warmest Australian city detected: " + warmestCity + " (" + maxTemp + "°C)");

        return warmestCity;
    }

    // =============================
    // AC4: COLDEST US STATE
    // =============================

    public String findColdestState(List<String> cities) {

        double minTemp = Double.POSITIVE_INFINITY;
        String coldestCity = "";

        // Map city to state
        Map<String, String> cityStateMap = new HashMap<>();

        cityStateMap.put("Austin", "Texas");
        cityStateMap.put("Sacramento", "California");
        cityStateMap.put("Tallahassee", "Florida");
        cityStateMap.put("Albany", "New York");
        cityStateMap.put("Juneau", "Alaska");
        cityStateMap.put("Denver", "Colorado");
        cityStateMap.put("Phoenix", "Arizona");
        cityStateMap.put("Carson City", "Nevada");

        for (String city : cities) {

            Response res =
                    SerenityRest.given()
                            .queryParam("city", city)
                            .queryParam("country", "US")
                            .queryParam("key", getApiKey())
                            .queryParam("units", "M")
                            .when()
                            .get(getBaseUrl());

            res.then().statusCode(200);

            double temp = res.jsonPath().getDouble("data[0].temp");

            System.out.println(city + " temperature = " + temp + "°C");

            if (temp < minTemp) {
                minTemp = temp;
                coldestCity = city;
            }
        }

        String coldestState = cityStateMap.get(coldestCity);
        System.out.println("Coldest State is '" + coldestState + "' (" + minTemp + "°C)");
        return coldestState;
    }

}