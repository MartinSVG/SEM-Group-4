package com.napier.semgroup4;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AppTest {
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
    }

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

    //Unit tests for 'Get specific population' feature
    @Test
    void printPopulationTestNull()
    {
        app.printPopulation(null);
    }

    @Test
    void printPopulationTestEmpty()
    {
        ArrayList<String> population = new ArrayList<String>();
        app.printPopulation(population);
    }

}