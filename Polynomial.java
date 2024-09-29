import java.io.*;
import java.util.*;


public class Polynomial{
	
	double[] coef;
	int[] exponent;
	
	public Polynomial() {
		coef = new double[]{0};
		exponent = new int[]{0};
	}
	
	public Polynomial(double[] newCoefs, int[] newExp) {
		coef = new double[newCoefs.length];
		for (int i = 0;i < newCoefs.length;i++) {
			coef[i] = newCoefs[i];
		}
		exponent = new int[newExp.length];
		for (int i = 0;i < newExp.length;i++) {
			exponent[i] = newExp[i];
		}
	}
	
	public Polynomial(File polyText) throws IOException {
		BufferedReader read = new BufferedReader(new FileReader(polyText));
		String line = read.readLine();
		read.close();
		
		ArrayList<Integer> newExps = new ArrayList<>();
		ArrayList<Double> newCoefs = new ArrayList<>();
		
		String[] terms = line.split("(?=[+-])");
		
		for (int i = 0;i < terms.length;i++) {
			String term = terms[i].trim();
			double coef = 0;
			int exp = 0;
			
			
			if (!term.contains("x")) {
				coef = Double.parseDouble(term);
				exp = 0;
			}
			else {
				String[] parts = term.split("x");
				
				//Check the Coef
				if (parts[0].isEmpty() || parts[0].equals("+")) {
					coef = 1;
				}
				else if (parts[0].equals("-")) {
					coef = -1;
				}
				else {
					coef = Double.parseDouble(parts[0]);
				}
					
				//Check the Exp
				if (parts.length == 1 || parts[1].isEmpty()) {
					exp = 1;
				}
				else {
					exp = Integer.parseInt(parts[1]);
				}
			}
			
			newCoefs.add(coef);
			newExps.add(exp);
		}
		
		coef = new double[newCoefs.size()];
		exponent = new int[newExps.size()];
		
		for (int i = 0;i < coef.length;i++) {
			coef[i] = newCoefs.get(i);
			exponent[i] = newExps.get(i);
		}
	}
	
	public void saveToFile(String fileName) throws IOException {
		FileWriter output = new FileWriter(fileName);
		
		StringBuilder poly = new StringBuilder();
		
		for (int i = 0;i < coef.length;i++) {
			if (i > 0 && coef[i] > 0) {
				poly.append("+");
			}
			poly.append(coef[i]);
			if (exponent[i] != 0.0) {
				poly.append("x");
				if (exponent[i] > 1) {
					poly.append("^");
					poly.append(exponent[i]);
				}
			}
		}
		output.write(poly.toString());
		output.close();
		
	}
	
	public Polynomial add(Polynomial other) {
		ArrayList<Integer> newExp = new ArrayList<>();
		ArrayList<Double> newCoef = new ArrayList<>();
		
		for (int i = 0;i < this.coef.length;i++) {
			newCoef.add(this.coef[i]);
			newExp.add(this.exponent[i]);
		}
		for (int i = 0;i < other.coef.length;i++) {
			boolean seen = false;
			for (int j = 0;j < newExp.size();j++) {
				if (newExp.get(j) == other.exponent[i]) {
					newCoef.set(j, newCoef.get(j) + other.coef[i]);
					seen = true;
					break;
				}
			}
			if (!seen) {
				newCoef.add(other.coef[i]);
				newExp.add(other.exponent[i]);
			}
		}
		
		for (int i = 0;i < newCoef.size();i++) {
			if (newCoef.get(i) == 0.0) {
				newCoef.remove(i);
				newExp.remove(i);
			}
		}
			
		double[] updatedCoef = new double[newCoef.size()];
		int[] updatedExp = new int[newExp.size()];
		
		for (int i = 0;i < newCoef.size();i++) {
			updatedCoef[i] = newCoef.get(i);
			updatedExp[i] = newExp.get(i);
		}
		
		return new Polynomial(updatedCoef, updatedExp);
	}
	
	public Polynomial multiply(Polynomial other) {
		ArrayList<Integer> newExps = new ArrayList<>();
		ArrayList<Double> newCoefs = new ArrayList<>();
		
		for (int i = 0;i < this.coef.length;i++) {
			for (int j = 0;j < other.coef.length;j++) {
				double currentCoef = this.coef[i] * other.coef[j];
				int currentExp = this.exponent[i] + other.exponent[j];
				boolean seen = false;
				for (int k = 0;k < newExps.size();k++) {
					if(currentExp == newExps.get(k)) {
						newCoefs.set(k, currentCoef + newCoefs.get(k));
						seen = true;
						break;
					}
				}
				if (!seen) {
					newCoefs.add(currentCoef);
					newExps.add(currentExp);
				}
			}
		}
		
		for (int i = 0;i < newCoefs.size();i++) {
			if (newCoefs.get(i) == 0.0) {
				newCoefs.remove(i);
				newExps.remove(i);
			}
		}
		int[] updatedExps = new int[newExps.size()];
		double[] updatedCoefs = new double[newCoefs.size()];
		
		for (int i = 0;i < newExps.size();i++) {
			updatedExps[i] = newExps.get(i);
			updatedCoefs[i] = newCoefs.get(i);
		}
		return new Polynomial(updatedCoefs, updatedExps);
	}
	
	public double evaluate(double x) {
		double value = 0;
		for (int i = 0;i < coef.length;i++) {
			value += coef[i]*Math.pow(x, exponent[i]);
		}
		return value;
	}
	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}
}