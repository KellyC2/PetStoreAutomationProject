package api.tests;

import api.endpoints.PetEndpoints;
import api.payload.Category;
import api.payload.Pet;
import api.payload.Tag;
import api.utilities.ExtentReportManager;
import com.github.javafaker.Faker;
import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;


import java.util.List;

@ExtendWith(ExtentReportManager.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PetTest {
    Faker faker;
    Pet pet;
    public Logger logger;

    @BeforeAll
    public void setUp() {
        faker = new Faker();
        pet = new Pet();
        Category category = new Category();
        category.setId(faker.number().numberBetween(1, 100));
        category.setName(faker.name().fullName());

        Tag tag = new Tag();
        tag.setId(faker.number().numberBetween(1, 100));
        tag.setName(faker.name().username());

        pet.setId(faker.number().numberBetween(1, 10));
        pet.setName(faker.name().firstName());
        pet.setStatus(
                faker.options().option("available", "pending", "sold")
        );
        pet.setCategory(category);
        pet.setPhotoUrls(List.of(faker.internet().image(),faker.internet().image()));
        pet.setTags(List.of(tag));
        logger = LogManager.getLogger(this.getClass());
    }

    @Test
    @Order(1)
    public void testCreatePet() {
        logger.info("*** Test Add a new Pet ***");
        Response response = PetEndpoints.addNewPet(pet);
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(2)
    public void testReadPet() {
        logger.info("*** Test Retrieve a Pet ***");
        Response response = PetEndpoints.readPetById(pet.getId());
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(3)
    public void testUpdatePet() {
        logger.info("*** Test Update a Pet ***");
        pet.setName(faker.name().firstName());
        Response response = PetEndpoints.UpdatePet(pet.getId(), pet.getName(), pet.getStatus());
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(4)
    public void testDeletePet() {
        logger.info("*** Test Delete a Pet ***");
        Response response = PetEndpoints.deletePet(pet.getId());
        response.then().assertThat().statusCode(200);
    }
}
