package com.switchfully.evolveandgo.lmsbackend.codelab;

import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentCodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.codelab.service.CodelabService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin //("http://localhost:4200")
@RestController
public class CodelabController {

    private final CodelabService codelabService;

    public CodelabController(CodelabService codelabService) {
        this.codelabService = codelabService;
    }

    @GetMapping(path = "/students/{id}/codelabs", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<StudentCodelabProgressDto> getCodelabsForStudent(@PathVariable Long id) {
        return codelabService.getCodelabsForStudent(id);
    }

}
