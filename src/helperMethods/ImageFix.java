package helperMethods;

import java.awt.*;
import java.awt.image.BufferedImage;

// Clasa "ImageFix" oferă metode pentru manipularea imaginilor, cum ar fi rotirea și suprapunerea imaginilor
public class ImageFix {

    // Metoda "buildImage" combină un set de imagini într-o singură imagine, suprapunându-le în ordinea dată
    public static BufferedImage buildImage(BufferedImage[] images) {
        int width = images[0].getWidth();
        int height = images[0].getHeight();

        BufferedImage newImage = new BufferedImage(width, height, images[0].getType());
        Graphics2D g2d = newImage.createGraphics();

        // Desenează fiecare imagine peste cea anterioară
        for(BufferedImage image : images) {
            g2d.drawImage(image, 0, 0, null);
        }
        g2d.dispose();

        return newImage;
    }

    // Metoda "getBuildRotatedImage" combină un set de imagini și rotește una dintre ele în funcție de unghiul specificat
    public static BufferedImage getBuildRotatedImage(BufferedImage[] images, int rotAngle, int rotateAtIndex) {

        int width = images[0].getWidth();
        int height = images[0].getHeight();

        BufferedImage newImage = new BufferedImage(width, height, images[0].getType());
        Graphics2D g2d = newImage.createGraphics();

        for(int i = 0; i < images.length; i++) {
            // Dacă indexul imaginii curente este egal cu "rotateAtIndex", rotește imaginea
            if(rotateAtIndex == i)
                g2d.rotate(Math.toRadians(rotAngle), (double) width /2, (double) height /2);
            g2d.drawImage(images[i], 0, 0, null);
            // După ce a rotit imaginea, resetarea unghiului de rotire pentru a nu afecta imaginile ulterioare
            if(rotateAtIndex == i)
                g2d.rotate(Math.toRadians(-rotAngle), (double) width /2, (double) height /2);
        }
        g2d.dispose();

        return newImage;
    }
}