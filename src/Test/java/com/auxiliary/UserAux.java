package com.auxiliary;

import com.factory.UserDataFactory;
import com.pojo.User;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class UserAux {
    protected static String request = "/user/";

    public static void VerificaSeUsuarioExiste(String userName) {

        // Pesquiso se o usuário está cadastrado na base
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(request + userName)
                .then()
                .statusCode(200);
    }

    public static void VerificaSeUsuarioNaoExiste(String userName) {

        // Pesquiso se o usuário não está cadastrado na base
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(request + userName)
                .then()
                .statusCode(404);
    }

    public static JsonPath RetornaResponseGetUsuario(String userName) {

        JsonPath jsonPath = given()
                .contentType(ContentType.JSON)
                .when()
                .get(request + userName)
                .then()
                .statusCode(200)
                .extract().jsonPath();

        return jsonPath;
    }

    public static String CriarUsuario() throws IOException {
        User user = UserDataFactory.objectMapperUser();
        String randValue = RandomAux.rndCaracterAndNumber(15);

        // Altero algumas informações que estavam no JSON
        user.setUsername(user.getUsername() + randValue);
        user.setFirstName(user.getFirstName() + randValue);
        user.setLastName(user.getLastName() + randValue);
        user.setEmail(randValue + user.getEmail());
        user.setPassword(randValue);
        user.setPhone(user.getPhone() + RandomAux.rndNumber(8));

        // Crio o usuário
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(user)
                .post(request)
                .then()
                .statusCode(200);
        return user.getUsername();
    }

    public static User ObjectUser() throws IOException {
        User user = UserDataFactory.objectMapperUser();
        String randValue = RandomAux.rndCaracterAndNumber(15);

        // Altero algumas informações que estavam no JSON
        user.setUsername(user.getUsername() + randValue);
        user.setFirstName(user.getFirstName() + randValue);
        user.setLastName(user.getLastName() + randValue);
        user.setEmail(randValue + user.getEmail());
        user.setPassword(randValue);
        user.setPhone(user.getPhone() + RandomAux.rndNumber(8));

        // Crio o usuário
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(user)
                .post(request)
                .then()
                .statusCode(200);

        return user;
    }
}
