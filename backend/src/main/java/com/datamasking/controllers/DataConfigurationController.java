package com.datamasking.controllers;

import com.datamasking.helperClasses.DataConfigurationReqestBody;
import com.datamasking.services.ConfigurationFileGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.parsers.ParserConfigurationException;
@RequestMapping
@RestController
@CrossOrigin
public class DataConfigurationController {


    @RequestMapping(value = "/configuration", method = RequestMethod.POST)
    public ResponseEntity<?> sendConfiguration(@RequestBody DataConfigurationReqestBody dataConfigurationReqestBody) throws ParserConfigurationException {
       System.out.println("hii");
       System.out.println(dataConfigurationReqestBody.toString());
        new ConfigurationFileGeneratorService().createConfiguration(dataConfigurationReqestBody);
        Object entity=null;
        return ResponseEntity.ok("entity");
    }
}
