package com.switchfully.evolveandgo.lmsbackend.coach;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachJpaRepository extends JpaRepository<Coach, Long> {

    Coach findByEmail(String email);
}
