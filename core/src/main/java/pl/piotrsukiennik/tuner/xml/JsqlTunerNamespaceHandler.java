package pl.piotrsukiennik.tuner.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import pl.piotrsukiennik.tuner.xml.context.AopContextBeanDefinitionParser;
import pl.piotrsukiennik.tuner.xml.context.WrapperContextBeanDefinitionParser;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class JsqlTunerNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser( "wrapper-context", new WrapperContextBeanDefinitionParser() );
        registerBeanDefinitionParser( "aop-context", new AopContextBeanDefinitionParser() );
        registerBeanDefinitionParser( "datasource", new DataSourceBeanDefinitionParser() );
    }
}
