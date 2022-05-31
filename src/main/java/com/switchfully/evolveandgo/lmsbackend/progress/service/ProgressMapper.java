package com.switchfully.evolveandgo.lmsbackend.progress.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.progress.domain.ProgressOverview;
import com.switchfully.evolveandgo.lmsbackend.progress.dto.ProgressOverviewDto;
import com.switchfully.evolveandgo.lmsbackend.student.service.StudentService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProgressMapper {

    private final StudentService studentService;
    private final CodelabJpaRepository codelabJpaRepository;

    public ProgressMapper(StudentService studentService, CodelabJpaRepository codelabJpaRepository) {
        this.studentService = studentService;
        this.codelabJpaRepository = codelabJpaRepository;
    }

    public ProgressOverviewDto toDto(ProgressOverview progress) {
        return new ProgressOverviewDto(progress.getStudentId(),
                studentService.findById(progress.getStudentId()).getDisplayName(),
                progress.getNumberOfCompletedCodelabs(),
                codelabJpaRepository.count()
        );
    }

    public List<ProgressOverviewDto> toDtoList(List<ProgressOverview> progressList) {
        return progressList.stream()
                .map(progress -> toDto(progress))
                .collect(Collectors.toList());
    }
}
