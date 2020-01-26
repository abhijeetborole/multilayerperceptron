import java.util.Random;

public class MLP {
	double learningRate = 0.1;
	Activation function;
	Random rand = new Random();
	Layer[]  layers;
	// f = activation function, l = number of layers
		public MLP (int[] l, double learnRate, Activation f) {
			learningRate = learnRate;
			function = f;
			layers = new Layer[l.length];
			for(int i = 0; i < l.length; i++)
			{			
				if(i==0)
				{
					layers[i] = new Layer(l[i], 0);
				}
				else
				{
					layers[i] = new Layer(l[i], l[i - 1]);				
				}
			}
		}
		
		public double[] calcOutput(double[] input) {
			int i,j,k;
			double n_out;
			double output[] = new double[layers[layers.length - 1].len]; 	
			for(i = 0; i < layers[0].len; i++)
			{
				layers[0].Neurons[i].val = input[i];
			}
			for(k = 1; k < layers.length; k++)
			{
				for(i = 0; i < layers[k].len; i++)
				{
					n_out = 0.0;
					for(j = 0; j < layers[k - 1].len; j++)
						n_out += layers[k].Neurons[i].weights[j] * layers[k - 1].Neurons[j].val;	
					n_out += layers[k].Neurons[i].bias;		
					layers[k].Neurons[i].val = function.eval(n_out);
				}
			}
			for(i = 0; i < layers[layers.length - 1].len; i++)
			{
				output[i] = layers[layers.length - 1].Neurons[i].val;
			}		
			return output;
		}
		
		public double backProp(double[] input, double[] output) {
			int i;int j;int k;
			double err;
			double n_out[] = calcOutput(input);

			for(i = 0; i < layers[layers.length - 1].len; i++)
			{
				err = output[i] - n_out[i];
				layers[layers.length - 1].Neurons[i].del = err * function.evalD(n_out[i]);
			} 	
			for(k = layers.length - 2; k >= 0; k--)
			{
				for(i = 0; i < layers[k].len; i++)
				{
					err = 0.0;
					for(j = 0; j < layers[k + 1].len; j++)
						err += layers[k + 1].Neurons[j].del * layers[k + 1].Neurons[j].weights[i];//dot product of weights & delta
									
					layers[k].Neurons[i].del = err * function.evalD(layers[k].Neurons[i].val);				
				}
				for(i = 0; i < layers[k + 1].len; i++)
				{
					for(j = 0; j < layers[k].len; j++)
						layers[k + 1].Neurons[i].weights[j] += learningRate * layers[k + 1].Neurons[i].del * layers[k].Neurons[j].val;//update weights. delta learning rule
					layers[k + 1].Neurons[i].bias += learningRate * layers[k + 1].Neurons[i].del;// update bias
				}
			}	
			err = 0.0;	
			for(i = 0; i < output.length; i++)
			{
				err += Math.abs(n_out[i] - output[i]);//lite
			}
			return err/output.length;
			
		}
}
