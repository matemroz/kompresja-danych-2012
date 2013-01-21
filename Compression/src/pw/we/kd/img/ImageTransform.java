package pw.we.kd.img;

import pw.we.kd.wavelets.Daub;

public class ImageTransform {
	
	public static void main(String[] args) {

		GrayScaleImage img1 = new GrayScaleImage("lenagray.bmp");
		Daub daub1= new Daub();
		double[][] mat1 = img1.getData();
		daub1.transform(mat1, 256, 128);
		img1.setData(mat1);
		img1.save("lenka_tr.bmp");
		
		GrayScaleImage img2 = new GrayScaleImage("lenka_tr.bmp");
		Daub daub2= new Daub();
		double[][] mat2 = img2.getData();
	
		daub1.invTransform(mat2, 128, 256);
		img2.setData(mat2);
		img2.save("lenka_inv.bmp");
	
	}
}
