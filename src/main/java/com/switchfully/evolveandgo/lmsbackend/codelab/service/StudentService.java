package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.StudentJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentJpaRepository studentJpaRepository;

    public StudentService(StudentJpaRepository studentJpaRepository) {
        this.studentJpaRepository = studentJpaRepository;
    }

    public Student findById(Long id){
        return studentJpaRepository.findById(id).orElseThrow(() -> {
            throw new StudentNotFoundException(id);
        });
    }

    public Student findByEmail(String email){
        return studentJpaRepository.findByEmail(email).orElseThrow(() -> {
            throw new StudentNotFoundException(email);
        });
    }

}
