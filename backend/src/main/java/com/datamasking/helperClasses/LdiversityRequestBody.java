package com.datamasking.helperClasses;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public class LdiversityRequestBody {
    MultipartFile xmlFile;
    Integer k;

    public Integer getK() {
        return k;
    }

    Integer l;
    ArrayList<String> xPaths;
    ArrayList<String> sas; //sensitive attributes

    public Integer getL() {
        return l;
    }

    public void setL(Integer l) {
        this.l = l;
    }

    public LdiversityRequestBody(MultipartFile xmlFile, Integer k, Integer l, ArrayList<String> xPaths, ArrayList<String> sas) {
        this.xmlFile = xmlFile;
        this.k = k;
        this.l = l;
        this.xPaths = xPaths;
        this.sas = sas;
    }

    public ArrayList<String> getSas() {
        return sas;
    }

    public void setSas(ArrayList<String> sas) {
        this.sas = sas;
    }

    public MultipartFile getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(MultipartFile xmlFile) {
        this.xmlFile = xmlFile;
    }



    public void setK(Integer l) {
        this.l = l;
    }

    public ArrayList<String> getxPaths() {
        return xPaths;
    }

    public void setxPaths(ArrayList<String> xPaths) {
        this.xPaths = xPaths;
    }
}
