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
        this.faker = new Faker();
        this.pet = new Pet();
        Category category = new Category();
        category.setId(this.faker.number().numberBetween(1, 100));
        category.setName(this.faker.name().fullName());

        Tag tag = new Tag();
        tag.setId(this.faker.number().numberBetween(1, 100));
        tag.setName(this.faker.name().username());

        this.pet.setId(this.faker.number().numberBetween(1, 10));
        this.pet.setName(this.faker.name().firstName());
        this.pet.setStatus(
                faker.options().option("available", "pending", "sold")
        );
        this.pet.setCategory(category);
        this.pet.setPhotoUrls(List.of(this.faker.internet().image(), this.faker.internet().image()));
        this.pet.setTags(List.of(tag));
        this.logger = LogManager.getLogger(this.getClass());
    }

    @Test
    @Order(1)
    public void testCreatePet() {
        this.logger.info("*** Test Add a new Pet ***");
        Response response = PetEndpoints.addNewPet(this.pet);
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(2)
    public void testReadPet() {
        this.logger.info("*** Test Retrieve a Pet ***");
        Response response = PetEndpoints.readPetById(this.pet.getId());
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(3)
    public void testUpdatePet() {
        this.logger.info("*** Test Update a Pet ***");
        this.pet.setName(this.faker.name().firstName());
        Response response = PetEndpoints.UpdatePet(this.pet.getId(), this.pet.getName(), this.pet.getStatus());
        response.then().assertThat().statusCode(200);
    }

    @Test
    @Order(4)
    public void testDeletePet() {
        this.logger.info("*** Test Delete a Pet ***");
        Response response = PetEndpoints.deletePet(this.pet.getId());
        response.then().assertThat().statusCode(200);
    }
}
