package com.datamasking.services;

import com.datamasking.helperClasses.KAnonymityRequestBody;
import com.datamasking.helperClasses.Pair;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;
import java.util.*;

public class KAnonymityService {
    public ArrayList<ArrayList<Pair>> applyAlgorithm(KAnonymityRequestBody kAnonymityRequestBody) throws ParserConfigurationException
    {
        String xmlString = new MultipartFileToStringService().convert(kAnonymityRequestBody.getXmlFile());
        ArrayList<ArrayList<Pair>> xmlArray = new XMLToArrayListService().getElementList(xmlString);
        Set<String> uniqueXPaths = new HashSet<>();
        for (ArrayList<Pair> a: xmlArray)
        {
            for (Pair p : a) {
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
            for (Pair p: xmlArray.get(i))
            {
                xmlArrayFull[i][columnMapping.get(p.getFirst())] = p.getSecond();
            }
        }
        for (String s: kAnonymityRequestBody.getxPaths())
        {
            Map<String, Integer> valueCount = new HashMap<>();
            for (int i=0; i<rows; i++)
            {
                String rowValue = xmlArrayFull[i][columnMapping.get(s)];
                if (valueCount.containsKey(rowValue))
                {
                    valueCount.put(rowValue, valueCount.get(rowValue) + 1);
                }
                else
                    valueCount.put(rowValue, 1);
            }
            Boolean satisfies = true;
            for (Map.Entry<String, Integer> entry: valueCount.entrySet())
            {
                if (entry.getValue() < kAnonymityRequestBody.getK())
                {
                    satisfies = false;
                    break;
                }
            }
            if (!satisfies)
            {
                int col = columnMapping.get(s);
                for (int i=0; i<rows; i++)
                {
                    xmlArrayFull[i][col] = "**";
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