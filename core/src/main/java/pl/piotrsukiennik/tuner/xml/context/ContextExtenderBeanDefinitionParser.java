package pl.piotrsukiennik.tuner.xml.context;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;
import pl.piotrsukiennik.tuner.xml.ContextExtender;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public abstract class ContextExtenderBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass( Element element ) {
        return ContextExtender.class;
    }

    @Override
    protected void doParse( Element element, BeanDefinitionBuilder bean ) {
        bean.addPropertyValue( "resources", getResources() );
    }

    protected abstract String[] getResources();
}
