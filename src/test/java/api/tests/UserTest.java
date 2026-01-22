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
        this.faker = new Faker();
        this.userPayload = new User();
        this.userPayload.setId(this.faker.idNumber().hashCode());
        this.userPayload.setUsername(this.faker.name().username());
        this.userPayload.setFirstName(this.faker.name().firstName());
        this.userPayload.setLastName(this.faker.name().lastName());
        this.userPayload.setEmail(this.faker.internet().emailAddress());
        this.userPayload.setPassword(this.faker.internet().password(5, 10));
        this.userPayload.setPhone(this.faker.phoneNumber().phoneNumber());
        this.logger = LogManager.getLogger(this.getClass());
    }

    @Test
    @Order(1)
    public void testCreateUser() {
        this.logger.info("*** Test CreateUser ***");
        Response response = UserEndpoints.createUser(this.userPayload);
        response.then().log().all();
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(2)
    public void testReadUser() {
        this.logger.info("*** Test ReadUser ***");
        Response response = UserEndpoints.readUser(this.userPayload.getUsername());
        response.then().log().all();
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(3)
    public void testUpdateUser() {
        this.logger.info("*** Test UpdateUser ***");
        this.userPayload.setUsername(this.faker.name().username());
        this.userPayload.setFirstName(this.faker.name().firstName());
        this.userPayload.setLastName(this.faker.name().lastName());
        Response response = UserEndpoints.UpdateUser(this.userPayload.getUsername(), this.userPayload);
        response.then().log().all();
        response.then().assertThat().statusCode(200);

        Response responseAfterUpdate = UserEndpoints.readUser(this.userPayload.getUsername());
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(4)
    public void testDeleteUser() {
        this.logger.info("*** Test DeleteUser ***");
        Response response = UserEndpoints.deleteUser(this.userPayload.getUsername());
        response.then().log().all();
        response.then().assertThat().statusCode(200);
    }
}
