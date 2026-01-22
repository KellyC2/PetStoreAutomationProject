package api.suite;

import api.tests.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        UserTest.class,
        PetTest.class,
        StoreTest.class,
        DataDrivenUserTest.class,
        User2Test.class
})
public class PetStoreSuite {
}
