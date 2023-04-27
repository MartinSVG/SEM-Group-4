package com.napier.semgroup4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LanguagePopulationReport {

        public static void main(String[] args) {
            LanguageStats languageStats = new LanguageStats();
            // pass in the Connection object
            languageStats.populateLanguageStats(null);
            languageStats.printLanguageStats();
        }

        public static void reportLanguagePopulation(String[] ignoredArgs) {
        }

        public static class LanguageStats {

            private final String[] languageNames = {"Chinese", "English", "Hindi", "Spanish", "Arabic"};
            private final List<Language> languages = new ArrayList<>();

            public void populateLanguageStats(Connection connection) {
                try {
                    // Check if connection is null
                    if (connection == null) {
                        System.out.println("Error: connection is null.");
                        return;
                    }

                    // Create a prepared statement to retrieve data for Chinese, English, Hindi, Spanish, Arabic, and additional variants of English
                List<String> englishVariants = Arrays.asList("Creole English", "Arabic-French-English", "Samoan-English", "Malay-English");
                List<String> languagesToRetrieve = new ArrayList<>(Arrays.asList(languageNames));
                languagesToRetrieve.addAll(englishVariants);
                String query = String.format("SELECT cl.Language, SUM(cl.Percentage) AS TotalPercentage, SUM(c.Population * (cl.Percentage/100)) as Population "
                        + "FROM countrylanguage cl "
                        + "JOIN country c ON c.Code = cl.CountryCode "
                        + "WHERE cl.Language IN (%s) "
                        + "GROUP BY cl.Language", String.join(",", languagesToRetrieve));

                PreparedStatement statement = connection.prepareStatement(query);

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
            // Filter out languages that are not in the specified languageNames
            List<Language> filteredLanguages = new ArrayList<>();
            for (Language language : languages) {
                if (Arrays.asList(languageNames).contains(language.getName())) {
                    filteredLanguages.add(language);
                }
            }

            // Sort the languages in descending order of population
            filteredLanguages.sort(Comparator.comparing(Language::getPopulation).reversed());

            // Print the header
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%-15s %-15s %-15s\n", "Language", "Population", "Percent of World Population");
            System.out.println("--------------------------------------------------------------------------------");

            // Print the language statistics
            for (Language language : filteredLanguages) {
                System.out.printf("%-15s %-15.0f %-15.2f%%\n", language.getName(), language.getPopulation(),
                        language.getPercentOfWorldPopulation());
            }
            // Print the footer
            System.out.println("--------------------------------------------------------------------------------");
        }
    }
}