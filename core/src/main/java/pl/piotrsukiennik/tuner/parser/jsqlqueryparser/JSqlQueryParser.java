package pl.piotrsukiennik.tuner.parser.jsqlqueryparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.parser.IQuery;
import pl.piotrsukiennik.tuner.parser.IQueryParser;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement.StatementParserVisitor;
import pl.piotrsukiennik.tuner.persistance.service.ILogService;


import javax.annotation.Resource;
import java.io.StringReader;

/**
 * Author: Piotr Sukiennik
 * Date: 05.07.13
 * Time: 19:05
 */
@Component
public class JSqlQueryParser implements IQueryParser {

    @Override
    public IQuery parse(String query) throws JSQLParserException{
        CCJSqlParserManager pm = new CCJSqlParserManager();
        StatementParserVisitor statementParserVisitor = new StatementParserVisitor(pm.parse(new StringReader(query)));
        return  statementParserVisitor.getQuery();
    }

}
