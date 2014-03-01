package pl.piotrsukiennik.parser.impl.statement;

import pl.piotrsukiennik.parser.QueryParsingContext;


/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:53
 */
public abstract class Visitor {
    protected QueryParsingContext parsingContext;


    protected Visitor( QueryParsingContext parsingContext ) {
        this.parsingContext = parsingContext;
    }


}
