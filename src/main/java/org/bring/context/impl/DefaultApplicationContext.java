package org.bring.context.impl;

import lombok.SneakyThrows;
import org.bring.context.ApplicationContext;
import org.bring.context.annotation.Bean;
import org.bring.context.exception.NoSuchBeanException;
import org.bring.context.exception.NoUniqueBeanException;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public class DefaultApplicationContext implements ApplicationContext {
    private Map<String, Object> applicationContext = new HashMap<>();

    public DefaultApplicationContext(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Bean.class);
        initContext(typesAnnotatedWith);
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        var allBeansByType = this.getAllBeans(beanType);
        if (allBeansByType.size() > 1) {
            throw new NoUniqueBeanException("Can not get bean. Found more than one instance. Use getBean(String name, Class<T> beanType)");
        }
        return allBeansByType.values().stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchBeanException(String.format("Bean with type: %s is not present", beanType)));
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) {
        applicationContext.entrySet().stream()
                .filter(bean -> bean.getKey().equals(name))
                .map(bean -> beanType.cast(bean.getValue()))
                .findFirst()
                .orElseThrow(() -> new NoSuchBeanException("Can not get bean. Found more than one instance. Use getBean(String name, Class<T> beanType)"));
        return beanType.cast(applicationContext.get(name));
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return applicationContext.entrySet().stream()
                .filter(bean -> beanType.isAssignableFrom(bean.getValue().getClass()))
                .collect(toMap(Map.Entry::getKey, bean -> beanType.cast(bean.getValue())));
    }

    private void initContext(Set<Class<?>> beans) {
        beans.forEach(bean -> applicationContext.put(resolveBeanName(bean), initBeanInstance(bean)));
    }

    @SneakyThrows
    private Object initBeanInstance(Class<?> bean) {
        return bean.getConstructor().newInstance();
    }

    private String resolveBeanName(Class<?> bean) {
        var beanName = bean.getAnnotation(Bean.class).name();
        return beanName.isBlank() ? normalizeBeanNameIfNeeded(bean.getSimpleName()) : beanName;
    }

    private String normalizeBeanNameIfNeeded(String beanName) {
        return beanName.substring(0, 1).toLowerCase().concat(beanName.substring(1));
    }
}