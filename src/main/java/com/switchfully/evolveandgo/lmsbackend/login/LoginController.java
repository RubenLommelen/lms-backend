package com.switchfully.evolveandgo.lmsbackend.login;

import com.switchfully.evolveandgo.lmsbackend.user.coach.domain.Coach;
import com.switchfully.evolveandgo.lmsbackend.user.coach.service.CoachService;
import com.switchfully.evolveandgo.lmsbackend.user.User;
import com.switchfully.evolveandgo.lmsbackend.login.domain.UserType;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.StudentJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.user.exception.UserNotFoundException;
import com.switchfully.evolveandgo.lmsbackend.user.student.service.StudentService;
import com.switchfully.evolveandgo.lmsbackend.login.dto.LoginDto;
import com.switchfully.evolveandgo.lmsbackend.login.dto.TokenDto;
import com.switchfully.evolveandgo.lmsbackend.login.service.LoginService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin (origins = {"https://evolveandgo.netlify.app", "http://localhost:4200"} )
@RequestMapping("login")
public class LoginController {


    private final LoginService loginService;
    private final CoachService coachService;
    private final StudentJpaRepository studentJpaRepository;

    public LoginController(LoginService loginService, CoachService coachService, StudentJpaRepository studentJpaRepository) {
        this.loginService = loginService;
        this.coachService = coachService;
        this.studentJpaRepository = studentJpaRepository;
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto login(@RequestBody @Valid LoginDto loginDto) {
        User user = studentJpaRepository.findByEmail(loginDto.getEmail()).orElse(null);
        UserType userType = UserType.STUDENT;

        if (user == null) {
            user = coachService.findByEmail(loginDto.getEmail()).orElse(null);
        }

        if (user == null) {
            throw new UserNotFoundException(loginDto.getEmail());
        }

        if (user instanceof Coach) {
            userType = UserType.COACH;
        }

        return new TokenDto(loginService.getToken(user.getEmail(), loginDto.getPassword()), user.getDisplayName(), user.getId(), userType);
    }

}