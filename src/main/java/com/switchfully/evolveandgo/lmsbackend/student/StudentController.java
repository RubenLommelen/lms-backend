package com.switchfully.evolveandgo.lmsbackend.student;

import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentDto;
import com.switchfully.evolveandgo.lmsbackend.student.service.StudentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@CrossOrigin()
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
