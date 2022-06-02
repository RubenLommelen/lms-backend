package com.switchfully.evolveandgo.lmsbackend.coach;

import com.switchfully.evolveandgo.lmsbackend.coach.dto.CoachDto;
import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentCodelabProgressDto;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class CoachControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void givenId_whenProfileAsked_thenCoachDtoReturned() {
        //GIVEN
        Long id = 1L;

        //WHEN
        CoachDto actualCoach = RestAssured
                .given()
                .contentType(JSON)
                .when()
                .port(port)
                .get("/coachs/"+id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(CoachDto.class);

        //THEN
        Assertions.assertThat(actualCoach.getDisplayName()).isEqualTo("Oscar");
        Assertions.assertThat(actualCoach.getEmail()).isEqualTo("oscar@mail.com");
    }

}