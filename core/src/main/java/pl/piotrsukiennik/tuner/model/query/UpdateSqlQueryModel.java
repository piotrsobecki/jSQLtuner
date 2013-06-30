package pl.piotrsukiennik.tuner.model.query;

/**
 * Author: Piotr Sukiennik
 * Date: 30.06.13
 * Time: 16:45
 */
public class UpdateSqlQueryModel extends SqlQueryModel {

    private String columns;
    private String columnsValues;
    public UpdateSqlQueryModel() {
        super(QueryType.UPDATE);
    }


    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getColumnsValues() {
        return columnsValues;
    }

    public void setColumnsValues(String columnsValues) {
        this.columnsValues = columnsValues;
    }
}
