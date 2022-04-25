package com.datamasking.controllers;
import com.datamasking.helperClasses.*;
import com.datamasking.services.*;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.json.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

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

    MultipartFile tempfile;
    
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

    @PostMapping("/uploaddata")
    @CrossOrigin(origins = {"*"})
    String uploaddata(FileUploadRequestBody fileUploadRequestBody) throws IOException {

        System.out.println("hello");
        OutputStream outputStream = new FileOutputStream("hello.xml");
        outputStream.write(fileUploadRequestBody.getFile().getBytes());
//        System.out.println(tempfile.getBytes());
      return "";
    }


    @PostMapping("/applyMultiMasking")
    @CrossOrigin(origins = {"*"})
    public ResponseEntity<?> applyMultiMasking(@RequestBody Multiplemask multiplemask) throws ParserConfigurationException, FileNotFoundException, IOException, JSONException {

        System.out.println("hello123");
        System.out.println(multiplemask.getAlgorithms());
        MultipartFile inputFile = new MockMultipartFile("hello.xml", new FileInputStream(new File("hello.xml")));
        MultipleMaskingRequestBody multipleMaskingRequestBody=new MultipleMaskingRequestBody(inputFile, multiplemask.getAlgorithms());

        for (AlgorithmItem algorithmItem: multipleMaskingRequestBody.getAlgorithms())
        {
            if (algorithmItem.getAlgo().equals("datamasking"))
                applyMasking(new TextMaskingRequestBody(multipleMaskingRequestBody.getXmlFile(), algorithmItem.getPattern(), algorithmItem.getxPaths()));
            else if (algorithmItem.getAlgo().equals("kanonymity"))
                applyKAnonymity(new KAnonymityRequestBody(multipleMaskingRequestBody.getXmlFile(), algorithmItem.getK(), algorithmItem.getxPaths()));
            else if (algorithmItem.getAlgo().equals("ldiversity"))
                applyLdiversity(new LdiversityRequestBody(multipleMaskingRequestBody.getXmlFile(), algorithmItem.getK(), algorithmItem.getL(), algorithmItem.getxPaths(), algorithmItem.getSensitive_attributes()));
            else if (algorithmItem.getAlgo().equals("tcloseness"))
                applyTcloseness(new TclosenessRequestBody(multipleMaskingRequestBody.getXmlFile(), algorithmItem.getK(), algorithmItem.getT(), algorithmItem.getxPaths(), algorithmItem.getSensitive_attributes()));
            else if (algorithmItem.getAlgo().equals("numericgeneralization"))
                applyNumericGeneralization(new NumericGeneralizationRequestBody(multipleMaskingRequestBody.getXmlFile(), algorithmItem.getxPaths(), algorithmItem.getRangeMax(), algorithmItem.getK()));
            MultipartFile outputFile = new MockMultipartFile("Anonymized.xml", new FileInputStream(new File("Anonymized.xml")));
            multipleMaskingRequestBody.setXmlFile(outputFile);
        }
        return ResponseEntity.ok("fine");
    }
}
