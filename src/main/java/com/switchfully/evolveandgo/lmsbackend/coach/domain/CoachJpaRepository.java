package com.switchfully.evolveandgo.lmsbackend.coach.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoachJpaRepository extends JpaRepository<Coach, Long> {

    Optional<Coach> findByEmail(String email);
}
