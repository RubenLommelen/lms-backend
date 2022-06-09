package com.switchfully.evolveandgo.lmsbackend.user.student;

import com.switchfully.evolveandgo.lmsbackend.user.student.dto.StudentDto;
import com.switchfully.evolveandgo.lmsbackend.user.student.service.StudentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@CrossOrigin (origins = {"https://evolveandgo.netlify.app", "http://localhost:4200"} )
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDto getStudentById(@PathVariable Long id) {
        return studentService.getStudentDtoById(id);
    }

}
