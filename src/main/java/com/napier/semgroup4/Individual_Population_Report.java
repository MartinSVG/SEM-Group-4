package com.napier.semgroup4;

public class Individual_Population_Report {
    public static void main(App a) {
        a.printPopulation(a.getPopulation("",""));
        a.printPopulation(a.getPopulation("Continent","North America"));
        a.printPopulation(a.getPopulation("Region","Caribbean"));
        a.printPopulation(a.getPopulation("Country","Barbados"));
        a.printPopulation(a.getPopulation("District","Gujarat"));
        a.printPopulation(a.getPopulation("City","Austin"));
    }
}
