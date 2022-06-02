package com.switchfully.evolveandgo.lmsbackend.progress.dto;

import com.switchfully.evolveandgo.lmsbackend.progress.domain.ProgressState;

public class SaveStudentCodelabProgressDto {
    private final Long studentId;
    private final ProgressState progress;
    private final Long codelabId;
    private final String codelabName;

    public SaveStudentCodelabProgressDto(Long studentId, ProgressState progress, Long codelabId, String codelabName) {
        this.studentId = studentId;
        this.progress = progress;
        this.codelabId = codelabId;
        this.codelabName = codelabName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public ProgressState getProgress() {
        return progress;
    }

    public Long getCodelabId() {
        return codelabId;
    }

    public String getCodelabName() {
        return codelabName;
    }
}
