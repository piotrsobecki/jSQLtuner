package pl.piotrsukiennik.tuner.persistance.transactional;

import org.hibernate.Session;
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
@Transactional( value = "jsqlTunerTransactionManager" )
class SchemaDaoImpl extends CrudDaoImpl implements SchemaDao {

    @Override
    public Database getDatabase( String databaseName ) {
        Session s = s();
        Database database = (Database) s().createCriteria( Database.class )
         .add( Restrictions.eq( "value", databaseName ) )
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
         .add( Restrictions.eq( "database.value", database ) )
         .add( Restrictions.eq( "value", functionName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( function == null ) {
            function = getFunction( getDatabase( database ), functionName );
        }
        return function;
    }

    @Override
    public Function getFunction( Database database, String functionName ) {
        Session s = s();
        Function function = (Function) s().createCriteria( Function.class )
         .add( Restrictions.eq( "database", database ) )
         .add( Restrictions.eq( "value", functionName ) )
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
         .add( Restrictions.eq( "database.value", database ) )
         .add( Restrictions.eq( "value", typeName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( type == null ) {
            type = getType( getDatabase( database ), typeName );
        }
        return type;
    }

    @Override
    public Type getType( Database database, String typeName ) {
        Session s = s();
        Type type = (Type) s().createCriteria( Type.class )
         .add( Restrictions.eq( "database", database ) )
         .add( Restrictions.eq( "value", typeName ) )
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
         .add( Restrictions.eq( "database.value", database ) )
         .add( Restrictions.eq( "value", schemaName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( schema == null ) {
            schema = getSchema( getDatabase( database ), schemaName );
        }
        return schema;
    }

    @Override
    public Schema getSchema( Database database, String schemaName ) {
        Session s = s();
        Schema schema = (Schema) s.createCriteria( Schema.class )
         .add( Restrictions.eq( "database", database ) )
         .add( Restrictions.eq( "value", schemaName ) )
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
         .add( Restrictions.eq( "schema.database.value", database ) )
         .add( Restrictions.eq( "schema.value", schema ) )
         .add( Restrictions.eq( "value", tableName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( table == null ) {
            table = getTable( getDatabase( database ), schema, tableName );
        }
        return table;
    }

    @Override
    public Table getTable( Database database, String schema, String tableName ) {
        Table table = (Table) s().createCriteria( Table.class )
         .add( Restrictions.eq( "schema.database", database ) )
         .add( Restrictions.eq( "schema.value", schema ) )
         .add( Restrictions.eq( "value", tableName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( table == null ) {
            table = getTable( getSchema( database, schema ), tableName );
        }
        return table;
    }

    @Override
    public Table getTable( Schema schema, String tableName ) {
        Session s = s();
        Table table = (Table) s.createCriteria( Table.class )
         .add( Restrictions.eq( "schema", schema ) )
         .add( Restrictions.eq( "value", tableName ) )
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
    public Column getColumn( String database, String schema, String tableName, String columnName ) {
        Column column = (Column) s().createCriteria( Column.class )
         .add( Restrictions.eq( "table.schema.database.value", database ) )
         .add( Restrictions.eq( "table.schema.value", schema ) )
         .add( Restrictions.eq( "table.value", tableName ) )
         .add( Restrictions.eq( "value", columnName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( column == null ) {
            column = getColumn( getDatabase( database ), schema, tableName, columnName );
        }
        return column;
    }

    @Override
    public Column getColumn( Database database, String schema, String tableName, String columnName ) {
        Column column = (Column) s().createCriteria( Column.class )
         .add( Restrictions.eq( "table.schema.database", database ) )
         .add( Restrictions.eq( "table.schema.value", schema ) )
         .add( Restrictions.eq( "table.value", tableName ) )
         .add( Restrictions.eq( "value", columnName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( column == null ) {
            column = getColumn( getSchema( database, schema ), tableName, columnName );
        }
        return column;
    }

    @Override
    public Column getColumn( Schema schema, String tableName, String columnName ) {
        Column column = (Column) s().createCriteria( Column.class )
         .add( Restrictions.eq( "table.schema.value", schema ) )
         .add( Restrictions.eq( "table.value", tableName ) )
         .add( Restrictions.eq( "value", columnName ) )
         .setMaxResults( 1 ).uniqueResult();
        if ( column == null ) {
            column = getColumn( getTable( schema, tableName ), columnName );
        }
        return column;
    }

    @Override
    public Column getColumn( Table tableName, String columnName ) {
        Session s = s();
        Column column = (Column) s.createCriteria( Column.class )
         .add( Restrictions.eq( "table", tableName ) )
         .add( Restrictions.eq( "value", columnName ) )
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
