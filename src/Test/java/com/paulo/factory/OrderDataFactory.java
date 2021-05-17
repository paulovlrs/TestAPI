package com.paulo.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulo.pojo.Order;
import com.paulo.pojo.Pet;

import java.io.FileInputStream;
import java.io.IOException;

public class OrderDataFactory {
    public static Order criarPedido() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = objectMapper.readValue(
                new FileInputStream("src/Test/resources/requestBody/postV2StoreOrder.json"), Order.class);
        return order;
    }

    public static Order criarPedidoDoAnimalEscolhido(Long id) throws IOException {
        Order order = criarPedido();
        order.setPetId(id);
        return order;
    }
}
