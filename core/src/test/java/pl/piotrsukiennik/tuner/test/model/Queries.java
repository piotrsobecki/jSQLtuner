package pl.piotrsukiennik.tuner.test.model;

/**
 * @author Piotr Sukiennik
 * @date 10.03.14
 */
public class Queries {

    public static final String HQL_JOIN_SUBSELECT_LIKE_A_QUERY_STR =
     "SELECT md FROM MockDataModel AS md WHERE lower(md.email) like 'a%' and md.id in (" +
      "SELECT md2.id FROM MockDataModel md2 WHERE lower(md2.firstName) LIKE 'a%' AND md2.id IN (" +
      "SELECT md3.id FROM MockDataModel md3 WHERE lower(md3.lastName) LIKE 'a%'" +
      "))";


    private Queries() {

    }
}
