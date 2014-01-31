package com.github.nekdenis.util;

@SuppressWarnings("JavadocReference")
public class ColorConverter {

    /**
     * XYZ to sRGB conversion matrix
     */
    public static double[][] Mi = {{3.2406, -1.5372, -0.4986},
            {-0.9689, 1.8758, 0.0415},
            {0.0557, -0.2040, 1.0570}};

    /**
     * sRGB to XYZ conversion matrix
     */
    public static double[][] M = {{0.4124, 0.3576, 0.1805},
            {0.2126, 0.7152, 0.0722},
            {0.0193, 0.1192, 0.9505}};

    /**
     * reference white in XYZ coordinates
     */
    public static double[] D50 = {96.4212, 100.0, 82.5188};
    public static double[] D55 = {95.6797, 100.0, 92.1481};
    public static double[] D65 = {95.0429, 100.0, 108.8900};
    public static double[] D75 = {94.9722, 100.0, 122.6394};
    public static double[] whitePoint = D65;

    /**
     * Second variant of convertion RGB color to LAB
     *
     * @param R red
     * @param G green
     * @param B blue
     * @return 3 int array with lab-presented color
     * @see {@link http://www.f4.fhtw-berlin.de/~barthel/ImageJ/ColorInspector//HTMLHelp/farbraumJava.htm}
     */
    public static int[] RGBtoLABII(int R, int G, int B) {
        int[] lab = {0, 0, 0};

        float r, g, b, X, Y, Z, fx, fy, fz, xr, yr, zr;
        float Ls, as, bs;
        float eps = 216.f / 24389.f;
        float k = 24389.f / 27.f;

        float Xr = 0.964221f;  // reference white D50
        float Yr = 1.0f;
        float Zr = 0.825211f;

        // RGB to XYZ
        r = R / 255.f; //R 0..1
        g = G / 255.f; //G 0..1
        b = B / 255.f; //B 0..1

        // assuming sRGB (D65)
        if (r <= 0.04045)
            r = r / 12;
        else
            r = (float) Math.pow((r + 0.055) / 1.055, 2.4);

        if (g <= 0.04045)
            g = g / 12;
        else
            g = (float) Math.pow((g + 0.055) / 1.055, 2.4);

        if (b <= 0.04045)
            b = b / 12;
        else
            b = (float) Math.pow((b + 0.055) / 1.055, 2.4);


        X = 0.436052025f * r + 0.385081593f * g + 0.143087414f * b;
        Y = 0.222491598f * r + 0.71688606f * g + 0.060621486f * b;
        Z = 0.013929122f * r + 0.097097002f * g + 0.71418547f * b;

        // XYZ to Lab
        xr = X / Xr;
        yr = Y / Yr;
        zr = Z / Zr;

        if (xr > eps)
            fx = (float) Math.pow(xr, 1 / 3.);
        else
            fx = (float) ((k * xr + 16.) / 116.);

        if (yr > eps)
            fy = (float) Math.pow(yr, 1 / 3.);
        else
            fy = (float) ((k * yr + 16.) / 116.);

        if (zr > eps)
            fz = (float) Math.pow(zr, 1 / 3.);
        else
            fz = (float) ((k * zr + 16.) / 116);

        Ls = (116 * fy) - 16;
        as = 500 * (fx - fy);
        bs = 200 * (fy - fz);

        lab[0] = (int) (2.55 * Ls + .5);
        lab[1] = (int) (as + .5);
        lab[2] = (int) (bs + .5);
        return lab;
    }

    public static double[] RGBtoLAB(int r, int g, int b) {
        return XYZtoLAB(RGBtoXYZ(r, g, b));
    }

    /**
     * Convert XYZ to LAB.
     *
     * @param X
     * @param Y
     * @param Z
     * @return Lab values
     * @see {@link http://rsb.info.nih.gov/ij/plugins/download/Color_Space_Converter.java}
     */
    public static double[] XYZtoLAB(double X, double Y, double Z) {

        double x = X / whitePoint[0];
        double y = Y / whitePoint[1];
        double z = Z / whitePoint[2];

        if (x > 0.008856) {
            x = Math.pow(x, 1.0 / 3.0);
        } else {
            x = (7.787 * x) + (16.0 / 116.0);
        }
        if (y > 0.008856) {
            y = Math.pow(y, 1.0 / 3.0);
        } else {
            y = (7.787 * y) + (16.0 / 116.0);
        }
        if (z > 0.008856) {
            z = Math.pow(z, 1.0 / 3.0);
        } else {
            z = (7.787 * z) + (16.0 / 116.0);
        }

        double[] result = new double[3];

        result[0] = (116.0 * y) - 16.0;
        result[1] = 500.0 * (x - y);
        result[2] = 200.0 * (y - z);

        return result;
    }

    /**
     * Convert XYZ to LAB.
     *
     * @param XYZ
     * @return Lab values
     */
    public static double[] XYZtoLAB(double[] XYZ) {
        return XYZtoLAB(XYZ[0], XYZ[1], XYZ[2]);
    }

