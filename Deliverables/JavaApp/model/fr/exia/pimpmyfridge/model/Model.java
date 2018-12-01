package fr.exia.pimpmyfridge.model;
@SuppressWarnings("unused")
public class Model {

	
	private double tempPeltier; // Peltier Temperature from termistor Variable
	private double tempOutside; // Outside Temperature from termistor Variable
	private double tempDHT; // Temperature from DHT Variable
	private double humidity; // Humidity Variable
	private double targetTemp;

    private int tempMiniValue = 5; // Minimal Temperature Value
	private int tempMaxiValue = 30; // Maximal Temperature Value
	
	private boolean isDoorOpen; // Boolean : is fridge door open ?
	private boolean isCondensation; // Boolean : is there a risk for condensation ?
	private boolean isTempAnomaly; // Boolean : is there an abnormal temperature ?

	private boolean isAutoPilot; // Boolean : should the Arduino auto manage temperature or not ?
	
	
	private Database DB = new Database(); // Declare the Database Object named DB
	
	// ==== METHODS ========
	
	////////////: SETTERS
	
	// Set the target temperature
	public void setTargetTemp(int targetTemp) {
		this.targetTemp = targetTemp;
		System.out.println("New Target Temp :" + targetTemp);
	}
	
	// Set the humidity value
	public void setHumidity(double humidity) {
		this.humidity = humidity;
		System.out.println("New Humidity :" + humidity);
	}
	
	// Set the Peltier Temperature value
	public void setTempPeltier(double tempPeltier) {
		this.tempPeltier = tempPeltier;
		System.out.println("Peltier Temperature :" + tempPeltier);
	}
	
	// Set the Outside Temperature value
	public void setTempOutside(double tempOutside) {
		this.tempOutside = tempOutside;
		System.out.println("Outside Temperature :" + tempOutside);
	}
	
	// Set the DHT Temperature value
	public void setTempDHT(double tempDHT) {
		this.tempDHT = tempDHT;
		System.out.println("DHT Temperature :" + tempOutside);
	}
	
	
	// Set AutoPilot Mode for Arduino
	public void setAutoPilot(boolean isAutoPilot)
	{
		this.isAutoPilot = isAutoPilot;
		System.out.println("AutoPilot Status :" + isAutoPilot);
	}
	
	
	/////////////    GETTERS
	
	// Get the target temperature
	public double getTargetTemperature()
	{
		return targetTemp;
	}
	
	public double getTempPeltier()
	{
		return tempPeltier;
	}
	
	public double getTempOutside()
	{
		return tempOutside;
	}
	
	public double getTempDHT()
	{
		return tempDHT;
	}
	
	public double getHumidity()
	{
		return humidity;
	}
	
	public int getMiniTempValue()
	{
		return tempMiniValue;
	}
	
	public int getMaxiTempValue()
	{
		return tempMaxiValue;
	}
	
	public boolean getDoorStatus()
	{
		return isDoorOpen;
	}
	
	public boolean getCondensationStatus()
	{
		return isCondensation;
	}
	
	public boolean getTempAnomaly()
	{
		return isTempAnomaly;
	}
	
	
}
