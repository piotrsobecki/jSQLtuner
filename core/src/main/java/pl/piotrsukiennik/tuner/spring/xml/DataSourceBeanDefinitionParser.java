package pl.piotrsukiennik.tuner.spring.xml;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;
import pl.piotrsukiennik.tuner.wrapper.JsqlTunerDataSource;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class DataSourceBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {


    @Override
    protected Class<?> getBeanClass( Element element ) {
        return JsqlTunerDataSource.class;
    }

    @Override
    protected void doParse( Element element, ParserContext parserContext, BeanDefinitionBuilder bean ) {
        String ref = element.getAttribute( "ref" );
        if ( StringUtils.hasText( ref ) ) {
            bean.addDependsOn( ref );
            bean.addPropertyReference( "dataSource", ref );
        }
        bean.addPropertyReference( "statementBuilder", "pl.piotrsukiennik.tuner.service.PreparedStatementBuilder.impl" );
    }

}
