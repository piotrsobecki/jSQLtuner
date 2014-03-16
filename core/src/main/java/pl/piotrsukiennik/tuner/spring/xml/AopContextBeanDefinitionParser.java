package pl.piotrsukiennik.tuner.spring.xml;

import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;
import pl.piotrsukiennik.tuner.spring.JsqlTunerAopConfiguration;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class AopContextBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass( Element element ) {
        return JsqlTunerAopConfiguration.class;
    }

    @Override
    protected boolean shouldGenerateId() {
        return true;
    }

    @Override
    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }
}
