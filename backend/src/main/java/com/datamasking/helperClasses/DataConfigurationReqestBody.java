package com.datamasking.helperClasses;


import java.util.Arrays;

public class DataConfigurationReqestBody {

    String algorithm;
    String datasetPath;
    String schemaPath;
    Parameter []parameters;

    public DataConfigurationReqestBody(String algorithm, String datasetPath, String schemaPath, Parameter[] parameters) {
        this.algorithm = algorithm;
        this.datasetPath = datasetPath;
        this.schemaPath = schemaPath;
        this.parameters = parameters;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getDatasetPath() {
        return datasetPath;
    }

    public void setDatasetPath(String datasetPath) {
        this.datasetPath = datasetPath;
    }

    public String getSchemaPath() {
        return schemaPath;
    }

    public void setSchemaPath(String schemaPath) {
        this.schemaPath = schemaPath;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "DataConfigurationReqestBody{" +
                "algorithm='" + algorithm + '\'' +
                ", datasetPath='" + datasetPath + '\'' +
                ", schemaPath='" + schemaPath + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}