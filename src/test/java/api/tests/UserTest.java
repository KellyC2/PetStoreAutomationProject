package api.tests;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.ExtentReportManager;
import com.github.javafaker.Faker;
import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ExtentReportManager.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest {
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
        Response response = UserEndpoints.createUser(userPayload);
        response.then().log().all();
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(2)
    public void testReadUser() {
        logger.info("*** Test ReadUser ***");
        Response response = UserEndpoints.readUser(userPayload.getUsername());
        response.then().log().all();
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(3)
    public void testUpdateUser() {
        logger.info("*** Test UpdateUser ***");
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        Response response = UserEndpoints.UpdateUser(userPayload.getUsername(),userPayload);
        response.then().log().all();
        response.then().assertThat().statusCode(200);

        Response responseAfterUpdate = UserEndpoints.readUser(userPayload.getUsername());
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(4)
    public void testDeleteUser() {
        logger.info("*** Test DeleteUser ***");
        Response response = UserEndpoints.deleteUser(userPayload.getUsername());
        response.then().log().all();
        response.then().assertThat().statusCode(200);
    }
}
