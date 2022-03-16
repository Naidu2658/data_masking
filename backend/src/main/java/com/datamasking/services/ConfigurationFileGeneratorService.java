package com.datamasking.services;
import com.datamasking.helperClasses.DataMaskingAlgorithm;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.datamasking.helperClasses.DataConfigurationReqestBody;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class ConfigurationFileGeneratorService {

    public void createConfiguration(DataConfigurationReqestBody dataConfigurationReqestBody) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root element
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("configuration");
        doc.appendChild(rootElement);
        rootElement.setAttribute("name", dataConfigurationReqestBody.getConfigurationName());

        //Fetch the things from front end
        ArrayList<DataMaskingAlgorithm> algorithms_list=new ArrayList<DataMaskingAlgorithm>();
        ArrayList<String> col_list=new ArrayList<>();
        col_list.add("phone");
        col_list.add("email");
        ArrayList<String>parameters_list=new ArrayList<>();
        parameters_list.add("#");
        DataMaskingAlgorithm a1=new DataMaskingAlgorithm("TextMasking",parameters_list,col_list);
        algorithms_list.add(a1);

        //Add the config specs collected from the frontend to the config.xml doc
        DataMaskingAlgorithm.addAlgorithmParametersToXMLDocument(doc,rootElement,algorithms_list);

        try (FileOutputStream output =
                     new FileOutputStream(dataConfigurationReqestBody.getOutputFileName())) {
            writeXml(doc, output);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
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
