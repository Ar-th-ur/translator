package com.montero.translator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;

@Data
@AllArgsConstructor
public class Translation implements Comparable<Translation> {
    private String translation;
    private int nearestDistance;
    private int appearanceCount;

    @Override
    public int compareTo(Translation o) {
        return Comparator
                .comparing(Translation::getAppearanceCount).reversed()
                .thenComparing(Translation::getNearestDistance)
                .compare(this, o);
    }
}
