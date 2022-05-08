package com.tests;

import com.auxiliary.RandomAux;
import com.auxiliary.UserAux;
import com.config.Configuracoes;
import com.factory.UserDataFactory;

import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.aeonbits.owner.ConfigFactory;
import com.pojo.User;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserTest {

    protected String request = "/user/";

    @Before
    public void setUp() {
        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);

        baseURI = configuracoes.baseURI();
        basePath = configuracoes.basePath();
    }

    @Test
    public void testCreateUserSuccess() throws IOException {

        User user = UserDataFactory.objectMapperUser();
        String randValue = RandomAux.rndCaracterAndNumber(10);

        // Altero algumas informações
        user.setUsername(user.getUsername() + randValue);
        user.setFirstName(user.getFirstName() + randValue);
        user.setLastName(user.getLastName() + randValue);
        user.setEmail(randValue + user.getEmail());
        user.setPassword(randValue);
        user.setPhone(user.getPhone() + RandomAux.rndNumber(8));

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(user)
                .post(request)
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetByUserName() throws IOException {

        User user = UserDataFactory.objectMapperUser();
        String randValue = RandomAux.rndCaracterAndNumber(10);

        // Altero algumas informações
        user.setUsername(user.getUsername() + randValue);
        user.setFirstName(user.getFirstName() + randValue);
        user.setLastName(user.getLastName() + randValue);
        user.setEmail(randValue + user.getEmail());
        user.setPassword(randValue);
        user.setPhone(user.getPhone() + RandomAux.rndNumber(8));

        // Crio o usuário para ser pesquisado
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(user)
                .post(request)
                .then()
                .statusCode(200);

        // Procuro o usuário recém criado
        given()
            .contentType(ContentType.JSON)
        .when()
            .get(request + user.getUsername())
        .then()
            .statusCode(200);
    }

   // Não foi possível obter o status 400, apenas 200, 404 e 405,
    // mas na documentação do swagger é validado apenas 200, 400 e 404

    @Test
    public void testGetByUserNameNotFound(){
        // Procuro um usuário inexistente
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(request + RandomAux.rndCaracterAndNumber(30))
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void testUpdateUser() throws IOException {
        User user = UserDataFactory.objectMapperUser();
        String randValue = RandomAux.rndCaracterAndNumber(15);

        // Altero algumas informações que estavam no JSON
        user.setUsername(user.getUsername() + randValue);
        user.setFirstName(user.getFirstName() + randValue);
        user.setLastName(user.getLastName() + randValue);
        user.setEmail(randValue + user.getEmail());
        user.setPassword(randValue);
        user.setPhone(user.getPhone() + RandomAux.rndNumber(8));

        // Crio o usuário para ser pesquisado
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(user)
                .post(request)
                .then()
                .statusCode(200);

        // Recupero o Id do usuário pela pesquisa
        user.setId(given()
                .contentType(ContentType.JSON)
                .when()
                .get(request + user.getUsername())
                .then()
                .extract().path("id"));
        // Informo os novos valores que serão atualizados
        user.setFirstName(user.getFirstName() + " - Editado");
        user.setLastName(user.getLastName() + " - Editado");
        user.setEmail("editado." + user.getEmail());

        // Atualizo o usuário
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(user)
                .put(request + user.getUsername())
                .then()
                .statusCode(200);
    }

    // To Do: Bug API não responde aos status 404
    @Test
    public void testUpdateUserNotFound() throws IOException {
        User user = UserDataFactory.objectMapperUser();

        // Atualizo o usuário não existente
        given()
            .contentType(ContentType.JSON)
        .when()
            .body(user)
            .put(request + RandomAux.rndCaracterAndNumber(15))
        .then()
            .statusCode(404);
    }

    // To Do: Bug API não responde aos status 400
    @Test
    public void testUpdateUserInvalid() throws IOException {
        User user = UserDataFactory.objectMapperUser();

        // Atualizo o usuário não existente
        given()
            .contentType(ContentType.JSON)
        .when()
            .body(user)
            .put(request)// Não informo o username para ser atualizado
        .then()
            .statusCode(400);
    }

    @Test
    public void testDeleteUser() throws IOException {
        // Crio usuário e recupero userName
        String userName = UserAux.CriarUsuario();

        // Deleto o usuário
        given()
            .contentType(ContentType.JSON)
        .when()
            .delete(request + userName)
        .then()
            .statusCode(200);

        // Verifico se o usuário ainda existe
        UserAux.VerificaSeUsuarioNaoExiste(userName);
    }

    @Test
    public void testDeleteUserNotFound(){

        // Deleto o usuário não existente
        given()
            .contentType(ContentType.JSON)
        .when()
            .delete(request + RandomAux.rndCaracterAndNumber(20))
        .then()
            .statusCode(404);
    }

    // To Do: Bug API não responde aos status 400
    @Test
    public void testDeleteUserInvalid(){
        // Deleto o usuário não existente
        given()
            .contentType(ContentType.JSON)
        .when()
            .delete(request)
        .then()
            .statusCode(400);
    }

    @Test
    public void testCreateListUser() throws IOException {
        List<User> users = new ArrayList<>();

        // Quantidade de usuários que serão criados
        for (int i = 0; i < 5; i++) {
            // Adiciono o usuário a lista
            users.add(UserAux.ObjectUser());
        }

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(users)
                .post(request + "createWithList")
                .then()
                .statusCode(200);

        // Verifica se os usuários foram criados
        for (int i = 0; i < 5; i++) {
            UserAux.VerificaSeUsuarioExiste(users.get(i).getUsername());
        }
    }

    @Test
    public void testCreateArrayUser() throws IOException {
        List<User> users = new ArrayList<>();

        // Quantidade de usuários que serão criados
        for (int i = 0; i < 5; i++) {
            // Adiciono o usuário a lista
            users.add(UserAux.ObjectUser());
        }

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(users)
                .post(request + "createWithArray")
                .then()
                .statusCode(200);

        // Verifica se os usuários foram criados
        for (int i = 0; i < 5; i++) {
            UserAux.VerificaSeUsuarioExiste(users.get(i).getUsername());
        }
    }

    @Test
    public void testLogin() throws IOException {
        // Crio usuário e recupero o userName
        String userName = UserAux.CriarUsuario();
        // Pesquiso usuário e recupero a response
        JsonPath response = UserAux.RetornaResponseGetUsuario(userName);

        // Realizo o login de usuário
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(request + "login?username=" + userName
                        + "&password=" + response.get("password").toString())
                .then()
                .statusCode(200);
    }

    // To Do: Bug API não responde aos status 400
    @Test
    public void testLoginInvalidUserNamePassword() throws IOException {

        // Realizo o login de usuário
        given()
            .contentType(ContentType.JSON)
        .when()
            .get(request + "login?username=&password=")
        .then()
            .statusCode(400);
    }
}
