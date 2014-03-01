package pl.piotrsukiennik.tuner.statement.impl;

/**
 * @author Piotr Sukiennik
 * @date 15.02.14
 */
public class PSParameter implements Comparable<PSParameter> {

    private static final String NULL_VALUE = "null";


    private long index;

    private String stringValue;

    public PSParameter( long index, String stringValue ) {
        this.index = index;
        this.stringValue = stringValue;
        if ( stringValue == null ) {
            this.stringValue = NULL_VALUE;
        }
    }

    public long getIndex() {
        return index;
    }

    public String getStringValue() {
        return stringValue;
    }

    @Override
    public int compareTo( PSParameter o ) {
        return Long.compare( index, o.index );
    }
}
