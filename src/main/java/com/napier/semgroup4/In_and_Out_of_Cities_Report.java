package com.napier.semgroup4;

public class In_and_Out_of_Cities_Report {

    public static void main(App a) {

        // Prints the population of people living in cities in the continent of North America
        a.printInandOutofCities(a.getInAndOutofCities("Continent","North America"));

        // Prints the population of people living in cities in the continent of North America
        a.printInandOutofCities(a.getInAndOutofCities("Region","Caribbean"));

        // Prints the population of people living in cities in the continent of North America
        a.printInandOutofCities(a.getInAndOutofCities("Country","Barbados"));

    }
}
