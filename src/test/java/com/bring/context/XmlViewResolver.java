package com.bring.context;

public class XmlViewResolver implements ViewResolver {
    @Override
    public String resolve() {
        return "xmlResolver";
    }
}
