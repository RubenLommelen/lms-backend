package com.switchfully.evolveandgo.lmsbackend.codelab.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentJpaRepository extends JpaRepository<Student, Long> {
}
