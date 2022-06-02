package com.switchfully.evolveandgo.lmsbackend.coach.service;

import com.switchfully.evolveandgo.lmsbackend.coach.domain.Coach;
import com.switchfully.evolveandgo.lmsbackend.coach.domain.CoachJpaRepository;
import com.switchfully.evolveandgo.lmsbackend.coach.dto.CoachDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoachService {

    private final CoachJpaRepository coachJpaRepository;
    private final CoachMapper coachMapper;

    public CoachService(CoachJpaRepository coachJpaRepository, CoachMapper coachMapper) {
        this.coachJpaRepository = coachJpaRepository;
        this.coachMapper = coachMapper;
    }

    public Optional<Coach> findByEmail(String email){
        return coachJpaRepository.findByEmail(email);
    }

    public CoachDto getCoachDtoById(Long id) {
        return coachMapper.toDto(coachJpaRepository.findById(id).get());
    }
}
