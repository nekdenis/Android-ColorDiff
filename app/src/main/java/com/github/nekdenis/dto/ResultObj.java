package com.github.nekdenis.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Contains list of modified colors
 */

public class ResultObj implements Serializable {

    ColorObj originalColor;
    List<ColorObj> modifiedColors;

    public ResultObj() {
        this.modifiedColors = new ArrayList<ColorObj>();
    }

    public ColorObj getOriginalColor() {
        return originalColor;
    }

    public void setOriginalColor(ColorObj originalColor) {
        this.originalColor = originalColor;
    }

    public List<ColorObj> getModifiedColors() {
        return modifiedColors;
    }

    public void addModifiedColor(ColorObj modifiedColor) {
        this.modifiedColors.add(modifiedColor);
    }

    public void addModifiedColor(int index, ColorObj modifiedColor) {
        this.modifiedColors.add(index, modifiedColor);
    }
}
