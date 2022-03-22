package com.datamasking.helperClasses;

public class MaskingRequestBody {
    String xmlFileName;
    String configFileName;
    String maskedFileName;

    public String getMaskedFileName() {
        return maskedFileName;
    }

    public void setMaskedFileName(String maskedFileName) {
        this.maskedFileName = maskedFileName;
    }

    public MaskingRequestBody(String xmlFileName, String configFileName) {
        this.xmlFileName = xmlFileName;
        this.configFileName = configFileName;
    }

    public String getXmlFileName() {
        return xmlFileName;
    }

    public void setXmlFileName(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }
}
