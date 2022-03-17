package com.datamasking.helperClasses;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

public class DataMaskingAlgorithm {
    private String name;
    private String []parameters;
    private String []paths; //Attribute names (XPaths of the elements)in the instance document on which the algorithm has to be applied

    public DataMaskingAlgorithm(String name, String []parameters, String []paths) {
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

    public static void addAlgorithmParametersToXMLDocument(Document doc, Element rootElement, ArrayList<DataMaskingAlgorithm> algorithms) {

        Element algorithmsElement = doc.createElement("algorithms");
        rootElement.appendChild(algorithmsElement);

        for (DataMaskingAlgorithm algo : algorithms) {
            Element algorithmElement = doc.createElement("algorithm");
            algorithmsElement.appendChild(algorithmElement);

            Element nameElement = doc.createElement("name");
            nameElement.setTextContent(algo.name);
            algorithmElement.appendChild(nameElement);

            Element parametersElement = doc.createElement("parameters");
            algorithmElement.appendChild(parametersElement);


            for (String parameter : algo.parameters) {
                Element parameterElement = doc.createElement("parameter");
                parameterElement.setTextContent(parameter);
                parametersElement.appendChild(parameterElement);
            }
            Element pathsElement = doc.createElement("paths");
            algorithmElement.appendChild(pathsElement);

            for (String path : algo.paths) {
                Element pathElement = doc.createElement("path" );
                pathElement.setTextContent(path);
                pathsElement.appendChild(pathElement);
            }
        }
        rootElement.appendChild(algorithmsElement);
    }
}

