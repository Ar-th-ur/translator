package com.montero.translator.translators;

import com.montero.translator.model.Translation;
import com.montero.translator.model.TranslationRequest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 Since in DeepL there is no access to the field of the translatable word if it is empty,
 it was decided initially to write a dot in this field (this does not affect the translation)
 so that this field would always be accessible. But clearing the field happens in a different way:
 all characters except the dot are clearing one by one, and then the required word is written.
 */
public class DeepLTranslator extends Translator {
    private static final By TRANSLATABLE_WORD_FIELD = By.xpath("//*[@id=\"textareasContainer\"]/div[1]/section/div/div[1]/d-textarea/div[1]/p/span");
    private static final By ALL_TRANSLATIONS_FIELDS_FIRST_PART = By.xpath("//*[@class=\"translation sortablemg featured\"]");
    private static final By ALL_TRANSLATIONS_FIELDS_SECOND_PART = By.xpath("//*[@class=\"translation sortablemg\"]");
    private static final By TRANSLATABLE_WORD_IN_DICTIONARY = By.xpath("//*[@id=\"headlessui-tabs-panel-:R1la35bke6l:\"]/div/div[2]/div/section/div/div/div/div/div/div[1]/span[1]/a");

    public DeepLTranslator(WebDriver webDriver, String url) {
        super(webDriver, url);
    }

    @Override
    public List<Translation> translate(TranslationRequest translationRequest) {
        WebElement translatableWordField = webDriver.findElement(TRANSLATABLE_WORD_FIELD);
        int prevWordLength = translatableWordField.getText().length() - 1;
        translatableWordField.sendKeys(Keys.BACK_SPACE.toString().repeat(prevWordLength));

        String translatableWord = translationRequest.getTranslatableWord().trim();
        translatableWordField.sendKeys(translatableWord);
        waitForUpdate(translatableWord);

        List<Translation> translations = new ArrayList<>();
        AtomicInteger nearestDistance = new AtomicInteger(1);
        webDriver.findElements(ALL_TRANSLATIONS_FIELDS_FIRST_PART)
                .stream()
                .map(e -> e.findElement(By.xpath("div[1]/span[1]/a[1]")))
                .map(e -> new Translation(e.getText(), nearestDistance.getAndIncrement(), 1))
                .forEach(translations::add);
        webDriver.findElements(ALL_TRANSLATIONS_FIELDS_SECOND_PART)
                .stream()
                .map(e -> e.findElement(By.xpath("div[1]/span[1]/a[1]")))
                .map(e -> new Translation(e.getText(), nearestDistance.getAndIncrement(), 1))
                .forEach(translations::add);
        return translations;
    }

    /**
     * Waits until the translatable word appears in the dictionary.
     * This is necessary because words are taken from the dictionary,
     * but they appear too late, which is why there is a chance to capture previous words
     */
    private void waitForUpdate(String translatableWord) {
        webDriverWait.until(ExpectedConditions.textToBe(TRANSLATABLE_WORD_IN_DICTIONARY, translatableWord));
    }

    @Override
    protected void swapLanguages() {

    }
}
