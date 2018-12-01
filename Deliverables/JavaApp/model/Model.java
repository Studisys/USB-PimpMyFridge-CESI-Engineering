
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
	
	// ==== METHODS ========
	
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
