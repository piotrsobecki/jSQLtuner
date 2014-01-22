package pl.piotrsukiennik.tuner.datasources;

import pl.piotrsukiennik.tuner.IDataSourceMetaData;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 21:15
 */
public class IdentifierMetaData implements IDataSourceMetaData {
    private String identifier;

    public IdentifierMetaData( String identifier ) {
        this.identifier = identifier;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }
}
