package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.Codelab;
import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;
import com.switchfully.evolveandgo.lmsbackend.codelab.dto.CodelabDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodelabService {

    private final CodelabMapper codelabMapper;

    public CodelabService(CodelabMapper codelabMapper) {
        this.codelabMapper = codelabMapper;
    }

    public List<CodelabDto> getCodelabsForStudent() {
        return List.of(
                new Codelab("Rinaldo", CodelabProgress.DONE),
                new Codelab("Ronaldo", CodelabProgress.BUSY)
        ).stream()
                .map(codelabDto -> codelabMapper.toDto(codelabDto))
                .toList();
    }
}
