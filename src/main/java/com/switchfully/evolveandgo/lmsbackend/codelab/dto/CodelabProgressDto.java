package com.switchfully.evolveandgo.lmsbackend.codelab.dto;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;

public class CodelabProgressDto {
    private final Long studentId;
    private final CodelabProgress progress;
    private final Long codelabId;
    private final String codelabName;

    public CodelabProgressDto(Long studentId, CodelabProgress progress, Long codelabId, String codelabName) {
        this.studentId = studentId;
        this.progress = progress;
        this.codelabId = codelabId;
        this.codelabName = codelabName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public CodelabProgress getProgress() {
        return progress;
    }

    public Long getCodelabId() {
        return codelabId;
    }

    public String getCodelabName() {
        return codelabName;
    }
}
