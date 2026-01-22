package api.endpoints;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

public class UserEndponits2 {

    public static String getBaseUrl() {
        return getUrl().getString("base.url");
    }

    public static ResourceBundle getUrl() {
        ResourceBundle routes = ResourceBundle.getBundle("routes");
        return routes;
    }

    public static Response createUser(User payload) {
        String createUser = getUrl().getString("user.create");
        Response response = given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(payload)
                            .when()
                                .post(getBaseUrl() + createUser);
        return response;
    }

    public static Response readUser(String userName) {
        String readUser = getUrl().getString("user.retrieve");
        Response response = given()
                                .pathParam("username", userName)
                            .when()
                                .get(getBaseUrl() + readUser);
        return response;
    }

    public static Response UpdateUser(String userName, User payload) {
        String updateUser = getUrl().getString("user.update");
        Response response = given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .pathParam("username", userName)
                                .body(payload)
                            .when()
                                .put(getBaseUrl() + updateUser);
        return response;
    }

    public static Response deleteUser(String userName) {
        String deleteUser = getUrl().getString("user.delete");
        Response response = given()
                                .pathParam("username", userName)
                            .when()
                                .delete(getBaseUrl() + deleteUser);
        return response;
    }
}
