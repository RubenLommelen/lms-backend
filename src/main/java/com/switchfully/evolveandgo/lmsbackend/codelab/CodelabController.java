package com.switchfully.evolveandgo.lmsbackend.codelab;

import com.switchfully.evolveandgo.lmsbackend.codelab.dto.CodelabDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CodelabController {

    @GetMapping(path = "/students/1/codelabs", produces = MediaType.APPLICATION_JSON_VALUE)
    private List<CodelabDto> getCodelabsForStudent() {
        return List.of(
                new CodelabDto("Rinaldo", CodelabProgress.DONE),
                new CodelabDto("Ronaldo", CodelabProgress.BUSY)
        );
    }

}
