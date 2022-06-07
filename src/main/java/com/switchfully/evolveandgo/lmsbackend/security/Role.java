package com.switchfully.evolveandgo.lmsbackend.security;

import com.google.common.collect.Lists;

import java.util.List;

public enum Role {
    COACH("coach", Feature.VIEW_STUDENT_PROGRESS, Feature.VIEW_PROFILE),
    STUDENT("student", Feature.SAVE_CODELAB_PROGRESS, Feature.VIEW_CODELAB_PROGRESS, Feature.VIEW_PROFILE);

    private final String label;
    private final List<Feature> featureList;

    Role(String label, Feature... featureList) {
        this.label = label;
        this.featureList = Lists.newArrayList(featureList);
    }

    public List<Feature> getFeatures() {
        return featureList;
    }

    public String getLabel() {
        return label;
    }
}