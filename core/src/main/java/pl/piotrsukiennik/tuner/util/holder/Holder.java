package pl.piotrsukiennik.tuner.util.holder;

import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

/**
 * Author: Piotr Sukiennik
 * Date: 02.09.13
 * Time: 22:46
 */
public class Holder {
    @Resource
    protected
    ApplicationContext applicationContext;

    ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
