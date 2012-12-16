package pw.we.kd.img;

import pw.we.kd.wavelets.Daub;
import pw.we.kd.wavelets.Haar;

public class ImageTransform {
	
	public static void main(String[] args) {
		GrayScaleImage img = new GrayScaleImage("lenagray.bmp");
		Haar haar = new Haar();
		Daub d = new Daub();
		//d.transform(img, 0, img.getHeight());
		double[][] mat = img.getData();
	//	d.transform(mat);
		img.save("lenka_d4");
	//	d.invDaubTrans(mat);
		img.save("lenka_d4_inv.bmp");
		//d.invTransform(img, 0, img.getHeight());
		//haar.invTransform(img, 2);
		//img.save("lenka_inv.bmp");
	}
}
