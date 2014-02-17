package pl.piotrsukiennik.tuner.service.wrapper.preparedstatement;

import pl.piotrsukiennik.tuner.exception.QueryInterceptionNotSupportedException;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.TreeSet;

/**
 * @author Piotr Sukiennik
 * @date 15.02.14
 */
public class PSParametersIntercepting<T extends PreparedStatement> extends PSWrapper<T> {

    private Collection<PSParameter> parameterSet = new TreeSet<PSParameter>();

    private static final String STRING_VALUE_FORMAT = "\'%s\'";

    public PSParametersIntercepting( T preparedStatement ) {
        super( preparedStatement );
    }

    protected Collection<PSParameter> getParameterSet() {
        return parameterSet;
    }

    @Override
    public void setCharacterStream( int parameterIndex, Reader reader, long length ) throws SQLException {
        //TODO
        super.setCharacterStream( parameterIndex, reader, length );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setAsciiStream( int parameterIndex, InputStream x ) throws SQLException {
        //TODO
        super.setAsciiStream( parameterIndex, x );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setBinaryStream( int parameterIndex, InputStream x ) throws SQLException {
        //TODO
        super.setBinaryStream( parameterIndex, x );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setCharacterStream( int parameterIndex, Reader reader ) throws SQLException {
        //TODO
        super.setCharacterStream( parameterIndex, reader );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setNCharacterStream( int parameterIndex, Reader value ) throws SQLException {
        //TODO
        super.setNCharacterStream( parameterIndex, value );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setClob( int parameterIndex, Reader reader ) throws SQLException {
        //TODO
        super.setClob( parameterIndex, reader );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setBlob( int parameterIndex, InputStream inputStream ) throws SQLException {
        //TODO
        super.setBlob( parameterIndex, inputStream );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setNClob( int parameterIndex, Reader reader ) throws SQLException {
        //TODO
        super.setNClob( parameterIndex, reader );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setAsciiStream( int parameterIndex, InputStream x, int length ) throws SQLException {
        //TODO
        super.setAsciiStream( parameterIndex, x, length );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setUnicodeStream( int parameterIndex, InputStream x, int length ) throws SQLException {
        //TODO
        super.setUnicodeStream( parameterIndex, x, length );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setBinaryStream( int parameterIndex, InputStream x, int length ) throws SQLException {
        //TODO
        super.setBinaryStream( parameterIndex, x, length );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setCharacterStream( int parameterIndex, Reader reader, int length ) throws SQLException {
        //TODO
        super.setCharacterStream( parameterIndex, reader, length );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setNull( int parameterIndex, int sqlType ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, null ) );
        super.setNull( parameterIndex, sqlType );
    }

    @Override
    public void setBoolean( int parameterIndex, boolean x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, Boolean.toString( x ) ) );
        super.setBoolean( parameterIndex, x );
    }

    @Override
    public void setByte( int parameterIndex, byte x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, Byte.toString( x ) ) );
        super.setByte( parameterIndex, x );
    }

    @Override
    public void setShort( int parameterIndex, short x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, Short.toString( x ) ) );
        super.setShort( parameterIndex, x );
    }

    @Override
    public void setInt( int parameterIndex, int x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, Integer.toString( x ) ) );
        super.setInt( parameterIndex, x );
    }

    @Override
    public void setLong( int parameterIndex, long x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, Long.toString( x ) ) );
        super.setLong( parameterIndex, x );
    }

