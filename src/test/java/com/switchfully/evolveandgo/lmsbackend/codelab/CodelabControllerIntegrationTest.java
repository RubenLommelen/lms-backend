package com.switchfully.evolveandgo.lmsbackend.codelab;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.Codelab;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.StudentCodelabProgressJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.progress.dto.ProgressOverviewDto;
import com.switchfully.evolveandgo.lmsbackend.progress.service.ProgressMapper;
import com.switchfully.evolveandgo.lmsbackend.student.domain.StudentJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.StudentCodelabProgressJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.codelab.dto.CodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.StudentCodelabProgressJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.student.domain.StudentJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentCodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.codelab.service.CodelabService;
import com.switchfully.evolveandgo.lmsbackend.student.exception.StudentNotFoundException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class CodelabControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CodelabService codelabService;
    @Autowired
    private CodelabJpaRepository codelabJpaRepository;

    @Autowired
    private StudentCodelabProgressJpaRepository studentCodelabProgressJpaRepository;

    @Autowired
    private ProgressMapper progressMapper;

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Test
    void whenGetAllCodelabsForStudentId_thenCodelabsReturns() {
        //GIVEN
        List<StudentCodelabProgressDto> expectedCodelabs = codelabService.getCodelabsForStudent(1L);
        Long studentId = 1L;


        //WHEN
        List<StudentCodelabProgressDto> actualCodelabs = RestAssured
                .given()
                .contentType(JSON)
                .when()
                .port(port)
                .get("/students/" + studentId + "/codelabs")
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


    @Test
    void whenProgressUpdatePosted_thenProgressIsSaved() {
        //GIVEN

        Codelab codelab1 = codelabJpaRepository.findById(1L).get();
        Codelab codelab2 = codelabJpaRepository.findById(2L).get();
        Long studentId = 1L;
        CodelabProgressDto codelabProgressDto1 = new CodelabProgressDto(studentId, CodelabProgress.BUSY, codelab1.getId(), codelab1.getName());
        CodelabProgressDto codelabProgressDto2 = new CodelabProgressDto(studentId, CodelabProgress.STUCK, codelab2.getId(), codelab2.getName());
        List<CodelabProgressDto> codelabProgressDtoList = List.of(
                codelabProgressDto1,
                codelabProgressDto2
        );

        //WHEN
        RestAssured.given()
                .port(port)
                .body(codelabProgressDtoList)
                .contentType(ContentType.JSON)
                .when()
                .accept(ContentType.JSON)
                .post("/students/" + studentId + "/codelabs")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());

        //THEN
        List<StudentCodelabProgressDto> codelabsForStudent = codelabService.getCodelabsForStudent(studentId);
        Map<Long,StudentCodelabProgressDto> actualProgressList = codelabsForStudent.stream()
                .filter(studentCodelabProgressDto -> studentCodelabProgressDto.getCodelabId().equals(codelab1.getId()) || studentCodelabProgressDto.getCodelabId().equals(codelab2.getId()))
                .collect(Collectors.toMap(value->value.getCodelabId(),value->value));



        Assertions.assertThat(actualProgressList.get(1L).getProgress()).isEqualTo(codelabProgressDto1.getProgress());
        Assertions.assertThat(actualProgressList.get(2L).getProgress()).isEqualTo(codelabProgressDto2.getProgress());
    }

    @Test
    void given_whenGetCodelabProgressForAllStudents_thenProgressReturned() {
        List<ProgressOverviewDto> expected = progressMapper.toDtoList(studentCodelabProgressJpaRepository.findProgressOverview());

        // WHEN
        List<ProgressOverviewDto> actual = RestAssured
                .given()
                .contentType(JSON)
                .when()
                .port(port)
                .get("/progress")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", ProgressOverviewDto.class);

        // THEN
        Assertions.assertThat(actual).containsAll(expected);
    }
}