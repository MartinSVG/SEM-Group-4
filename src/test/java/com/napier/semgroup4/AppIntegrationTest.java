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

    // Top 5 Capital Cities in the World
    @Test
    void testTopFivePopulatedCapitalCitiesInTheWorld()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCapitalCities("",5);
        assertEquals(5, cities.size());
    }

    // Top 5 Capital Cities in a Continent
    @Test
    void testTopFivePopulatedCapitalCitiesInAContinent()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCapitalCities("country.Continent = 'South America'",5);
        assertEquals(5, cities.size());
    }

    // Top 5 Capital Cities in a Region
    @Test
    void testTopFivePopulatedCapitalCitiesInARegion()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCapitalCities("country.Region = 'Eastern Asia'",5);
        assertEquals(5, cities.size());
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

    // Top 5 Populated Countries in a Continent (South America)

    @Test
    void testTopFivePopulatedCountriesInAContinent()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        countries = app.getCountries("country.Continent = 'South America'",5);
        assertEquals(5, countries.size());
    }

    // Top 5 Populated Countries in a Region (Eastern Asia)

    @Test
    void testTopFivePopulatedCountriesInARegion()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        countries = app.getCountries("country.Region = 'Eastern Asia'",5);
        assertEquals(5, countries.size());
    }

    // All Countries in the World.

    @Test
    void testGetAllCountriesInTheWorld()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        countries = app.getCountries("",-1);
        assertNotNull(countries);
    }

    // All Countries in a Continent

    @Test
    void testGetAllCountriesInAContinent()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        countries = app.getCountries("country.Continent = 'South America'",-1);
        assertNotNull(countries);
    }

    // All Countries in a Region

    @Test
    void testGetAllCountriesInARegion()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        countries = app.getCountries("country.Region = 'Eastern Asia'",-1);
        assertNotNull(countries);
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

    /* Integration Test for In and Out of Cities Feature */

    //Population of people that are living in cities in the continent of North America

    @Test
    void testGetInAndOutOfCitiesInContinent(){
        ArrayList<String> result = new ArrayList<String>();
        result = app.getInAndOutOfCities("Continent", "North America");
        assertNotNull(result);
    }

    //Population of people that are living in cities in the region of the Caribbean
    @Test
    void testgGetInAndOutOfCitiesInRegion(){
        ArrayList<String> result = new ArrayList<String>();
        result = app.getInAndOutOfCities("Region", "Caribbean");
        assertNotNull(result);
    }

    //Population of people that are living in cities in the country of Barbados

    @Test
    void testGetInAndOutOfCitiesInCountry(){
        ArrayList<String> result = new ArrayList<String>();
        result = app.getInAndOutOfCities("Country", "Barbados");
        assertNotNull(result);
    }

    //Population of people speaking Chinese, English, Hindi, Spanish and Arabic.
    @Test
    void testGetLanguageStats(){
        App.LanguageStats results = new App.LanguageStats();
        results.getLanguageStats(app.con);
        assertNotNull(results);
    }


}


