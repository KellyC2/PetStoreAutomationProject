package api.tests;

import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.ExtentReportManager;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@ExtendWith(ExtentReportManager.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataDrivenUserTest {

    Logger logger= LogManager.getLogger(this.getClass());

    @Order(1)
    @ParameterizedTest
    @MethodSource("api.utilities.DataProviders#getAllData")
    public void testCreateUser(String userId, String userName, String firstName, String lastName, String userEmail, String password, String phone) {
        logger.info("*** Test CreateUser ***");
        User userPayload = new User();
        userPayload.setId(Integer.parseInt(userId));
        userPayload.setUsername(userName);
        userPayload.setFirstName(firstName);
        userPayload.setLastName(lastName);
        userPayload.setEmail(userEmail);
        userPayload.setPassword(password);
        userPayload.setPhone(phone);
        Response response = UserEndpoints.createUser(userPayload);
        response.then().assertThat().statusCode(200);
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("api.utilities.DataProviders#getUserNames")
    public void testDeleteUserByName(String userName) {
        logger.info("*** Test DeleteUser ***");
        System.out.println("Deleting user: [" + userName + "]");
        Response response = UserEndpoints.deleteUser(userName);
        response.then().assertThat().statusCode(200);
    }
}
