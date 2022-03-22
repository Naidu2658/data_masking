package com.datamasking.services;
import com.datamasking.helperClasses.DataMaskingAlgorithm;
import com.datamasking.helperClasses.MaskingRulesAnalyzer;
import com.datamasking.helperClasses.TextMasking;
import org.w3c.dom.Document;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MaskingService {
    public static void mask(String[] args) {
        //Extract all these from config.xml
        /*
        String file_name="/home/praveen/Desktop/contacts6.xml";
        ArrayList<String> paths1=new ArrayList<>();
        ArrayList<String> parameters1=new ArrayList<>();
        paths1.add("contacts/contact/name/first");
        paths1.add("contacts/contact/phone");
        parameters1.add("xxx");

        ArrayList<String> paths2=new ArrayList<>();
        ArrayList<String> parameters2=new ArrayList<>();
        paths2.add("contacts/contact/location");
        parameters2.add("?????");

        ArrayList<DataMaskingAlgorithm> algorithms_list=new ArrayList<DataMaskingAlgorithm>();
        DataMaskingAlgorithm a1=new DataMaskingAlgorithm("TextMasking",parameters1,paths1);
        DataMaskingAlgorithm a2=new DataMaskingAlgorithm("TextMasking",parameters2,paths2);
        algorithms_list.add(a1);
        algorithms_list.add(a2);
        */


        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ArrayList<DataMaskingAlgorithm> algorithmsList= MaskingRulesAnalyzer.extractMaskingRules("/home/praveen/Desktop/config.xml",builder);

            String file_name="/home/praveen/Desktop/contacts6.xml";
            byte[] bytes = Files.readAllBytes(Paths.get(file_name));

            String xmlString=new String(bytes);
            InputSource is = new InputSource(new StringReader(xmlString));
            Document document = builder.parse(is);
            document.getDocumentElement().normalize();
            System.out.println(algorithmsList.size());
            for(DataMaskingAlgorithm algo:algorithmsList){
                System.out.println(algo.getName());
                if(algo.getName().equals("textmasking")){
                    TextMasking.Mask(document, algo.getParameters(), algo.getPaths());
                }
            }

            try (FileOutputStream output = new FileOutputStream("masked.xml")) {
                writeXml(document, output);
            }
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException, UnsupportedEncodingException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // add a xslt to remove the extra newlines
        //Transformer transformer = transformerFactory.newTransformer(
        // new StreamSource(new File(FORMAT_XSLT)));

        // pretty print
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "no");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }
}
