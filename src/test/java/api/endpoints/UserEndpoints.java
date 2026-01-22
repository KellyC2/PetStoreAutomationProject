package api.endpoints;

import api.payload.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class UserEndpoints {

    public static Response createUser(User payload) {
        Response response = given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(payload)
                            .when()
                                .post(Routes.createUser);
        return response;
    }

    public static Response readUser(String userName) {
        Response response = given()
                                .pathParam("username", userName)
                            .when()
                                .get(Routes.retrieveUser);
        return response;
    }

    public static Response UpdateUser(String userName, User payload) {
        Response response = given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .pathParam("username", userName)
                                .body(payload)
                            .when()
                                .put(Routes.updateUser);
        return response;
    }

    public static Response deleteUser(String userName) {
        Response response = given()
                                .pathParam("username", userName)
                            .when()
                                .delete(Routes.deleteUser);
        return response;
    }

}
