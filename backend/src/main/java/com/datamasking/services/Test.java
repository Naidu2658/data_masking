package com.datamasking.services;

import com.datamasking.helperClasses.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        String srcfile="/home/praveen/Desktop/contacts6.xml";
        try{
            byte[] bytes = Files.readAllBytes(Paths.get(srcfile));
            String xmlString=new String(bytes);
            ArrayList<ArrayList<Pair>>elementsList=XMLToArrayListService.getElementList(xmlString);
            int i=0;
            for (ArrayList<Pair> list:elementsList) {
                System.out.println(++i);
                for (Pair p:list) {
                    System.out.println(p.getFirst() +" : "+p.getSecond());
                }
            }
            ArrayListToXMLService.buildXMLFromArrayList(elementsList);
        }
       catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
