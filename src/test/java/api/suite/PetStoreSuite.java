package api.suite;

import api.tests.*;
import api.utilities.ExtentReportManager;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        UserTest.class,
        PetTest.class,
        StoreTest.class,
        DataDrivenUserTest.class,
        UserTest2.class
})
public class PetStoreSuite {
}
