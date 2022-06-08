package com.switchfully.evolveandgo.lmsbackend.register;

import com.switchfully.evolveandgo.lmsbackend.register.dto.RegisterStudentDto;
import com.switchfully.evolveandgo.lmsbackend.register.dto.RegisterValidDto;
import com.switchfully.evolveandgo.lmsbackend.register.exception.PasswordsDoNotMatchException;
import com.switchfully.evolveandgo.lmsbackend.user.exception.StudentEmailAlreadyExistsException;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.StudentJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.user.student.service.StudentService;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class RegisterControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Autowired
    private StudentService studentService;

    @Test
    void givenRegisterStudentDto_whenRegister_thenNewStudentCreated() {
        // Every time after running this test remove user in keycloak

        RegisterStudentDto expected = new RegisterStudentDto("Pizza", "pizza@hawai.com", "Ruben123!", "Ruben123!");

        RestAssured
                .given()
                .contentType(JSON)
                .body(expected)
                .when()
                .port(port)
                .post("/register")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value());


        Assertions.assertThat(studentJpaRepository.findByEmail(expected.getEmail()).isPresent()).isTrue();
        Student actual = studentJpaRepository.findByEmail(expected.getEmail()).get();

        Assertions.assertThat(actual.getDisplayName()).isEqualTo(expected.getDisplayName());
        Assertions.assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        Assertions.assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
    }

    @Test
    void givenRegisterStudentDto_whenPasswordsNotTheSame_thenExceptionThrown() {

        String pass1 = "Ruben123!";
        String pass2 = "Ruben124!";

        RegisterStudentDto expected = new RegisterStudentDto("Pizza", "pizza@hawai.com", pass1, pass2);

        RestAssured
                .given()
                .contentType(JSON)
                .body(expected)
                .when()
                .port(port)
                .post("/register")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        Throwable thrown = Assertions.catchThrowable(() -> studentService.registerStudent(expected));

        //THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(PasswordsDoNotMatchException.class)
                .hasMessage("Passwords do not match");

    }

    @Test
    void givenRegisterStudentDto_whenDisplayNameEmpty_thenBadRequest() {

        String pass1 = "Ruben123!";
        String pass2 = "Ruben123!";

        RegisterStudentDto expected = new RegisterStudentDto("", "pizza@hawai.com", pass1, pass2);

        RestAssured
                .given()
                .contentType(JSON)
                .body(expected)
                .when()
                .port(port)
                .post("/register")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void givenRegisterStudentDto_whenPasswordFormatIncorrect_thenBadRequest() {

        String pass1 = "zzzzzzzz";
        String pass2 = "zzzzzzzz";

        RegisterStudentDto expected = new RegisterStudentDto("Pizza", "pizza@hawai.com", pass1, pass2);

        RestAssured
                .given()
                .contentType(JSON)
                .body(expected)
                .when()
                .port(port)
                .post("/register")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());


    }

    @Test
    void givenRegisterStudentDto_whenRepeatPasswordBlank_thenBadRequest() {

        RegisterStudentDto expected = new RegisterStudentDto("Pizza", "pizza@hawai.com", "Ruben123!", "  ");

        RestAssured
                .given()
                .contentType(JSON)
                .body(expected)
                .when()
                .port(port)
                .post("/register")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void givenRegisterStudentDto_whenEmailAlreadyExists_ThenThrowException(){
        RegisterStudentDto expected = new RegisterStudentDto("Spaghetti", "rinaldo@spaghetto.be","Ruben123!", "Ruben123!");

        RegisterValidDto registerValidDto = RestAssured
                .given()
                .contentType(JSON)
                .body(expected)
                .when()
                .port(port)
                .post("/register")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RegisterValidDto.class);

        Assertions.assertThat(registerValidDto.isEmailUnique()).isFalse();
    }
}