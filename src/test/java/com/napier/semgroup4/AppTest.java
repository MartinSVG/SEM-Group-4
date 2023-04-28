package com.napier.semgroup4;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppTest {
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

//    Unit Tests for Countries feature
    @Test
    void printCountriesTestNull()
    {
        app.printCountries(null);
    }

    @Test
    void printCountriesTestEmpty()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        app.printCountries(countries);
    }

    @Test
    void printCountriesTestContainsNull()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
        countries.add(null);
        app.printCountries(countries);
    }

    @Test
    void printCountriesWithoutError()
    {
        ArrayList<Country> countries = new ArrayList<Country>();
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

//    Unit Tests for Cities feature
    @Test
    void printCitiesTestNull()
    {
        app.printCities(null);
    }

    @Test
    void printCitiesTestEmpty()
    {
        ArrayList<City> cities = new ArrayList<City>();
        app.printCities(cities);
    }

    @Test
    void printCitiesTestContainsNull()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities.add(null);
        app.printCities(cities);
    }

    @Test
    void printCitiesWithoutError()
    {
        ArrayList<City> cities = new ArrayList<City>();
        City cty = new City();
        cty.Name = "Los Angeles";
        cty.CountryCode = "USA";
        cty.Country = "United States";
        cty.District = "California";
        cty.Population = 3694820;
        cty.Region = "North America";
        cty.Continent = "North America";
        cities.add(cty);
        app.printCities(cities);
    }

//    Unit Tests for Capital Cities feature
    @Test
    void printCapitalCitiesTestNull()
    {
        app.printCapitalCities(null);
    }

    @Test
    void printCapitalCitiesTestEmpty()
    {
        ArrayList<City> cities = new ArrayList<City>();
        app.printCapitalCities(cities);
    }

    @Test
    void printCapitalCitiesTestContainsNull()
    {
        ArrayList<City> cities = new ArrayList<City>();
        cities.add(null);
        app.printCapitalCities(cities);
    }

    @Test
    void printCapitalCitiesWithoutError()
    {
        ArrayList<City> cities = new ArrayList<City>();
        City cty = new City();
        cty.Country = "Saint Vincent and the Grenadines";
        cty.Name = "Kingstown";
        cty.Continent = "North America";
        cty.CountryCode = "VCT";
        cty.Region = "Caribbean";
        cty.District = "St George";
        cty.Population = 17100;
        cities.add(cty);
        app.printCapitalCities(cities);
    }

    // Unit Tests for In and Out of Cities Feature
    @Test
    void printInAndOutOfCitiesTestNull()
    {
        app.printInAndOutOfCities(null);
    }

    @Test
    void printInAndOutOfCitiesTestEmpty()
    {
        ArrayList<String> result = new ArrayList<String>();
        app.printInAndOutOfCities(result);
    }

    @Test
    void printInAndOutOfCitiesTestContainsNull()
    {
        ArrayList<String> result = new ArrayList<String>();
        result.add(null);
        app.printInAndOutOfCities(result);
    }

    @Test
    void printInAndOutOfCitiesWithoutError()
    {
        ArrayList<String> result = new ArrayList<String>();
        result.add("Barbados");
        result.add("Country");
        result.add("270,000");
        result.add("6,070");
        result.add("2.25%");
        result.add("263,930");
        result.add("97.75%");
        app.printInAndOutOfCities(result);
    }

    //Unit tests for 'Get specific population' feature
    @Test
    void printPopulationTestNull()
    {
        app.printPopulation(null);
    }
    @Test
    void printPopulationTestEmpty()
    {
        ArrayList<String> result = new ArrayList<String>();
        app.printPopulation(result);
    }
    @Test
    void printPopulationTestContainsNull()
    {
        ArrayList<String> result = new ArrayList<String>();
        result.add(null);
        app.printPopulation(result);
    }

    @Test
    void printPopulationWithoutError()
    {
        ArrayList<String> result = new ArrayList<String>();
        result.add("Continent");
        result.add("North America");
        result.add("482993000");
        app.printPopulation(result);
    }

    //Unit tests for Language Population feature

    @Test
    void printLanguageStatsTestNull()
    {
        App.LanguageStats.printLanguageStats();
    }
    @Test
    void printLanguageStatsTestEmpty()
    {

        App.LanguageStats results = new App.LanguageStats();
        results.getLanguageStats(app.con);
    }
    @Test
    void printLanguageStatsTestContainsNull()
    {

        app.connect("localhost:33060", 0);
        App.LanguageStats results = new App.LanguageStats();
        results.getLanguageStats(null);
    }

    @Test
    void printLanguageStatsWithoutError()
    {
        app.connect("localhost:33060", 0);

        App.LanguageStats languageStats = new App.LanguageStats();
        languageStats.getLanguageStats(app.con);
        languageStats.printLanguageStats();
    }

}