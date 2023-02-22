package com.napier.semgroup4;
import java.util.ArrayList;
public class Main_UC2 {
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Extract employee salary information
        ArrayList<City> cities = a.getAllCities();

        // Test the size of the returned data - should be 4079
        System.out.println(cities.size());

        // Disconnect from database
        a.disconnect();
    }
}
