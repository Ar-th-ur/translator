package com.montero.translator.controller;

import com.montero.translator.model.TranslationRequest;
import com.montero.translator.model.TranslationResponse;
import com.montero.translator.service.TranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/translate")
public class TranslationController {
    private final TranslationService translationService;

    @GetMapping
    public TranslationResponse translate(@RequestBody @Valid TranslationRequest translationRequest) {
        return translationService.translate(translationRequest);
    }
}
