package pl.piotrsukiennik.tuner.model.query;

/**
 * Author: Piotr Sukiennik
 * Date: 30.06.13
 * Time: 16:45
 */
public class SelectSqlQueryModel extends SqlQueryModel {

    private String columns;

    public SelectSqlQueryModel() {
        super(QueryType.SELECT);
    }


    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

}
