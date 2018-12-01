package fr.exia.pimpmyfridge.model;
import java.util.ArrayList;

public class Database {

	// New Array for temperatures from Peltier Module
	private ArrayList<Value> tempPeltier = new ArrayList<>();
	
	// New Array for temperatures from outside the Fridge
	private ArrayList<Value> tempOutside = new ArrayList<>();
	
	// New Array for temperatures from the DHT Module
	private ArrayList<Value> tempDHT = new ArrayList<>();
	
	// Get the Array for tempPeltier
	public ArrayList<Value> getTempPeltier() {
		return tempPeltier;
	}

	// Add value in the tempPeltier array
	public void addTempPeltierArray(Value tempPeltier) {
		this.tempPeltier.add(tempPeltier);
	}

	// Get the array of tempOutside
	public ArrayList<Value> getTempOutside() {
		return tempOutside;
	}

	// Add value in the tempOutside array
	public void addTempOutside(Value tempOutside) {
		this.tempOutside.add(tempOutside);
	}
	
	
	// Get the array of tempDHT
	public ArrayList<Value> getTempDHT() {
		return tempDHT;
	}

	// Add value in the tempDHT array
	public void addTempDHT(Value tempDHT) {
		this.tempDHT.add(tempDHT);
	}
	
}
