package pl.piotrsukiennik.tuner.spring.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import pl.piotrsukiennik.tuner.service.KeyValueServiceLocalImpl;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class KeyValueLocalServiceBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {


    @Override
    protected Class<?> getBeanClass( Element element ) {
        return KeyValueServiceLocalImpl.class;
    }

    @Override
    protected void doParse( Element element, ParserContext parserContext, BeanDefinitionBuilder bean ) {

    }

}
