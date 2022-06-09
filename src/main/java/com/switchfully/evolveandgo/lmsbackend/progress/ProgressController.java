package com.switchfully.evolveandgo.lmsbackend.progress;

import com.switchfully.evolveandgo.lmsbackend.progress.dto.*;
import com.switchfully.evolveandgo.lmsbackend.progress.service.ProgressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;


@CrossOrigin (origins = {"https://evolveandgo.netlify.app", "http://localhost:4200"} )
@RestController
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService codelabService) {
        this.progressService = codelabService;
    }

    @PreAuthorize("hasAuthority('VIEW_CODELAB_PROGRESS')")
    @GetMapping(path = "/students/{id}/codelabs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentCodelabProgressDto> getCodelabsForStudent(@PathVariable Long id) throws IOException {
        System.out.println(progressService.getCodelabsForStudent(id));

        return progressService.getCodelabsForStudent(id);
    }

    @PreAuthorize("hasAuthority('SAVE_CODELAB_PROGRESS')")
    @PostMapping(path = "/students/{id}/codelabs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveCodelabsProgess(@PathVariable Long id, @RequestBody List<SaveStudentCodelabProgressDto> saveStudentCodelabProgressDtos) {
        progressService.saveCodelabProgress(saveStudentCodelabProgressDtos, id);
    }

    @PreAuthorize("hasAuthority('VIEW_STUDENT_PROGRESS')")
    @GetMapping(path = "/progress", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProgressOverviewDto> getProgressOverview() {
        return progressService.getProgressOverview();
    }

    @PreAuthorize("hasAuthority('SAVE_CODELAB_PROGRESS')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/students/{studentId}/codelabs/{codelabId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CodelabCommentDto saveCodelabsProgess(@PathVariable("studentId") Long studentId, @PathVariable("codelabId") Long codelabId, @RequestBody CodelabCommentDto codelabCommentDto) {
        return progressService.saveCodelabComment(codelabCommentDto, studentId, codelabId);
    }

    @PreAuthorize("hasAuthority('VIEW_CODELAB_PROGRESS')")
    @GetMapping(path = "/codelabs/{codelabId}/solutions", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CodelabSolutionDto> getCodelabSolutions(@PathVariable Long codelabId) {

        return progressService.getCodelabSolutions(codelabId);
    }

}
