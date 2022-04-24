package com.datamasking.helperClasses;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public class MultipleMaskingRequestBody {
    MultipartFile xmlFile;
    ArrayList<AlgorithmItem> algorithms;

    public MultipleMaskingRequestBody(MultipartFile xmlFile, ArrayList<AlgorithmItem> algorithms) {
        this.xmlFile = xmlFile;
        this.algorithms = algorithms;
    }

    public MultipartFile getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(MultipartFile xmlFile) {
        this.xmlFile = xmlFile;
    }

    public ArrayList<AlgorithmItem> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(ArrayList<AlgorithmItem> algorithms) {
        this.algorithms = algorithms;
    }
}
