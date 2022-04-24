package com.datamasking.helperClasses;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public class TextMaskingRequestBody {
    MultipartFile xmlFile;
    String pattern;
    ArrayList<String> xPaths;

    public TextMaskingRequestBody() {

    }

    public TextMaskingRequestBody(MultipartFile xmlFile, String pattern, ArrayList<String> xPaths) {
        this.xmlFile = xmlFile;
        this.pattern = pattern;
        this.xPaths = xPaths;
    }

    public MultipartFile getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(MultipartFile xmlFile) {
        this.xmlFile = xmlFile;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public ArrayList<String> getxPaths() {
        return xPaths;
    }

    public void setxPaths(ArrayList<String> xPaths) {
        this.xPaths = xPaths;
    }
}

