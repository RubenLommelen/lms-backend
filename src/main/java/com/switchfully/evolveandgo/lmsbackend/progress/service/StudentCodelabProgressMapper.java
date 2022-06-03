package com.switchfully.evolveandgo.lmsbackend.progress.service;

import com.switchfully.evolveandgo.lmsbackend.progress.domain.StudentCodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.progress.dto.StudentCodelabProgressDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentCodelabProgressMapper {

    public StudentCodelabProgressDto toDto(StudentCodelabProgress studentCodelabProgress) {
        return new StudentCodelabProgressDto(
                studentCodelabProgress.getCodelab().getId(),
                studentCodelabProgress.getProgress(),
                studentCodelabProgress.getCodelab().getName(),
                studentCodelabProgress.getCodelab().getCreationDate(),
                studentCodelabProgress.getComment()
                );
    }

    public List<StudentCodelabProgressDto> toDtoList(List<StudentCodelabProgress> studentCodelabProgressList) {
        return studentCodelabProgressList.stream()
                .map(studentCodelabProgress -> toDto(studentCodelabProgress))
                .collect(Collectors.toList());
    }
}
