package pw.we.kd.img;

import pw.we.kd.wavelets.Haar;

public class ImageTransform {
	public static void main(String[] args) {
		GrayScaleImage img = new GrayScaleImage("lenagray.bmp");
		Haar haar = new Haar();
		haar.transform(img, 1);
		img.save("lenka_conv.bmp");
		haar.invTransform(img, 8);
		img.save("lenka_inv.bmp");
	}
}
