package com.switchfully.evolveandgo.lmsbackend.codelab.dto;

import com.switchfully.evolveandgo.lmsbackend.codelab.CodelabProgress;

import java.util.Objects;

public class CodelabDto {

    private String name;
    private CodelabProgress codelabProgress;

    public CodelabDto() {
    }

    public CodelabDto(String name, CodelabProgress codelabProgress) {
        this.name = name;
        this.codelabProgress = codelabProgress;
    }

    public String getName() {
        return name;
    }

    public CodelabProgress getCodelabProgress() {
        return codelabProgress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodelabDto that = (CodelabDto) o;
        return Objects.equals(name, that.name) && codelabProgress == that.codelabProgress;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, codelabProgress);
    }
}
