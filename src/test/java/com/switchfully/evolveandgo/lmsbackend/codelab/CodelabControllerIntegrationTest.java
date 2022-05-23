package com.switchfully.evolveandgo.lmsbackend.codelab;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.Codelab;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.codelab.dto.StudentCodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.codelab.service.CodelabService;
import com.switchfully.evolveandgo.lmsbackend.codelab.service.StudentNotFoundException;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class CodelabControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CodelabService codelabService;

    @Test
    void whenGetAllCodelabsForStudentId_thenCodelabsReturns() {
        //GIVEN
        List<StudentCodelabProgressDto> expectedCodelabs = List.of(
                new StudentCodelabProgressDto(CodelabProgress.DONE, "Rinaldo"),
                new StudentCodelabProgressDto(CodelabProgress.BUSY, "Ronaldo")
        );
        //WHEN
        List<StudentCodelabProgressDto> actualCodelabs = RestAssured
                .given()
                .contentType(JSON)
                .when()
                .port(port)
                .get("/students/1/codelabs")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", StudentCodelabProgressDto.class);

        //THEN
        Assertions.assertThat(actualCodelabs).containsAll(expectedCodelabs);
    }

    @Test
    void givenStudentIdThatDoesNotExist_whenGetAllCodelabsForStudentId_thenNotFoundIsReturned() {
        //GIVEN
        Long studentId = 999999999999999999L;

        //WHEN

        RestAssured
                .given()
                .contentType(JSON)
                .when()
                .port(port)
                .get("/students/" + studentId + "/codelabs")
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

        Throwable thrown = Assertions.catchThrowable(() -> codelabService.getCodelabsForStudent(studentId));

        //THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessage("No student found for: " + studentId);
    }
}