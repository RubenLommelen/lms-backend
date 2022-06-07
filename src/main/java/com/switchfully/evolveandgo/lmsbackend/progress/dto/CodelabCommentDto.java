package com.switchfully.evolveandgo.lmsbackend.progress.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CodelabCommentDto {
    private final String codelabComment;



    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CodelabCommentDto(String codelabComment) {
        this.codelabComment = codelabComment;


    }

    public String getCodelabComment() {
        return codelabComment;
    }


}
