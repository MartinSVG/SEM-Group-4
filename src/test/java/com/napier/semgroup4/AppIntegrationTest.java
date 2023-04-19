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

    // Integration Tests for Cities Feature
    @Test
    void testTopFivePopulatedCitiesInTheWorld()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("",5);
        assertEquals(cities.size(),5);
    }

    @Test
    void testTopFivePopulatedCitiesInAContinent()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("country.Continent = 'North America'",5);
        assertEquals(cities.size(),5);
    }

    @Test
    void testTopFivePopulatedCitiesInARegion()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("country.Region = 'Caribbean'",5);
        assertEquals(cities.size(),5);
    }

    @Test
    void testTopFivePopulatedCitiesInACountry()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("country.Name = 'India'",5);
        assertEquals(cities.size(),5);
    }

    @Test
    void testTopFivePopulatedCitiesInADistrict()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCities("city.District = 'Punjab'",5);
        assertEquals(cities.size(),5);
    }

    // Integration Tests for Countries Feature
    @Test
    void testTopFivePopulatedCountriesInTheWorld()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        countries = app.getCountries("",5);
        assertEquals(countries.size(),5);
    }

    @Test
    void testTopFivePopulatedCapitalCitiesInTheWorld()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities = app.getCapitalCities("",5);
        assertEquals(cities.size(),5);
    }
}
