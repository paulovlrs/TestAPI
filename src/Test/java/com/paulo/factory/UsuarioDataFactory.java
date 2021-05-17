package com.paulo.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulo.pojo.Usuario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UsuarioDataFactory {

    public static Usuario criarUsuario() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Usuario usuario = objectMapper.readValue(
                new FileInputStream("src/Test/resources/requestBody/postV2User.json"), Usuario.class);
        return usuario;
    }
}
