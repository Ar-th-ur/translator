package com.montero.translator.translators;

import com.montero.translator.model.Translation;
import com.montero.translator.model.TranslationRequest;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.List;

public abstract class Translator {
    protected final WebDriver webDriver;
    protected final Wait<WebDriver> webDriverWait;

    public Translator(WebDriver webDriver, String url) {
        this.webDriver = webDriver;
        this.webDriverWait = new FluentWait<>(webDriver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);

        webDriver.get(url);
    }

    public void destroy() {
        webDriver.quit();
    }

    public abstract List<Translation> translate(TranslationRequest translationRequest);

    protected abstract void swapLanguages();
}
