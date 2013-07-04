package pl.piotrsukiennik.tuner.parser.antlr;

import org.antlr.runtime.*;
import org.antlr.v4.parse.ANTLRLexer;
import pl.piotrsukiennik.tuner.parser.IQuery;
import pl.piotrsukiennik.tuner.parser.IQueryParser;

/**
 * Author: Piotr Sukiennik
 * Date: 04.07.13
 * Time: 18:42
 */
public class AntlrQueryParser implements IQueryParser {
    @Override
    public IQuery parse(String query) {
         TokenSource tokenSource = getTokenSource(query);
        Token token = null;
        while ((token = tokenSource.nextToken())!=null){
            System.out.println(token);
        }
        return null;
    }

    protected TokenSource getTokenSource(String query){
        CharStream cs = new ANTLRStringStream(query);
        Lexer lexer = new ANTLRLexer(cs);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        return tokenStream.getTokenSource();

    }
}
