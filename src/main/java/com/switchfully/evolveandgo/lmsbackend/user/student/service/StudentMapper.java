package com.switchfully.evolveandgo.lmsbackend.user.student.service;

import com.switchfully.evolveandgo.lmsbackend.register.dto.RegisterStudentDto;
import com.switchfully.evolveandgo.lmsbackend.user.student.dto.StudentDto;
import com.switchfully.evolveandgo.lmsbackend.user.student.domain.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDto toDto(Student student) {
        return new StudentDto(student.getDisplayName(), student.getEmail());
    }

    public Student toStudent(RegisterStudentDto registerStudentDto) {
        return new Student(registerStudentDto.email(), registerStudentDto.displayName(), registerStudentDto.password());
    }
}
