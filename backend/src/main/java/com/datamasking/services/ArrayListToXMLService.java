package com.datamasking.services;

import com.datamasking.helperClasses.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class ArrayListToXMLService {
    static public void buildXMLFromArrayList(ArrayList<ArrayList<Pair>> elementsList) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root element
        Document doc = docBuilder.newDocument();
        String rootName=getRootName(elementsList.get(0).get(0));
        Element rootElement =doc.createElement(rootName);
        doc.appendChild(rootElement);
        for (ArrayList<Pair> elementList:elementsList) {
            Element firstLevelChild=doc.createElement("dummyChild");
            for(Pair p:elementList) { //pair.first= xpath, pair.second=PC data
                //System.out.println(rootElement.getNodeName());
                //System.out.println(firstLevelChild.getNodeName());
                ArrayListToXMLService.appendElements(p, firstLevelChild, doc);
            }
            rootElement.appendChild(firstLevelChild.getFirstChild().getFirstChild());
        }
      
        /*for(int i=0;i<childNodes.getLength();i++) {
            Node child=childNodes.item(i);
            System.out.println(child.getNodeName());
            originalRoot.appendChild(child);
        }*/
        try (FileOutputStream output =
                     new FileOutputStream("Anonymized.xml")) {
            writeXml(doc, output);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
    private static String getRootName(Pair p){
        return p.getFirst().split("/")[1];
    }
    static void appendElements(Pair p, Node firstLevelChild,Document doc){

        Node currentNode=firstLevelChild;
        Node nextNode=null;
        String[] elements=p.getFirst().split("/");
        //System.out.println(currentNode.getNodeName());
        for (String elementName:elements) {
            //System.out.println(elementName);
            if(!elementName.equals("")){
               NodeList childNodes=currentNode.getChildNodes();
               boolean flag=true;
               for (int i=0;i< childNodes.getLength();i++) {
                   Node node=childNodes.item(i);
                   if(node.getNodeName().equals(elementName)){
                       nextNode=node;
                       flag=false;
                       break;
                   }
               }
               if(flag){
                   Element child= doc.createElement(elementName);
                   currentNode.appendChild(child);
                   nextNode=child;
               }
               currentNode=nextNode;
            }
        }
        if(currentNode!=null){
            System.out.println(p.getSecond());
            currentNode.setTextContent(p.getSecond());
        }
    }
    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //Enable indentation

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);

    }

}
