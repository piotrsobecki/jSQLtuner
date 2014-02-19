package pl.piotrsukiennik.tuner.persistance.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.model.schema.*;
import pl.piotrsukiennik.tuner.persistance.SchemaDao;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 20:03
 */
@Repository
@Transactional(value = "jsqlTunerTransactionManager")
class SchemaDaoImpl extends CrudDaoImpl implements SchemaDao {

    private static final String DATABASE = "database";

    private static final String SCHEMA = "schema";

    private static final String TABLE = "table";

    private static final String VALUE = "value";

    private static final String JOIN_CONNECTOR = ".";

    private static final String DATABASE_VALUE = DATABASE + JOIN_CONNECTOR + VALUE;

    private static final String SCHEMA_VALUE = SCHEMA + JOIN_CONNECTOR + VALUE;

    private static final String TABLE_VALUE = TABLE + JOIN_CONNECTOR + VALUE;

    private static final String SCHEMA_DATABASE = SCHEMA + JOIN_CONNECTOR + DATABASE;

    private static final String SCHEMA_DATABASE_VALUE = SCHEMA + JOIN_CONNECTOR + DATABASE_VALUE;

    private static final String TABLE_SCHEMA_VALUE = TABLE + JOIN_CONNECTOR + SCHEMA_VALUE;

    private static final String TABLE_SCHEMA_DATABASE = TABLE + JOIN_CONNECTOR + SCHEMA_DATABASE;

    private static final String TABLE_SCHEMA_DATABASE_VALUE = TABLE + JOIN_CONNECTOR + SCHEMA_DATABASE_VALUE;

