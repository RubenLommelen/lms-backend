package com.switchfully.evolveandgo.lmsbackend.codelab.dto;

import com.switchfully.evolveandgo.lmsbackend.codelab.CodelabProgress;

import java.util.Objects;

public class CodelabDto {

    private String name;
    private CodelabProgress progress;

    public CodelabDto() {
    }

    public CodelabDto(String name, CodelabProgress progress) {
        this.name = name;
        this.progress = progress;
    }

    public String getName() {
        return name;
    }

    public CodelabProgress getProgress() {
        return progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodelabDto that = (CodelabDto) o;
        return Objects.equals(name, that.name) && progress == that.progress;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, progress);
    }
}
