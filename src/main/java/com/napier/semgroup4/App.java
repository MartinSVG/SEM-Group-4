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
        if(args.length < 1){
            a.connect("localhost:33060", 30000);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }

//        // Prints Reports for Countries feature
//        Country_Report.main(a);

        // Prints Reports for Cities feature
        Cities_Report.main(a);
//
//        // Prints Reports for Capital Cities feature
//        Capital_Cities_Report.main(a);

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
    public void connect(String location, int delay) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                                + "/world?allowPublicKeyRetrieval=true&useSSL=false",
                        "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
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
                                + " ORDER BY Population DESC";
            }

            if(!clause.isEmpty() && top != -1) {
                strSelect =
                        "SELECT Code, Name, Continent, Region, Population, Capital "
                                + "FROM country "
                                + clause
                                + " ORDER BY Population DESC "
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
     * Gets cities and population in the world.
     * @return A list of cities and population, or null if there is an error.
     */
    public ArrayList<City> getCities(String clause, int top)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            String strSelect = "";
            // Create string for SQL statement
            if (clause.isEmpty() && top == -1){
                strSelect =
                        "SELECT city.Name, city.CountryCode, city.District, city.Population, country.Continent, country.Region, country.Name "
                                + "FROM city, country "
                                + "WHERE city.CountryCode = country.Code "
                                + "ORDER BY city.Population DESC";
            }

            if (clause.isEmpty() && top != -1){
                strSelect =
                        "SELECT city.Name, city.CountryCode, city.District, city.Population, country.Continent, country.Region, country.Name "
                                + "FROM city, country "
                                + "WHERE city.CountryCode = country.Code "
                                + "ORDER BY city.Population DESC "
                                + "LIMIT " + top;
            }

            if(!clause.isEmpty() && top == -1) {
                strSelect =
                        "SELECT city.Name, city.CountryCode, city.District, city.Population, country.Continent, country.Region, country.Name "
                                + "FROM city, country "
                                + "WHERE city.CountryCode = country.Code AND " + clause
                                + " ORDER BY city.Population DESC";
            }

            if(!clause.isEmpty() && top != -1) {
                strSelect =
                        "SELECT city.Name, city.CountryCode, city.District, city.Population, country.Continent, country.Region, country.Name "
                                + "FROM city, country "
                                + "WHERE city.CountryCode = country.Code AND " + clause
                                + " ORDER BY city.Population DESC "
                                + "LIMIT " + top;

            }

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
                                + "WHERE city.CountryCode = country.Code AND city.ID = country.Capital "
                                + "ORDER BY city.Population DESC";
            }

            if (clause.isEmpty() && top != -1){
                strSelect =
                        "SELECT city.Name, city.CountryCode, city.Population, country.Name, country.Continent, country.Region "
                                + "FROM city, country "
                                + "WHERE city.CountryCode = country.Code AND city.ID = country.Capital "
                                + "ORDER BY city.Population DESC "
                                + "LIMIT " + top;
            }

            if(!clause.isEmpty() && top == -1) {
                strSelect =
                        "SELECT city.Name, city.CountryCode, city.Population, country.Name, country.Continent, country.Region "
                                + "FROM city, country "
                                + "WHERE city.CountryCode = country.Code AND city.ID = country.Capital AND " + clause
                                + " ORDER BY city.Population DESC";
            }

            if(!clause.isEmpty() && top != -1) {
                strSelect =
                        "SELECT city.Name, city.CountryCode, city.Population, country.Name, country.Continent, country.Region "
                                + "FROM city, country "
                                + "WHERE city.CountryCode = country.Code AND city.ID = country.Capital AND " + clause
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
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%3s %45s %25s %35s %15s %20s", "ID", "NAME", "CONTINENT", "REGION", "POPULATION", "CAPITAL");
        System.out.println();
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
        // Loop over all countries in the list
        for (Country cnt : countries)
        {
            if (cnt == null)
                continue;
            String emp_string =
                    String.format("%3s %45s %25s %35s %15s %20s",
                            cnt.countryID,cnt.name,cnt.continent,cnt.region,cnt.population,cnt.capital);
            System.out.println(emp_string);
        }
    }

    /**
     * Prints a list of cities.
     * @param cities The list of cities to print.
     */
    public void printCities(ArrayList<City> cities)
    {
        if (cities == null)
        {
            System.out.println("No cities");
            return;
        }
        // Print header
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %-35s %-20s %-20s", "Name", "Country", "District", "Population");
        System.out.println();
        System.out.println("-------------------------------------------------------------------------------------------------");
        // Loop over all cities in the list
        for (City cit : cities)
        {
            if (cit == null)
                continue;
            String cit_string =
                    String.format("%-30s %-35s %-20s %-20s",
                            cit.Name, cit.Country, cit.District, cit.Population);
            System.out.println(cit_string);
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

}

