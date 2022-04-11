package com.datamasking.services;

import com.datamasking.helperClasses.KAnonymityRequestBody;
import com.datamasking.helperClasses.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.synth.SynthTabbedPaneUI;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;

@Service
public class KAnonymityService {

    @Autowired
    private XMLToArrayListService xmlToArrayListService;

    public ArrayList<ArrayList<Pair>> applyAlgorithm(KAnonymityRequestBody kAnonymityRequestBody) throws ParserConfigurationException
    {
        String xmlString = new MultipartFileToStringService().convert(kAnonymityRequestBody.getXmlFile());
        ArrayList<ArrayList<Pair>> xmlArray = xmlToArrayListService.getElementList(xmlString);
        Set<String> uniqueXPaths = new HashSet<>();
        for (ArrayList<Pair> a: xmlArray)
        {
            for (Pair p : a)
            {
                uniqueXPaths.add(p.getFirst());
            }
        }
        int rows = xmlArray.size();
        int cols = uniqueXPaths.size();
        String[][] xmlArrayFull = new String[rows][cols];
        for (int i=0; i<rows; i++)
            for (int j=0; j<cols; j++)
                xmlArrayFull[i][j] = null;
        Map<String, Integer> columnMapping = new HashMap<>();
        int ind = 0;
        for (String s: uniqueXPaths)
        {
            columnMapping.put(s, ind++);
        }
        for (int i=0; i<rows; i++)
        {
            for (Pair p : xmlArray.get(i))
            {
                xmlArrayFull[i][columnMapping.get(p.getFirst())] = p.getSecond();
            }
        }

        Map<String, Integer> rowValueCount = new HashMap<>();

        for (int row=0; row<rows; row++)
        {
            String rowValue = "";
            for (String s: kAnonymityRequestBody.getxPaths())
            {
                rowValue += (":"+xmlArrayFull[row][columnMapping.get(s)]);
            }
            if (rowValueCount.containsKey(rowValue))
                rowValueCount.put(rowValue, rowValueCount.get(rowValue)+1);
            else
                rowValueCount.put(rowValue, 1);
        }

        Boolean satisfies = true;

        for (Map.Entry<String, Integer> entry: rowValueCount.entrySet())
        {
            if (entry.getValue() < kAnonymityRequestBody.getK())
            {
                satisfies = false;
                break;
            }
        }

        if (!satisfies)
        {
            for (int row = 0; row<rows; row++)
            {
                for (String s: kAnonymityRequestBody.getxPaths())
                {
                    xmlArrayFull[row][columnMapping.get(s)] = "**";
                }
            }
        }

        for (int i=0; i<rows; i++)
        {
            int col = xmlArray.get(i).size();
            for (int j=0; j<col; j++)
            {
                Pair p = xmlArray.get(i).get(j);
                p.setSecond(xmlArrayFull[i][columnMapping.get(p.getFirst())]);
                xmlArray.get(i).set(j, p);
            }
        }
        return xmlArray;
    }
}