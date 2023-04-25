package com.napier.semgroup4;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);

    }

    /* Integration Tests for Cities Feature */

    // All cities in the world
    @Test
    void testGetAllCitiesInTheWorld(){
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("",-1);
        assertNotNull(cities);
    }
    // All cities in a continent
    @Test
    void testGetAllCitiesInAContinent(){
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("country.Continent = 'North America'",-1);
        assertNotNull(cities);
    }
    // All cities in a region
    @Test
    void testGetAllCitiesInARegion(){
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("country.Region = 'Caribbean'",-1);
        assertNotNull(cities);
    }
    // All cities in a country
    @Test
    void testGetAllCitiesInACountry(){
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("country.Name = 'India'",-1);
        assertNotNull(cities);
    }
    // All cities in a district
    @Test
    void testGetAllCitiesInADistrict(){
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("city.District = 'Punjab'",-1);
        assertNotNull(cities);
    }
    // Top 5 cities in the world
    @Test
    void testTopFivePopulatedCitiesInTheWorld()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("",5);
        assertEquals(5, cities.size());
    }
    // Top 5 cities in a continent
    @Test
    void testTopFivePopulatedCitiesInAContinent()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("country.Continent = 'North America'",5);
        assertEquals(5, cities.size());
    }
    // Top 5 cities in a region
    @Test
    void testTopFivePopulatedCitiesInARegion()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("country.Region = 'Caribbean'",5);
        assertEquals(5, cities.size());
    }
    // Top 5 cities in a country
    @Test
    void testTopFivePopulatedCitiesInACountry()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("country.Name = 'India'",5);
        assertEquals(5, cities.size());
    }
    // Top 5 cities in a district
    @Test
    void testTopFivePopulatedCitiesInADistrict()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("city.District = 'Punjab'",5);
        assertEquals(5, cities.size());
    }

    /* Integration Tests for Capital Cities Feature */

    // All Capital Cities in the world
    @Test
    void testGetAllCapitalCitiesInTheWorld(){
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCapitalCities("",-1);
        assertNotNull(cities);
    }

    // All Capital Cities in a continent
    @Test
    void testGetAllCapitalCitiesInAContinent(){
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCapitalCities("country.Continent = 'North America'",-1);
        assertNotNull(cities);
    }

    // All Capital Cities in a region
    @Test
    void testGetAllCapitalCitiesInARegion(){
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCapitalCities("country.Region = 'Caribbean'",-1);
        assertNotNull(cities);
    }

    /* Integration Tests for Countries Feature */

    // Top 5 Populated Countries in the World
    @Test
    void testTopFivePopulatedCountriesInTheWorld()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        countries = app.getCountries("",5);
        assertEquals(5, countries.size());
    }

    // Top 5 Populated Cities in the World
    @Test
    void testTopFivePopulatedCapitalCitiesInTheWorld()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCapitalCities("",5);
        assertEquals(5, cities.size());
    }


    /* Integration Tests for Specific Population Features */

    // Population of the world
    @Test
    void testGetPopulationOfTheWorld(){
        ArrayList<String> population = new ArrayList<String>();
        population = app.getPopulation("","");
        assertNotNull(population);
    }

    // Population of a continent
    @Test
    void testGetPopulationOfAContinent() {
        ArrayList<String> population = new ArrayList<String>();
        population = app.getPopulation("Continent","North America");
        assertNotNull(population);
    }

    // Population of a region
    @Test
    void testGetPopulationOfARegion(){
        ArrayList<String> population = new ArrayList<String>();
        population = app.getPopulation("Region","Caribbean");
        assertNotNull(population);
    }

    // Population of a country
    @Test
    void testGetPopulationOfACountry(){
        ArrayList<String> population = new ArrayList<String>();
        population = app.getPopulation("Country","Barbados");
        assertNotNull(population);
    }

    // Population of a district
    @Test
    void testGetPopulationOfADistrict(){
        ArrayList<String> population = new ArrayList<String>();
        population = app.getPopulation("District","Gujarat");
        assertNotNull(population);
    }

    // Population of a city
    @Test
    void testGetPopulationOfACity(){
        ArrayList<String> population = new ArrayList<String>();
        population = app.getPopulation("City","Austin");
        assertNotNull(population);
    }
}

