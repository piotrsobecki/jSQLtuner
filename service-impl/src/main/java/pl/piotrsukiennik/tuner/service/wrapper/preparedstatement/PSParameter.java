package pl.piotrsukiennik.tuner.service.wrapper.preparedstatement;

/**
 * @author Piotr Sukiennik
 * @date 15.02.14
 */
public class PSParameter implements Comparable<PSParameter> {
    private long index;

    private String stringValue;

    public PSParameter( long index, String stringValue ) {
        this.index = index;
        this.stringValue = stringValue;
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
