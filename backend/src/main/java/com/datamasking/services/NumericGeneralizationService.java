package com.datamasking.services;

import com.datamasking.helperClasses.NumericGeneralizationRequestBody;
import com.datamasking.helperClasses.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.util.*;

@Service
public class NumericGeneralizationService {

    @Autowired
    private MultipartFileToStringService multipartFileToStringService;

    @Autowired
    private XMLToArrayListService xmlToArrayListService;


    public ArrayList<ArrayList<Pair>> applyNumericGeneralization(NumericGeneralizationRequestBody numericGeneralizationRequestBody) throws  ParserConfigurationException
    {
        String xmlString = multipartFileToStringService.convert(numericGeneralizationRequestBody.getXmlFile());
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

        ArrayList<Integer> rangeMax = numericGeneralizationRequestBody.getRangeMax();
        Collections.sort(rangeMax, Collections.reverseOrder());

        for (String xpath: numericGeneralizationRequestBody.getXpaths())
        {
            int col = columnMapping.get(xpath);
            Map<String, ArrayList<Integer>> buckets = new HashMap<>();
            for (int row=0; row<rows; row++)
            {
                Integer element = Integer.parseInt(xmlArrayFull[row][col]);
                Integer x = -1;
                for (Integer i: rangeMax)
                {
                    if (element <= i)
                        x = i;
                    else
                        break;
                }
                if (buckets.containsKey(x.toString()))
                {
                    buckets.get(x.toString()).add(row);
                }
                else
                {
                    ArrayList<Integer> lst = new ArrayList<>();
                    lst.add(row);
                    buckets.put(x.toString(), lst);
                }
            }
            ArrayList<ArrayList<Integer>> occurences;
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
