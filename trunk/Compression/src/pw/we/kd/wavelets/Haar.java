package pw.we.kd.wavelets;

public class Haar implements IWavelet {

	public void transform(double[][] mat, int start, int end) {
		if (start > end) {
			int row = mat.length;
			int col = mat[0].length;

			for (int i = 0; i < row; i++) {
				int w = start;
				do {
					double a[] = new double[w];

					for (int k = 0; k < w; k++) {
						a[k] = mat[i][k];
					}

					for (int j = 0; j < w / 2; j++)
						a[j] = (mat[i][2 * j] + mat[i][2 * j + 1]) / 2;
					for (int j = w / 2, k = 0; j < w; j++, k += 2)
						a[j] = mat[i][k] - a[j - w / 2];

					for (int j = 0; j < a.length; j++)
						mat[i][j] = a[j];
					w = w / 2;
				} while (w != end);
			}

			for (int i = 0; i < col; i++) {
				int w = start;
				do {
					double a[] = new double[w];
					for (int k = 0; k < w; k++) {
						a[k] = mat[k][i];
					}

					for (int j = 0; j < w / 2; j++)
						a[j] = (mat[2 * j][i] + mat[2 * j + 1][i]) / 2;
					for (int j = w / 2, k = 0; j < w; j++, k += 2)
						a[j] = mat[k][i] - a[j - w / 2];

					for (int j = 0; j < a.length; j++)
						mat[j][i] = a[j];
					w = w / 2;
				} while (w != end);
			}
		} else {
			System.err.println("Zle dane wejsciowe: start >= end");
		}
	}

	public void invTransform(double[][] mat, int start, int end) {
		if (start < end) {
			int row = mat.length;
			int col = mat[0].length;

			for (int i = 0; i < row; i++) {
				int w = start;
				do {
					double b[] = new double[w * 2];
					for (int j = 0; j < b.length; j++)
						b[j] = mat[i][j];

					for (int j = 0, k = 0; j < w * 2; j = j + 2, k++) {
						mat[i][j] = b[k] + mat[i][k + w];
						mat[i][j + 1] = b[k] - mat[i][k + w];
					}
					w = w * 2;
				} while (w != end);
			}

			for (int i = 0; i < col; i++) {
				int w = start;
				do {
					double b[] = new double[w * 2];
					for (int j = 0; j < w * 2; j++)
						b[j] = mat[j][i];

					for (int j = 0, k = 0; j < w * 2; j = j + 2, k++) {
						mat[j][i] = b[k] + mat[k + w][i];
						mat[j + 1][i] = b[k] - mat[k + w][i];
					}
					w = w * 2;
				} while (w != end);
			}
		} else {
			System.err.println("Zle dane wejsciowe: start <= end");
		}
	}
}