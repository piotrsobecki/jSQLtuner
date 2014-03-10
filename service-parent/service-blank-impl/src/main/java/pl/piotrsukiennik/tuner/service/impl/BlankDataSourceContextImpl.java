package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.service.DataSourceContext;

//import pl.piotrsukiennik.tuner.util.Statements;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
@Service
public class BlankDataSourceContextImpl implements DataSourceContext {

    @Override
    public boolean contains( String database, String schema ) {
        return false;
    }

}
