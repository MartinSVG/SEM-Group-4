package com.napier.semgroup4;
import java.util.ArrayList;

public class City_Report {
    public static void main() {
        // Create new Application
        App a = new App();

        // Connect to database without timer
        a.fastconnect();
        System.out.println();

        // Number variable for N City
        int num1 = 5;
        int num2 = 7;
        int num3 = 4;
        int num4 = 3;
        int num5 = 6;


        // Extract City information
        ArrayList<City> cities = a.getAllCities("city.CountryCode = country.Code ");
        ArrayList<City> world = a.getAllCities("city.CountryCode = country.Code ");
        ArrayList<City> continent = a.getAllCities("country.Continent = 'Africa' ");
        ArrayList<City> region = a.getAllCities("country.Region = 'Caribbean' ");
        ArrayList<City> country = a.getAllCities("country.Name = 'India' ");
        ArrayList<City> district = a.getAllCities("city.District = 'California' ");

        // Extract City Information for N City
        ArrayList<City> nWorld = a.getNCities("city.CountryCode = country.Code ", num1);
        ArrayList<City> nContinent = a.getNCities("country.Continent = 'Africa' ", num2);
        ArrayList<City> nRegion = a.getNCities("country.Region = 'Caribbean' ", num3);
        ArrayList<City> nCountry = a.getNCities("country.Name = 'India' ", num4);
        ArrayList<City> nDistrict = a.getNCities("city.District = 'California' ", num5);

        // Test the size of the returned data - should be 4079
        System.out.println("The number of Cities in the world are:");
        System.out.println(cities.size());
        System.out.println();


        //Print a list of all cities in the world organized by largest to smallest population
        System.out.println();
        System.out.println("All the cities in the world organized by largest to smallest population:");
        a.printAllCities(world);


        //Print a list of all cities in a continent organized by largest to smallest population
        System.out.println();
        System.out.println("All the cities in a continent (Africa) organised by largest population to smallest:");
        a.printCityContinent(continent);

        //Print a list of all cities in a region organized by largest to smallest population
        System.out.println();
        System.out.println("All the cities in a region (Caribbean) organised by largest population to smallest:");
        a.printCityRegion(region);

        //Print a list of all cities in a country organized by largest to smallest population
        System.out.println();
        System.out.println("All the cities in a country (India) organised by largest population to smallest:");
        a.printCityCountry(country);

        //Print a list of all cities in a district organized by largest to smallest population
        System.out.println();
        System.out.println("All the cities in a district (California) organised by largest population to smallest:");
        a.printCityDistrict(district);

        System.out.println();


        System.out.println();
        System.out.println("The top " + num1 + " populated cities in the world:");
        a.printAllCities(nWorld);


        System.out.println();
        System.out.println("The top " + num2 + " populated cities in a continent(Africa):");
        a.printCityContinent(nContinent);


        System.out.println();
        System.out.println("The top " + num3 + " populated cities in a region(Caribbean):");
        a.printCityRegion(nRegion);


        System.out.println();
        System.out.println("The top " + num4 + " populated cities in a country(India):");
        a.printCityCountry(nCountry);


        System.out.println();
        System.out.println("The top " + num5 + " populated cities in a district(California):");
        a.printCityDistrict(nDistrict);

    }
}