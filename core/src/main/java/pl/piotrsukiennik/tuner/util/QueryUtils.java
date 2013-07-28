package pl.piotrsukiennik.tuner.util;

import pl.piotrsukiennik.tuner.persistance.model.query.JoinsAware;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SourcesAware;
import pl.piotrsukiennik.tuner.persistance.model.query.source.Source;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:43
 */
public class QueryUtils {



    public static void addSource(SourcesAware query, Source source){
        Set<Source> sources = query.getSources();
        if (sources==null){
            sources = new LinkedHashSet<Source>();
            query.setSources(sources);
        }
        sources.add(source);
    }


    public static void addJoin(JoinsAware query, Source source){
        Set<Source> sources = query.getJoins();
        if (sources==null){
            sources = new LinkedHashSet<Source>();
            query.setJoins(sources);
        }
        sources.add(source);
    }
}
