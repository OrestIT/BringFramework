package org.bring.context;

import java.util.List;
import java.util.Map;

public interface ApplicationContext {
    <T> T getBean(Class<T> beanType);
    <T> T getBean(String name, Class<T> beanType);
    <T> Map<String, T> getAllBeans(Class<T> beanType);
}
