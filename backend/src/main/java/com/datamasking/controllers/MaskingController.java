package com.datamasking.controllers;


import com.datamasking.helperClasses.MaskingRequestBody;
import com.datamasking.services.MaskingService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaskingController {
    @PostMapping("/applyMasking")
    @CrossOrigin(origins = {"*"})
    String applyMasking(@RequestBody MaskingRequestBody maskingRequestBody)
    {
        return new MaskingService().mask(maskingRequestBody);
    }
}
