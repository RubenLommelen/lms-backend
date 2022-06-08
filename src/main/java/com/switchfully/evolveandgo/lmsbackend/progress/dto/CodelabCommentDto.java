package com.switchfully.evolveandgo.lmsbackend.progress.dto;

public class CodelabCommentDto {
    private final String codelabComment;
    private final String codelabSolutionUrl;

    public CodelabCommentDto(String codelabComment, String codelabSolutionUrl) {
        this.codelabComment = codelabComment;
        this.codelabSolutionUrl = codelabSolutionUrl;
    }

    public String getCodelabComment() {
        return codelabComment;
    }

    public String getCodelabSolutionUrl() {
        return codelabSolutionUrl;
    }
}
