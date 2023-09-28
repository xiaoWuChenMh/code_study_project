package com.future.enums;

public enum ChatGroupType {

    GPT_4("gpt-4"),
    GPT_3_5("gpt-3.5");

    private final String name;

    private ChatGroupType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
