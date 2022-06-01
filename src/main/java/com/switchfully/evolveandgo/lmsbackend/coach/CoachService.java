package com.switchfully.evolveandgo.lmsbackend.coach;

import org.springframework.stereotype.Service;

@Service
public class CoachService {

    private final CoachJpaRepository coachJpaRepository;

    public CoachService(CoachJpaRepository coachJpaRepository) {
        this.coachJpaRepository = coachJpaRepository;
    }

    public Coach findByEmail(String email){
        return coachJpaRepository.findByEmail(email);
    }

}
