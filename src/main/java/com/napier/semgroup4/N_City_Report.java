package com.napier.semgroup4;
import java.util.ArrayList;

public class N_City_Report {
    public static void main() {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();
        System.out.println();

        // Extract City information
        ArrayList<City> cities = a.getAllCities();

        // Test the size of the returned data - should be 4079
        System.out.println("The number of Cities in the world are:");
        System.out.println(cities.size());
        System.out.println();


        //Print a list of all cities in the world organized by largest to smallest population
        System.out.println();
        System.out.println("All the cities in the world organized by largest to smallest population:");
        a.printAllCities(a.getNCities(5));


        //Print a list of all cities in a continent organized by largest to smallest population
        System.out.println();
        System.out.println("All the cities in a continent organised by largest population to smallest:");
        a.printCityContinent(a.getNCities(5));

        //Print a list of all cities in a region organized by largest to smallest population
        System.out.println();
        System.out.println("All the cities in a region organised by largest population to smallest:");
        a.printCityRegion(a.getNCities(5));

        //Print a list of all cities in a country organized by largest to smallest population
        System.out.println();
        System.out.println("All the cities in a country organised by largest population to smallest:");
        a.printCityCountry(a.getNCities(5));

        //Print a list of all cities in a district organized by largest to smallest population
        System.out.println();
        System.out.println("All the cities in a district organised by largest population to smallest:");
        a.printCityDistrict(a.getNCities(5));

    }
}