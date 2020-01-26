public class tanh implements Activation 
{
	public double eval(double val) 
	{
		return Math.tanh(val);
	}
	public double evalD(double val) 
	{
		return 1 - Math.pow(val, 2);
	}
}