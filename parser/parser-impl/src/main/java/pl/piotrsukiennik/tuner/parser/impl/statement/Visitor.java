package pl.piotrsukiennik.tuner.parser.impl.statement;

import pl.piotrsukiennik.tuner.parser.JsqlParserQueryParsingContext;


/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:53
 */
public abstract class Visitor {
    protected JsqlParserQueryParsingContext parsingContext;


    protected Visitor( JsqlParserQueryParsingContext parsingContext ) {
        this.parsingContext = parsingContext;
    }


}
