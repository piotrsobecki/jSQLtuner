package pl.piotrsukiennik.tuner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Author: Piotr Sukiennik
 * Date: 25.06.13
 * Time: 21:29
 */
@Configuration
@ImportResource("classpath:pl/piotrsukiennik/tuner/tuner-context.xml")
public class TunerConfiguration {
    public TunerConfiguration() {
    }
}
