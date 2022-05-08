package com.tests;

import com.config.Configuracoes;
import com.pojo.Pet;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class PetContractTest {

    private String idPet;
    private Long id;

    @Before
    public void setUp() {
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);

        baseURI = configuracoes.baseURI();
        basePath = configuracoes.basePath();
    }

    @Test
    public void testCadastrarPetContrato() throws IOException {
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
                    .statusCode(200)
                        .body(matchesJsonSchemaInClasspath("schemas/postV2PetValidaCadastro.json"));
    }
}
