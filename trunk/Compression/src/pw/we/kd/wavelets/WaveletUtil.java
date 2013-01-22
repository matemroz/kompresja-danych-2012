package pw.we.kd.wavelets;

public class WaveletUtil {
	public static int countNonZeroElements(double[][] mat){
		int c = 0;;
		for(int i = 0; i < mat.length; i++){
			for(int j = 0; j < mat[i].length; j++){
				if(mat[i][j] == 0.0 || mat[i][j] == 0){
					c++;
				}
			}
		}
		return c;
	}
	
	public static double calcCR(double matOrigin[][], double[][] matCalc){
		return countNonZeroElements(matOrigin)/countNonZeroElements(matCalc);
	}
}
