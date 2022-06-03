package com.switchfully.evolveandgo.lmsbackend.register;

import com.switchfully.evolveandgo.lmsbackend.register.dto.RegisterStudentDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RegisterController {

    @PostMapping(path = "register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerStudent(@RequestBody RegisterStudentDto registerStudentDto) {
        System.out.println(registerStudentDto);
    }
}
