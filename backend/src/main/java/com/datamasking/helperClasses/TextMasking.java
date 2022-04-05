package com.datamasking.helperClasses;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;

import java.util.ArrayList;


public class TextMasking {

   // private static final String FORMAT_XSLT="";
    public static void Mask(Document document, ArrayList<String> parameters, ArrayList<String> paths)
    {
        try
        {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            String masking_pattern=parameters.get(0);
            for(String expr:paths)
            {
                NodeList nodeList = (NodeList) xpath.compile(expr).evaluate(document, XPathConstants.NODESET);
                //System.out.println(nodeList.getLength());
                for(int i=0;i<nodeList.getLength();i++)
                {
                    Node currentNode=nodeList.item(i);
                    if (currentNode.getNodeType() != Node.TEXT_NODE)
                    {
                        currentNode.setTextContent(masking_pattern);
                    }
                }
            }
        } catch (XPathExpressionException e)
        {
            e.printStackTrace();
        }
    }


}
