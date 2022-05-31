package com.switchfully.evolveandgo.lmsbackend.codelab;

import com.switchfully.evolveandgo.lmsbackend.codelab.dto.CodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentCodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.codelab.service.CodelabService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
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

    @PostMapping(path = "/students/{id}/codelabs", consumes = MediaType.APPLICATION_JSON_VALUE)
    private void saveCodelabsProgess(@PathVariable Long id, @RequestBody List<CodelabProgressDto> codelabProgressDto) {
       codelabService.saveCodelabProgress(codelabProgressDto, id);
    }

}
