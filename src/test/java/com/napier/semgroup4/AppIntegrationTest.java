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