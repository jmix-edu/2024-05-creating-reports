package com.company.jmixpmflowbase.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import javax.annotation.Nullable;


public enum ProjectStatus implements EnumClass<Integer> {

    NEW(10),
    IN_PROGRESS(20),
    COMPLETED(30);

    private Integer id;

    ProjectStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static ProjectStatus fromId(Integer id) {
        for (ProjectStatus at : ProjectStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}