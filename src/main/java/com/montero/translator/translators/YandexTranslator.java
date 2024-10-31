package com.montero.translator.translators;

import com.montero.translator.model.Translation;
import com.montero.translator.model.TranslationRequest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class YandexTranslator extends Translator {
    private static final By TRANSLATABLE_WORD_FIELD = By.xpath("//*[@id=\"fakeArea\"]");
    private static final By ALL_TRANSLATIONS_FIELDS = By.xpath("//*[@id=\"dictionary\"]/div/ul/li[1]/ol/li");

    public YandexTranslator(WebDriver webDriver, String url) {
        super(webDriver, url);
    }

    @Override
    public List<Translation> translate(TranslationRequest translationRequest) {
        WebElement translatableWordField = webDriver.findElement(TRANSLATABLE_WORD_FIELD);
        translatableWordField.clear();
        translatableWordField.sendKeys(translationRequest.getTranslatableWord().trim());

        AtomicInteger nearestDistance = new AtomicInteger(1);
        return webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ALL_TRANSLATIONS_FIELDS))
                .stream()
                .flatMap(e -> e.findElements(By.xpath("ul[1]/li")).stream())
                .flatMap(e -> e.findElements(By.xpath("span[1]")).stream())
                .map(e -> new Translation(e.getText(), nearestDistance.getAndIncrement(), 1))
                .toList();
    }

    @Override
    protected void swapLanguages() {
        // yandex swaps the languages itself
    }
}
