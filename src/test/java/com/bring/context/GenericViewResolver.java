package com.bring.context;

import org.bring.context.annotation.Bean;

@Bean
public class GenericViewResolver implements ViewResolver {
    @Override
    public String resolve() {
        return "genericResolver";
    }
}
