package com.napier.semgroup4;

public class Language {
    private String name;
    private double population;
    private double percentOfWorldPopulation;

    public Language(String name, double population, double percentOfWorldPopulation) {
        this.name = name;
        this.population = population;
        this.percentOfWorldPopulation = percentOfWorldPopulation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

    public double getPercentOfWorldPopulation() {
        return percentOfWorldPopulation;
    }

    public void setPercentOfWorldPopulation(double percentOfWorldPopulation) {
        this.percentOfWorldPopulation = percentOfWorldPopulation;
    }
}
