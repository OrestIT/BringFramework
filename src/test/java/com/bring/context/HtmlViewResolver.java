package com.bring.context;

import org.bring.context.annotation.Bean;

@Bean(name = "baseViewResolver")
public class HtmlViewResolver implements ViewResolver {
    @Override
    public String resolve() {
        return "htmlResolver";
    }
}
