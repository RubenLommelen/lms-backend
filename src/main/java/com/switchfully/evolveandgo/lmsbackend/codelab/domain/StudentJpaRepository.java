package com.switchfully.evolveandgo.lmsbackend.codelab.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentJpaRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
}