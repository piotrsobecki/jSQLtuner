package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author Piotr Sukiennik
 * @date 23.05.14
 */
public class DataSources {
    private DataSources(){
    }

    public static DataSource getByIdentity( Collection<DataSource> dataSources, DataSourceIdentity identity ){
        for (DataSource dataSource:dataSources){
            if (dataSource.getDataSourceIdentity().equals( identity )){
                return dataSource;
            }
        }
        return null;
    }

    public static Collection<DataSourceIdentity> minus(Collection<DataSourceIdentity> dataSources, Collection<DataSourceIdentity> dataSourceIdentities){
        //Prepare output list
        Collection<DataSourceIdentity> newNodes = new HashSet<>(dataSources);
        newNodes.removeAll( dataSourceIdentities );
        return newNodes;
    }

    public static Collection<DataSource> intersection(Collection<DataSource> dataSources, Collection<DataSourceIdentity> dataSourceIdentities){
        //Prepare output list
        Collection<DataSource> newNodes = new HashSet<>();
        //filter dataSources
        for (DataSource shardNode: dataSources ){
            //If no data source identity in identities list
            if (dataSourceIdentities.contains( shardNode.getDataSourceIdentity())){
                //Add node to output
                newNodes.add( shardNode );
            }
        }
        return newNodes;
    }
}
