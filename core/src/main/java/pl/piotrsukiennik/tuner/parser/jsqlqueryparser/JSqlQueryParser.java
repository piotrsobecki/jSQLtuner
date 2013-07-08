package pl.piotrsukiennik.tuner.parser.jsqlqueryparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.deparser.SelectDeParser;
import pl.piotrsukiennik.tuner.model.query.*;
import pl.piotrsukiennik.tuner.parser.IQuery;
import pl.piotrsukiennik.tuner.parser.IQueryParser;


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

            Statement statement =  pm.parse(new StringReader(query));
            SqlQueryModel queryParsed = null;
            if (statement instanceof  Select){
                queryParsed = parse((Select)statement);

            } else if (statement instanceof Delete){

                queryParsed = parse((Delete)statement);

            } else if (statement instanceof Update){

                queryParsed = parse((Update)statement);
            }  else if (statement instanceof Insert){

                queryParsed = parse((Insert)statement);
            }


            int i=0;



        } catch (JSQLParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }


    protected UpdateSqlQueryModel parse(Update update){
        UpdateSqlQueryModel updateSqlQueryModel = new UpdateSqlQueryModel();
        updateSqlQueryModel.setTable(update.getTable().getWholeTableName());
        updateSqlQueryModel = processWhere(updateSqlQueryModel,update.getWhere());

        return updateSqlQueryModel;
    }
    protected SelectSqlQueryModel parse(Select select){
      PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
      SelectSqlQueryModel selectSqlQueryModel = new SelectSqlQueryModel();

      selectSqlQueryModel = processWhere(selectSqlQueryModel,plainSelect.getWhere());
      return selectSqlQueryModel;
    }
    protected DeleteSqlQueryModel parse(Delete delete){
       DeleteSqlQueryModel deleteSqlQueryModel = new DeleteSqlQueryModel();
       deleteSqlQueryModel.setTable(delete.getTable().getWholeTableName());
       deleteSqlQueryModel = processWhere(deleteSqlQueryModel,delete.getWhere());
       return deleteSqlQueryModel;
    }
    protected InsertSqlQueryModel parse(Insert insert){
       InsertSqlQueryModel insertSqlQueryModel = new InsertSqlQueryModel();
       insertSqlQueryModel.setTable(insert.getTable().getWholeTableName());

       return insertSqlQueryModel;
    }

    protected <T extends SqlQueryModel> T processWhere(T t, Expression expression){

        return t;
    }
}
