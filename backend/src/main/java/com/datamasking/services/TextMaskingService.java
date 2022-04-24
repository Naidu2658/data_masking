package com.datamasking.services;

import com.datamasking.helperClasses.TextMaskingRequestBody;
import com.datamasking.helperClasses.Pair;
import com.datamasking.services.MultipartFileToStringService;
import com.datamasking.services.XMLToArrayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import javax.xml.parsers.ParserConfigurationException;
import java.util.*;

@Service
public class TextMaskingService {

    @Autowired
    private XMLToArrayListService xmlToArrayListService;

    public ArrayList<ArrayList<Pair>> applyAlgorithm(TextMaskingRequestBody textMaskingRequestBody) throws ParserConfigurationException {
        String xmlString = new MultipartFileToStringService().convert(textMaskingRequestBody.getXmlFile());
        ArrayList<ArrayList<Pair>> xmlArray = xmlToArrayListService.getElementList(xmlString);

        Set<String> targetXPaths = new HashSet<>();
        for (String xPath : textMaskingRequestBody.getxPaths()) {
            targetXPaths.add(xPath);
        }
        String pattern = textMaskingRequestBody.getPattern();

        Set<String> uniqueXPaths = new HashSet<>();
        for (ArrayList<Pair> a : xmlArray) {
            for (Pair p : a) {
                uniqueXPaths.add(p.getFirst());
            }
        }

        int rows = xmlArray.size();
        int cols = uniqueXPaths.size();
        String[][] xmlArrayFull = new String[rows][cols];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                xmlArrayFull[i][j] = null;

        Map<String, Integer> columnMapping = new HashMap<>();
        int ind = 0;

        for (String s : uniqueXPaths) {
            columnMapping.put(s, ind++);
        }

        for (int i = 0; i < rows; i++) {
            for (Pair p : xmlArray.get(i)) {
                if (targetXPaths.contains(p.getFirst()))
                    xmlArrayFull[i][columnMapping.get(p.getFirst())] = pattern;

                else xmlArrayFull[i][columnMapping.get(p.getFirst())] = p.getSecond();
            }
        }
        return xmlArray;
    }
}