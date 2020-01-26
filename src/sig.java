public class sig implements Activation 
{
	public double eval(double val) 
	{
		return 1 / (1 + Math.pow(Math.E, - val));
	}
	public double evalD(double val) 
	{
		return (val - Math.pow(val, 2));
	}
}