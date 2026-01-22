package tests;

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
        this.faker = new Faker();
        this.order = new Order();
        this.order.setId(this.faker.number().numberBetween(0, 10));
        this.order.setPetId(this.faker.number().numberBetween(0, 10));
        this.order.setQuantity(this.faker.number().numberBetween(0, 10));
        this.order.setShipDate(OffsetDateTime.now().toString());
        this.order.setStatus((String)this.faker.options().nextElement(List.of("placed", "approved", "delivered")));
        this.order.setComplete(this.faker.bool().bool());
        this.logger = LogManager.getLogger(this.getClass());
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testCreateOrder() {
        this.logger.info("*** Test CreateOrder ***");
        Response response = StoreEndpoints.createOrder(this.order);
        response.then().log().all();
        response.then().statusCode(200);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void testReadOrder() {
        this.logger.info("*** Test Retrieve Order ***");
        Response response = StoreEndpoints.readOrder(this.order.getId());
        response.then().log().all();
        response.then().statusCode(200);
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testDeleteOrder() {
        this.logger.info("*** Test Delete Order ***");
        Response response = StoreEndpoints.deleteOrder(this.order.getId());
        response.then().log().all();
        response.then().statusCode(200);
    }
}
