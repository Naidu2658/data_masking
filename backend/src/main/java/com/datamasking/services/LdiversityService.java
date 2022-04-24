package com.datamasking.services;

import com.datamasking.helperClasses.LdiversityRequestBody;
import com.datamasking.helperClasses.Pair;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.nio.file.Files;
import java.util.*;

@Service
public class LdiversityService {
    public ArrayList<ArrayList<Pair>> applyAlgorithm(LdiversityRequestBody ldrb) throws ParserConfigurationException
    {

        //String xmlString = new MultipartFileToStringService().convert(ldrb.getXmlFile());
        String xmlString = "";
        try {
            File file = new File("Anonymized.xml");
            xmlString = new String(Files.readAllBytes(file.toPath()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //System.out.println("xmlstring:::" + xmlString);
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
        String[][] xmlArrayFull = new String[rows][cols+1];
        for (int i=0; i<rows; i++)
            for (int j=0; j<cols-1; j++)
                xmlArrayFull[i][j] = null;

        for (int i=0; i<rows; i++)
            xmlArrayFull[i][cols-1] = String.valueOf(i+'0');

        Map<String, Integer> columnMapping = new HashMap<>();
        int ind = 0;
        for (String s: uniqueXPaths)
        {
            // System.out.println(s);
            columnMapping.put(s, ind++);
        }
        for (int i=0; i<rows; i++)
        {
            for (Pair p: xmlArray.get(i))
            {
                xmlArrayFull[i][columnMapping.get(p.getFirst())] = p.getSecond();
            }
        }

        Map<String, Set<String>> unique_sensitive_attribute_count=new HashMap<>();
        //String sa=ldrb.getSas();
        // ArrayList<Integer> sa=new ArrayList<>();


        // System.out.println(sa);
        //System.out.println(columnMapping.get(sa));
        //int sai=columnMapping.get(sa);

        //System.out.println("yes====================");
        for(int i=0;i<xmlArrayFull.length;i++)
        {

            StringBuilder qausi_identifier=new StringBuilder("");
            StringBuilder sensitive_attributes_as_string=new StringBuilder("");
            for(int j=0;j<ldrb.getxPaths().size();j++)
            {

                if(xmlArrayFull[i][columnMapping.get(ldrb.getxPaths().get(j))]!=null) {
                    qausi_identifier.append(xmlArrayFull[i][columnMapping.get(ldrb.getxPaths().get(j))]);
                    // System.out.println(xmlArrayFull[i][columnMapping.get(ldrb.getxPaths().get(j))]+ "inside if");
                }

            }
            for(int f=0;f<ldrb.getSensitive_attributes().size();f++)
            {
                if(xmlArrayFull[i][columnMapping.get(ldrb.getSensitive_attributes().get(f))]!=null)
                {
                    sensitive_attributes_as_string.append(xmlArrayFull[i][columnMapping.get(ldrb.getSensitive_attributes().get(f))]);
                }
                System.out.println("sab"+ sensitive_attributes_as_string);
            }
            // System.out.println("world");
            if (unique_sensitive_attribute_count.containsKey(qausi_identifier.toString())) {
                //  System.out.println(sacount.get(qa.toString())+ "this is qa");
                unique_sensitive_attribute_count.get(qausi_identifier.toString()).add(sensitive_attributes_as_string.toString());
            }
            else
            {
                Set<String> st=new HashSet<>();
                st.add(sensitive_attributes_as_string.toString());
                unique_sensitive_attribute_count.put(qausi_identifier.toString(), st);
                System.out.println("inside else");
            }
        }

        //   System.out.println("world1");
        for(int i=0;i<xmlArrayFull.length;i++)
        {
            StringBuilder qausi_identifier=new StringBuilder("");
            for(int j=0;j<ldrb.getxPaths().size();j++)
            {
                if(xmlArrayFull[i][columnMapping.get(ldrb.getxPaths().get(j))]!=null)
                    qausi_identifier.append(xmlArrayFull[i][columnMapping.get(ldrb.getxPaths().get(j))]);
            }

            if(unique_sensitive_attribute_count.containsKey(qausi_identifier.toString()) &&  unique_sensitive_attribute_count.get(qausi_identifier.toString()).size() >= ldrb.getL())
            {
                continue;
            }
            // System.out.println("yes");
            for(String e : ldrb.getSensitive_attributes())
            {
                xmlArrayFull[i][columnMapping.get(e)]="**";
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