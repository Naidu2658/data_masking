package com.datamasking.controllers;

import com.datamasking.helperClasses.DataConfigurationReqestBody;
import com.datamasking.helperClasses.FileUploadRequestBody;
import com.datamasking.helperClasses.XmlUploadResponseBody;
import com.datamasking.services.ConfigurationFileGeneratorService;
import com.datamasking.services.RequestAggregatorService;
import com.datamasking.services.XPathGeneratorService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Set;

@RestController
public class DataConfigurationController {

    @PostMapping("/configuration")
    @CrossOrigin(origins={"*"})
    String sendConfiguration(@RequestBody DataConfigurationReqestBody dataConfigurationReqestBody) throws ParserConfigurationException {
        new ConfigurationFileGeneratorService().createConfiguration(new RequestAggregatorService().aggregator(dataConfigurationReqestBody));
        return dataConfigurationReqestBody.toString();
    }

    @PostMapping("/xmlUpload")
    @CrossOrigin(origins={"*"})
    XmlUploadResponseBody sendElements(FileUploadRequestBody fileUploadRequestBody)
    {
        try (OutputStream outputStream = new FileOutputStream(fileUploadRequestBody.getFilename()))
        {
            outputStream.write(fileUploadRequestBody.getFile().getBytes());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        XmlUploadResponseBody xmlUploadResponseBody = new XmlUploadResponseBody("");
        XPathGeneratorService xPathGeneratorService = new XPathGeneratorService();
        if (!fileUploadRequestBody.getFile().isEmpty())
        {
            try {
                byte[] bytes = fileUploadRequestBody.getFile().getBytes();
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
