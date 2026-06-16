package com.kasha;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class StartupImages {

    @PostConstruct
    public void init() throws IOException {
        String staticDir = System.getenv("STATIC_DIR");
        if (staticDir == null) {
            staticDir = "static";
        }
        Path imgDir = Paths.get(staticDir, "img");
        if (!imgDir.toFile().exists()) {
            imgDir.toFile().mkdirs();
        }

        Path imgPath = imgDir.resolve("kasha.jpg");
        if (!imgPath.toFile().exists()) {
            BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = img.createGraphics();
            g.setColor(new Color(102, 126, 234));
            g.fillRect(0, 0, 800, 600);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics fm = g.getFontMetrics();
            g.drawString("Ka'sha", 400 - fm.stringWidth("Ka'sha") / 2, 280);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            fm = g.getFontMetrics();
            g.drawString("Tambor Wayuu", 400 - fm.stringWidth("Tambor Wayuu") / 2, 340);
            g.drawString("Riohacha, La Guajira", 400 - fm.stringWidth("Riohacha, La Guajira") / 2, 390);
            g.dispose();
            ImageIO.write(img, "jpg", imgPath.toFile());
            System.out.println("Imagen placeholder generada: " + imgPath.toAbsolutePath());
        }
    }
}
