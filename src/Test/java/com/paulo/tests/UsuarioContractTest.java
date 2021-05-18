package com.paulo.tests;

import com.paulo.config.Configuracoes;
import com.paulo.factory.UsuarioDataFactory;
import com.paulo.pojo.Usuario;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UsuarioContractTest {

    @Before
    public void setUp(){
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);

        baseURI = configuracoes.baseURI();
        basePath = configuracoes.basePath();
    }

    @Test
    public void testCadastrarUsuarioValidaContrato() throws IOException {

        Usuario usuario = UsuarioDataFactory.criarUsuario();

        given()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/user")
        .then()
                .log()
                    .all()
                        .assertThat()
                            .statusCode(200)
                                .body(matchesJsonSchemaInClasspath("schemas/postV2UserValidaCadastro.json"));
    }
}
