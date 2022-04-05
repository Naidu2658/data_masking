package com.datamasking.controllers;


import com.datamasking.helperClasses.KAnonymityRequestBody;
import com.datamasking.helperClasses.MaskingRequestBody;
import com.datamasking.services.ArrayListToXMLService;
import com.datamasking.services.KAnonymityService;
import com.datamasking.services.MaskingService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.parsers.ParserConfigurationException;

@RestController
public class MaskingController {
    @PostMapping("/applyMasking")
    @CrossOrigin(origins = {"*"})
    String applyMasking(@RequestBody MaskingRequestBody maskingRequestBody)
    {
        return new MaskingService().mask(maskingRequestBody);
    }

    @PostMapping("/applyKAnonymity")
    @CrossOrigin(origins = {"*"})
    String applyKAnonymity(KAnonymityRequestBody kAnonymityRequestBody) throws ParserConfigurationException
    {
        new ArrayListToXMLService().buildXMLFromArrayList(new KAnonymityService().applyAlgorithm(kAnonymityRequestBody));
        return "";
    }
}
