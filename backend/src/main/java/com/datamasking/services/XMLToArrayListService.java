package com.datamasking.services;

import com.datamasking.helperClasses.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.StringReader;
import java.util.ArrayList;


public class XMLToArrayListService {
    public static ArrayList<ArrayList<Pair>> getElementList (String xmlstring)throws ParserConfigurationException {
        ArrayList<ArrayList<Pair>> elementsList=new ArrayList<>();
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlstring));
            Document document = builder.parse(is);
            document.getDocumentElement().normalize();
            Node element = document.getFirstChild();
            NodeList nodesList = element.getChildNodes();

            for(int i=0;i< nodesList.getLength();i++){
                Node currentNode=nodesList.item(i);
                if (currentNode.getNodeType() != Node.TEXT_NODE){
                    ArrayList<Pair> elementList=new ArrayList<>();
                    XMLToArrayListService.addLeafElementsToArrayList(nodesList.item(i),"/"+element.getNodeName(),elementList );
                    elementsList.add(elementList);
                }

            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return  elementsList;
    }
    public static void addLeafElementsToArrayList(Node node,String xpath, ArrayList<Pair> elementList){
        NodeList nodes = node.getChildNodes();
        String new_xpath=xpath+"/"+node.getNodeName();
        if (nodes.getLength() == 1) {
           // System.out.println(node.getNodeName());
            Pair p = new Pair(new_xpath, node.getTextContent());
            elementList.add(p);
            return;
        }
        //System.out.println(new_xpath+" : "+node.getNodeName() + " | "+nodes.getLength());
        for(int i=0; i<nodes.getLength(); i++){
            Node currentNode=nodes.item(i);
            if (currentNode.getNodeType() != Node.TEXT_NODE){
                addLeafElementsToArrayList(currentNode,new_xpath,elementList);
            }
        }
    }
}
