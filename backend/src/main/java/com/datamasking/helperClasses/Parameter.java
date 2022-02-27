package com.datamasking.helperClasses;

public class Parameter {

    String element;
    String value;

    @Override
    public String toString() {
        return "Parameter{" +
                "element='" + element + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public Parameter(String element, String value) {
        this.element = element;
        this.value = value;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
