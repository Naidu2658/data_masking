package com.datamasking.services;

import com.datamasking.helperClasses.LdiversityRequestBody;
import com.datamasking.helperClasses.Pair;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.nio.file.Files;
import java.util.*;

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

        Map<String, Set<String>> sacount=new HashMap<>();
        String sa=ldrb.getSas();
       // System.out.println(sa);
        //System.out.println(columnMapping.get(sa));
        int sai=columnMapping.get(sa);


        for(int i=0;i<xmlArrayFull.length;i++)
        {

            StringBuilder qa=new StringBuilder("");
            for(int j=0;j<ldrb.getxPaths().size();j++)
            {

                if(xmlArrayFull[i][columnMapping.get(ldrb.getxPaths().get(j))]!=null) {
                    qa.append(xmlArrayFull[i][columnMapping.get(ldrb.getxPaths().get(j))]);
                   // System.out.println(xmlArrayFull[i][columnMapping.get(ldrb.getxPaths().get(j))]+ "inside if");
                }
            }
           // System.out.println("world");
            if (sacount.containsKey(qa.toString())) {
              //  System.out.println(sacount.get(qa.toString())+ "this is qa");
                sacount.get(qa.toString()).add(xmlArrayFull[i][sai]);
            }
            else
            {
                Set<String> st=new HashSet<>();
                st.add(xmlArrayFull[i][sai]);
                sacount.put(qa.toString(), st);
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

            if(sacount.containsKey(qa.toString()) &&  sacount.get(qa.toString()).size() >= ldrb.getL())
            {
                continue;
            }
            xmlArrayFull[i][columnMapping.get(sa)]="**";
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
