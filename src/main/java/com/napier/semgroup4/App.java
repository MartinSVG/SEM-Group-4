package com.napier.semgroup4;

import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.sql.Connection;
import java.util.Comparator;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to the database
        if (args.length < 1) {
            a.connect("localhost:33060", 30000);
        } else {
            a.connect(args[0], Integer.parseInt(args[1]));
        }

/*
        // Prints Reports for the Countries feature
        Country_Report.main(a);

        // Prints Reports for the Cities feature
        Cities_Report.main(a);

        // Prints Reports for the Capital Cities feature
        Capital_Cities_Report.main(a);

        //Prints Reports for the Population of the World, a Continent, Region, Country, District, and City
        Individual_Population_Report.main(a);

        //Prints Reports for the Population of people living in and out of cities
        In_and_Out_of_Cities_Report.main(a);
*/
        // Populates and Prints Reports for Population of language speakers
        LanguagePopulationReport.main(a);

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    Connection con = null;

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
                System.out.println("Failed to connect to database attempt ");
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

            ArrayList<Country> countries = new ArrayList<>();
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
     *  @return A list with type, name, and total population, or null if there is an error.
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
     *  Gets population of people living in and out of cities in a contient, region and country
     *  @param type Defines what type of report is to be generated. eg (City or Continent)
     *  @param name Name of Continent, City, etc.
     */
    public ArrayList<String> getInAndOutOfCities(String type, String name)
    {
        try{
            // Create an SQL statement
            Statement ruralstmt = con.createStatement();
            Statement urbanstmt = con.createStatement();
            Statement stmt = con.createStatement();
            String ruralQuery = "";
            String urbanQuery = "";
            String strSelect = "";

            if(type.equals("Continent")){

                ruralQuery = "SELECT "
                        + "(SELECT SUM(Population) FROM country WHERE Continent = '" + name + "') "
                        + "- (SELECT SUM(city.Population) FROM city, country WHERE city.CountryCode = country.Code AND country.Continent = '" + name + "') "
                        + "AS RuralPopulation";

                urbanQuery = "SELECT "
                        + " (SELECT SUM(city.Population) FROM city, country WHERE city.CountryCode = country.Code AND country.Continent = '" + name + "') "
                        + "AS UrbanPopulation";

                strSelect = "SELECT SUM(Population) From country WHERE country.Continent = '" + name +"'";

                type = "(Continent)";
            }

            if(type.equals("Region")){
                ruralQuery = "SELECT "
                        + "(SELECT SUM(Population) FROM country WHERE Region = '" + name + "') "
                        + "- (SELECT SUM(city.Population) FROM city, country WHERE city.CountryCode = country.Code AND country.Region = '" + name + "') "
                        + "AS RuralPopulation";

                urbanQuery = "SELECT "
                        + " (SELECT SUM(city.Population) FROM city, country WHERE city.CountryCode = country.Code AND country.Region = '" + name + "') "
                        + "AS UrbanPopulation";

                strSelect = "SELECT SUM(Population) From country WHERE country.Region = '" + name +"'";
                type = "(Region)";
            }

            if(type.equals("Country")){
                ruralQuery = "SELECT "
                        + "(SELECT SUM(Population) FROM country WHERE Name = '" + name + "') "
                        + "- (SELECT SUM(city.Population) FROM city, country WHERE city.CountryCode = country.Code AND country.Name = '" + name + "') "
                        + "AS RuralPopulation";

                urbanQuery = "SELECT "
                        + " (SELECT SUM(city.Population) FROM city, country WHERE city.CountryCode = country.Code AND country.Name = '" + name + "') "
                        + "AS UrbanPopulation";

                strSelect = "SELECT SUM(Population) From country WHERE country.Name = '" + name +"'";
                type = "(Country)";
            }



            // Execute SQL statement for each kind of result
            ResultSet ruralset = ruralstmt.executeQuery(ruralQuery);
            String ruralPopulation = "";
            while (ruralset.next()) {
                 ruralPopulation = ruralset.getBigDecimal("RuralPopulation").toBigInteger().toString();

            }
            ruralset.close();

            ResultSet urbanset = urbanstmt.executeQuery(urbanQuery);
            String urbanPopulation = "";
            while (urbanset.next()) {
                urbanPopulation = urbanset.getBigDecimal("UrbanPopulation").toBigInteger().toString();

            }
            urbanset.close();

            ResultSet rset = stmt.executeQuery(strSelect);
            String population = "";
            while (rset.next()) {               // Position the cursor                 3
                population = rset.getBigDecimal(1).toBigInteger().toString();

            }
            rset.close();

            // Calculate percentage of urban and rural population to total population
            double urbanPercentage = Double.parseDouble(urbanPopulation) / Double.parseDouble(population) * 100;
            double ruralPercentage = Double.parseDouble(ruralPopulation) / Double.parseDouble(population) * 100;

            // Added percentage to string variable to be added in detail output
            String urbanPercent = String.format("%.2f%%", urbanPercentage);
            String ruralPercent = String.format("%.2f%%", ruralPercentage);
            NumberFormat nf= NumberFormat.getInstance();
            nf.setMaximumFractionDigits(0);
            urbanPopulation = nf.format(Double.parseDouble(urbanPopulation));
            ruralPopulation = nf.format(Double.parseDouble(ruralPopulation));
            population = nf.format(Double.parseDouble(population));

            ArrayList<String> result = new ArrayList<String>();
            result.add(name);
            result.add(type);
            result.add(population);
            result.add(urbanPopulation);
            result.add(urbanPercent);
            result.add(ruralPopulation);
            result.add(ruralPercent);

            return result;


        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population");
            return null;
        }
    }

    public static class LanguageStats {
        private final String[] languageNames = {"Chinese", "English", "Hindi", "Spanish", "Arabic"};
        private final List<Language> languages = new ArrayList<>();

        public void getLanguageStats(Connection connection) {
            try {
                // Check if connection is null
                if (connection == null) {
                    System.out.println("Error: connection is null.");
                    return;
                }

                // Create a prepared statement to retrieve data for Chinese, English, Hindi, Spanish, and Arabic
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT cl.Language, SUM(cl.Percentage) AS TotalPercentage, SUM(c.Population * (cl.Percentage/100)) as Population "
                                + "FROM countrylanguage cl "
                                + "JOIN country c ON c.Code = cl.CountryCode "
                                + "WHERE cl.Language IN (?, ?, ?, ?, ?)"
                                + "GROUP BY cl.Language");
                for (int i = 0; i < languageNames.length; i++) {
                    statement.setString(i + 1, languageNames[i]);
                }

                // Execute the query and retrieve results
                ResultSet resultSet = statement.executeQuery();

                // Populate the Language objects with the retrieved data

                while (resultSet.next()) {
                    String languageName = resultSet.getString("Language");
                    double population = resultSet.getDouble("Population");
                    double percentOfWorldPop = population / 6078749450.0 * 100.0;
                    languages.add(new Language(languageName, population, percentOfWorldPop));
                }
            } catch (SQLException e) {
                System.out.println("Error populating language stats from database.");
                e.printStackTrace();
            }
        }

        public void printLanguageStats() {
            // Sort the languages in descending order of population
            languages.sort(Comparator.comparing(Language::getPopulation).reversed());

            // Print the header
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s\n", "Language", "Population", "Percent of World Population");
            System.out.println("--------------------------------------------------------------------------------");
            NumberFormat nf= NumberFormat.getInstance();
            nf.setMaximumFractionDigits(0);
            // Print the language statistics
            for (Language language : languages) {
                System.out.printf("%-15s %-15s %-15.2f%%\n", language.getName(), nf.format(language.getPopulation()),
                        language.getPercentOfWorldPopulation());
            }

            // Print the footer
            System.out.println("--------------------------------------------------------------------------------");
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
     *  @param cities The list of capital cities to print.
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


    /**
     * Prints the countries retrieved from the database
     *  @param result The list of variables to print
     */
    public void printPopulation(ArrayList<String> result){
        if (result == null || result.contains(null) || result.isEmpty())
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
     * Prints the city population retrieved from the database
     *  @param result The list of variables to print
     */
    public void printInAndOutOfCities(ArrayList<String> result){
        if (result == null || result.contains(null) || result.isEmpty())
        {
            System.out.println("Failed to get population");
            return;
        }
        // Print Population Summary, Total, Urban and Rural
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("%3s", "                  Population Summary for " + result.get(0) + " " + result.get(1));
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Total population: " + result.get(2));
        System.out.printf("Urban population: %s (%s)\n", result.get(3), result.get(4));
        System.out.printf("Rural population: %s (%s)\n", result.get(5), result.get(6));

    }


}
