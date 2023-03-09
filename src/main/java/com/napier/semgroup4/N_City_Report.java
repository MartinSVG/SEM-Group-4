package com.napier.semgroup4;
import java.util.ArrayList;

public class N_City_Report {
    public static void main() {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();
        System.out.println();

        int num1 = 5;
        int num2 = 7;
        int num3 = 4;
        int num4 = 3;
        int num5 = 6;


        ArrayList<City> world = a.getNCities(num1);
        ArrayList<City> continent = a.getNCities(num2);
        ArrayList<City> region = a.getNCities(num3);
        ArrayList<City> country = a.getNCities(num4);
        ArrayList<City> district = a.getNCities(num5);



        System.out.println();
        System.out.println("The top " + num1 + " populated cities in the world:");
        a.printAllCities(world);



        System.out.println();
        System.out.println("The top " + num2 + " populated cities in a continent:");
        a.printCityContinent(continent);


        System.out.println();
        System.out.println("The top " + num3 + " populated cities in a region:");
        a.printCityRegion(region);


        System.out.println();
        System.out.println("The top " + num4 + " populated cities in a country:");
        a.printCityCountry(country);


        System.out.println();
        System.out.println("The top " + num5 + " populated cities in a district:");
        a.printCityDistrict(district);

    }
}