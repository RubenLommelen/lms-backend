package com.switchfully.evolveandgo.lmsbackend.codelab.dto;

import java.util.List;

public class CodelabsProgressDto {
    private final List<CodelabProgressDto> codelabsProgressList;
    private final Long id;

    public CodelabsProgressDto(List<CodelabProgressDto> codelabsProgressList, Long id) {
        this.codelabsProgressList = codelabsProgressList;
        this.id = id;
    }

    public List<CodelabProgressDto> getCodelabsProgressList() {
        return codelabsProgressList;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CodelabsProgressDto{" +
                "codelabsProgressList=" + codelabsProgressList +
                ", id=" + id +
                '}';
    }
}
