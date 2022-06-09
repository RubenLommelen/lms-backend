package com.switchfully.evolveandgo.lmsbackend.progress;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.Codelab;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.progress.domain.ProgressState;
import com.switchfully.evolveandgo.lmsbackend.progress.domain.StudentCodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.progress.domain.StudentCodelabProgressJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.progress.dto.*;
import com.switchfully.evolveandgo.lmsbackend.progress.exception.InvalidProgressException;
import com.switchfully.evolveandgo.lmsbackend.progress.service.ProgressMapper;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.StudentJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.progress.service.ProgressService;
import com.switchfully.evolveandgo.lmsbackend.user.exception.UserNotFoundException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.http.ContentType.JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class ProgressControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProgressService progressService;
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
        List<StudentCodelabProgressDto> expectedCodelabs = progressService.getCodelabsForStudent(1L);
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

        Throwable thrown = Assertions.catchThrowable(() -> progressService.getCodelabsForStudent(studentId));

        //THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("No user found for: " + studentId);
    }


    @Test
    void whenProgressUpdatePosted_thenProgressIsSaved() {
        //GIVEN

        Codelab codelab1 = codelabJpaRepository.findById(1L).get();
        Codelab codelab2 = codelabJpaRepository.findById(2L).get();
        Long studentId = 1L;
        SaveStudentCodelabProgressDto codelabProgressDto1 = new SaveStudentCodelabProgressDto(studentId, ProgressState.BUSY, codelab1.getId(), codelab1.getName());
        SaveStudentCodelabProgressDto codelabProgressDto2 = new SaveStudentCodelabProgressDto(studentId, ProgressState.STUCK, codelab2.getId(), codelab2.getName());
        List<SaveStudentCodelabProgressDto> codelabProgressDtoList = List.of(
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
        List<StudentCodelabProgressDto> codelabsForStudent = progressService.getCodelabsForStudent(studentId);
        Map<Long, StudentCodelabProgressDto> actualProgressList = codelabsForStudent.stream()
                .filter(studentCodelabProgressDto -> studentCodelabProgressDto.getCodelabId().equals(codelab1.getId()) || studentCodelabProgressDto.getCodelabId().equals(codelab2.getId()))
                .collect(Collectors.toMap(value -> value.getCodelabId(), value -> value));


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

    @Test
    void givenNewlyCreatedCodelab_whenGetAllCodelabs_thenCodelabsSortedByAscendingCreationDate() {
        //GIVEN
        Long studentId = 1L;
        Long eldestCodelabId = 99999998L;
        Long mostRecentCodelabId = 99999999L;

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
        System.out.println(actualCodelabs);
        Assertions.assertThat(actualCodelabs.get(actualCodelabs.size() - 1).getCodelabId()).isEqualTo(mostRecentCodelabId);
        Assertions.assertThat(actualCodelabs.get(0).getCodelabId()).isEqualTo(eldestCodelabId);

    }

    @Nested
    class CommentTest{
        @Test
        void givenCodelabCommentDto_whenSaved_thenCommentAddedToDataBase() {
            Long studentId = 1L;
            Long codelabId = 1L;
            CodelabCommentDto codelabCommentDto = new CodelabCommentDto("Codelab 1 is hard", "https://www.donuts.com/delicious");

                    RestAssured.given()
                            .port(port)
                            .body(codelabCommentDto)
                            .contentType(ContentType.JSON)
                            .when()
                            .accept(ContentType.JSON)
                            .post("/students/"+studentId+"/codelabs/"+codelabId+"/comments")
                            .then()
                            .assertThat()
                            .statusCode(HttpStatus.CREATED.value());

            StudentCodelabProgress studentCodelabProgress = studentCodelabProgressJpaRepository.findByCodelabIdAndStudentId(studentId, codelabId);
            Assertions.assertThat(studentCodelabProgress.getComment()).isEqualTo(codelabCommentDto.getCodelabComment());
            Assertions.assertThat(studentCodelabProgress.getCodelab().getName()).isEqualTo("Variables");
        }

        @Test
        void givenCodelabCommentDto_whenNoCombinationOfCodelabIdAndStudentIdFound_thenAddProgressWithComment() {
            Long studentId = 1L;
            Long codelabId = 99999999L;
            CodelabCommentDto codelabCommentDto = new CodelabCommentDto("Codelab 1 is hard", null);

            RestAssured.given()
                    .port(port)
                    .body(codelabCommentDto)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/students/"+studentId+"/codelabs/"+codelabId+"/comments")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value());

            StudentCodelabProgress studentCodelabProgress = studentCodelabProgressJpaRepository.findByCodelabIdAndStudentId(codelabId, studentId);
            Assertions.assertThat(studentCodelabProgress.getComment()).isEqualTo(codelabCommentDto.getCodelabComment());

        }
    }

    @Nested
    class CommentAndSolutionUrlTest{
        @Test
        void givenCodelabCommentDto_whenSaved_thenCommentAndSolutionUrlAddedToDataBase() {
            Long studentId = 1L;
            Long codelabId = 1L;
            CodelabCommentDto codelabCommentDto = new CodelabCommentDto("Codelab 1 is hard", "https://www.donuts.com/delicious");

            RestAssured.given()
                    .port(port)
                    .body(codelabCommentDto)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/students/"+studentId+"/codelabs/"+codelabId+"/comments")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value());

            StudentCodelabProgress studentCodelabProgress = studentCodelabProgressJpaRepository.findByCodelabIdAndStudentId(studentId, codelabId);
            Assertions.assertThat(studentCodelabProgress.getComment()).isEqualTo(codelabCommentDto.getCodelabComment());
            Assertions.assertThat(studentCodelabProgress.getSolutionUrl()).isEqualTo(codelabCommentDto.getCodelabSolutionUrl());
        }

        @Test
        void givenCodelabCommentDto_whenNoCombinationOfCodelabIdAndStudentIdFound_thenAddProgressWithComment() {
            Long studentId = 1L;
            Long codelabId = 99999999L;
            CodelabCommentDto codelabCommentDto = new CodelabCommentDto("Codelab 1 is hard", null);

            RestAssured.given()
                    .port(port)
                    .body(codelabCommentDto)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/students/"+studentId+"/codelabs/"+codelabId+"/comments")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value());

            StudentCodelabProgress studentCodelabProgress = studentCodelabProgressJpaRepository.findByCodelabIdAndStudentId(codelabId, studentId);
            Assertions.assertThat(studentCodelabProgress.getComment()).isEqualTo(codelabCommentDto.getCodelabComment());
            Assertions.assertThat(studentCodelabProgress.getSolutionUrl()).isEqualTo(codelabCommentDto.getCodelabSolutionUrl());


        }

//        removed due to a bug not allowing a codelab that went from done to incomplete status to save a comment
//        @Test
//        void givenNotCompletedCodelab_WhenSaveSolution_ThenErrorIsThrown() {
//            Long studentId = 1L;
//            Long codelabId = 99999999L;
//            CodelabCommentDto codelabCommentDto = new CodelabCommentDto("Codelab 1 is hard", "https://www.donuts.com/delicious");
//
//            RestAssured.given()
//                    .port(port)
//                    .body(codelabCommentDto)
//                    .contentType(ContentType.JSON)
//                    .when()
//                    .accept(ContentType.JSON)
//                    .post("/students/"+studentId+"/codelabs/"+codelabId+"/comments")
//                    .then()
//                    .assertThat()
//                    .statusCode(HttpStatus.BAD_REQUEST.value());
//
//            Throwable thrown = Assertions.catchThrowable(() -> progressService.saveCodelabComment(codelabCommentDto,studentId,codelabId));
//
//            //THEN
//            Assertions.assertThat(thrown)
//                    .isInstanceOf(InvalidProgressException.class)
//                    .hasMessage("You can only add a solution when codelab is completed");
//        }

        @Test
        void givenNoCodelabProgress_WhenSaveSolution_ThenSolutionIsNotSaved() {
            Long studentId = 1L;
            Long codelabId = 99999998L;
            CodelabCommentDto codelabCommentDto = new CodelabCommentDto("Codelab 1 is hard", "https://www.donuts.com/delicious");

            RestAssured.given()
                    .port(port)
                    .body(codelabCommentDto)
                    .contentType(ContentType.JSON)
                    .when()
                    .accept(ContentType.JSON)
                    .post("/students/"+studentId+"/codelabs/"+codelabId+"/comments")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.CREATED.value());

            StudentCodelabProgress actualProgress = studentCodelabProgressJpaRepository.findByCodelabIdAndStudentId(codelabId, studentId);

            Assertions.assertThat(actualProgress.getSolutionUrl()).isNull();
        }

        @Test
        void givenCodelabId_whenGetSolutions_thenSolutionUrlAndAuthorReturned() {
            //GIVEN
            int codelabId = 99999997;
            Student student = studentJpaRepository.findById(1L).get();
            CodelabSolutionDto expected = new CodelabSolutionDto(student.getDisplayName(), "https://github.com/BakouBakou/java-feb-2022/blob/08d9080acb8ad758a3ee1d473895858a7f8f8ad9/Jenkinsfile");

            //WHEN
            List<CodelabSolutionDto> actual = RestAssured
                    .given()
                    .contentType(JSON)
                    .when()
                    .port(port)
                    .get("/codelabs/" + codelabId + "/solutions")
                    .then()
                    .assertThat()
                    .statusCode(HttpStatus.OK.value())
                    .extract().body().jsonPath().getList(".", CodelabSolutionDto.class);

            //THEN
            Assertions.assertThat(actual).contains(expected);
        }
    }
}