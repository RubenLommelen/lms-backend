package com.switchfully.evolveandgo.lmsbackend.student.service;

import com.switchfully.evolveandgo.lmsbackend.student.exception.StudentNotFoundException;
import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import com.switchfully.evolveandgo.lmsbackend.student.domain.StudentJpaRepository;
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
//        return studentJpaRepository.findByEmail(email).orElseThrow(() -> {
//            throw new StudentNotFoundException(email);
//        });
        return studentJpaRepository.findByEmail(email).orElse(null);
    }

}
