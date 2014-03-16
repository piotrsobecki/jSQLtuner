package pl.piotrsukiennik.tuner.spring.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class JsqlTunerNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser( "wrapper-context", new WrapperContextBeanDefinitionParser() );
        registerBeanDefinitionParser( "aop-context", new AopContextBeanDefinitionParser() );
        registerBeanDefinitionParser( "dataSourceWrapper", new DataSourceBeanDefinitionParser() );
        registerBeanDefinitionParser( "keyValueDataSource", new KeyValueDataSourceBeanDefinitionParser() );
        registerBeanDefinitionParser( "keyValueMemcachedService", new KeyValueMemcachedServiceBeanDefinitionParser() );
        registerBeanDefinitionParser( "keyValueLocalService", new KeyValueLocalServiceBeanDefinitionParser() );


    }
}
