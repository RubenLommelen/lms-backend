package com.switchfully.evolveandgo.lmsbackend.login;

import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.student.service.StudentService;
import com.switchfully.evolveandgo.lmsbackend.login.dto.LoginDto;
import com.switchfully.evolveandgo.lmsbackend.login.dto.TokenDto;
import com.switchfully.evolveandgo.lmsbackend.login.service.LoginService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("login")
public class LoginController {


    private final LoginService loginService;
    private final StudentService studentService;

    public LoginController(LoginService loginService, StudentService studentService) {
        this.loginService = loginService;
        this.studentService = studentService;
    }

    @CrossOrigin
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto login(@RequestBody @Valid LoginDto loginDto) throws AuthenticationException {
        Student student = studentService.findByEmail(loginDto.getEmail());
        return new TokenDto(loginService.getToken(student.getDisplayName(), loginDto.getPassword()), student.getDisplayName());
    }

}