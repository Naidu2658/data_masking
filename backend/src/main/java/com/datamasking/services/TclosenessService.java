package com.datamasking.services;

import com.datamasking.helperClasses.LdiversityRequestBody;
import com.datamasking.helperClasses.Pair;
import com.datamasking.helperClasses.TclosenessRequestBody;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.nio.file.Files;
import java.util.*;

public class TclosenessService {

    public ArrayList<ArrayList<Pair>> applyAlgorithm(TclosenessRequestBody ldrb) throws ParserConfigurationException
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
       /// String sa=ldrb.getSas();
       // System.out.println(sa);
       // System.out.println(columnMapping.get(sa));
     //   int sai=columnMapping.get(sa);

        Set<String> unique_sa= new HashSet<>();



            for(int j=0;j<xmlArrayFull.length;j++)
            {
                StringBuilder sensitive_attributes_as_string=new StringBuilder("");
                for(int f=0;f<ldrb.getSensitive_attributes().size();f++)
                {
                    if(xmlArrayFull[j][columnMapping.get(ldrb.getSensitive_attributes().get(f))]!=null)
                    {
                        sensitive_attributes_as_string.append(xmlArrayFull[j][columnMapping.get(ldrb.getSensitive_attributes().get(f))]);
                    }
                    unique_sa.add(sensitive_attributes_as_string.toString());
                    System.out.println("sab"+ sensitive_attributes_as_string);
                }
            }



        int unique_count= unique_sa.size();
        System.out.println("uniuq count:"+ unique_count);
        for(int i=0;i<xmlArrayFull.length;i++)
        {

            StringBuilder qa=new StringBuilder("");
            StringBuilder sensitive_attributes_as_string=new StringBuilder("");
            for(int j=0;j<ldrb.getxPaths().size();j++)
            {

                if(xmlArrayFull[i][columnMapping.get(ldrb.getxPaths().get(j))]!=null) {
                    qa.append(xmlArrayFull[i][columnMapping.get(ldrb.getxPaths().get(j))]);
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
            if (unique_sensitive_attribute_count.containsKey(qa.toString())) {
                //  System.out.println(sacount.get(qa.toString())+ "this is qa");
                unique_sensitive_attribute_count.get(qa.toString()).add(sensitive_attributes_as_string.toString());
            }
            else
            {
                Set<String> st=new HashSet<>();
                st.add(sensitive_attributes_as_string.toString());
                unique_sensitive_attribute_count.put(qa.toString(), st);
                //    System.out.println("inside else");
            }
        }

        //   System.out.println("world1");
        for(int i=0;i<xmlArrayFull.length;i++)
        {
            StringBuilder qa=new StringBuilder("");;
            for(int j=0;j<ldrb.getxPaths().size();j++)
            {
                if(xmlArrayFull[i][columnMapping.get(ldrb.getxPaths().get(j))]!=null)
                    qa.append(xmlArrayFull[i][columnMapping.get(ldrb.getxPaths().get(j))]);
            }

            if(unique_sensitive_attribute_count.containsKey(qa.toString()) &&  (unique_count-unique_sensitive_attribute_count.get(qa.toString()).size()) <= ldrb.getT())
            {
                int temp=unique_count-unique_sensitive_attribute_count.get(qa.toString()).size();
                System.out.println("set size " + qa + "  " +  temp);
                continue;
            }
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
