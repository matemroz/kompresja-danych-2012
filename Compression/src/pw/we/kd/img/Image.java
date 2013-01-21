package pw.we.kd.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {

	protected int width;
	protected int height;
	protected BufferedImage bfImg;
	protected int type;

	public Image(String path) {
		try {
			this.bfImg = ImageIO.read(new File(path));
			this.type = bfImg.getType();
			this.width = bfImg.getWidth();
			this.height = bfImg.getHeight();
			System.out.println("type: " + this.type + " " + this.width + " " + this.height);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected int getHeight() {
		return height;
	}

	protected void setHeight(int height) {
		this.height = height;
	}

	protected int getWidth() {
		return width;
	}

	protected void setWidth(int width) {
		this.width = width;
	}
}
