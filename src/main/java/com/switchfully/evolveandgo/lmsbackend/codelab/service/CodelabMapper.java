package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.Codelab;
import com.switchfully.evolveandgo.lmsbackend.codelab.dto.CodelabDto;
import org.springframework.stereotype.Component;

@Component
public class CodelabMapper {

    public CodelabDto toDto(Codelab codelab) {
        return new CodelabDto(codelab.getName(), codelab.getProgress());
    }
}
