package com.datamasking.helperClasses;


import java.util.Arrays;

public class DataConfigurationReqestBody {

    String configurationName;
    Algorithm []algorithms;
    String outputFileName;

    public DataConfigurationReqestBody(String configurationName, Algorithm[] algorithms, String outputFileName) {
        this.configurationName = configurationName;
        this.algorithms = algorithms;
        this.outputFileName = outputFileName;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    public Algorithm[] getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(Algorithm[] algorithms) {
        this.algorithms = algorithms;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    @Override
    public String toString() {
        return "DataConfigurationReqestBody{" +
                "configurationName='" + configurationName + '\'' +
                ", algorithms=" + Arrays.toString(algorithms) +
                ", outputFileName='" + outputFileName + '\'' +
                '}';
    }
}