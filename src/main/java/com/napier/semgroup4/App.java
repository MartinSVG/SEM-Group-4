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
        ArrayList<Country> countries = a.getCountries("WHERE Continent = 'Asia' ");

        a.printCountries(countries);

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

    public ArrayList<Country> getCountries(String clause)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            String strSelect = "";
            // Create string for SQL statement
            if (clause.isEmpty()){
                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + "ORDER BY Population DESC";
            }
            else {
                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + clause
                                + "ORDER BY Population DESC";
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

    public void printCountries(ArrayList<Country> countries)
    {
        // Print header
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %15s %25s %25s %15s %10s", "ID", "NAME", "CONTINENT", "REGION", "POPULATION", "CAPITAL");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Loop over all employees in the list
        for (Country cnt : countries)
        {
            String emp_string =
                    String.format("%3s %15s %25s %25s %15s %10s",
                            cnt.countryID,cnt.name,cnt.continent,cnt.region,cnt.population,cnt.capital);
            System.out.println(emp_string);
        }
    }
}
