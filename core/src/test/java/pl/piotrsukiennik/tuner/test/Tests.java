package pl.piotrsukiennik.tuner.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import pl.piotrsukiennik.tuner.test.query.CacheManagerServiceTest;

/**
 * Author: Piotr Sukiennik
 * Date: 05.07.13
 * Time: 18:20
 */

@RunWith(Suite.class)
@SuiteClasses({ CacheManagerServiceTest.class })
public class Tests {
}
