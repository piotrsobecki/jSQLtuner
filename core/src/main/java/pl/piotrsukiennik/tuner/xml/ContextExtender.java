package pl.piotrsukiennik.tuner.xml;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class ContextExtender implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    private String[] resources;

    public String[] getResources() {
        return resources;
    }

    public void setResources( String[] resources ) {
        this.resources = resources;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {
        this.applicationContext = new ClassPathXmlApplicationContext( resources, applicationContext );
    }
}
