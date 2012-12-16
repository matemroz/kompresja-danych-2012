package pw.we.kd.wavelets;

public class Daub {

	public static final double H0 = (1 + Math.sqrt(3)) / (4 * Math.sqrt(2));
	public static final double H1 = (3 + Math.sqrt(3)) / (4 * Math.sqrt(2));
	public static final double H2 = (3 - Math.sqrt(3)) / (4 * Math.sqrt(2));
	public static final double H3 = (1 - Math.sqrt(3)) / (4 * Math.sqrt(2));
	public static final double G0 = H3;
	public static final double G1 = -H2;
	public static final double G2 = H1;
	public static final double G3 = -H0;

	public static final double IH0 = H2;
	public static final double IH1 = G2;
	public static final double IH2 = H0;
	public static final double IH3 = G0;
	public static final double IG0 = H3;
	public static final double IG1 = G3;
	public static final double IG2 = H1;
	public static final double IG3 = G1;

	private void forward(double line[], int n) {
		if (n >= 4) {
			int i, j;

			double a[] = new double[n];
			int h = n / 2;

			i = 0;
			for (j = 0; j < n - 3; j = j + 2) {
				a[i] = line[j] * H0 + line[j + 1] * H1 + line[j + 2] * H2
						+ line[j + 3] * H3;
				a[i + h] = line[j] * G0 + line[j + 1] * G1 + line[j + 2]
						* G2 + line[j + 3] * G3;
				i++;
			}

			a[i] = line[n - 2] * H0 + line[n - 1] * H1 + line[0] * H2 + line[1]
					* H3;
			a[i + h] = line[n - 2] * G0 + line[n - 1] * G1 + line[0] * G2
					+ line[1] * G3;

			for (i = 0; i < n; i++) {
				line[i] = a[i];
			}
		}
	}

	private void backward(double line[], int n) {
		if (n >= 4) {
			int i, j;
			int h = n / 2;
			int hp = h + 1;

			double a[] = new double[n];

			a[0] = line[h - 1] * IH0 + line[n - 1] * IH1 + line[0] * IH2
					+ line[h] * IH3;
			a[1] = line[h - 1] * IG0 + line[n - 1] * IG1 + line[0] * IG2
					+ line[h] * IG3;
			j = 2;
			for (i = 0; i < h - 1; i++) {
				a[j++] = line[i] * IH0 + line[i + h] * IH1 + line[i + 1]
						* IH2 + line[i + hp] * IH3;
				a[j++] = line[i] * IG0 + line[i + h] * IG1 + line[i + 1]
						* IG2 + line[i + hp] * IG3;
			}
			for (i = 0; i < n; i++) {
				line[i] = a[i];
			}
		}
	}

	public void transform(double mat[][], int start, int end) {
		int n;
		if(start < end){
			System.err.println("Podano niepoprawne dane wejsciowe: start < end");
		}
		for (n = start; n >= end; n=n/2) {
			for (int i = 0; i < n; i++) {
				forward(mat[i], n);
			}
		}
	}

	public void invTransform(double mat[][], int start, int end) {
		int n;
		if(start > end){
			System.err.println("Podano niepoprawne dane wejsciowe: start > end");
		}
		for (n = start; n <= end; n=n*2) {
			for (int i = 0; i < n; i++) {
				backward(mat[i], n);
			}
		}
	}

	public static void main(String[] args) {
		double S[][] = { { 64, 2, 3, 61, 60, 6, 7, 57 },
				{ 9, 55, 54, 12, 13, 51, 50, 16 },
				{ 17, 47, 46, 20, 21, 43, 42, 24 },
				{ 40, 26, 27, 37, 36, 30, 31, 33 },
				{ 32, 34, 35, 29, 28, 38, 39, 25 },
				{ 41, 23, 22, 44, 45, 19, 18, 48 },
				{ 49, 15, 14, 52, 53, 11, 10, 56 },
				{ 8, 58, 59, 5, 4, 62, 63, 1 } };


		Daub d = new Daub();
		d.transform(S, 8, 2);
		d.invTransform(S, 2, 8);

		for (int i = 0; i < S.length; i++) {
			for (int j = 0; j < S[i].length; j++) {
				System.out.print(S[i][j] + " ");
			}
			System.out.println();
		}
	}
}
