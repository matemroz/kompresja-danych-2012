package pw.we.kd.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImageViewer extends JPanel {

	private static final long serialVersionUID = -9062296937575060410L;

    private BufferedImage image;

    public ImageViewer(String filepath) {
       try {                
          image = ImageIO.read(new File(filepath));
          this.setLayout(null);
       } catch (IOException ex) {
            System.err.println("Image load problem...");
       }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }
}
