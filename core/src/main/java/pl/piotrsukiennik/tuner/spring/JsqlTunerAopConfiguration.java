package pl.piotrsukiennik.tuner.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Piotr Sukiennik
 * @date 15.03.14
 */
@Configuration
@PropertySource( value = {
     "classpath:/pl/piotrsukiennik/tuner/spring/context/jsqltuner.properties",
     "classpath*:jsqltuner-ext.properties"
    },
    ignoreResourceNotFound = true
)
@ImportResource( { "classpath:/pl/piotrsukiennik/tuner/spring/jsqltuner-context-aop.xml" } )
public class JsqlTunerAopConfiguration {

}
