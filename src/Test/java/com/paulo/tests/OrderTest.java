package com.paulo.tests;

import com.paulo.config.Configuracoes;
import com.paulo.factory.OrderDataFactory;
import com.paulo.pojo.Order;
import com.paulo.pojo.Pet;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;

public class OrderTest {
    private String idPet;
    private String idOrder;

    @Before
    public void setUp(){
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);

        baseURI = configuracoes.baseURI();
        basePath = configuracoes.basePath();
    }

    @Test
    public void testCadastrarPedidoRetornoStatus200() throws IOException {
        // https://petstore.swagger.io/v2/store/order
        Order order = OrderDataFactory.criarPedido();

        given()
                .contentType(ContentType.JSON)
                .body(order)
        .when()
                .post("/store/order")
        .then()
                .log()
                    .all()
                        .assertThat()
                        .statusCode(200);
    }

    @Test
    public void testCadastrarPedidoComAnimalEscolhidoRetornoStatus200() throws IOException {

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
                    .   all()
                    .assertThat()
                        .statusCode(200)
                    .extract()
                        .jsonPath().getString("id");

        Long id = new Long(idPet);

        Order order = OrderDataFactory.criarPedidoDoAnimalEscolhido(id);

        given()
                .contentType(ContentType.JSON)
                .body(order)
        .when()
                .post("/store/order")
        .then()
                .log()
                    .all()
                        .assertThat()
                        .statusCode(200);
    }

    @Test
    public void testConsultarVendaRetornoStatus200() throws IOException {

        Pet pet = new Pet();

        pet.SetAllValues();
        String bodyJson = pet.returnJsonBodyPet();

        // Cadastro Pet
        idPet =
                given()
                        .contentType(ContentType.JSON)
                        .body(bodyJson)
                        .when()
                        .post("/pet")
                        .then()
                        .log()
                        .   all()
                        .assertThat()
                        .statusCode(200)
                        .extract()
                        .jsonPath().getString("id");

        Long id = new Long(idPet);

        Order order = OrderDataFactory.criarPedidoDoAnimalEscolhido(id);

        // Pedido de venda do Pet
        idOrder =
        given()
                .contentType(ContentType.JSON)
                .body(order)
        .when()
                .post("/store/order")
        .then()
                .log()
                .all()
                    .assertThat()
                    .statusCode(200)
                        .extract()
                        .jsonPath().getString("id");

        // Consulta status pedido de venda
        given()
                .contentType(ContentType.JSON)
                .body("/store/order/1")
        .when()
                .get("/store/order/" + idOrder)
        .then()
                .log()
                    .all()
                        .assertThat()
                        .statusCode(200);
    }
}
