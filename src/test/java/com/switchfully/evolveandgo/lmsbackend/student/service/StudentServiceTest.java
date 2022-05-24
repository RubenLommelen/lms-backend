package com.switchfully.evolveandgo.lmsbackend.student.service;

import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
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
        long id = 1;
        Student expectedStudent = new Student("rinaldo@spaghetto.be", "Rinaldo", "Spaghetto");
        Student actualStudent = studentService.findById(id);

        Assertions.assertThat(actualStudent).isEqualTo(expectedStudent);
    }

}