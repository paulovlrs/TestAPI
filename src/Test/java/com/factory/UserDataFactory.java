package com.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pojo.User;

import java.io.FileInputStream;
import java.io.IOException;

public class UserDataFactory {

    public static User objectMapperUser() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(
                new FileInputStream("src/Test/resources/requestBody/postV2User.json"), User.class);
        return user;
    }
}
