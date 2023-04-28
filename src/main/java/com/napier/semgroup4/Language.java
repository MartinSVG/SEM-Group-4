package com.napier.semgroup4;

public class Language {

    /**
     * Language name
     *
     */
    private String name;

    /**
     * Population
     *
     */
    private double population;

    /**
     * Population Percentage
     *
     */
    private double percentOfWorldPopulation;

    /**
     * Constructor to create a new Language object with a given name, population, and percentage of world population
     *
     */
    public Language(String name, double population, double percentOfWorldPopulation) {
        this.name = name;
        this.population = population;
        this.percentOfWorldPopulation = percentOfWorldPopulation;
    }
    /**
     *  Getter method to retrieve the name of the language
     *
     */
    public String getName() {
        return name;
    }

    /**
     *  Setter method to set the name of the language
     *
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *  Getter method to retrieve the population of speakers of this language
     *
     */
    public double getPopulation() {
        return population;
    }

    /**
     *  Setter method to set the population of speakers of this language
     *
     */
    public void setPopulation(double population) {
        this.population = population;
    }

    /**
     *  Getter method to retrieve the percentage of the world's population that speaks this language
     *
     */
    public double getPercentOfWorldPopulation() {
        return percentOfWorldPopulation;
    }

}
