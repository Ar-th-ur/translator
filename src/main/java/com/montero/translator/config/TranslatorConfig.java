package com.montero.translator.config;

import com.montero.translator.service.TranslationService;
import com.montero.translator.translators.DeepLTranslator;
import com.montero.translator.translators.Translator;
import com.montero.translator.translators.YandexTranslator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
public class TranslatorConfig {
    @Bean
    public TranslationService translationService() {
        CopyOnWriteArrayList<Translator> translators = new CopyOnWriteArrayList<>(List.of(
                yandexTranslator(),
                deepLTranslator()
        ));
        return new TranslationService(translators);
    }

    private YandexTranslator yandexTranslator() {
        return new YandexTranslator(webDriver(), "https://translate.yandex.ru/?source_lang=ru&target_lang=en");
    }

    public DeepLTranslator deepLTranslator() {
        return new DeepLTranslator(webDriver(), "https://www.deepl.com/ru/translator#ru/en-us/.");
    }

    public WebDriver webDriver() {
        ChromeOptions webDriverOptions = new ChromeOptions();
        // webDriverOptions.addArguments("--headless"); // не открывать браузер
        return new ChromeDriver(webDriverOptions);
    }
}