    @Override
    public Database getDatabase( String databaseName ) {
        Database database = (Database) s().createCriteria( Database.class )
         .add( Restrictions.eq( VALUE, databaseName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( database == null ) {
            database = new Database();
            database.setValue( databaseName );
            create( database );
        }
        return database;
    }


    @Override
    public Function getFunction( String database, String functionName ) {
        Function function = (Function) s().createCriteria( Function.class )
         .add( Restrictions.eq( DATABASE_VALUE, database ) )
         .add( Restrictions.eq( VALUE, functionName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( function == null ) {
            function = getFunction( getDatabase( database ), functionName );
        }
        return function;
    }

    @Override
    public Function getFunction( Database database, String functionName ) {
        Function function = (Function) s().createCriteria( Function.class )
         .add( Restrictions.eq( DATABASE, database ) )
         .add( Restrictions.eq( VALUE, functionName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( function == null ) {
            function = new Function();
            function.setDatabase( database );
            function.setValue( functionName );
            create( function );
        }
        return function;
    }

    @Override
    public Type getType( String database, String typeName ) {
        Type type = (Type) s().createCriteria( Type.class )
         .add( Restrictions.eq( DATABASE_VALUE, database ) )
         .add( Restrictions.eq( VALUE, typeName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( type == null ) {
            type = getType( getDatabase( database ), typeName );
        }
        return type;
    }

    @Override
    public Type getType( Database database, String typeName ) {
        Type type = (Type) s().createCriteria( Type.class )
         .add( Restrictions.eq( DATABASE, database ) )
         .add( Restrictions.eq( VALUE, typeName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( type == null ) {
            type = new Type();
            type.setDatabase( database );
            type.setValue( typeName );
            create( type );
        }
        return type;
    }


    @Override
    public Schema getSchema( String database, String schemaName ) {
        Schema schema = (Schema) s().createCriteria( Schema.class )
         .add( Restrictions.eq( DATABASE_VALUE, database ) )
         .add( Restrictions.eq( VALUE, schemaName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( schema == null ) {
            schema = getSchema( getDatabase( database ), schemaName );
        }
        return schema;
    }

    @Override
    public Schema getSchema( Database database, String schemaName ) {
        Schema schema = (Schema) s().createCriteria( Schema.class )
         .add( Restrictions.eq( DATABASE, database ) )
         .add( Restrictions.eq( VALUE, schemaName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( schema == null ) {
            schema = new Schema();
            schema.setDatabase( database );
            schema.setValue( schemaName );
            create( schema );
        }
        return schema;
    }

    @Override
    public Table getTable( String database, String schema, String tableName ) {
        Table table = (Table) s().createCriteria( Table.class )
         .add( Restrictions.eq( SCHEMA_DATABASE_VALUE, database ) )
         .add( Restrictions.eq( SCHEMA_VALUE, schema ) )
         .add( Restrictions.eq( VALUE, tableName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( table == null ) {
            table = getTable( getDatabase( database ), schema, tableName );
        }
        return table;
    }

    @Override
    public Table getTable( Database database, String schema, String tableName ) {
        Table table = (Table) s().createCriteria( Table.class )
         .add( Restrictions.eq( SCHEMA_DATABASE, database ) )
         .add( Restrictions.eq( SCHEMA_VALUE, schema ) )
         .add( Restrictions.eq( VALUE, tableName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( table == null ) {
            table = getTable( getSchema( database, schema ), tableName );
        }
        return table;
    }

    @Override
    public Table getTable( Schema schema, String tableName ) {
        Table table = (Table) s().createCriteria( Table.class )
         .add( Restrictions.eq( SCHEMA, schema ) )
         .add( Restrictions.eq( VALUE, tableName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( table == null ) {
            table = new Table();
            table.setSchema( schema );
            table.setValue( tableName );
            create( table );
        }
        return table;
    }

    @Override
    public Index getIndex( Table table, String indexName ) {
        Index index = (Index) s().createCriteria( Index.class )
         .add( Restrictions.eq( TABLE, table ) )
         .add( Restrictions.eq( VALUE, indexName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( index == null ) {
            index = new Index();
            index.setTable( table );
            index.setValue( indexName );
            create( index );
        }
        return index;
    }

    @Override
    public Column getColumn( String database, String schema, String tableName, String columnName ) {
        Column column = (Column) s().createCriteria( Column.class )
         .add( Restrictions.eq( TABLE_SCHEMA_DATABASE_VALUE, database ) )
         .add( Restrictions.eq( TABLE_SCHEMA_VALUE, schema ) )
         .add( Restrictions.eq( TABLE_VALUE, tableName ) )
         .add( Restrictions.eq( VALUE, columnName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( column == null ) {
            column = getColumn( getDatabase( database ), schema, tableName, columnName );
        }
        return column;
    }

    @Override
    public Column getColumn( Database database, String schema, String tableName, String columnName ) {
        Column column = (Column) s().createCriteria( Column.class )
         .add( Restrictions.eq( TABLE_SCHEMA_DATABASE, database ) )
         .add( Restrictions.eq( TABLE_SCHEMA_VALUE, schema ) )
         .add( Restrictions.eq( TABLE_VALUE, tableName ) )
         .add( Restrictions.eq( VALUE, columnName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( column == null ) {
            column = getColumn( getSchema( database, schema ), tableName, columnName );
        }
        return column;
    }

    @Override
    public Column getColumn( Schema schema, String tableName, String columnName ) {
        Column column = (Column) s().createCriteria( Column.class )
         .add( Restrictions.eq( TABLE_SCHEMA_VALUE, schema ) )
         .add( Restrictions.eq( TABLE_VALUE, tableName ) )
         .add( Restrictions.eq( VALUE, columnName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( column == null ) {
            column = getColumn( getTable( schema, tableName ), columnName );
        }
        return column;
    }

    @Override
    public Column getColumn( Table tableName, String columnName ) {
        Column column = (Column) s().createCriteria( Column.class )
         .add( Restrictions.eq( TABLE, tableName ) )
         .add( Restrictions.eq( VALUE, columnName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( column == null ) {
            column = new Column();
            column.setValue( columnName );
            column.setTable( tableName );
            create( column );
        }
        else {
            column.setTable( tableName );
        }
        return column;
    }
}
