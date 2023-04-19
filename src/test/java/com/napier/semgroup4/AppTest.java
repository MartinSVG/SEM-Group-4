package com.napier.semgroup4;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

// City class with private fields and public getter and setter methods
class City {
    private String city_name;
    private String country_code;
    private String country_name;
    private String district;
    private int population;

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    // These methods are empty and not used in this code
    public void setContinent(String northAmerica) {
    }

    public void setRegion(String caribbean) {
    }
}

public class AppTest {
    static App app;

    // BeforeAll method runs before all tests and initializes an instance of the App class
    @BeforeAll
    static void init() {
        app = new App();
    }

    // This test calls the printCountries method of the App class with a null argument
    // No output is expected, but the method should not throw any exceptions
    @Test
    void printCountriesTestNull() {
        app.printCountries(null);
    }

    // This test calls the printCountries method of the App class with an empty ArrayList
    // No output is expected, but the method should not throw any exceptions
    @Test
    void printCountriesTestEmpty() {
        ArrayList<Country> countries = new ArrayList<>();
        app.printCountries(countries);
    }

    // This test calls the printCountries method of the App class with an ArrayList containing a null element
    // The method should handle the null element gracefully and not throw any exceptions
    @Test
    void printCountriesTestContainsNull() {
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(null);
        app.printCountries(countries);
    }

    // This test calls the printCountries method of the App class with an ArrayList containing one valid Country object
    // The method should print the name of the country and its population
    @Test
    void printCountriesWithoutError() {
        ArrayList<Country> countries = new ArrayList<>();
        Country cnt = new Country();
        cnt.countryID = "VCT";
        cnt.name = "Saint Vincent and the Grenadines";
        cnt.continent = "North America";
        cnt.region = "Caribbean";
        cnt.capital = "Kingstown";
        cnt.population = 130000;
        countries.add(cnt);
        app.printCountries(countries);
    }

    // This test calls the printCapitalCities method of the App class with a null argument
    // No output is expected, but the method should not throw any exceptions
    @Test
    void printCapitalCitiesTestNull() {
        app.printCapitalCities(null);
    }

    // This test calls the printCapitalCities method of the App class with an empty ArrayList as an argument
// No output is expected, but the method should not throw any exceptions
    @Test
    void printCapitalCitiesTestEmpty() {
        ArrayList<City> cities = new ArrayList<>();
        app.printCapitalCities(cities);
    }

    // This test calls the printCapitalCities method of the App class with an ArrayList containing a null element as an argument
// The output is expected to indicate that there is an error with that element, but the method should not throw any exceptions
    @Test
    void printCapitalCitiesTestContainsNull() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(null);
        app.printCapitalCities(cities);
    }

    // This test calls the printCapitalCities method of the App class with an ArrayList containing a valid City object as an argument
// The output is expected to display information about the city, including its name, country, population, and district
    @Test
    void printCapitalCitiesWithoutError() {
        ArrayList<City> cities = new ArrayList<>();
        City cty = new City();
        cty.setCountry_name("Saint Vincent and the Grenadines");
        cty.setCity_name("Kingstown");
        cty.setContinent("North America");
        cty.setCountry_code("VCT");
        cty.setRegion("Caribbean");
        cty.setDistrict("St George");
        cty.setPopulation(17100);
        cities.add(cty);
        app.printCapitalCities(cities);
    }

    // This test calls the printCities method of the App class with a null argument
// The method is expected to throw a NullPointerException
    @Test
    void printCitiesTestNull() {
        assertThrows(NullPointerException.class, () -> App.printCities(null));
    }

    // This test calls the printCities method of the App class with an empty ArrayList as an argument
// No output is expected, but the method should not throw any exceptions
    @Test
    void printCitiesTestEmpty() {
        ArrayList<City> cities = new ArrayList<>();
        App.printCities(cities);
    }

    // This test calls the printCities method of the App class with an ArrayList containing a null element as an argument
// The output is expected to indicate that there is an error with that element, but the method should not throw any exceptions
    @Test
    void printCitiesTestContainsNull() {
        ArrayList<City> cities = new ArrayList<>();
        cities.add(new City());
        cities.add(null);
        cities.add(new City());

        // Redirecting standard output to a ByteArrayOutputStream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Calling the method that is being tested
        App.printCities(cities);

        // Defining the expected output based on the contents of the ArrayList
        String expectedOutput = "City: London, Country: United Kingdom, Population: 8982000\n" +
                "Error: City is null\n" +
                "City: New York City, Country: United States, Population: 8336817\n";

        // Comparing the actual output to the expected output
        assertEquals(expectedOutput, outContent.toString());
    }

}