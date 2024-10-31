package com.montero.translator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TranslatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranslatorApplication.class, args);
    }

}
