package com.paulo.tests;
import com.paulo.config.Configuracoes;
import com.paulo.factory.UsuarioDataFactory;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.aeonbits.owner.ConfigFactory;
import com.paulo.pojo.Usuario;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class UsuarioTest {

    @Before
    public void setUp(){
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);

        baseURI = configuracoes.baseURI();
        basePath = configuracoes.basePath();
    }

    @Test
    public void testCadastrarUsuarioRetornoStatus200() throws IOException {
        //https://petstore.swagger.io/v2/user
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
                            .statusCode(200);
    }
}
