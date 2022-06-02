package com.switchfully.evolveandgo.lmsbackend.student.service;

import com.switchfully.evolveandgo.lmsbackend.student.dto.StudentDto;
import com.switchfully.evolveandgo.lmsbackend.student.exception.UserNotFoundException;
import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.student.domain.StudentJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentJpaRepository studentJpaRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentJpaRepository studentJpaRepository, StudentMapper studentMapper) {
        this.studentJpaRepository = studentJpaRepository;
        this.studentMapper = studentMapper;
    }

    public Student findById(Long id){
        return studentJpaRepository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException(id);
        });
    }

    public Student findByEmail(String email){
        return studentJpaRepository.findByEmail(email).orElseThrow(() -> {
            throw new UserNotFoundException(email);
        });
    }

    public StudentDto getStudentDtoById(Long id) {
        return studentMapper.toDto(findById(id));
    }
}
