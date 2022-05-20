package com.switchfully.evolveandgo.lmsbackend.codelab.domain;

import com.switchfully.evolveandgo.lmsbackend.codelab.domain.CodelabProgress;

public class Codelab {

    private final String name;
    private final CodelabProgress progress;

    public Codelab(String name, CodelabProgress progress) {
        this.name = name;
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public CodelabProgress getProgress() {
        return progress;
    }
}
