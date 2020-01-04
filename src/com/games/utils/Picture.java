package com.games.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Picture {
    private static Map<String, BufferedImage> cache = new HashMap<>();

    /**
     * Draw an image.
     *
     * @param g The graphics context in which to draw the image.
     * @param filepath The location of the image file.
     * @param x The x-coordinate of where the upper-left corner of the image should be drawn.
     * @param y The y-coordinate of where the upper-left corner of the image should be drawn.
     */
    public static void draw(Graphics g, String filepath, int x, int y, int width, int height) {
        try {
            BufferedImage img;
            if (cache.containsKey(filepath))
                img = cache.get(filepath);
            else {
                img = ImageIO.read(new File(filepath));
                cache.put(filepath, img);
            }
            g.drawImage(img, x, y, width, height+8,null);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
