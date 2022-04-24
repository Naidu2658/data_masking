package com.datamasking.controllers;
import com.datamasking.helperClasses.*;
import com.datamasking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.parsers.ParserConfigurationException;

@RestController
public class MaskingController {

    @Autowired
    private KAnonymityService kAnonymityService;

    @Autowired
    private ArrayListToXMLService arrayListToXMLService;

    @Autowired
    private TextMaskingService textMaskingService;
    @PostMapping("/applyTextMasking")
    @CrossOrigin(origins = {"*"})
    String applyMasking(TextMaskingRequestBody textMaskingRequestBody) throws ParserConfigurationException
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
        System.out.println("hii");
        KAnonymityRequestBody kanrb= new KAnonymityRequestBody();
        kanrb.setK(ldrb.getK());
        kanrb.setxPaths(ldrb.getxPaths());
        kanrb.setXmlFile((ldrb.getXmlFile()));

        new ArrayListToXMLService().buildXMLFromArrayList(new KAnonymityService().applyAlgorithm(kanrb));
//        String xmlString = "";
//        try {
//            File file = new File("Anonymized.xml");
//            xmlString = new String(Files.readAllBytes(file.toPath()));
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        System.out.println("anon:"+ xmlString);
        new ArrayListToXMLService().buildXMLFromArrayList(new LdiversityService().applyAlgorithm(ldrb));

        return "";


    }

    @PostMapping("/applytcloseness")
    @CrossOrigin(origins = {"*"})
    String applyTcloseness(TclosenessRequestBody ldrb) throws ParserConfigurationException
    {
        System.out.println("hii");
        KAnonymityRequestBody kanrb= new KAnonymityRequestBody();
        kanrb.setK(ldrb.getK());
        kanrb.setxPaths(ldrb.getxPaths());
        kanrb.setXmlFile((ldrb.getXmlFile()));

        new ArrayListToXMLService().buildXMLFromArrayList(new KAnonymityService().applyAlgorithm(kanrb));
//        String xmlString = "";
//        try {
//            File file = new File("Anonymized.xml");
//            xmlString = new String(Files.readAllBytes(file.toPath()));
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        System.out.println("anon:"+ xmlString);
        new ArrayListToXMLService().buildXMLFromArrayList(new TclosenessService().applyAlgorithm(ldrb));

        return "";


    }
}
