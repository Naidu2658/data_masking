package com.datamasking.helperClasses;


import java.util.Arrays;

public class DataConfigurationReqestBody {

    String configurationName;
    String algorithm;
    String datasetPath;
    String schemaPath;
    Parameter []parameters;
    String outputFileName;

    public DataConfigurationReqestBody(String algorithm, String datasetPath, String schemaPath, Parameter[] parameters, String configurationName, String outputFileName) {
        this.algorithm = algorithm;
        this.datasetPath = datasetPath;
        this.schemaPath = schemaPath;
        this.parameters = parameters;
        this.configurationName = configurationName;
        this.outputFileName = outputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
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