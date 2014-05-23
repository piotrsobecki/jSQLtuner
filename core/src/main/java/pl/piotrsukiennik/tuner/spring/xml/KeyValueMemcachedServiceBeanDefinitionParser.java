package pl.piotrsukiennik.tuner.spring.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import pl.piotrsukiennik.tuner.dataservice.KeyValueServiceMemcachedImpl;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class KeyValueMemcachedServiceBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {


    @Override
    protected Class<?> getBeanClass( Element element ) {
        return KeyValueServiceMemcachedImpl.class;
    }

    @Override
    protected void doParse( Element element, ParserContext parserContext, BeanDefinitionBuilder bean ) {
        String ref = element.getAttribute( "memcachedClient-ref" );
        if ( StringUtils.hasText( ref ) ) {
            bean.addDependsOn( ref );
            bean.addConstructorArgReference( ref );
        }

    }

}
