package com.montero.translator.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Language {
    RU,
    EN;

    @JsonValue
    public String getName() {
        return this.name();
    }
}
