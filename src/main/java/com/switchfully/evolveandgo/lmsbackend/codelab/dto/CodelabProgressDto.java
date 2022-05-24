package com.switchfully.evolveandgo.lmsbackend.codelab.dto;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;

public class CodelabProgressDto {
    private final String title;
    private final CodelabProgress progress;

    public CodelabProgressDto(String title, CodelabProgress progress) {
        this.title = title;
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public CodelabProgress getProgress() {
        return progress;
    }

    @Override
    public String toString() {
        return "CodelabProgressDto{" +
                "title='" + title + '\'' +
                ", progress=" + progress +
                '}';
    }
}
