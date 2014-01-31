package com.github.nekdenis.dto;


import java.util.ArrayList;


/**
 * Class that contains pallete of colors
 */
public class ColorPallete {

    int lastIndex = 0;

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static ArrayList<ColorObj> ITEMS = new ArrayList<ColorObj>();

    public ColorPallete() {
        addItem(new ColorObj(String.valueOf(lastIndex++), 50,10,10));
        addItem(new ColorObj(String.valueOf(lastIndex++), 50,40,10));
        addItem(new ColorObj(String.valueOf(lastIndex++), 50,30,100));
    }

    private static void addItem(ColorObj item) {
        ITEMS.add(item);
    }
}
