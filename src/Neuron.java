import java.util.*;
public class Neuron {
	public double[] weights;
	public double bias;
	public double del;
	public double val;
	public Neuron(int p) {
		Random r = new Random();
			weights = new double[p];
			bias = r.nextDouble()*0.0000000001;
			del = r.nextDouble()*0.0000000001;
			val = r.nextDouble()*0.0000000001;
			for(int i = 0; i<weights.length;i++) {
				weights[i] = r.nextDouble()*0.0000000001;
			}
		}
	}
