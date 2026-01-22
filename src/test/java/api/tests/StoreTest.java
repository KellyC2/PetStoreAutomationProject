package api.tests;

import api.endpoints.StoreEndpoints;
import api.utilities.ExtentReportManager;
import api.payload.Order;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import java.time.OffsetDateTime;
import java.util.List;

@ExtendWith(ExtentReportManager.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StoreTest {
    Faker faker;
    Order order;
    public Logger logger;

    @BeforeAll
    public void setUpData() {
       faker = new Faker();
       order = new Order();
       order.setId(faker.number().numberBetween(0, 10));
       order.setPetId(faker.number().numberBetween(0, 10));
       order.setQuantity(faker.number().numberBetween(0, 10));
       order.setShipDate(OffsetDateTime.now().toString());
       order.setStatus((String)faker.options().nextElement(List.of("placed", "approved", "delivered")));
       order.setComplete(faker.bool().bool());
       logger = LogManager.getLogger(this.getClass());
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testCreateOrder() {
        logger.info("*** Test CreateOrder ***");
        Response response = StoreEndpoints.createOrder(this.order);
        response.then().log().all();
        response.then().statusCode(200);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void testReadOrder() {
        logger.info("*** Test Retrieve Order ***");
        Response response = StoreEndpoints.readOrder(this.order.getId());
        response.then().log().all();
        response.then().statusCode(200);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testDeleteOrder() {
        logger.info("*** Test Delete Order ***");
        Response response = StoreEndpoints.deleteOrder(this.order.getId());
        response.then().log().all();
        response.then().statusCode(200);
    }
}
