package pl.piotrsukiennik.tuner.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.piotrsukiennik.tuner.test.model.Test;
import pl.piotrsukiennik.tuner.test.service.EntityService;
import pl.piotrsukiennik.tuner.test.service.SQLQueryExecutionService;
import pl.piotrsukiennik.tuner.test.util.Utils;

import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 25.06.13
 * Time: 21:25
 */
public class Main {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("root-context.xml");
        for (int i=0; i<3; i++){
            testQueries(applicationContext);
            testEntities(applicationContext);
        }

    }


    public static void testQueries(ApplicationContext applicationContext ){
        final SQLQueryExecutionService queryExecutionService = applicationContext.getBean(SQLQueryExecutionService.class);
        Utils.processEachLine("queries.sql", new Utils.StringProcessor() {
            @Override
            public void process(String line) {
                try{
                    List list = queryExecutionService.execute(line);
                    System.out.println(line + " " + list);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void testEntities(ApplicationContext applicationContext ){
        final EntityService entityService = applicationContext.getBean(EntityService.class);
        List<Test> tests =  entityService.testEntities();
        System.out.println(tests);
    }
}
