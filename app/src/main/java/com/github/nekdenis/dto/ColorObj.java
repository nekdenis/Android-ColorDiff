package com.github.nekdenis.dto;

import com.github.nekdenis.util.ColorConverter;

import java.io.Serializable;

/**
 * Color object represented in LAB color scheme
 */
public class ColorObj implements Serializable {

    private String id;

    private double l;
    private double a;
    private double b;

    public ColorObj(String id, double l, double a, double b) {
        this.id = id;
        this.l = l;
        this.a = a;
        this.b = b;
    }

    public ColorObj(String id, ColorObj colorObj) {
        this.id = id;
        double[] lab = colorObj.getLAB();
        this.l = lab[0];
        this.a = lab[1];
        this.b = lab[2];
    }

    /**
     * get color presented in CIE LAB
     *
     * @return l-a-b array
     */
    public double[] getLAB() {
        double[] result = {l, a, b};
        return result;
    }

    /**
     * get color presented in CIE LCH
     *
     * @return l-a-b array
     */
    public double[] getLCH() {
        return ColorConverter.LABtoLCH(l, a, b);
    }

    /**
     * get color presented in RGB
     *
     * @return r-g-b array
     */

    public void setLCH(double[] lch) {
        double[] lab = ColorConverter.LCHtoLAB(lch[0], lch[1], lch[2]);
        l = lab[0];
        a = lab[1];
        b = lab[2];
    }

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

    public double getL() {
        return l;
    }

    public double getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public double getB() {
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

    /**
     *
     * @return Color represented in CIA LCH color space
     */
    public String getLCHString(){
        double[] lch = getLCH();
        return String.format("l:%1$,.1f c:%2$,.1f h:a:%3$,.1f",lch[0], lch[1], lch[2]);
    }

    @Override
    public String toString() {
        return String.format("l:%1$,.1f a:%2$,.1f b:a:%3$,.1f",l, a, b);
    }
}