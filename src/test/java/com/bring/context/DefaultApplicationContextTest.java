package com.bring.context;

import org.bring.context.ApplicationContext;
import org.bring.context.exception.NoSuchBeanException;
import org.bring.context.exception.NoUniqueBeanException;
import org.bring.context.impl.DefaultApplicationContext;
import org.junit.jupiter.api.*;

import java.util.Map;

public class DefaultApplicationContextTest {

    private ApplicationContext applicationContext;
    private final static String GENERIC_RESOLVER = "genericResolver";
    private final static String HTML_RESOLVER = "htmlResolver";
    private final static String GENERIC_VIEW_RESOLVER_CLASS_NAME = "genericViewResolver";

    @BeforeEach
    public void initContext() {
        applicationContext = new DefaultApplicationContext("com.bring.context");
    }

    @Test
    @Order(1)
    void getAllBeansTest() {
        Map<String, ViewResolver> allBeans = applicationContext.getAllBeans(ViewResolver.class);
        Assertions.assertNotNull(allBeans);
        Assertions.assertEquals(2, allBeans.size());
    }

    @Test
    @Order(2)
    void getBeanByTypeTest() {
        GenericViewResolver bean = applicationContext.getBean(GenericViewResolver.class);
        Assertions.assertNotNull(bean);
        Assertions.assertEquals(GENERIC_RESOLVER, bean.resolve());
    }

    @Test
    @Order(3)
    void getBeanByTypeShouldThrowNoUniqueBeanException() {
        Assertions.assertThrows(NoUniqueBeanException.class, () -> applicationContext.getBean(ViewResolver.class));
    }

    @Test
    @Order(4)
    void getBeanByTypeShouldThrowNoSuchBeanException() {
        Assertions.assertThrows(NoSuchBeanException.class, () -> applicationContext.getBean(XmlViewResolver.class));
    }

    @Test
    @Order(5)
    void getBeanByCustomNameAndTypeTest() {
        ViewResolver baseViewResolver = applicationContext.getBean("baseViewResolver", ViewResolver.class);
        Assertions.assertNotNull(baseViewResolver);
        Assertions.assertEquals(HTML_RESOLVER, baseViewResolver.resolve());
    }

    @Test
    @Order(6)
    void getBeanByNameAndTypeTest() {
        ViewResolver bean = applicationContext.getBean(GENERIC_VIEW_RESOLVER_CLASS_NAME, ViewResolver.class);
        Assertions.assertNotNull(bean);
        Assertions.assertEquals(GENERIC_RESOLVER, bean.resolve());
    }

    @Test
    @Order(7)
    void getBeanByNameAndTypeShouldThrowException() {
        Assertions.assertThrows(NoSuchBeanException.class, () -> applicationContext.getBean("JsonViewResolver", ViewResolver.class));
    }
}