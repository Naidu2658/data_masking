package com.datamasking.helperClasses;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.*;

public class TextMasking{

    private String element;
    private String pattern;

    public TextMasking(String element, String pattern){
        this.element=element;
        this.pattern=pattern;
    }
    public String getElement(){
        return this.element;
    }
    public String getPattern(){
        return this.pattern;
    }

    public static void addTextMaskingElementsToDocument(Document doc,Element rootElement, ArrayList<TextMasking> list){

        Element algorithm=doc.createElement("algorithm");
        rootElement.appendChild(algorithm);
        algorithm.setAttribute("name", "TextMasking");

        Element parameters=doc.createElement("parameters");

        for (TextMasking ob : list) {

            Element parameter=doc.createElement("parameter");
            parameters.appendChild(parameter);

            Element element=doc.createElement("element");
            element.setTextContent(ob.getElement());

            Element value=doc.createElement("value");
            value.setTextContent(ob.getPattern());

            parameter.appendChild(element);
            parameter.appendChild(value);
        }
        algorithm.appendChild(parameters);

    }
}