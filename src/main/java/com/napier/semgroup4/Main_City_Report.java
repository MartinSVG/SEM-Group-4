package com.napier.semgroup4;
import java.util.ArrayList;
import java.util.Scanner;
public class Main_City_Report {
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();
        System.out.println();

        // Extract City information
        ArrayList<City> cities = a.getAllCities();

        // Test the size of the returned data - should be 4079
        //System.out.println("The number of Cities in the world are:");
        //System.out.println(cities.size());
        System.out.println();

        //Give user an option to select city report organised by largest population to smallest
        Scanner input = new Scanner(System.in);
        System.out.println("Select a City Report Organised by Largest Population to Smallest:");
        System.out.println("1. All the cities in the world.");
        System.out.println("2. All the cities in a continent.");
        System.out.println("3. All the cities in a region.");
        System.out.println("4. All the cities in a country.");
        System.out.println("5. All the cities in a district.");

        int choice = input.nextInt();

        if (choice == 1) {
                        //Print a list of all cities in the world organized by largest to smallest population
                        System.out.println("All the cities in the world organized by largest to smallest population:");
                        a.printAllCities(cities);

                } else if (choice == 2) {
                        //Print a list of all cities in a continent organized by largest to smallest population
                        System.out.println("All the cities in a continent organised by largest population to smallest:");
                        a.printCitiesContinent(cities);
                }
                  else if (choice == 3) {
                    System.out.println("You selected Option C");
                }
                  else if (choice == 4) {
                    System.out.println("You selected Option D");
                }
                  else if (choice == 5) {
                    System.out.println("You selected Option E");
                }
                  else {
                    System.out.println("Invalid choice");
                }

        // Disconnect from database
        a.disconnect();
    }
}
