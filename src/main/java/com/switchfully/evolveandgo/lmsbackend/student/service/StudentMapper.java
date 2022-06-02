package com.switchfully.evolveandgo.lmsbackend.student.service;

import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentDto;
import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDto toDto(Student student) {
        return new StudentDto(student.getDisplayName(), student.getEmail());
    }
}