    /**
     * Convert LAB to RGB.
     *
     * @param l
     * @param a
     * @param b
     * @return RGB values
     */
    public static int[] LABtoRGB(double l, double a, double b) {
        return XYZtoRGB(LABtoXYZ(l, a, b));
    }


    /**
     * Convert LAB to XYZ.
     *
     * @param L
     * @param a
     * @param b
     * @return XYZ values
     * @see {@link http://rsb.info.nih.gov/ij/plugins/download/Color_Space_Converter.java}
     */
    public static double[] LABtoXYZ(double L, double a, double b) {
        double[] result = new double[3];

        double y = (L + 16.0) / 116.0;
        double y3 = Math.pow(y, 3.0);
        double x = (a / 500.0) + y;
        double x3 = Math.pow(x, 3.0);
        double z = y - (b / 200.0);
        double z3 = Math.pow(z, 3.0);

        if (y3 > 0.008856) {
            y = y3;
        } else {
            y = (y - (16.0 / 116.0)) / 7.787;
        }
        if (x3 > 0.008856) {
            x = x3;
        } else {
            x = (x - (16.0 / 116.0)) / 7.787;
        }
        if (z3 > 0.008856) {
            z = z3;
        } else {
            z = (z - (16.0 / 116.0)) / 7.787;
        }

        result[0] = x * whitePoint[0];
        result[1] = y * whitePoint[1];
        result[2] = z * whitePoint[2];

        return result;
    }

    /**
     * Convert XYZ to RGB
     *
     * @param XYZ in a double array.
     * @return RGB in int array.
     */
    public static int[] XYZtoRGB(double[] XYZ) {
        return XYZtoRGB(XYZ[0], XYZ[1], XYZ[2]);
    }

    /**
     * Convert XYZ to RGB.
     *
     * @param X
     * @param Y
     * @param Z
     * @return RGB in int array.
     * @see {@link http://rsb.info.nih.gov/ij/plugins/download/Color_Space_Converter.java}
     */
    public static int[] XYZtoRGB(double X, double Y, double Z) {
        int[] result = new int[3];

        double x = X / 100.0;
        double y = Y / 100.0;
        double z = Z / 100.0;

        // [r g b] = [X Y Z][Mi]
        double r = (x * Mi[0][0]) + (y * Mi[0][1]) + (z * Mi[0][2]);
        double g = (x * Mi[1][0]) + (y * Mi[1][1]) + (z * Mi[1][2]);
        double b = (x * Mi[2][0]) + (y * Mi[2][1]) + (z * Mi[2][2]);

        // assume sRGB
        if (r > 0.0031308) {
            r = ((1.055 * Math.pow(r, 1.0 / 2.4)) - 0.055);
        } else {
            r = (r * 12.92);
        }
        if (g > 0.0031308) {
            g = ((1.055 * Math.pow(g, 1.0 / 2.4)) - 0.055);
        } else {
            g = (g * 12.92);
        }
        if (b > 0.0031308) {
            b = ((1.055 * Math.pow(b, 1.0 / 2.4)) - 0.055);
        } else {
            b = (b * 12.92);
        }

        r = (r < 0) ? 0 : r;
        g = (g < 0) ? 0 : g;
        b = (b < 0) ? 0 : b;

        // convert 0..1 into 0..255
        result[0] = (int) Math.round(r * 255);
        result[1] = (int) Math.round(g * 255);
        result[2] = (int) Math.round(b * 255);

        return result;
    }

    /**
     * Convert RGB to XYZ
     *
     * @param R
     * @param G
     * @param B
     * @return XYZ in double array.
     * @see {@link http://rsb.info.nih.gov/ij/plugins/download/Color_Space_Converter.java}
     */
    public static double[] RGBtoXYZ(int R, int G, int B) {
        double[] result = new double[3];

        // convert 0..255 into 0..1
        double r = R / 255.0;
        double g = G / 255.0;
        double b = B / 255.0;

        // assume sRGB
        if (r <= 0.04045) {
            r = r / 12.92;
        } else {
            r = Math.pow(((r + 0.055) / 1.055), 2.4);
        }
        if (g <= 0.04045) {
            g = g / 12.92;
        } else {
            g = Math.pow(((g + 0.055) / 1.055), 2.4);
        }
        if (b <= 0.04045) {
            b = b / 12.92;
        } else {
            b = Math.pow(((b + 0.055) / 1.055), 2.4);
        }

        r *= 100.0;
        g *= 100.0;
        b *= 100.0;

        // [X Y Z] = [r g b][M]
        result[0] = (r * M[0][0]) + (g * M[0][1]) + (b * M[0][2]);
        result[1] = (r * M[1][0]) + (g * M[1][1]) + (b * M[1][2]);
        result[2] = (r * M[2][0]) + (g * M[2][1]) + (b * M[2][2]);

        return result;
    }

}
