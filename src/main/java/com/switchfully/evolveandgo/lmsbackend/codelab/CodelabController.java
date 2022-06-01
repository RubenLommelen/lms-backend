package com.switchfully.evolveandgo.lmsbackend.codelab;

import com.switchfully.evolveandgo.lmsbackend.codelab.dto.CodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.progress.dto.ProgressOverviewDto;
import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentCodelabProgressDto;
import com.switchfully.evolveandgo.lmsbackend.codelab.service.CodelabService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin //("http://localhost:4200")
@RestController
public class CodelabController {

    private final CodelabService codelabService;

    public CodelabController(CodelabService codelabService) {
        this.codelabService = codelabService;
    }

    @PreAuthorize("hasAuthority('VIEW_CODELAB_PROGRESS')")
    @GetMapping(path = "/students/{id}/codelabs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentCodelabProgressDto> getCodelabsForStudent(@PathVariable Long id) {
        return codelabService.getCodelabsForStudent(id);
    }

    @PreAuthorize("hasAuthority('SAVE_CODELAB_PROGRESS')")
    @PostMapping(path = "/students/{id}/codelabs", consumes = MediaType.APPLICATION_JSON_VALUE)
    private void saveCodelabsProgess(@PathVariable Long id, @RequestBody List<CodelabProgressDto> codelabProgressDto) {
        codelabService.saveCodelabProgress(codelabProgressDto, id);
    }

    @PreAuthorize("hasAuthority('VIEW_STUDENT_PROGRESS')")
    @GetMapping(path = "/progress", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProgressOverviewDto> getProgressOverview() {
        return codelabService.getProgressOverview();
    }

}
