public class Polynomial{
	
	double[] coef;
	
	public Polynomial() {
		coef = new double[1];
		coef[0] = 0;
	}
	public Polynomial(double[] array) {
		coef = new double[array.length];
		for(int i = 0;i < array.length;i++) {
			coef[i] = array[i];
		} 
	}
	public Polynomial add(Polynomial poly) {
		int maxLength = Math.max(coef.length, poly.coef.length);
		int minLength = Math.min(coef.length, poly.coef.length);
		double[] resultArray = new double[maxLength];
		
		for(int i = 0;i < minLength;i++) {
			resultArray[i] = coef[i] + poly.coef[i];
			}
		if (coef.length < poly.coef.length) {
			for(int i = coef.length;i < poly.coef.length;i++) {
				resultArray[i] += poly.coef[i];
			}
		}
		else if (coef.length > poly.coef.length) {
			for(int i = poly.coef.length;i < coef.length;i++) {
				resultArray[i] += coef[i];
			}
		}
		return new Polynomial(resultArray);
	}
	public double evaluate(double x) {
		double value = 0;
		for(int i = 0;i < coef.length;i++) {
			value += coef[i]*Math.pow(x, i);
		}
		return value;
	}
	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}
}