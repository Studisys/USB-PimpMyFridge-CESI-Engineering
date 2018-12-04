package fr.cesi.pimpmyfridge.model;

public class Model {
	
	// Variables
	private double tempIn; // Temperature (DHT)
	private double tempPeltier; // Temperature (Peltier Module / Termistor)
	private double tempOut; // Temperature (DHT Out)
	private double tempDew; // Condensation temperature (dewpoint)
	private double humidity; // Humidity (DHT)


	// Method to set the variables
	public Model(double tempIn, double tempPeltier, double tempOut, double tempDew, double humidity) {
		this.tempIn = tempIn;
		this.tempPeltier = tempPeltier;
		this.tempOut = tempOut;
		this.tempDew = tempDew;
		this.humidity = humidity;		
	}
	
	// Return the humidity
	public double getHumidityPercent() {
		return humidity;
	}

	// Return the Outside Temperature
	public double getOutsideTemp() {
		return tempOut;
	}

	// Return the Internal Temperature
	public double getDHTTemp() {
		return tempIn;
	}
	
	// Return the Temperature from Peltier Module
	public double getPeltierTemp() {
		return tempPeltier;
	}
	
	
	// Return the Dew temperature (condensation temperature)
	public double getDewTemp()
	{
		return tempDew;
	}
	
	
	// Return a pre-formatted string
	@Override
	public String toString() {
		return String.format("Tin=%s°C Tpelt=%s°C Tout=%s°C Tdew=%s°C H=%s%%", tempIn, tempPeltier, tempOut, tempDew, humidity);
	}

}
