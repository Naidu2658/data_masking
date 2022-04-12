package com.datamasking.helperClasses;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public class TclosenessRequestBody {

    MultipartFile xmlFile;
    Integer k;
    Integer t;
    ArrayList<String> xPaths;
    ArrayList<String> sensitive_attributes; //sensitive attributes

    public void setXmlFile(MultipartFile xmlFile) {
        this.xmlFile = xmlFile;
    }

    public TclosenessRequestBody(MultipartFile xmlFile, Integer k, Integer t, ArrayList<String> xPaths, ArrayList<String> sensitive_attributes) {
        this.xmlFile = xmlFile;
        this.k = k;
        this.t = t;
        this.xPaths = xPaths;
        this.sensitive_attributes = sensitive_attributes;
    }

    @Override
    public String toString() {
        return "TclosenessRequestBody{" +
                "xmlFile=" + xmlFile +
                ", k=" + k +
                ", t=" + t +
                ", xPaths=" + xPaths +
                ", sensitive_attributes=" + sensitive_attributes +
                '}';
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public void setT(Integer t) {
        this.t = t;
    }

    public void setxPaths(ArrayList<String> xPaths) {
        this.xPaths = xPaths;
    }

    public ArrayList<String> getSensitive_attributes() {
        return sensitive_attributes;
    }

    public MultipartFile getXmlFile() {
        return xmlFile;
    }

    public Integer getK() {
        return k;
    }

    public Integer getT() {
        return t;
    }

    public ArrayList<String> getxPaths() {
        return xPaths;
    }

    public void setSensitive_attributes(ArrayList<String> sensitive_attributes) {
        this.sensitive_attributes = sensitive_attributes;
    }
}
