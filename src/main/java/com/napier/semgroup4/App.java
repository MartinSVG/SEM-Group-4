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

        //Print top N countries a region from the largest population to smallest
        System.out.println("All top 5 countries in a region (Eastern Asia) - Largest Population to Smallest:");
        a.printCountries(a.getCountries("WHERE Region = 'Eastern Asia' ",5));
        System.out.println();

        // Print all capital cities in the world from the largest population to smallest
        System.out.println("All capital cities in the world - Largest Population to Smallest:");
        a.printCapitalCities(a.getCapitalCities("",-1));
        System.out.println();

        // Print all capital cities in a continent from the largest population to smallest
        System.out.println("All capital cities in a continent (South America) - Largest Population to Smallest:");
        a.printCapitalCities(a.getCapitalCities("country.Continent = 'South America' ",-1));
        System.out.println();

        //Print all capital cities in a region from the largest population to smallest
        System.out.println("All capital cities in a region (Eastern Asia) - Largest Population to Smallest:");
        a.printCapitalCities(a.getCapitalCities("country.Region = 'Eastern Asia' ",-1));
        System.out.println();

        // Print top N capital cities in the world from the largest population to smallest
        System.out.println("All top 5 capital cities in the world - Largest Population to Smallest:");
        a.printCapitalCities(a.getCapitalCities("",5));
        System.out.println();

        //Print top N capital cities a continent from the largest population to smallest
        System.out.println("All top 5 capital cities in a continent (South America) - Largest Population to Smallest:");
        a.printCapitalCities(a.getCapitalCities("country.Continent = 'South America' ",5));
        System.out.println();

        //Print top N capital cities a region from the largest population to smallest
        System.out.println("All top 5 capital cities in a region (Eastern Asia) - Largest Population to Smallest:");
        a.printCapitalCities(a.getCapitalCities("country.Region = 'Eastern Asia' ",5));
        System.out.println();

        // Generate City Report
        City_Report.main();

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

    public void fastconnect()
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

        try
        {
            // Connect to database
            con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
        }

        catch (SQLException sqle)
        {
            System.out.println("Failed to connect to database attempt ");
            System.out.println(sqle.getMessage());
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
     *  Gets all the current countries.
     *  @return A list of capital cities, or null if there is an error.
     */
    public ArrayList<City> getCapitalCities(String clause, int top)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            String strSelect = "";
            // Create string for SQL statement
            if (clause.isEmpty() && top == -1){
                strSelect =
                        "SELECT city.Name, city.CountryCode, city.Population, country.Name, country.Continent, country.Region "
                                + "FROM city, country "
                                + "WHERE city.CountryCode = country.Code "
                                + "ORDER BY city.Population DESC";
            }

            if (clause.isEmpty() && top != -1){
                strSelect =
                        "SELECT city.Name, city.CountryCode, city.Population, country.Name, country.Continent, country.Region "
                                + "FROM city, country "
                                + "WHERE city.CountryCode = country.Code "
                                + "ORDER BY city.Population DESC "
                                + "LIMIT " + top;
            }

            if(!clause.isEmpty() && top == -1) {
                strSelect =
                        "SELECT city.Name, city.CountryCode, city.Population, country.Name, country.Continent, country.Region "
                                + "FROM city, country "
                                + "WHERE city.CountryCode = country.Code AND " + clause
                                + " ORDER BY city.Population DESC";
            }

            if(!clause.isEmpty() && top != -1) {
                strSelect =
                        "SELECT city.Name, city.CountryCode, city.Population, country.Name, country.Continent, country.Region "
                                + "FROM city, country "
                                + "WHERE city.CountryCode = country.Code AND " + clause
                                + " ORDER BY city.Population DESC "
                                + "LIMIT " + top;

            }

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            ArrayList<City> cities = new ArrayList<City>();
            while (rset.next())
            {
                City city = new City();
                city.Name = rset.getString("city.Name");
                city.CountryCode = rset.getString("city.CountryCode");
                city.Continent = rset.getString("country.Continent");
                city.Region = rset.getString("country.Region");
                city.Population = rset.getInt("city.Population");
                city.Country = rset.getString("country.Name");
                cities.add(city);
            }
            return cities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }
    }

    /**
     * Prints the name of the Capital City
     * @param ID The ID of the capital
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
        if (countries == null)
        {
            System.out.println("No countries");
            return;
        }
        // Print header
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%5s %15s %25s %25s %15s %10s", "ID", "NAME", "CONTINENT", "REGION", "POPULATION", "CAPITAL");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Loop over all countries in the list
        for (Country cnt : countries)
        {
            if (cnt == null)
                continue;
            String emp_string =
                    String.format("%3s %15s %25s %25s %15s %10s",
                            cnt.countryID,cnt.name,cnt.continent,cnt.region,cnt.population,cnt.capital);
            System.out.println(emp_string);
        }
    }

    /**
     * Prints the cities retrieved from the database
     *  @param cities The list of countries to print.
     */
    public void printCapitalCities(ArrayList<City> cities)
    {
        if (cities == null)
        {
            System.out.println("No capital cities");
            return;
        }
        // Print header
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%20s %20s %20s", "NAME", "COUNTRY", "POPULATION");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Loop over all cities in the list
        for (City cty : cities)
        {
            if (cty == null)
                continue;
            String emp_string =
                    String.format("%20s %20s %20s",
                            cty.Name,cty.Country,cty.Population);
            System.out.println(emp_string);
        }
    }

    /**
     * Gets all the cities and population in the world.
     * @return A list of all cities and population, or null if there is an error.
     */
    public ArrayList<City> getAllCities(String clause)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name, city.CountryCode, city.District, city.Population, country.Continent, country.Region, country.Name "
                            + "FROM city, country "
                            + "WHERE city.CountryCode = country.Code AND " + clause
                            + "ORDER BY city.Population DESC ";

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
     * Gets all the cities and population in the world.
     * @return A list of all cities and population, or null if there is an error.
     */
    public ArrayList<City> getNCities(String clause, int top)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement

            String strSelect  =
                        "SELECT city.Name, city.CountryCode, city.District, city.Population, country.Continent, country.Region, country.Name "
                                + "FROM city, country "
                                + "WHERE city.CountryCode = country.code AND " + clause
                                + "ORDER BY city.Population DESC "
                                + "LIMIT " + top;

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
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-25s %-30s %-20s %-15s", "City Name", "Country", "District", "Population");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Loop over all cities in the list
        for (City cit : cities)
        {
            String cit_string =
                    String.format("%-25s %-30s %-20s %-15s",
                            cit.Name, cit.Country, cit.District, cit.Population);
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
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %-30s %-20s %-15s", "City Name", "Country", "District", "Population");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Loop over all cities in the list
        for (City cit : cityContinent)
        {
            String cit_string =
                    String.format("%-30s %-30s %-20s %-15s",
                            cit.Name, cit.Country, cit.District, cit.Population);
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
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-25s %-40s %-20s %-15s", "City Name", "Country", "District", "Population");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Loop over all cities in the list
        for (City cit : cityRegion)
        {
            String cit_string =
                    String.format("%-25s %-40s %-20s %-15s",
                            cit.Name, cit.Country, cit.District, cit.Population);
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
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %-30s %-20s %-15s", "City Name", "Country", "District", "Population");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Loop over all cities in the list
        for (City cit : cityCountry)
        {
            String cit_string =
                    String.format("%-30s %-30s %-20s %-15s",
                            cit.Name, cit.Country, cit.District, cit.Population);
            System.out.println(cit_string);
        }
    }

    /**
     * Prints a list of cities.
     * @param cityDistrict The list of cities to print by continent.
     */
    public void printCityDistrict(ArrayList<City> cityDistrict)
    {
        // Print header
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-25s %-30s %-20s %-15s", "City Name", "Country", "District", "Population");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Loop over all cities in the list
        for (City cit : cityDistrict)
        {
            String cit_string =
                    String.format("%-25s %-30s %-20s %-15s",
                            cit.Name, cit.Country, cit.District, cit.Population);
            System.out.println(cit_string);
        }
    }



}

