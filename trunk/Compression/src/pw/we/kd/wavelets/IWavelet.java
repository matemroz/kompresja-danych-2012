package pw.we.kd.wavelets;

public interface IWavelet {
	void transform(double[][] mat, int start, int it);
	void invTransform(double[][] mat, int start, int it);
}
