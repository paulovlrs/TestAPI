package com.tests;

import com.config.Configuracoes;
import com.pojo.Pet;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;

public class PetTest {

    private String idPet;
    private Long id;

    @Before
    public void setUp() {
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);

        baseURI = configuracoes.baseURI();
        basePath = configuracoes.basePath();
    }

    @Test
    public void testCadastrarPetRetornoStatus200() throws IOException {
        Pet pet = new Pet();

        pet.SetAllValues();
        String bodyJson = pet.returnJsonBodyPet();

        given()
            .contentType(ContentType.JSON)
            .body(bodyJson)
        .when()
            .post("/pet")
        .then()
            .log()
                .all()
                    .assertThat()
                    .statusCode(200);
    }

    @Test
    public void testUdatePetRetornoStatus200() throws IOException {
        Pet pet = new Pet();

        pet.SetAllValues();
        String bodyJson = pet.returnJsonBodyPet();

        idPet =
                given()
                        .contentType(ContentType.JSON)
                        .body(bodyJson)
                .when()
                        .post("/pet")
                .then()
                        .log()
                        .all()
                        .assertThat()
                        .statusCode(200)
                        .extract()
                            .jsonPath()
                            .getString("id");

        id = new Long(idPet);
        pet.setId(id);
        pet.setStatus("unavailable");

        bodyJson = pet.returnJsonBodyPet();

        given()
                .contentType(ContentType.JSON)
                .body(bodyJson)
        .when()
                .post("/pet")
        .then()
                .log()
                    .all()
                        .assertThat()
                        .statusCode(200);
    }
}
