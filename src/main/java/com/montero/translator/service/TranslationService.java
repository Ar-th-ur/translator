package com.montero.translator.service;

import com.montero.translator.model.Translation;
import com.montero.translator.model.TranslationRequest;
import com.montero.translator.model.TranslationResponse;
import com.montero.translator.translators.Translator;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TranslationService {
    private final CopyOnWriteArrayList<Translator> translators;

    public TranslationResponse translate(TranslationRequest translationRequest) {
        List<Translation> summarizedTranslations = translators.stream()
                .flatMap(t -> t.translate(translationRequest).stream())
                .collect(Collectors.groupingBy(
                        Translation::getTranslation,
                        Collector.of(
                                () -> new int[2],
                                (a, t) -> {
                                    a[0]++;
                                    a[1] += t.getNearestDistance();
                                },
                                (a1, a2) -> {
                                    a1[0] += a2[0];
                                    a1[1] += a2[1];
                                    return a1;
                                }
                        )
                ))
                .entrySet()
                .stream()
                .map(e -> new Translation(
                        e.getKey(),
                        e.getValue()[1] * 10 / e.getValue()[0],
                        e.getValue()[0])
                )
                .sorted()
                .toList();

        return TranslationResponse.builder()
                .sourceLanguage(translationRequest.getSourceLanguage())
                .translatableWord(translationRequest.getTranslatableWord().toLowerCase())
                .translations(summarizedTranslations)
                .build();
    }

    @PreDestroy
    private void destroyAllTranslators() {
        translators.forEach(Translator::destroy);
    }
}
