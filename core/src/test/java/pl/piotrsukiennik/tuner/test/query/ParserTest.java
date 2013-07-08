package pl.piotrsukiennik.tuner.test.query;


import org.junit.Test;
import pl.piotrsukiennik.tuner.execution.QueryExecution;
import pl.piotrsukiennik.tuner.execution.QueryExecutionBuilder;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.JSqlQueryParser;
import pl.piotrsukiennik.tuner.util.HashGenerators;

/**
 * Author: Piotr Sukiennik
 * Date: 05.07.13
 * Time: 17:41
 */

public class ParserTest {

    private String [] queries = {
            "SELECT * FROM TestTable t",
            "UPDATE TestTable tt SET tt.Active =1 WHERE tt.id>0",
            "SELECT * FROM MY_TABLE1, MY_TABLE2, (SELECT * FROM MY_TABLE3) LEFT OUTER JOIN MY_TABLE4 WHERE ID = (SELECT MAX(ID) FROM MY_TABLE5) AND ID2 IN (SELECT * FROM MY_TABLE6)"};
    @Test
    public void parseTest(){
        QueryExecutionBuilder queryExecutionBuilder  = new QueryExecutionBuilder(HashGenerators.MD5,new JSqlQueryParser());
        for (String query: queries){
            QueryExecution queryExecution = queryExecutionBuilder.build(query,0l);
            System.out.println(queryExecution);
        }

    }
}
