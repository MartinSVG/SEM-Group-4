package com.napier.semgroup4;

import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.math.BigInteger;

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
//
//        // Prints Reports for Cities feature
//        Cities_Report.main(a);
//
//        // Prints Reports for Capital Cities feature
//        Capital_Cities_Report.main(a);

        //Prints Reports for Population of the World, a Continent, Region, Country, District, and City
        Individual_Population_Report.main(a);

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
     *  Gets all countries based on clause and top params.
     *  @param clause Where clause for SQL statement
     *  @param top Number to limit results in SQL statement
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
                        "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name "
                                + "FROM country, city "
                                + "WHERE country.Capital = city.ID "
                                + "ORDER BY Population DESC";
            }

            if (clause.isEmpty() && top != -1){
                strSelect =
                        "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name "
                                + "FROM country, city "
                                + "WHERE country.Capital = city.ID "
                                + "ORDER BY Population DESC "
                                + "LIMIT " + top;
            }

            if(!clause.isEmpty() && top == -1) {
                strSelect =
                        "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name "
                                + "FROM country, city "
                                + "WHERE country.Capital = city.ID AND "
                                + clause
                                + " ORDER BY Population DESC";
            }

            if(!clause.isEmpty() && top != -1) {
                strSelect =
                        "SELECT country.Code, country.Name, country.Continent, country.Region, country.Population, city.Name "
                                + "FROM country, city "
                                + "WHERE country.Capital = city.ID AND "
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
                country.countryID = rset.getString("country.Code");
                country.name = rset.getString("country.Name");
                country.continent = rset.getString("country.Continent");
                country.region = rset.getString("country.Region");
                country.population = rset.getInt("country.Population");
                country.capital = rset.getString("city.Name");
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
     *  Gets population of different subgroups of the world
     *  @param type Defines what type of report is to be generated. eg (City or Continent)
     *  @param name Name of Continent, City, etc.
     *  @return A list with type, name, and total population
     */
    public ArrayList<String> getPopulation(String type, String name){
        try{
            // Create an SQL statement
            Statement stmt = con.createStatement();
            String strSelect = "";

            if(type.isEmpty()){
                strSelect = "SELECT SUM(Population) FROM country";
                name = "The World";
            }

            if(type == "Continent"){
                strSelect = "SELECT SUM(Population) From country WHERE country.Continent = '" + name +"'";
                type = "(Continent)";
            }

            if(type == "Region"){
                strSelect = "SELECT SUM(Population) From country WHERE country.Region = '" + name +"'";
                type = "(Region)";
            }

            if(type == "Country"){
                strSelect = "SELECT SUM(Population) From country WHERE country.Name = '" + name +"'";
                type = "(Country)";
            }

            if(type == "District"){
                strSelect = "SELECT SUM(Population) From city WHERE city.District = '" + name +"'";
                type = "(District)";
            }

            if(type == "City"){
                strSelect = "SELECT SUM(Population) From city WHERE city.Name = '" + name +"'";
                type = "(City)";
            }
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            String population = "";
            while (rset.next()) {               // Position the cursor                 3
                population = rset.getBigDecimal(1).toBigInteger().toString();

            }
            ArrayList<String> res = new ArrayList<String>();
            res.add(type);
            res.add(name);
            res.add(population);
            return res;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population");
            return null;
        }
    }

    public void printPopulation(ArrayList<String> result){
        if (result == null)
        {
            System.out.println("Failed to get population");
            return;
        }
        // Print header
        System.out.println("------------------------------------");
        System.out.printf("%3s", "Population of " + result.get(1) + " " + result.get(0));
        System.out.println();
        System.out.println("------------------------------------");
        NumberFormat nf= NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        String popString =
                String.format("%3s",
                        nf.format(Double.parseDouble(result.get(2))));
        System.out.println(popString);
    }


    /**
     * Gets cities and population in the world.
     * @param clause Where clause for SQL statement
     * @param top Number to limit results in SQL statement
     * @return A list of cities, or null if there is an error.
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
     *  @param clause Where clause for SQL statement
     *  @param top Number to limit results in SQL statement
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
            String resString =
                    String.format("%3s %45s %25s %35s %15s %20s",
                            cnt.countryID,cnt.name,cnt.continent,cnt.region,cnt.population,cnt.capital);
            System.out.println(resString);
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
        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %-35s %-20s %-20s", "NAME", "COUNTRY", "DISTRICT", "POPULATION");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------");
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
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%-25s %-40s %-30s", "NAME", "COUNTRY", "POPULATION");
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------");
        // Loop over all cities in the list
        for (City cty : cities)
        {
            if (cty == null)
                continue;
            String resString =
                    String.format("%-25s %-40s %-30s",
                            cty.Name,cty.Country,cty.Population);
            System.out.println(resString);
        }
    }

}
