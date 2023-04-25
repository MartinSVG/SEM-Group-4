package com.napier.semgroup4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class language_population_report {

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
                // Create a prepared statement to retrieve data for Chinese, English, Hindi, Spanish, and Arabic
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT language_name, population, percent_of_world_pop FROM languages WHERE language_name IN (?, ?, ?, ?, ?)");
                for (int i = 0; i < languageNames.length; i++) {
                    statement.setString(i + 1, languageNames[i]);
                }

                // Execute the query and retrieve results
                ResultSet resultSet = statement.executeQuery();

                // Populate the Language objects with the retrieved data
                while (resultSet.next()) {
                    String languageName = resultSet.getString("language_name");
                    double population = resultSet.getDouble("population");
                    double percentOfWorldPop = resultSet.getDouble("percent_of_world_pop");
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

            // Print the language statistics
            for (Language language : languages) {
                System.out.printf("%-15s %-15.0f %-15.2f%%\n", language.getName(), language.getPopulation(),
                        language.getPercentOfWorldPopulation());
            }

            // Print the footer
            System.out.println("--------------------------------------------------------------------------------");
        }
    }
}