package com.paulo.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paulo.pojo.Pet;
import com.paulo.pojo.Pet;

import java.io.FileInputStream;
import java.io.IOException;

public class PetDataFactory {
    public static Pet criarPet() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Pet pet = objectMapper.readValue(
                new FileInputStream("src/Test/resources/requestBody/postV2Pet.json"), Pet.class);
        return pet;
    }
}
