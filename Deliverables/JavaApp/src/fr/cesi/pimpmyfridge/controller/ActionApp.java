// Package
package fr.cesi.pimpmyfridge.controller;

// Java Imports
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

// Project Package Imports
import fr.cesi.pimpmyfridge.controller.IAction;
import fr.cesi.pimpmyfridge.controller.IActionListener;
import fr.cesi.pimpmyfridge.model.Model;


// 
public class ActionApp implements IAction {
	
	protected float targetTemp = 18.0f; 	// Default Target Temperature
	
	protected double histoIn;
	protected Date histoDate;
	
	protected boolean alertCondensation = false;
	protected boolean alertTempAnomaly = false;

	protected List<IActionListener> listeners;

	
	
	// Create an array of listeners
	public ActionApp() {
		this.listeners = new ArrayList<IActionListener>();
	}
	
	
	@Override
	public void onNewDataRead(Model data) {
		detectCondensation(data);
		detectTempAnomaly(data);
	}

	
	// Method to detect temperature anomaly
	protected void detectTempAnomaly(Model data) {
		
		boolean isTempAnomaly = alertTempAnomaly;
		if (this.histoDate == null || (new Date().getTime() - this.histoDate.getTime()) >= 3000) {
			
			if (this.histoDate != null)
				isTempAnomaly = (data.getDHTTemp() - this.histoIn > 1);
			
			this.histoDate = new Date();
			this.histoIn = data.getDHTTemp();
		}
		if (isTempAnomaly != alertTempAnomaly) {
			
			alertTempAnomaly = isTempAnomaly;
			
			notifyAlertTempAnomaly(isTempAnomaly);
		}
	}

	// Method to detect a condensation risk
	protected void detectCondensation(Model data) {
		
		// Relative Humidity
		double h = data.getHumidityPercent() / 100;
		
		// Dewpoint calculation
		double dewPoint = Math.pow(h , 1.0/8) * (112 + (0.9 * data.getPeltierTemp())) + (0.1 * data.getDHTTemp()) - 112;
		
		
		// Set to true if the internal temp is below the dewpoint, else set to false
		boolean isCondensation = (dewPoint >= data.getPeltierTemp());
		
		// If there's condensation
		if (isCondensation != alertCondensation) {
			
			// Set variable to true
			alertCondensation = isCondensation;
			// Trigger Notification from change
			notifyAlertCondensation(isCondensation);
		}
	}
	

	// Set the Target Temperature
	@Override
	public void setTempConsigne(float targetTemp) {
		
		if (targetTemp != this.targetTemp) {
			// Set variable with new value
			this.targetTemp = targetTemp;
			// Trigger Notification from changement
			notifyTargetTempChanged(targetTemp);
		}
	}

	@Override
	// Add listener
	public void addListener(IActionListener observer) {
		this.listeners.add(observer);
	}

	
	@Override
	// Remove listener
	public void removeListener(IActionListener observer) {
		this.listeners.remove(observer);
	}

	
	// Notifier for Target Temperature changes
	@Override
	public void notifyTargetTempChanged(final double targetTemp) {
		System.out.println("[NewAppEvent] New Target Temperature : " + targetTemp);
		this.listeners.forEach(new Consumer<IActionListener>() {
			public void accept(IActionListener observer) {
				observer.onTargetTempChanged(targetTemp);
			}
		});
	}


	// Notifier for Condensation Alert changes
	@Override
	public void notifyAlertCondensation(final boolean state) {
		System.out.println("[AIAlert] Condensation Risk!");
		this.listeners.forEach(new Consumer<IActionListener>() {
			public void accept(IActionListener observer) {
				observer.onAlertCondensationChanged(state);
			}
		});
	}

	// Notifier for Temperature Anomaly Alert changes
	@Override
	public void notifyAlertTempAnomaly(final boolean state) {
		System.out.println("[AIAlert] Abnormal Temperature!");
		this.listeners.forEach(new Consumer<IActionListener>() {
			@Override
			public void accept(IActionListener observer) {
				observer.onAlertTempAnomalyChanged(state);
			}
		});
	}

	// Getter : get Target Temperature
	@Override
	public float getTargetTemp() {
		return targetTemp;
	}


	// Getter : is there condensation ?
	@Override
	public boolean isAlertCondensation() {
		return alertCondensation;
	}

	// Getter : is there a temperature anomaly ?
	@Override
	public boolean isTempAnomaly() {
		return alertTempAnomaly;
	}

}
