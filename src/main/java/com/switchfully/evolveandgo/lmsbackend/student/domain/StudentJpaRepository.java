package com.switchfully.evolveandgo.lmsbackend.student.domain;

import com.switchfully.evolveandgo.lmsbackend.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentJpaRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
}
