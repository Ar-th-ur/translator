package com.montero.translator.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TranslationResponse {
    private Language sourceLanguage;
    private String translatableWord;
    private List<Translation> translations;
}
