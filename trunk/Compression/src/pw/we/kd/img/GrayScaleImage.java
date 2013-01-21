package pw.we.kd.img;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GrayScaleImage extends Image {

	private double[][] data;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public GrayScaleImage(String path) {
		super(path);
		Raster raster = this.bfImg.getData();
		this.data = new double[this.width][this.height];
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				this.data[i][j] = raster.getSample(i, j, 0);
			}
		}
	}

	public double[] getColumn(int index) {
		double column[] = new double[this.width];

		if (index < 0 || index > this.height) {
			System.err.println("Out of bounds argument given.");
			return null;
		}

		for (int i = 0; i < this.height; i++) {
			if (i == index) {
				for (int j = 0; j < this.width; j++) {
					column[j] = this.data[i][j];
				}
			}
		}
		return column;
	}

	public void setColumn(int index, double[] column) {
		if (index < 0 || index > this.height) {
			System.err.println("Out of bounds argument given.");
		}

		for (int i = 0; i < this.height; i++) {
			if (i == index) {
				for (int j = 0; j < this.width; j++) {
					this.data[i][j] = column[j];
				}
			}
		}
	}

	public double[] getRow(int index) {
		if (index < 0 || index > this.width) {
			System.err.println("Out of bounds argument given.");
			return null;
		}

		double row[] = new double[this.height];
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				if (j == index) {
					row[i] = this.data[i][j];
				}
			}
		}

		return row;
	}

	public void setRow(int index, double[] row) {
		if (index < 0 || index > this.width) {
			System.err.println("Out of bounds argument given.");
		}

		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				if (j == index) {
					this.data[i][j] = row[i];
				}
			}
		}
	}

	public double[][] getData() {
		return data;
	}

	public void setData(double[][] data) {
		this.data = data;
	}

	public void save(String filename) {
		try {
			BufferedImage trImage = new BufferedImage(this.width, this.height, this.type);
			WritableRaster wrRaster = trImage.getRaster();
			for (int i = 0; i < this.width; i++) {
				for (int j = 0; j < this.height; j++) {
					wrRaster.setSample(i, j, 0, data[i][j]);
				}
			}
			trImage.setData(wrRaster);
			ImageIO.write(trImage, "bmp", new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
