package javaproject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class urlTool {
    public ImageIcon getImageIcon(String url) {
        InputStream is = getClass().getResourceAsStream(url);
        BufferedImage img = null;
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ImageIcon(img);
    }

}
