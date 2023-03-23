package com.napier.semgroup4;

public class Country_Report {
    public static void main(App a) {


        // Print all countries in the world from the largest population to smallest
        System.out.println("All countries in the world - Largest Population to Smallest:");
        a.printCountries(a.getCountries("",-1));
        System.out.println();

        // Print all countries in a continent from the largest population to smallest
        System.out.println("All countries in a continent (South America) - Largest Population to Smallest:");
        a.printCountries(a.getCountries("country.Continent = 'South America'",-1));
        System.out.println();

        //Print all countries in a region from the largest population to smallest
        System.out.println("All countries in a region (Eastern Asia) - Largest Population to Smallest:");
        a.printCountries(a.getCountries("country.Region = 'Eastern Asia'",-1));
        System.out.println();

        //Print top N countries in the world from the largest population to smallest
        System.out.println("All top 5 countries in the world - Largest Population to Smallest:");
        a.printCountries(a.getCountries("",5));
        System.out.println();

        //Print top N countries a continent from the largest population to smallest
        System.out.println("All top 5 countries in a continent (South America) - Largest Population to Smallest:");
        a.printCountries(a.getCountries("country.Continent = 'South America'",5));
        System.out.println();

        //Print top N countries a region from the largest population to smallest
        System.out.println("All top 5 countries in a region (Eastern Asia) - Largest Population to Smallest:");
        a.printCountries(a.getCountries("country.Region = 'Eastern Asia'",5));
        System.out.println();
    }
}
