package com.datamasking.controllers;

import com.datamasking.helperClasses.DataConfigurationReqestBody;
import com.datamasking.helperClasses.XmlUploadResponseBody;
import com.datamasking.services.ConfigurationFileGeneratorService;
import com.datamasking.services.RequestAggregatorService;
import com.datamasking.services.XPathGeneratorService;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaCollection;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Set;

@RestController
public class DataConfigurationController {

    @PostMapping("/configuration")
    String sendConfiguration(@RequestBody DataConfigurationReqestBody dataConfigurationReqestBody) throws ParserConfigurationException {
        new ConfigurationFileGeneratorService().createConfiguration(new RequestAggregatorService().aggregator(dataConfigurationReqestBody));
        return dataConfigurationReqestBody.toString();
    }

    @PostMapping("/xmlUpload")
    @CrossOrigin
    XmlUploadResponseBody sendElements(@RequestParam("file") MultipartFile file)
    {
        XmlUploadResponseBody xmlUploadResponseBody = new XmlUploadResponseBody("");
        XPathGeneratorService xPathGeneratorService = new XPathGeneratorService();
        if (!file.isEmpty())
        {
            try {
                byte[] bytes = file.getBytes();
                String xmlString = new String(bytes);
                Set<String> xPaths = xPathGeneratorService.generateXpaths(xmlString);
                xmlUploadResponseBody.setxPaths(xPaths);
                xmlUploadResponseBody.setXml(xmlString);
                return xmlUploadResponseBody;
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        return xmlUploadResponseBody;
    }
}
