package pl.piotrsukiennik.tuner.parser.jsqlqueryparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import pl.piotrsukiennik.tuner.parser.IQuery;
import pl.piotrsukiennik.tuner.parser.IQueryParser;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement.StatementParserVisitor;


import java.io.StringReader;

/**
 * Author: Piotr Sukiennik
 * Date: 05.07.13
 * Time: 19:05
 */
public class JSqlQueryParser implements IQueryParser {
    @Override
    public IQuery parse(String query) {
        CCJSqlParserManager pm = new CCJSqlParserManager();
        try {
            StatementParserVisitor statementParserVisitor = new StatementParserVisitor(pm.parse(new StringReader(query)));

            IQuery queryParsed = statementParserVisitor.getQuery();





             return queryParsed;
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        return null;
    }

}
