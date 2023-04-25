package com.napier.semgroup4;

public class In_and_Out_of_Cities_Report {

    public static void main(App a) {

        // Prints the population of people living in cities in the continent of North America
        a.printInAndOutOfCities(a.getInAndOutOfCities("Continent","North America"));

        // Prints the population of people living in cities in the region of the Caribbean
        a.printInAndOutOfCities(a.getInAndOutOfCities("Region","Caribbean"));

        // Prints the population of people living in cities in the country of Barbados
        a.printInAndOutOfCities(a.getInAndOutOfCities("Country","Barbados"));

    }
}
