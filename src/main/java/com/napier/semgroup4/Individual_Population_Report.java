package com.napier.semgroup4;

public class Individual_Population_Report {
    public static void main(App a) {
        // Prints the population of the world
        a.printPopulation(a.getPopulation("",""));

        // Prints the population of the continent North America
        a.printPopulation(a.getPopulation("Continent","North America"));

        // Prints the population of the Caribbean
        a.printPopulation(a.getPopulation("Region","Caribbean"));

        // Prints the population of the country Barbados
        a.printPopulation(a.getPopulation("Country","Barbados"));

        // Prints the population of the district Gujarat
        a.printPopulation(a.getPopulation("District","Gujarat"));

        // Prints the population of the city Austin
        a.printPopulation(a.getPopulation("City","Austin"));
    }
}
