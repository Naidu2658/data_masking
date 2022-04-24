package com.datamasking.controllers;
import com.datamasking.helperClasses.*;
import com.datamasking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class MaskingController {

    @Autowired
    private KAnonymityService kAnonymityService;

    @Autowired
    private ArrayListToXMLService arrayListToXMLService;

    @Autowired
    private LdiversityService ldiversityService;

    @Autowired
    private TclosenessService tclosenessService;

    @Autowired
    private NumericGeneralizationService numericGeneralizationService;

    @Autowired
    private TextMaskingService textMaskingService;
    
    @PostMapping("/applyTextMasking")
    @CrossOrigin(origins = {"*"})
    String applyMasking(@RequestBody TextMaskingRequestBody textMaskingRequestBody) throws ParserConfigurationException
    {
        arrayListToXMLService.buildXMLFromArrayList(textMaskingService.applyAlgorithm(textMaskingRequestBody));
        return "";
    }

    @PostMapping("/applyKAnonymity")
    @CrossOrigin(origins = {"*"})
    String applyKAnonymity(KAnonymityRequestBody kAnonymityRequestBody) throws ParserConfigurationException
    {
        arrayListToXMLService.buildXMLFromArrayList(kAnonymityService.applyAlgorithm(kAnonymityRequestBody));
        return "";
    }

    @PostMapping("/applyLdiversity")
    @CrossOrigin(origins = {"*"})
    String applyLdiversity(LdiversityRequestBody ldrb) throws ParserConfigurationException
    {
        KAnonymityRequestBody kanrb= new KAnonymityRequestBody();
        kanrb.setK(ldrb.getK());
        kanrb.setxPaths(ldrb.getxPaths());
        kanrb.setXmlFile((ldrb.getXmlFile()));
        arrayListToXMLService.buildXMLFromArrayList(kAnonymityService.applyAlgorithm(kanrb));
        arrayListToXMLService.buildXMLFromArrayList(ldiversityService.applyAlgorithm(ldrb));
        return "";
    }

    @PostMapping("/applyTcloseness")
    @CrossOrigin(origins = {"*"})
    String applyTcloseness(TclosenessRequestBody ldrb) throws ParserConfigurationException
    {
        KAnonymityRequestBody kanrb= new KAnonymityRequestBody();
        kanrb.setK(ldrb.getK());
        kanrb.setxPaths(ldrb.getxPaths());
        kanrb.setXmlFile((ldrb.getXmlFile()));
        arrayListToXMLService.buildXMLFromArrayList(kAnonymityService.applyAlgorithm(kanrb));
        arrayListToXMLService.buildXMLFromArrayList(tclosenessService.applyAlgorithm(ldrb));
        return "";
    }

    @PostMapping("/applyNumericGeneralization")
    @CrossOrigin(origins = {"*"})
    String applyNumericGeneralization(NumericGeneralizationRequestBody numericGeneralizationRequestBody) throws ParserConfigurationException
    {
        arrayListToXMLService.buildXMLFromArrayList(numericGeneralizationService.applyNumericGeneralization(numericGeneralizationRequestBody));
        return "";
    }

    @PostMapping("/applyMultiMasking")
    @CrossOrigin(origins = {"*"})
    String applyMultiMasking(MultipleMaskingRequestBody multipleMaskingRequestBody) throws ParserConfigurationException, FileNotFoundException, IOException
    {
        for (AlgorithmItem algorithmItem: multipleMaskingRequestBody.getAlgorithms())
        {
            if (algorithmItem.getAlgo() == "datamasking")
                ;
            else if (algorithmItem.getAlgo() == "kanonymity")
                applyKAnonymity(new KAnonymityRequestBody(multipleMaskingRequestBody.getXmlFile(), algorithmItem.getK(), algorithmItem.getxPaths()));
            else if (algorithmItem.getAlgo() == "ldiversity")
                applyLdiversity(new LdiversityRequestBody(multipleMaskingRequestBody.getXmlFile(), algorithmItem.getK(), algorithmItem.getL(), algorithmItem.getxPaths(), algorithmItem.getSensitive_attributes()));
            else if (algorithmItem.getAlgo() == "tcloseness")
                applyTcloseness(new TclosenessRequestBody(multipleMaskingRequestBody.getXmlFile(), algorithmItem.getK(), algorithmItem.getT(), algorithmItem.getxPaths(), algorithmItem.getSensitive_attributes()));
            else if (algorithmItem.getAlgo() == "numericgeneralization")
                applyNumericGeneralization(new NumericGeneralizationRequestBody(multipleMaskingRequestBody.getXmlFile(), algorithmItem.getxPaths(), algorithmItem.getRangeMax(), algorithmItem.getK()));
            MultipartFile outputFile = new MockMultipartFile("Anonymized.xml", new FileInputStream(new File("/Anonymized.xml")));
            multipleMaskingRequestBody.setXmlFile(outputFile);
        }
        return "";
    }
}
