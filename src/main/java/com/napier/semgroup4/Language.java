package com.napier.semgroup4;

public class Language {

    private final String name;
    private final double population;
    private final double percentOfWorldPop;

    /**
     * This is the Constructor for the class named as Language.
     *
     * @param name              The name of the language
     * @param population        The population of people that speak the specific language
     * @param percentOfWorldPop The percentage of the world population that speaks the specific language
     */
    public Language(String name, double population, double percentOfWorldPop) {
        this.name = name;
        this.population = population;
        this.percentOfWorldPop = percentOfWorldPop;
    }

    /**
     * Getter method used to Get the name of the language.
     *
     * @return The name of the language
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method used here for the population of people that speak the language.
     *
     * @return The population of people that speak the specific language
     */
    public double getPopulation() {
        return population;
    }

    /**
     * Getter method used here for percentage of the world population that speaks the language.
     *
     * @return The percentage of the world population that speaks the specific language
     */
    public double getPercentOfWorldPop() {
        return percentOfWorldPop;
    }
}
