package com.datamasking.helperClasses;

import java.util.ArrayList;
import java.util.Arrays;

public class Algorithm {
    String name;
    ArrayList<String> parameters, paths;

    public Algorithm() {
        name="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }

    public ArrayList<String> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<String> paths) {
        this.paths = paths;
    }
}
