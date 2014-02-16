package pl.piotrsukiennik.tuner;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Author: Piotr Sukiennik
 * Date: 25.06.13
 * Time: 21:29
 */
@Configuration
@ImportResource("classpath*:jsqltuner-context.xml")
public class TunerConfiguration {
    public TunerConfiguration() {
    }
}
