package com.montero.translator.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslationRequest {
    @NotNull
    private Language sourceLanguage;
    @NotBlank
    private String translatableWord;
}
