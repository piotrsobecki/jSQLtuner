package pl.piotrsukiennik.tuner.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 25.06.13
 * Time: 21:25
 */
public class Main {
    static interface DoWithEachLine {
        void doIt(String line);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("root-context.xml");
        final QueryExecutionService queryExecutionService = applicationContext.getBean(QueryExecutionService.class);
        getBufferedReaderFromClasspathFile("queries.sql", new DoWithEachLine(){
            @Override
            public void doIt(String line) {
                List list = queryExecutionService.execute(line);
                System.out.println(line+ " " + list);
            }
        });


    }


    public static void getBufferedReaderFromClasspathFile(String file, DoWithEachLine doWithEachLine){
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = null;
        try {
            while ((line = bufferedReader.readLine())!=null){
                doWithEachLine.doIt(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
