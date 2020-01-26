public class Layer {
	public Neuron Neurons[];
	public int len;
	public Layer(int l, int p)
	{
		len = l;
		Neurons = new Neuron[l];		
		for(int i = 0; i < len; i++)
			Neurons[i] = new Neuron(p);
	}
}
