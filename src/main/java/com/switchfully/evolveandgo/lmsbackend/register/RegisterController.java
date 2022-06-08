package com.switchfully.evolveandgo.lmsbackend.register;

import com.switchfully.evolveandgo.lmsbackend.register.dto.RegisterStudentDto;
import com.switchfully.evolveandgo.lmsbackend.register.dto.RegisterValidDto;
import com.switchfully.evolveandgo.lmsbackend.register.exception.UserAlreadyExistsException;
import com.switchfully.evolveandgo.lmsbackend.user.student.service.StudentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class RegisterController {

    private final StudentService studentService;

    public RegisterController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RegisterValidDto registerStudent(@Valid @RequestBody RegisterStudentDto registerStudentDto) {
        return studentService.registerStudent(registerStudentDto);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
    }
}
