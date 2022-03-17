package com.datamasking.helperClasses;

import java.util.Arrays;

public class Algorithm {
    String name;
    String parameters[], paths[];

    public Algorithm(String name, String[] parameters, String[] paths) {
        this.name = name;
        this.parameters = parameters;
        this.paths = paths;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return "Algorithm{" +
                "name='" + name + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                ", paths=" + Arrays.toString(paths) +
                '}';
    }
}
