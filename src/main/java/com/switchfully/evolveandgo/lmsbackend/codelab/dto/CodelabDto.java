package com.switchfully.evolveandgo.lmsbackend.codelab.dto;

import java.util.Objects;

public class CodelabDto {

    private String name;

    public CodelabDto() {
    }

    public CodelabDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodelabDto that = (CodelabDto) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
