package com.datamasking.services;
import com.datamasking.helperClasses.DataMaskingAlgorithm;
import com.datamasking.helperClasses.MaskingRequestBody;
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
    public String mask(MaskingRequestBody maskingRequestBody) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ArrayList<DataMaskingAlgorithm> algorithmsList= MaskingRulesAnalyzer.extractMaskingRules(maskingRequestBody.getConfigFileName(),builder);

            String file_name=maskingRequestBody.getXmlFileName();
            byte[] bytes = Files.readAllBytes(Paths.get(file_name));

            String xmlString=new String(bytes);
            InputSource is = new InputSource(new StringReader(xmlString));
            Document document = builder.parse(is);
            document.getDocumentElement().normalize();
            for(DataMaskingAlgorithm algo:algorithmsList){
                if(algo.getName().equals("textmasking")){
                    TextMasking.Mask(document, algo.getParameters(), algo.getPaths());
                }
            }

            try (FileOutputStream output = new FileOutputStream(maskingRequestBody.getMaskedFileName())) {
                writeXml(document, output);

                return "Masking Successful";
            }
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
        return "Masking Unsuccessful";
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
