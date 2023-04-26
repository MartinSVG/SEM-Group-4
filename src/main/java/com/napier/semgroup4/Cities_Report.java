package com.napier.semgroup4;

public class Cities_Report {
    public static void main(App a) {

        // Print all cities in the world from the largest population to smallest
        System.out.println("All cities in the world - Largest Population to Smallest:");
        a.printCities(a.getCities("",-1));
        System.out.println();

        // Print all cities in a continent from the largest population to smallest
        System.out.println("All cities in a continent (North America) - Largest Population to Smallest:");
        a.printCities(a.getCities("country.Continent = 'North America'",-1));
        System.out.println();

        // Print all cities in a region from the largest population to smallest
        System.out.println("All cities in a region (Caribbean) - Largest Population to Smallest:");
        a.printCities(a.getCities("country.Region = 'Caribbean'",-1));
        System.out.println();

        // Print all cities in a country from the largest population to smallest
        System.out.println("All cities in a country (India) - Largest Population to Smallest:");
        a.printCities(a.getCities("country.Name = 'India'",-1));
        System.out.println();

        // Print all cities in a district from the largest population to smallest
        System.out.println("All cities in a District (Punjab) - Largest Population to Smallest:");
        a.printCities(a.getCities("city.District = 'Punjab'",-1));
        System.out.println();

        // Print top 5 cities in the world from the largest population to smallest
        System.out.println("Top 5 cities in the world - Largest Population to Smallest:");
        a.printCities(a.getCities("",5));
        System.out.println();

        // Print top 5 cities in a continent from the largest population to smallest
        System.out.println("Top 5 cities in a continent (North America) - Largest Population to Smallest:");
        a.printCities(a.getCities("country.Continent = 'North America'",5));
        System.out.println();

        // Print top 5 cities in a region from the largest population to smallest
        System.out.println("Top 5 cities in a region (Caribbean) - Largest Population to Smallest:");
        a.printCities(a.getCities("country.Region = 'Caribbean'",5));
        System.out.println();

        // Print top 5 cities in a country from the largest population to smallest
        System.out.println("Top 5 cities in a country (India) - Largest Population to Smallest:");
        a.printCities(a.getCities("country.Name = 'India'",5));
        System.out.println();

        // Print top 5 cities in a continent from the largest population to smallest
        System.out.println("Top 5 cities in a District (Punjab) - Largest Population to Smallest:");
        a.printCities(a.getCities("city.District = 'Punjab'",5));
        System.out.println();
    }
}
