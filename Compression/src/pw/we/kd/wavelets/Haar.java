package pw.we.kd.wavelets;

import pw.we.kd.img.GrayScaleImage;
import pw.we.kd.utils.MatrixTransformations;

public class Haar {
	public static final double SCAL_FUNC_COEF_H0 = 0.5;
	public static final double SCAL_FUNC_COEF_H1 = 0.5;
	public static final double WAVELET_FUNC_COEF_G0 = 0.5;
	public static final double WAVELET_FUNC_COEF_G1 = -0.5;
	public static final double SCAL_INV_COEF_T0 = 1.0;
	public static final double SCAL_INV_COEF_T1 = -1.0;
	public static final double WAVELET_INV_COEF_W0 = 1.0;
	public static final double WAVELET_INV_COEF_W1 = 1.0;

	public void transform(GrayScaleImage img, int iter) {
		int col = img.getWidth();
		int row = img.getHeight();
		double[][] mat = img.getData();

		for (int i = 0; i < row; i++) {
			int w = col;
			do {
				double b[] = new double[w];
				for (int k = 0; k < w; k++) {
					b[k] = mat[i][k];
				}
				double trM[][] = createTransformMatrix(w);
				b = MatrixTransformations.multiply(trM, b);
				for (int j = 0; j < b.length; j++)
					mat[i][j] = b[j];
				w = w / 2;
			} while (w != iter);
		}

		for (int i = 0; i < col; i++) {
			int w = row;
			do {
				double b[] = new double[w];
				for (int k = 0; k < w; k++) {
					b[k] = mat[k][i];
				}
				double trM[][] = createTransformMatrix(w);
				b = MatrixTransformations.multiply(trM, b);
				for (int j = 0; j < b.length; j++)
					mat[j][i] = b[j];
				w = w / 2;
			} while (w != iter);
		}
	}

	public void invTransform(GrayScaleImage img, int iter) {
		int col = img.getWidth();
		int row = img.getHeight();
		double[][] mat = img.getData();

		for (int i = 0; i < row; i++) {
			int w = 1;
			for (int m = 0; m < iter; m++) {

				double b[] = new double[w * 2];
				
				for(int j = 0; j < b.length; j++)
					b[j] = mat[i][j];

				for (int j = 0, k = 0; j < w * 2; j=j+2, k++) {
					mat[i][j] = b[k] + mat[i][k + w];
					mat[i][j + 1] = b[k] - mat[i][k + w];
				}
				w = w * 2;
			}
		}
		
		for (int i = 0; i < col; i++) {
			int w = 1;
			for (int m = 0; m < iter; m++) {

				double b[] = new double[w * 2];
				
				for(int j = 0; j < b.length; j++)
					b[j] = mat[j][i];

				for (int j = 0, k = 0; j < w * 2; j=j+2, k++) {
					mat[j][i] = b[k] + mat[k + w][i];
					mat[j + 1][i] = b[k] - mat[k + w][i];
				}
				
				w = w * 2;
			}
		}
	}

	public double[][] createTransformMatrix(int size) {
		double[][] trM = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (j == 2 * i)
					trM[i][j] = SCAL_FUNC_COEF_H0;
				if (j == 2 * i + 1)
					trM[i][j] = SCAL_FUNC_COEF_H1;
				if (i >= size / 2 && j == 2 * (i - size / 2))
					trM[i][j] = WAVELET_FUNC_COEF_G0;
				if (i >= size / 2 && j == 2 * (i - size / 2) + 1)
					trM[i][j] = WAVELET_FUNC_COEF_G1;
			}
		}
		return trM;
	}

	public double[][] createInversionMatrix(int size) {
		double[][] trM = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (j == 2 * i)
					trM[i][j] = WAVELET_INV_COEF_W0;
				if (j == 2 * i + 1)
					trM[i][j] = WAVELET_INV_COEF_W1;
				if (i >= size / 2 && j == 2 * (i - size / 2))
					trM[i][j] = SCAL_INV_COEF_T0;
				if (i >= size / 2 && j == 2 * (i - size / 2) + 1)
					trM[i][j] = SCAL_INV_COEF_T1;
			}
		}
		return trM;
	}
}