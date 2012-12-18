package pw.we.kd.img;

import pw.we.kd.wavelets.Daub;
import pw.we.kd.wavelets.Haar;

public class ImageTransform {
	
	public static void main(String[] args) {
		GrayScaleImage img = new GrayScaleImage("lenagray.bmp");
		Daub daub= new Daub();
		double[][] mat = img.getData();
		daub.transform(mat, 256, 64);
		img.setData(mat);
		img.save("lenka_tr.bmp");
		daub.invTransform(mat, 64, 256);
		img.setData(mat);
		img.save("lenka_inv.bmp");
	}
}
