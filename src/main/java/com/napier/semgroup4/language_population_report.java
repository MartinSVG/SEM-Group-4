package com.napier.semgroup4;

import java.util.ArrayList;
import java.util.Comparator;

public class language_population_report {
    public static void reportLanguagePopulation(String[] args) {
        LanguageStats languageStats = new LanguageStats();
        languageStats.printLanguageStats();
    }

    public static class LanguageStats {
        private final ArrayList<Language> languages = new ArrayList<>();

        /**
         * Constructor for LanguageStats class.
         */
        public LanguageStats() {
            // Add the languages with their respective population numbers and percentage of the world population
            languages.add(new Language("Chinese", 1100, 15.6));
            languages.add(new Language("English", 983, 13.8));
            languages.add(new Language("Hindi", 544, 7.7));
            languages.add(new Language("Spanish", 527, 7.4));
            languages.add(new Language("Arabic", 422, 5.9));
        }

        /**
         * Prints the top 5 most spoken languages in the world and their respective population numbers and percentage
         * of the world population.
         */
        public void printLanguageStats() {
            // Sort the languages in descending order of population
            languages.sort(Comparator.comparing(Language::getPopulation).reversed());

            // Print the header
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%-25s %-40s %-30s", "LANGUAGE", "POPULATION", "WORLD POPULATION %");
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------");

            // Print the top 5 languages
            for (int i = 0; i < 5; i++) {
                Language language = languages.get(i);
                String resString = String.format("%-25s %-40s %-30s", language.getName(),
                        language.getPopulation(), language.getPercentOfWorldPop());
                System.out.println(resString);
            }
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
             * @param name              The name of the language.
             * @param population        The number of people who speak the language.
             * @param percentOfWorldPop The percentage of the world population that speaks the language.
             */
            public Language(String name, double population, double percentOfWorldPop) {
                this.name = name;
                this.population = population;
                this.percentOfWorldPop = percentOfWorldPop;
            }

            public String getName() {
                return name;
            }

            public double getPopulation() {
                return population;
            }

            public double getPercentOfWorldPop() {
                return percentOfWorldPop;
            }
        }
    }
}
