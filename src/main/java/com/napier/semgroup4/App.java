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

        // Print all countries in the world from the largest population to smallest
        System.out.println("All countries in the world - Largest Population to Smallest:");
        a.printCountries(a.getCountries("",-1));
        System.out.println();

        // Print all countries in a continent from the largest population to smallest
        System.out.println("All countries in a continent (South America) - Largest Population to Smallest:");
        a.printCountries(a.getCountries("WHERE Continent = 'South America' ",-1));
        System.out.println();

        //Print all countries in a region from the largest population to smallest
        System.out.println("All countries in a region (Eastern Asia) - Largest Population to Smallest:");
        a.printCountries(a.getCountries("WHERE Region = 'Eastern Asia' ",-1));
        System.out.println();

        //Print top N countries in the world from the largest population to smallest
        System.out.println("All top 5 countries in the world - Largest Population to Smallest:");
        a.printCountries(a.getCountries("",5));
        System.out.println();

        //Print top N countries a continent from the largest population to smallest
        System.out.println("All top 5 countries in a continent (South America) - Largest Population to Smallest:");
        a.printCountries(a.getCountries("WHERE Continent = 'South America' ",5));
        System.out.println();

        //Print top N countries a continent from the largest population to smallest
        System.out.println("All top 5 countries in a region (Eastern Asia) - Largest Population to Smallest:");
        a.printCountries(a.getCountries("WHERE Region = 'Eastern Asia' ",5));
        System.out.println();
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
     *  Gets all the current countries.
     *  @return A list of all countries, or null if there is an error.
     */
    public ArrayList<Country> getCountries(String clause, int top)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            String strSelect = "";
            // Create string for SQL statement
            if (clause.isEmpty() && top == -1){
                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "ORDER BY Population DESC";
            }

            if (clause.isEmpty() && top != -1){
                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "ORDER BY Population DESC "
                                + "LIMIT " + top;
            }

            if(!clause.isEmpty() && top == -1) {
                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + clause
                                + "ORDER BY Population DESC";
            }

            if(!clause.isEmpty() && top != -1) {
                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + clause
                                + "ORDER BY Population DESC "
                                + "LIMIT " + top;
            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next())
            {
                Country country = new Country();
                country.countryID = rset.getString("Code");
                country.name = rset.getString("Name");
                country.continent = rset.getString("Continent");
                country.region = rset.getString("Region");
                country.population = rset.getInt("Population");
                country.capital = getCapital(rset.getInt("Capital"));
                countries.add(country);
            }
            return countries;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Prints a list of capital cites.
     * @param ID The list of capital cities to print.
     */
    public String getCapital(int ID)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name "
                            + "FROM city "
                            + "WHERE ID = " + ID;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next())
            {
                return rset.getString("Name");
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city name");
            return null;
        }
    }

    /**
     * Prints the countries retrieved from the database
     *  @param countries The list of countries to print.
     */
    public void printCountries(ArrayList<Country> countries)
    {
        // Print header
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %15s %25s %25s %15s %10s", "ID", "NAME", "CONTINENT", "REGION", "POPULATION", "CAPITAL");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Loop over all countries in the list
        for (Country cnt : countries)
        {
            String emp_string =
                    String.format("%3s %15s %25s %25s %15s %10s",
                            cnt.countryID,cnt.name,cnt.continent,cnt.region,cnt.population,cnt.capital);
            System.out.println(emp_string);
        }
    }
}
