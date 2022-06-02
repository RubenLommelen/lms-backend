package com.switchfully.evolveandgo.lmsbackend.coach;

import com.switchfully.evolveandgo.lmsbackend.coach.dto.CoachDto;
import com.switchfully.evolveandgo.lmsbackend.coach.service.CoachService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("coachs")
@CrossOrigin
public class CoachController {
    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping("{id}")
    public CoachDto getCoachDtoById(@PathVariable Long id) {
        return coachService.getCoachDtoById(id);
    }
}
