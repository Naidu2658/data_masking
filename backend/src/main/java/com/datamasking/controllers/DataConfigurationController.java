package com.datamasking.controllers;

import com.datamasking.helperClasses.DataConfigurationReqestBody;
import org.springframework.web.bind.annotation.*;

@RestController
public class DataConfigurationController {

    @PostMapping("/configuration")
    String sendConfiguration(@RequestBody DataConfigurationReqestBody dataConfigurationReqestBody)
    {
        return dataConfigurationReqestBody.toString();
    }
}
