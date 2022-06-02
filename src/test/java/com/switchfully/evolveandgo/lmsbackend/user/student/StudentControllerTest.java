package com.switchfully.evolveandgo.lmsbackend.user.student;

import com.switchfully.evolveandgo.lmsbackend.user.student.dto.StudentDto;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class StudentControllerTest {
    @LocalServerPort
    private int port;


    @Test
    void givenId_whenProfileAsked_thenStudentDtoReturned() {
        //GIVEN
        Long id = 1L;

        //WHEN
        StudentDto studentDto = RestAssured
                .given()
                .contentType(JSON)
                .when()
                .port(port)
                .get("/students/"+id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(StudentDto.class);

        //THEN
        Assertions.assertThat(studentDto.getDisplayName()).isEqualTo("Kamiel");
        Assertions.assertThat(studentDto.getEmail()).isEqualTo("kamiel@mail.com");
    }
}