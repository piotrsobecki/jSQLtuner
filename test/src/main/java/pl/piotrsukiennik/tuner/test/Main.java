package pl.piotrsukiennik.tuner.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author: Piotr Sukiennik
 * Date: 25.06.13
 * Time: 21:25
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("root-context.xml");
    }

}
