package com.switchfully.evolveandgo.lmsbackend.register;

import com.switchfully.evolveandgo.lmsbackend.register.dto.RegisterStudentDto;
import com.switchfully.evolveandgo.lmsbackend.register.exception.PasswordsDoNotMatchException;
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


        Assertions.assertThat(studentJpaRepository.findByEmail(expected.email()).isPresent()).isTrue();
        Student actual = studentJpaRepository.findByEmail(expected.email()).get();

        Assertions.assertThat(actual.getDisplayName()).isEqualTo(expected.displayName());
        Assertions.assertThat(actual.getEmail()).isEqualTo(expected.email());
        Assertions.assertThat(actual.getPassword()).isEqualTo(expected.password());
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
}