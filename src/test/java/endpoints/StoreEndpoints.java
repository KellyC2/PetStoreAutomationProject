package endpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.Order;

import static io.restassured.RestAssured.given;

public class StoreEndpoints {

    public static Response createOrder(Order order) {
        Response response = given()
                                .accept(ContentType.JSON)
                                .contentType(ContentType.JSON)
                                .body(order)
                            .when()
                                .post(Routes.placeOrder);
        return response;
    }

    public static Response readOrder(int orderId) {
        Response response = given()
                                .accept(ContentType.JSON)
                                .pathParam("orderId", orderId)
                            .when()
                                .get(Routes.retrievePurchaseOrder);
        return response;
    }

    public static Response deleteOrder(int orderId) {
        Response response = given()
                                .accept(ContentType.JSON)
                                .pathParam("orderId", orderId)
                            .when()
                                .delete(Routes.deletePurchaseOrder);
        return response;
    }
}