    @Override
    public void setFloat( int parameterIndex, float x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, Float.toString( x ) ) );
        super.setFloat( parameterIndex, x );
    }

    @Override
    public void setDouble( int parameterIndex, double x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, Double.toString( x ) ) );
        super.setDouble( parameterIndex, x );
    }

    @Override
    public void setBigDecimal( int parameterIndex, BigDecimal x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setBigDecimal( parameterIndex, x );
    }

    @Override
    public void setString( int parameterIndex, String x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, String.format( STRING_VALUE_FORMAT, x ) ) );
        super.setString( parameterIndex, x );
    }

    @Override
    public void setBytes( int parameterIndex, byte[] x ) throws SQLException {
        //TODO
        super.setBytes( parameterIndex, x );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setDate( int parameterIndex, Date x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setDate( parameterIndex, x );
    }

    @Override
    public void setTime( int parameterIndex, Time x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setTime( parameterIndex, x );
    }

    @Override
    public void setTimestamp( int parameterIndex, Timestamp x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setTimestamp( parameterIndex, x );
    }

    @Override
    public void clearParameters() throws SQLException {
        //TODO
        super.clearParameters();
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setObject( int parameterIndex, Object x, int targetSqlType ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setObject( parameterIndex, x, targetSqlType );
    }

    @Override
    public void setObject( int parameterIndex, Object x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setObject( parameterIndex, x );
    }

    @Override
    public void setRef( int parameterIndex, Ref x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setRef( parameterIndex, x );
    }

    @Override
    public void setBlob( int parameterIndex, Blob x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setBlob( parameterIndex, x );
    }

    @Override
    public void setClob( int parameterIndex, Clob x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setClob( parameterIndex, x );
    }

    @Override
    public void setArray( int parameterIndex, Array x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setArray( parameterIndex, x );
    }

    @Override
    public void setDate( int parameterIndex, Date x, Calendar cal ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setDate( parameterIndex, x, cal );
    }

    @Override
    public void setTime( int parameterIndex, Time x, Calendar cal ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setTime( parameterIndex, x, cal );
    }

    @Override
    public void setTimestamp( int parameterIndex, Timestamp x, Calendar cal ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setTimestamp( parameterIndex, x, cal );
    }

    @Override
    public void setNull( int parameterIndex, int sqlType, String typeName ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, null ) );
        super.setNull( parameterIndex, sqlType, typeName );
    }

    @Override
    public void setURL( int parameterIndex, URL x ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setURL( parameterIndex, x );
    }

    @Override
    public void setNString( int parameterIndex, String value ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, value ) );
        super.setNString( parameterIndex, value );
    }

    @Override
    public void setNCharacterStream( int parameterIndex, Reader value, long length ) throws SQLException {
        //TODO
        super.setNCharacterStream( parameterIndex, value, length );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setNClob( int parameterIndex, NClob value ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, value == null ? null : value.toString() ) );
        super.setNClob( parameterIndex, value );
    }

    @Override
    public void setClob( int parameterIndex, Reader reader, long length ) throws SQLException {
        //TODO
        super.setClob( parameterIndex, reader, length );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setBlob( int parameterIndex, InputStream inputStream, long length ) throws SQLException {
        //TODO
        super.setBlob( parameterIndex, inputStream, length );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setNClob( int parameterIndex, Reader reader, long length ) throws SQLException {
        //TODO
        super.setNClob( parameterIndex, reader, length );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setSQLXML( int parameterIndex, SQLXML xmlObject ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, xmlObject == null ? null : xmlObject.toString() ) );
        super.setSQLXML( parameterIndex, xmlObject );
    }

    @Override
    public void setObject( int parameterIndex, Object x, int targetSqlType, int scaleOrLength ) throws SQLException {
        parameterSet.add( new PSParameter( parameterIndex, x == null ? null : x.toString() ) );
        super.setObject( parameterIndex, x, targetSqlType, scaleOrLength );
    }

    @Override
    public void setAsciiStream( int parameterIndex, InputStream x, long length ) throws SQLException {
        //TODO
        super.setAsciiStream( parameterIndex, x, length );
        throw new QueryInterceptionNotSupportedException();
    }

    @Override
    public void setBinaryStream( int parameterIndex, InputStream x, long length ) throws SQLException {
        //TODO
        super.setBinaryStream( parameterIndex, x, length );
        throw new QueryInterceptionNotSupportedException();
    }


    @Override
    public void setMaxRows( int max ) throws SQLException {
        //TODO
        super.setMaxRows( max );
        throw new QueryInterceptionNotSupportedException();
    }
}
