package pw.we.kd.wavelets;

public class Daub {

	public static final double h0 = (1 + Math.sqrt(3)) / (4 * Math.sqrt(2));
	public static final double h1 = (3 + Math.sqrt(3)) / (4 * Math.sqrt(2));
	public static final double h2 = (3 - Math.sqrt(3)) / (4 * Math.sqrt(2));
	public static final double h3 = (1 - Math.sqrt(3)) / (4 * Math.sqrt(2));
	public static final double g0 = h3;
	public static final double g1 = -h2;
	public static final double g2 = h1;
	public static final double g3 = -h0;

	public static final double Ih0 = h2;
	public static final double Ih1 = g2;
	public static final double Ih2 = h0;
	public static final double Ih3 = g0;
	public static final double Ig0 = h3;
	public static final double Ig1 = g3;
	public static final double Ig2 = h1;
	public static final double Ig3 = g1;
	
	public void transform(double s[][], int start, int end) {
		
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
				s[i][j] = s[i][j] * end/start * 0.5;
			}
		}
		
		int N = s.length;
		int n;
		for (n = start; n >= end; n = n/2) {
			for (int i = 0; i < N; i++) {
				if (n >= 4) {
					int m, j;
					int h = n/2;

					double tmp[] = new double[n];

					m = 0;
					for (j = 0; j < n - 3; j = j + 2) {
						tmp[m] = s[i][j] * h0 + s[i][j + 1] * h1 + s[i][j + 2] * h2 + s[i][j + 3] * h3;
						tmp[m + h] = s[i][j] * g0 + s[i][j + 1] * g1 + s[i][j + 2] * g2 + s[i][j + 3] * g3;
						m++;
					}

					tmp[m] = s[i][n - 2] * h0 + s[i][n - 1] * h1 + s[i][0] * h2 + s[i][1] * h3;
					tmp[m + h] = s[i][n - 2] * g0 + s[i][n - 1] * g1 + s[i][0] * g2 + s[i][1] * g3;

					for (m = 0; m < tmp.length; m++) {
						s[i][m] = tmp[m];
					}
				}
			}
		}
		
		for (n = start; n >= end; n = n/2) {
			for (int i = 0; i < N; i++) {
				if (n >= 4) {
					int m, j;
					int h = n/2;

					double tmp[] = new double[n];

					m = 0;
					for (j = 0; j < n - 3; j = j + 2) {
						tmp[m] = s[j][i] * h0 + s[j + 1][i] * h1 + s[j + 2][i] * h2 + s[j + 3][i] * h3;
						tmp[m + h] = s[j][i] * g0 + s[j + 1][i] * g1 + s[j + 2][i] * g2 + s[j + 3][i] * g3;
						m++;
					}

					tmp[m] = s[n - 2][i] * h0 + s[n - 1][i] * h1 + s[0][i] * h2 + s[1][i] * h3;
					tmp[m + h] = s[n - 2][i] * g0 + s[n - 1][i] * g1 + s[0][i] * g2 + s[1][i] * g3;

					for (m = 0; m < tmp.length; m++) {
						s[m][i] = tmp[m];
					}
				}
				
			}
			for (int i = 0; i < s.length; i++) {
				for (int j = 0; j < s[i].length; j++) {
					if(s[i][j] > 255.0){
						s[i][j] = 255.0;
					}
				}
			}
		}
	/*	for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
				s[i][j] = Math.round(s[i][j]);
				if (s[i][j] > 255) {
					s[i][j] = 255;
				} else if (s[i][j] < 0) {
					s[i][j] = 0;
				}
			}
		}*/
	}
	
	public void invTransform(double s[][], int start, int end) {
		
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
					s[i][j] = s[i][j] * end/start * 2;
			}
		}
		final int N = s.length;
		int n;
		for (n = start; n <= end; n = n * 2) {
			for (int i = 0; i < s.length; i++) {
				if (n >= 4) {
					int m, j;
					int h = n/2;
					int hP = h + 1;

					double tmp[] = new double[n];

					tmp[0] = s[i][h - 1] * Ih0 + s[i][n - 1] * Ih1 + s[i][0] * Ih2 + s[i][h]
							* Ih3;
					tmp[1] = s[i][h - 1] * Ig0 + s[i][n - 1] * Ig1 + s[i][0] * Ig2 + s[i][h]
							* Ig3;
					j = 2;
					for (m = 0; m < h - 1; m++) {
						tmp[j++] = s[i][m] * Ih0 + s[i][m + h] * Ih1 + s[i][m + 1] * Ih2
								+ s[i][m + hP] * Ih3;
						tmp[j++] = s[i][m] * Ig0 + s[i][m + h] * Ig1 + s[i][m + 1] * Ig2
								+ s[i][m + hP] * Ig3;
					}
					for (m = 0; m < n; m++) {
						s[i][m] = tmp[m];
					}
				}
			}
		}
		
		for (n = start; n <= end; n = n * 2) {
			for (int i = 0; i < s.length; i++) {
				if (n >= 4) {
					int m, j;
					int h = n/2;
					int hP = h + 1;

					double tmp[] = new double[n];

					tmp[0] = s[h - 1][i] * Ih0 + s[n - 1][i] * Ih1 + s[0][i] * Ih2 + s[h][i]
							* Ih3;
					tmp[1] = s[h - 1][i] * Ig0 + s[n - 1][i] * Ig1 + s[0][i] * Ig2 + s[h][i]
							* Ig3;
					j = 2;
					for (m = 0; m < h - 1; m++) {
						tmp[j++] = s[m][i] * Ih0 + s[m + h][i] * Ih1 + s[m + 1][i] * Ih2
								+ s[m + hP][i] * Ih3;
						tmp[j++] = s[m][i] * Ig0 + s[m + h][i] * Ig1 + s[m + 1][i] * Ig2
								+ s[m + hP][i] * Ig3;
					}
					for (m = 0; m < n; m++) {
						s[m][i] = tmp[m];
					}
				}
			}
		}

	/*	for (int i = 0; i < coef.length; i++) {
			for (int j = 0; j < coef[i].length; j++) {
				coef[i][j] = Math.round(coef[i][j]);
					if (coef[i][j] > 255) {
						coef[i][j] = 255;
					} else if (coef[i][j] < 0) {
						coef[i][j] = 0;
					}
			}
		}*/
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
		d.transform(S, 8, 4);

		System.out.println("TRANS");
		for (int i = 0; i < S.length; i++) {
			for (int j = 0; j < S[i].length; j++) {
				System.out.print(S[i][j] + " ");
			}
			System.out.println();
		}
		d.invTransform(S, 4, 8);

		System.out.println("INV");
		for (int i = 0; i < S.length; i++) {
			for (int j = 0; j < S[i].length; j++) {
				System.out.print(S[i][j] + " ");
			}
			System.out.println();
		}

	}
} 

