package com.github.nekdenis.dto;

import com.github.nekdenis.util.ColorConverter;

import java.io.Serializable;

/**
 * Color object represented in LAB color scheme
 */
public class ColorObj implements Serializable {

    private String id;

    private int l;
    private int a;
    private int b;

    public ColorObj(String id, int l, int a, int b) {
        this.id = id;
        this.l = l;
        this.a = a;
        this.b = b;
    }

    public ColorObj(String id, ColorObj colorObj) {
        this.id = id;
        int[] lab = colorObj.getLAB();
        this.l = lab[0];
        this.a = lab[1];
        this.b = lab[2];
    }

    /**
     * get color presented in LAB
     *
     * @return l-a-b array
     */
    public int[] getLAB() {
        int[] result = {l, a, b};
        return result;
    }

    /**
     * get color presented in RGB
     *
     * @return r-g-b array
     */
    public int[] getRGB() {
        return ColorConverter.LABtoRGB(l, a, b);
    }

    /**
     * get color presented in RGB
     *
     * @return 0xRRGGBB integer
     */
    public int getRGBint() {
        int[] rgb = ColorConverter.LABtoRGB(l, a, b);

        rgb[0] = (rgb[0] << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        rgb[1] = (rgb[1] << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        rgb[2] = rgb[2] & 0x000000FF; //Mask out anything not blue.
        return 0xFF000000 | rgb[0] | rgb[1] | rgb[2]; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

    public int getL() {
        return l;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void setL(int l) {
        this.l = l;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "" + l + " " + a + " " + b;
    }
}