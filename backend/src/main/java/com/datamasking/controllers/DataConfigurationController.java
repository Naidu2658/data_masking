package com.datamasking.controllers;

import com.datamasking.helperClasses.DataConfigurationReqestBody;
import com.datamasking.services.ConfigurationFileGeneratorService;
import org.springframework.web.bind.annotation.*;

import javax.xml.parsers.ParserConfigurationException;

@RestController
public class DataConfigurationController {

    @PostMapping("/configuration")
    String sendConfiguration(@RequestBody DataConfigurationReqestBody dataConfigurationReqestBody) throws ParserConfigurationException {
        new ConfigurationFileGeneratorService().createConfiguration(dataConfigurationReqestBody);
        return dataConfigurationReqestBody.toString();
    }
}
