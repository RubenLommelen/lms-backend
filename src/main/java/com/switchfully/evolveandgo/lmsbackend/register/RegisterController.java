package com.switchfully.evolveandgo.lmsbackend.register;

import com.switchfully.evolveandgo.lmsbackend.register.dto.RegisterStudentDto;
import com.switchfully.evolveandgo.lmsbackend.user.student.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RegisterController {

    private final StudentService studentService;

    public RegisterController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerStudent(@RequestBody RegisterStudentDto registerStudentDto) {
        studentService.registerStudent(registerStudentDto);
    }
}
