package com.napier.semgroup4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class language_population_report {

    public static void main(App ignoredArgs) {
        LanguageStats languageStats = new LanguageStats();
        languageStats.populateLanguageStats(/* pass in the Connection object */);
        languageStats.printLanguageStats();
    }

    public static void reportLanguagePopulation(String[] ignoredArgs) {
    }

    public static class LanguageStats {

        /**
         * Populates the language statistics from the database.
         */
        public void populateLanguageStats(Connection connection, List<Language> languages) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT language_name, population, percent_of_world_pop FROM languages");
                ResultSet resultSet = statement.executeQuery();

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

        /**
         * Prints the top 5 most spoken languages in the world and their respective population numbers and percentage
         * of the world population.
         */
        public void printLanguageStats() {
            // Sort the languages in descending order of population
            List<Language> languages = new ArrayList<>();
            languages.sort(Comparator.comparing((Language language1) -> {
                return language1.getPopulation();
            }).reversed());

            // Print the header
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%-25s %-40s %-30s", "LANGUAGE", "POPULATION", "WORLD POPULATION %");
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------");

            // Print the top 5 languages
            for (int i = 0; i < 5 && i < languages.size(); i++) {
                Language language = languages.get(i);
                String resString = String.format("%-25s %-40s %-30s", language.getName(),
                        language.getPopulation(), language.getPercentOfWorldPop());
                System.out.println(resString);
            }
        }

        public void populateLanguageStats() {
        }

        /**
         * Inner class representing a language.
         */
        private static class Language {
            private final String name;
            private final double population;
            private final double percentOfWorldPop;

            /**
             * Constructor for Language class.
             *
             * @param name              The name of the language
             @param population The population of people that speak the language
              * @param percentOfWorldPop The percentage of the world population that speaks the language
             */
            public Language(String name, double population, double percentOfWorldPop) {
                this.name = name;
                this.population = population;
                this.percentOfWorldPop = percentOfWorldPop;
            }
            /**
             * Getter method for the name field.
             *
             * @return The name of the language.
             */
            public String getName() {
                return name;
            }

            /**
             * Getter method for the population field.
             *
             * @return The population of people that speak the language.
             */
            public double getPopulation() {
                return population;
            }

            /**
             * Getter method for the percentOfWorldPop field.
             *
             * @return The percentage of the world population that speaks the language.
             */
            public double getPercentOfWorldPop() {
                return percentOfWorldPop;
            }
        }
    }
}