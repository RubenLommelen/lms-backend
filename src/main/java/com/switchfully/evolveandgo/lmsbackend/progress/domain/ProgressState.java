package com.switchfully.evolveandgo.lmsbackend.progress.domain;

public enum ProgressState {
    NOT_STARTED(false),
    BUSY(false),
    FEEDBACK_NEEDED(true),
    STUCK(false),
    TESTING(false),
    REFACTORING(false),
    DONE(true);

    private final boolean codelabCompleted;

    ProgressState(boolean codelabCompleted) {
        this.codelabCompleted = codelabCompleted;
    }

    public boolean codelabCompleted() {
        return codelabCompleted;
    }
}
