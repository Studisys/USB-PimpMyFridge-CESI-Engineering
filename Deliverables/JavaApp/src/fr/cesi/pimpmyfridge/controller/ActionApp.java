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
	
	protected float targetTemp = 15.0f; 	// Default Target Temperature
	
	protected double histoIn;
	protected Date histoDate;
	
	protected boolean alertCondensation = false;
	protected boolean alertTempAnomaly = false;

	protected List<IActionListener> listeners;
	protected Date lastPeltierOn = null;
	

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
		
		double h = data.getHumidityPercent() / 100;
		
		
		double tempRosee = Math.pow(h , 1.0/8) * (112 + (0.9 * data.getDHTTemp())) + (0.1 * data.getOutsideTemp()) - 112;
		
		
		boolean isCondensation = (tempRosee >= data.getDHTTemp());
		if (isCondensation != alertCondensation) {
			
			// Set variable to true
			alertCondensation = isCondensation;
			// Trigger Notification from changement
			notifyAlertCondensation(isCondensation);
		}
	}

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
	public void removeListener(IActionListener observer) {
		this.listeners.remove(observer);
	}

	@Override
	public void notifyTargetTempChanged(final double targetTemp) {
		System.out.println("[NewAppEvent] New Target Temperature : " + targetTemp);
		this.listeners.forEach(new Consumer<IActionListener>() {
			public void accept(IActionListener observer) {
				observer.onTargetTempChanged(targetTemp);
			}
		});
	}

	
	@Override
	public void notifyAlertCondensation(final boolean state) {
		System.out.println("[AIAlert] Condensation Risk!");
		this.listeners.forEach(new Consumer<IActionListener>() {
			public void accept(IActionListener observer) {
				observer.onAlertCondensationChanged(state);
			}
		});
	}

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
