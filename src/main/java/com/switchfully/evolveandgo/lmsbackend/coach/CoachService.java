package com.switchfully.evolveandgo.lmsbackend.coach;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoachService {

    private final CoachJpaRepository coachJpaRepository;

    public CoachService(CoachJpaRepository coachJpaRepository) {
        this.coachJpaRepository = coachJpaRepository;
    }

    public Optional<Coach> findByEmail(String email){
        return coachJpaRepository.findByEmail(email);
    }

}
