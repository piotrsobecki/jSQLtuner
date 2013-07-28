package pl.piotrsukiennik.tuner.execution;

import pl.piotrsukiennik.tuner.parser.IQueryParser;
import pl.piotrsukiennik.tuner.util.IHashGenerator;

/**
 * Author: Piotr Sukiennik
 * Date: 30.06.13
 * Time: 17:32
 */
public class QueryExecutionBuilder {
    private IHashGenerator hashGenerator;
    private IQueryParser queryParser;

    public QueryExecutionBuilder(IHashGenerator hashGenerator, IQueryParser queryParser){
        this.hashGenerator=hashGenerator;
        this.queryParser=queryParser;
    }

    /*public QueryExecution build(String query, Long queryExecutionTime){
        return new QueryExecution(queryParser.parse(query),hashGenerator.getHash(query),queryExecutionTime);
    }*/
}
