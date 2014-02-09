package com.github.nekdenis.dto;

import java.io.Serializable;

public class ResultObj implements Serializable {

    ColorObj originalColor;
    ColorObj leftColor;
    ColorObj rightColor;

    public ColorObj getOriginalColor() {
        return originalColor;
    }

    public void setOriginalColor(ColorObj originalColor) {
        this.originalColor = originalColor;
    }

    public ColorObj getLeftColor() {
        return leftColor;
    }

    public void setLeftColor(ColorObj leftColor) {
        this.leftColor = leftColor;
    }

    public ColorObj getRightColor() {
        return rightColor;
    }

    public void setRightColor(ColorObj rightColor) {
        this.rightColor = rightColor;
    }
}
