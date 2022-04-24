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
    ArrayList<String> sensitive_attributes; //sensitive attributes

    public Integer getL() {
        return l;
    }

    public void setL(Integer l) {
        this.l = l;
    }


    public ArrayList<String> getSensitive_attributes() {
        return sensitive_attributes;
    }

    public LdiversityRequestBody(MultipartFile xmlFile, Integer k, Integer l, ArrayList<String> xPaths, ArrayList<String> sensitive_attributes) {
        this.xmlFile = xmlFile;
        this.k = k;
        this.l = l;
        this.xPaths = xPaths;
        this.sensitive_attributes = sensitive_attributes;
    }

    public MultipartFile getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(MultipartFile xmlFile) {
        this.xmlFile = xmlFile;
    }

    public void setSensitive_attributes(ArrayList<String> sensitive_attributes) {
        this.sensitive_attributes = sensitive_attributes;
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
