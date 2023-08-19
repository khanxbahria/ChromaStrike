package chromastrike.core;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorScanner {
    // opencv normalizes hue [0, 180], RGBtoHSV normalizes hue [0, 360]
    // opencv normalizes sat, val [0, 255], RGBtoHSV normalizes sat, val [0.0, 1.0]

    static double lower_h = 140 * 2;
    static double lower_s = 100 / 255.0;
    static double lower_v = 180 / 255.0;
    static double upper_h = 150 * 2;
    static double upper_s = 255 / 255.0;
    static double upper_v = 255 / 255.0;



    public static boolean containsPurple(BufferedImage img) {
        for (int row = 0; row < img.getHeight(); row++) {
            for (int col = 0; col < img.getWidth(); col++) {
                float[] hsv = new float[3];
                Color pixel = new Color(img.getRGB(col, row));
                RGBtoHSV(pixel.getRed(), pixel.getBlue(), pixel.getGreen(), hsv);
                boolean pixelPurple = (hsv[0] >= lower_h && hsv[0] <= upper_h)
                        && (hsv[1] >= lower_s && hsv[1] <= upper_s)
                        && (hsv[2] >= lower_v && hsv[2] <= upper_v);
                if (pixelPurple) {
                    return true;
                }
            }
        }
        return false;
    }

    private static float fmod(float a, float b) {
        int result = (int) Math.floor(a / b);
        return a - result * b;
    }

    private static void RGBtoHSV(int r, int b, int g, float[] hsv) {
        float fH = 0, fS = 0, fV = 0;
        float fR = r / 255.0f;
        float fB = b / 255.0f;
        float fG = g / 255.0f;
        float fCMax = Math.max(Math.max(fR, fG), fB);
        float fCMin = Math.min(Math.min(fR, fG), fB);
        float fDelta = fCMax - fCMin;

        if (fDelta > 0) {
            if (fCMax == fR) {
                fH = 60 * (fmod(((fG - fB) / fDelta), 6));
            } else if (fCMax == fG) {
                fH = 60 * (((fB - fR) / fDelta) + 2);
            } else if (fCMax == fB) {
                fH = 60 * (((fR - fG) / fDelta) + 4);
            }

            if (fCMax > 0) {
                fS = fDelta / fCMax;
            } else {
                fS = 0;
            }

            fV = fCMax;
        } else {
            fH = 0;
            fS = 0;
            fV = fCMax;
        }

        if (fH < 0) {
            fH = 360 + fH;
        }
        hsv[0] = fH;
        hsv[1] = fS;
        hsv[2] = fV;
    }

}
