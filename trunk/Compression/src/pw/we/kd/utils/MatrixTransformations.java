package pw.we.kd.utils;

public class MatrixTransformations {
	public static double[] multiply(double[][] matrix, double[] tab){
		if(matrix.length != matrix[0].length || tab.length != matrix.length){
			System.err.println("Wrong input values");
			return null;
		}
		
		double[] tabM = new double[matrix.length];
		
		for(int i = 0; i < matrix.length; i++){
			double a = 0.0;
			for(int j = 0; j < matrix.length; j++){
				a += matrix[i][j] * tab[j];
			}
			tabM[i] = a;
		}
		return tabM;
	}
}
