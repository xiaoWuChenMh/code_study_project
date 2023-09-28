package com.future.enums;

public enum EncodingType {

    R50K_BASE("r50k_base"),
    P50K_BASE("p50k_base"),
    P50K_EDIT("p50k_edit"),
    CL100K_BASE("cl100k_base");

    private final String name;

    private EncodingType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
