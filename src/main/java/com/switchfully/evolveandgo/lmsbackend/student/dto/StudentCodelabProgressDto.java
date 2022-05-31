package com.switchfully.evolveandgo.lmsbackend.student.dto;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;

import java.util.Objects;

public class StudentCodelabProgressDto {
    private Long codelabId;
    private CodelabProgress progress;
    private String codelabName;

    public StudentCodelabProgressDto(Long codelabId,CodelabProgress progress, String codelabName) {
        this.codelabId = codelabId;

        this.progress = progress;
        this.codelabName = codelabName;
    }

    public Long getCodelabId() {
        return codelabId;
    }


    public CodelabProgress getProgress() {
        return progress;
    }

    public String getCodelabName() {
        return codelabName;
    }


}
