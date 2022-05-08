package com.tests;

import com.config.Configuracoes;
import com.factory.OrderDataFactory;
import com.pojo.Order;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class OrderContractTest {

    @Before
    public void setUp(){
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);

        baseURI = configuracoes.baseURI();
        basePath = configuracoes.basePath();
    }

    @Test
    public void testCadastrarPedidoContrato() throws IOException {
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
                        .statusCode(200)
                            .body(matchesJsonSchemaInClasspath("schemas/postV2StoreOrderValidaCadastro.json"));
    }

    @Test
    public void testConsultarVendaContrato() throws IOException {

        // Consulta status pedido de venda
        given()
                .contentType(ContentType.JSON)
                .body("/store/order/1")
        .when()
                .get("/store/order/1")
        .then()
                .log()
                    .all()
                        .assertThat()
                        .statusCode(200)
                          .body(matchesJsonSchemaInClasspath("schemas/getV2StoreOrderValidaConsulta.json"));
    }
}
