package com.datamasking.services;

import org.w3c.dom.Document;
import org.w3c.dom.*;
import java.io.*;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.*;

public class XPathGeneratorService {
    public Set<String> generateXpaths(String srcfile)throws  ParserConfigurationException{

        Set<String> xpath_list=new HashSet<>();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = docFactory.newDocumentBuilder();

        try{
            Document document = builder.parse(new File(srcfile));
            document.getDocumentElement().normalize();
            Node element = document.getFirstChild();
            NodeList nodes = element.getChildNodes();
            xpath_list.add("/"+ element.getNodeName());
            for(int i=0; i<nodes.getLength(); i++){
                Node currentNode=nodes.item(i);
                if (currentNode.getNodeType() != Node.TEXT_NODE){
                    getXPath(currentNode,"/"+element.getNodeName(),xpath_list);
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return xpath_list;
    }
    public void getXPath(Node node,String xpath, Set<String>xpath_list){
        NodeList nodes = node.getChildNodes();
        String new_xpath=xpath+"/"+node.getNodeName();
        xpath_list.add(new_xpath);
        if (nodes.getLength() == 0) {
            return;
        }
        for(int i=0; i<nodes.getLength(); i++){
            Node currentNode=nodes.item(i);
            if (currentNode.getNodeType() != Node.TEXT_NODE){
                getXPath(currentNode,new_xpath,xpath_list);
            }
        }
    }
}
