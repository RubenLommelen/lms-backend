package com.switchfully.evolveandgo.lmsbackend.login;

import com.switchfully.evolveandgo.lmsbackend.login.dto.LoginDto;
import com.switchfully.evolveandgo.lmsbackend.login.service.LoginService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LoginControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private LoginService loginService;


    @Test
    void givenLoginDto_whenLoggedIn_thenStatusCodeCreated() {
        LoginDto loginDto = new LoginDto("student@mail.com", "pwd");

        RestAssured.given()
                .port(port)
                .body(loginDto)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void givenLoginDto_whenLoggedInWithWrongUsername_thenUnauthorized() {
        LoginDto loginDto = new LoginDto("Rinaldo", "pwd");

        RestAssured.given()
                .port(port)
                .body(loginDto)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void givenLoginDto_whenLoggedInWithWrongPassword_thenUnauthorized() {
        LoginDto loginDto = new LoginDto("student@mail.com", "pdwwwwww");

        RestAssured.given()
                .port(port)
                .body(loginDto)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void givenLoginDto_whenLoggedInWithEmptyUsername_thenUnauthorized() {
        LoginDto loginDto = new LoginDto("", "pwd");

        RestAssured.given()
                .port(port)
                .body(loginDto)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenLoginDto_whenLoggedInWithEmptyPassword_thenUnauthorized() {
        LoginDto loginDto = new LoginDto("student@mail.com", "");

        RestAssured.given()
                .port(port)
                .body(loginDto)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenLoginDto_whenLoggedInWithUsernameNull_thenUnauthorized() {
        LoginDto loginDto = new LoginDto(null, "pwd");

        RestAssured.given()
                .port(port)
                .body(loginDto)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenLoginDto_whenLoggedInWithPasswordNull_thenUnauthorized() {
        LoginDto loginDto = new LoginDto("student@mail.com", null);

        RestAssured.given()
                .port(port)
                .body(loginDto)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenLoginDto_whenLoggedInWithBlankUsername_thenUnauthorized() {
        LoginDto loginDto = new LoginDto("  ", "pwd");

        RestAssured.given()
                .port(port)
                .body(loginDto)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void givenLoginDto_whenLoggedInWithBlankPassword_thenUnauthorized() {
        LoginDto loginDto = new LoginDto("student@mail.com", "  ");

        RestAssured.given()
                .port(port)
                .body(loginDto)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/login")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


}