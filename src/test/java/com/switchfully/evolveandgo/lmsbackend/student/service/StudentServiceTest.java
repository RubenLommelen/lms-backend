package com.switchfully.evolveandgo.lmsbackend.student.service;

import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.student.exception.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
class StudentServiceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentService studentService;

    @Test
    void GivenStudentId_WhenIdExists_ThenFindStudentByID() {
        long id = 3;
        Student expectedStudent = new Student("rinaldo@spaghetto.be", "Rinaldo", "Spaghetto");
        Student actualStudent = studentService.findById(id);

        Assertions.assertThat(actualStudent).isEqualTo(expectedStudent);
    }

    @Test
    void GivenStudentEmail_WhenEmailExists_ThenFindStudentByEmail() {
        String email = "rinaldo@spaghetto.be";
        Student expectedStudent = new Student("rinaldo@spaghetto.be", "Rinaldo", "Spaghetto");
        Student actualStudent = studentService.findByEmail(email);

        Assertions.assertThat(actualStudent).isEqualTo(expectedStudent);
    }

    @Test
    void GivenStudentEmail_WhenEmailDoesNotExists_ThenThrowException() {
        String email = "rinaldo@pizza.be";

        Throwable thrown = Assertions.catchThrowable(() -> studentService.findByEmail(email));

        //THEN
        Assertions.assertThat(thrown)
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("No student found for: " + email);

    }

}