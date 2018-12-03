package fr.cesi.pimpmyfridge.model;

public class Model {
	
	private double humidity;
	private double tempOut;
	private double tempIn;
	private double tempPeltier;


	public Model(double humidity, double tempIn, double tempOut, double tempPeltier) {
		this.humidity = humidity;
		this.tempOut = tempOut;
		this.tempIn = tempIn;
		this.tempPeltier = tempPeltier;
	}
	
	
	public double getHumidityPercent() {
		return humidity;
	}

	
	public double getOutsideTemp() {
		return tempOut;
	}

	
	public double getDHTTemp() {
		return tempIn;
	}
	
	public double getPeltierTemp() {
		return tempPeltier;
	}
	
	@Override
	public String toString() {
		return String.format("Tin=%s°C Tpelt=%s°C Tout=%s°C H=%s%%", tempIn, tempPeltier, tempOut, humidity);
	}

}
