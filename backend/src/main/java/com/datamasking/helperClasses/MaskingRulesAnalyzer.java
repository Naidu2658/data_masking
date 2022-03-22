package com.datamasking.helperClasses;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MaskingRulesAnalyzer {
    public static ArrayList<DataMaskingAlgorithm> extractMaskingRules(final String fileName, DocumentBuilder builder){
        ArrayList<DataMaskingAlgorithm>algorithmsList=new ArrayList<>();
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(fileName));
            String xmlString=new String(bytes);
            InputSource is = new InputSource(new StringReader(xmlString));
            Document document = builder.parse(is);
            document.getDocumentElement().normalize();
            NodeList algorithmsNodeList=document.getElementsByTagName("algorithm");
            //System.out.println(algorithmsNodeList.getLength());
            for(int i=0;i<algorithmsNodeList.getLength();i++){

                Node currentAlgorithmNode=algorithmsNodeList.item(i);
                ArrayList<String>parameters=new ArrayList<>();
                ArrayList<String>paths=new ArrayList<>();
                String algorithmName="";
                NodeList childNodeList=currentAlgorithmNode.getChildNodes();
                //System.out.println(childNodeList.getLength());
                for(int j=0;j<childNodeList.getLength();j++){
                    Node currentChildNode=childNodeList.item(j);

                    if (currentChildNode.getNodeType() != Node.TEXT_NODE){
                       //System.out.println(currentChildNode.getNodeName());
                        switch(currentChildNode.getNodeName()){
                            case "name":   algorithmName=currentChildNode.getTextContent();
                                break;
                            case "paths":   NodeList pathNodeList=currentChildNode.getChildNodes();
                                for(int k=0;k<pathNodeList.getLength();k++) {
                                    Node currentPathNode = pathNodeList.item(k);
                                    if(currentPathNode.getNodeType()!=Node.TEXT_NODE){
                                        paths.add(currentPathNode.getTextContent());
                                    }
                                }
                                break;
                            case "parameters":  NodeList parameterNodeList=currentChildNode.getChildNodes();
                                for(int k=0;k<parameterNodeList.getLength();k++) {
                                    Node currentParameterNode = parameterNodeList.item(k);
                                    if(currentParameterNode.getNodeType()!=Node.TEXT_NODE){
                                        parameters.add(currentParameterNode.getTextContent());
                                    }
                                }
                                break;

                        }
                    }
                }
                DataMaskingAlgorithm algo=new DataMaskingAlgorithm(algorithmName,parameters,paths);
                algorithmsList.add(algo);
            }

        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        return algorithmsList;
    }
}
