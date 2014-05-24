package pl.piotrsukiennik.tuner.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Piotr Sukiennik
 * @date 24.05.14
 */
public class JsqltunerContextListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final String PROFILE_SERVICE_PERSISTENT = "service-persistent";
    @Override
    public void onApplicationEvent( ContextRefreshedEvent event ) {
        ConfigurableEnvironment env = (ConfigurableEnvironment) event.getApplicationContext().getEnvironment();
        String persistent  = env.getProperty( "jsqltuner.service.persistent" );
        if (persistent==null || Boolean.valueOf( persistent )){
            addProfiles( env, PROFILE_SERVICE_PERSISTENT );
        }
    }

    protected void addProfiles(ConfigurableEnvironment env, String... profiles ){
        String[] activeProfiles = env.getActiveProfiles();
        Set<String> aP = new HashSet<String>( Arrays.asList( activeProfiles  ) );
        if (profiles!=null){
            Collections.addAll( aP, profiles );
        }
        env.setActiveProfiles( aP.toArray( new String[aP.size()] ) );
    }
}
