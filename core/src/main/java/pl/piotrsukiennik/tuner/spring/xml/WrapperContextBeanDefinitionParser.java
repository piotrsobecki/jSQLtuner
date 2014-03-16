package pl.piotrsukiennik.tuner.spring.xml;

import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;
import pl.piotrsukiennik.tuner.spring.JsqlTunerWrapperConfiguration;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class WrapperContextBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass( Element element ) {
        return JsqlTunerWrapperConfiguration.class;
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
