package com.switchfully.evolveandgo.lmsbackend.login;

import com.switchfully.evolveandgo.lmsbackend.coach.domain.Coach;
import com.switchfully.evolveandgo.lmsbackend.coach.service.CoachService;
import com.switchfully.evolveandgo.lmsbackend.infrastructure.User;
import com.switchfully.evolveandgo.lmsbackend.login.dto.UserType;
import com.switchfully.evolveandgo.lmsbackend.student.domain.StudentJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.student.exception.UserNotFoundException;
import com.switchfully.evolveandgo.lmsbackend.student.service.StudentService;
import com.switchfully.evolveandgo.lmsbackend.login.dto.LoginDto;
import com.switchfully.evolveandgo.lmsbackend.login.dto.TokenDto;
import com.switchfully.evolveandgo.lmsbackend.login.service.LoginService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("login")
public class LoginController {


    private final LoginService loginService;
    private final StudentService studentService;
    private final CoachService coachService;
    private final StudentJpaRepository studentJpaRepository;

    public LoginController(LoginService loginService, StudentService studentService, CoachService coachService, StudentJpaRepository studentJpaRepository) {
        this.loginService = loginService;
        this.studentService = studentService;
        this.coachService = coachService;
        this.studentJpaRepository = studentJpaRepository;
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto login(@RequestBody @Valid LoginDto loginDto) {
//        User user = studentService.findByEmail(loginDto.getEmail());
        User user = studentJpaRepository.findByEmail(loginDto.getEmail()).orElse(null);
        UserType userType = UserType.STUDENT;

        if(user == null){
            user = coachService.findByEmail(loginDto.getEmail()).orElse(null);
        }

        if (user == null) {
            throw new UserNotFoundException(loginDto.getEmail());
        }

        if(user instanceof Coach){
            userType = UserType.COACH;
        }

        return new TokenDto(loginService.getToken(user.getDisplayName(), loginDto.getPassword()), user.getDisplayName(), user.getId(), userType);
    }

}