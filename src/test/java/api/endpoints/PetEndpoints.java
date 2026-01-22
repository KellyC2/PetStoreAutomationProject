package api.endpoints;

import api.payload.Pet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class PetEndpoints {

    public static Response addNewPet(Pet pet) {
        Response response = given()
                                .accept(ContentType.JSON)
                                .contentType(ContentType.JSON)
                                .body(pet)
                            .when()
                                .post(Routes.addNewPet);
        return response;
    }

    public static Response readPetById(int petId) {
        Response response = given()
                                .accept(ContentType.JSON)
                                .pathParam("petId", petId)
                            .when()
                                .get(Routes.retrievePet);
        return response;
    }

    public static Response UpdatePet(int petId, String petName, String petStatus) {
        Response response = given()
                                .accept(ContentType.JSON)
                                .contentType(ContentType.URLENC)
                                .pathParam("petId", petId)
                                .formParam("name", petName)
                                .formParam("status",petStatus)
                            .when()
                                .post(Routes.updatePet);
        return response;
    }

    public static Response deletePet(int petId) {
        Response response = given()
                                .accept(ContentType.JSON)
                                .header("api_key", "special-key")
                                .pathParam("petId", petId)
                            .when()
                                .delete(Routes.deletePet);
        return response;
    }
}
