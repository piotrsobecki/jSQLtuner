package pl.piotrsukiennik.tuner.persistance.transactional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.persistance.CommonDao;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 15:01
 */
@Repository
@Transactional(value = "jsqlTunerTransactionManager")
class CommonDaoImpl extends CrudDaoImpl implements CommonDao {

}
