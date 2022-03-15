package com.datamasking.controllers;

import com.datamasking.helperClasses.DataConfigurationReqestBody;
import com.datamasking.helperClasses.XmlUploadResponseBody;
import com.datamasking.services.ConfigurationFileGeneratorService;
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

@RestController
public class DataConfigurationController {

    @PostMapping("/configuration")
    String sendConfiguration(@RequestBody DataConfigurationReqestBody dataConfigurationReqestBody) throws ParserConfigurationException {
        new ConfigurationFileGeneratorService().createConfiguration(dataConfigurationReqestBody);
        return dataConfigurationReqestBody.toString();
    }

    @PostMapping("/xmlUpload")
    @CrossOrigin
    XmlUploadResponseBody sendElements(@RequestParam("file") MultipartFile file)
    {
        XmlUploadResponseBody xmlUploadResponseBody = new XmlUploadResponseBody("");
        if (!file.isEmpty())
        {
            try {
                byte[] bytes = file.getBytes();
                String xmlString = new String(bytes);
                System.out.println(xmlString);
                xmlUploadResponseBody.setElements(xmlString);
                return xmlUploadResponseBody;
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
        return xmlUploadResponseBody;
    }
}
