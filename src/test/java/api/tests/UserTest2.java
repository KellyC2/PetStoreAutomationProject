package api.tests;

import api.endpoints.UserEndponits2;
import api.payload.User;
import api.utilities.ExtentReportManager;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ExtentReportManager.class)
public class UserTest2 {
    Faker faker;
    User userPayload;
    public Logger logger;

    @BeforeAll
    public void setUp() {
        faker = new Faker();
        userPayload = new User();
        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().emailAddress());
        userPayload.setPassword(faker.internet().password(5, 10));
        userPayload.setPhone(faker.phoneNumber().phoneNumber());
        logger = LogManager.getLogger(this.getClass());
    }

    @Test
    @Order(1)
    public void testCreateUser() {
        logger.info("*** Test CreateUser ***");
        Response response = UserEndponits2.createUser(userPayload);
        response.then().log().all();
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(2)
    public void testReadUser() {
        logger.info("*** Test ReadUser ***");
        Response response = UserEndponits2.readUser(userPayload.getUsername());
        response.then().log().all();
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(3)
    public void testUpdateUser() {
        logger.info("*** Test UpdateUser ***");
        this.userPayload.setUsername(faker.name().username());
        this.userPayload.setFirstName(faker.name().firstName());
        this.userPayload.setLastName(faker.name().lastName());
        Response response = UserEndponits2.UpdateUser(userPayload.getUsername(),userPayload);
        response.then().log().all();
        response.then().assertThat().statusCode(200);
        Response responseAfterUpdate = UserEndponits2.readUser(userPayload.getUsername());
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(4)
    public void testDeleteUser() {
        logger.info("*** Test DeleteUser ***");
        Response response = UserEndponits2.deleteUser(userPayload.getUsername());
        response.then().log().all();
        response.then().assertThat().statusCode(200);
    }
}
