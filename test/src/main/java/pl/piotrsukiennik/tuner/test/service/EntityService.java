package pl.piotrsukiennik.tuner.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.test.model.Test;

import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 12:58
 */
@Service
@Transactional
public class EntityService extends AbstractService{
    public List<Test> testEntities(){
        return s().createCriteria(Test.class).list();
    }
}
