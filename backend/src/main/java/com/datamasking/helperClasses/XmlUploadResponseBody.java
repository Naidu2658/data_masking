package com.datamasking.helperClasses;

import java.util.Set;

public class XmlUploadResponseBody {
    String xml;
    Set<String> xPaths;

    public Set<String> getxPaths() {
        return xPaths;
    }

    public void setxPaths(Set<String> xPaths) {
        this.xPaths = xPaths;
    }

    public XmlUploadResponseBody(String xml) {
        this.xml = xml;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}
