package com.datamasking.services;
import com.datamasking.helperClasses.Parameter;
import com.datamasking.helperClasses.TextMasking;
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
import org.w3c.dom.Text;

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

        Element srcxml=doc.createElement("source-xml");
        Element xmlpath=doc.createElement("path");
        xmlpath.setTextContent(dataConfigurationReqestBody.getDatasetPath());  //collect this from user
        srcxml.appendChild(xmlpath);
        rootElement.appendChild(srcxml);

        Element srcxsd=doc.createElement("source-xsd");
        Element xsdpath=doc.createElement("path");
        xsdpath.setTextContent(dataConfigurationReqestBody.getSchemaPath()); //collect this from user
        srcxsd.appendChild(xsdpath);
        rootElement.appendChild(srcxsd);

        ArrayList<TextMasking> textMaskingObjectsList=new ArrayList<TextMasking>();

        for (Parameter p: dataConfigurationReqestBody.getParameters())
        {
            TextMasking tobje = new TextMasking(p.getElement(), p.getValue());
            textMaskingObjectsList.add(tobje);
        }

        TextMasking.addTextMaskingElementsToDocument(doc, rootElement, textMaskingObjectsList);

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
