package com.napier.semgroup4;

import java.sql.*;
import java.util.ArrayList;

public class App
{

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                // Use localhost:3307 for local testing if docker db is not working
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Gets all the cities and population in the world.
     * @return A list of all cities and population, or null if there is an error.
     */
    public ArrayList<City> getAllCities()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name, city.CountryCode, city.District, city.Population, country.Continent, country.Region, country.Name "
                            + "FROM city, country "
                            + "WHERE city.CountryCode = country.code "
                            + "ORDER BY city.Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract city information
            ArrayList<City> cities = new ArrayList<City>();
            while (rset.next())
            {
                City cit = new City();
                cit.Name = rset.getString("city.Name");
                cit.CountryCode = rset.getString("city.CountryCode");
                cit.District = rset.getString("city.District");
                cit.Population = rset.getInt("city.Population");
                cit.Continent = rset.getString("country.Continent");
                cit.Region = rset.getString("country.Region");
                cit.Country = rset.getString("country.Name");
                cities.add(cit);
            }
            return cities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * Prints a list of cities.
     * @param cities The list of cities to print.
     */
    public void printAllCities(ArrayList<City> cities)
    {
        // Print header
        System.out.println(String.format("%-35s %-18s %-25s %-15s", "City Name", "Country Code", "District", "Population"));        // Loop over all cities in the list
        for (City cit : cities)
        {
            String cit_string =
                    String.format("%-35s %-18s %-25s %-15s",
                            cit.Name, cit.CountryCode, cit.District, cit.Population);
            System.out.println(cit_string);
        }
    }

    /**
     * Prints a list of cities.
     * @param cityContinent The list of cities to print by continent.
     */
    public void printCityContinent(ArrayList<City> cityContinent)
    {
        // Print header
        System.out.println(String.format("%-15s %-15s %-18s %-25s %-15s", "Continent", "Country Code","City Name", "District", "Population"));        // Loop over all cities in the list
        for (City cit : cityContinent)
        {
            String cit_string =
                    String.format("%-15s %-15s %-18s %-25s %-15s",
                            cit.Continent, cit.CountryCode, cit.Name,  cit.District, cit.Population);
            System.out.println(cit_string);
        }
    }

    /**
     * Prints a list of cities.
     * @param cityRegion The list of cities to print by continent.
     */
    public void printCityRegion(ArrayList<City> cityRegion)
    {
        // Print header
        System.out.println(String.format("%-27s %-15s %-18s %-25s %-15s", "Region", "Country Code","City Name", "District", "Population"));        // Loop over all cities in the list
        for (City cit : cityRegion)
        {
            String cit_string =
                    String.format("%-27s %-15s %-18s %-25s %-15s",
                            cit.Region, cit.CountryCode, cit.Name,  cit.District, cit.Population);
            System.out.println(cit_string);
        }
    }

    /**
     * Prints a list of cities.
     * @param cityCountry The list of cities to print by continent.
     */
    public void printCityCountry(ArrayList<City> cityCountry)
    {
        // Print header
        System.out.println(String.format("%-27s %-15s %-18s %-25s %-15s", "Country", "Country Code","City Name", "District", "Population"));        // Loop over all cities in the list
        for (City cit : cityCountry)
        {
            String cit_string =
                    String.format("%-27s %-15s %-18s %-25s %-15s",
                            cit.Country, cit.CountryCode, cit.Name,  cit.District, cit.Population);
            System.out.println(cit_string);
        }
    }

}
