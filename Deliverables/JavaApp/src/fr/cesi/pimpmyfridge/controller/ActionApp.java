package fr.cesi.pimpmyfridge.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import fr.cesi.pimpmyfridge.controller.IAction;
import fr.cesi.pimpmyfridge.controller.IActionListener;
import fr.cesi.pimpmyfridge.model.Model;

public class ActionApp implements IAction {
	
	protected float targetTemp = 10.0f;
	protected boolean consigneAllumage = false;
	
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

	protected void detectCondensation(Model data) {
		
		double h = data.getHumidityPercent() / 100;
		
		
		double tempRosee = Math.pow(h , 1.0/8) * (112 + (0.9 * data.getDHTTemp())) + (0.1 * data.getOutsideTemp()) - 112;
		
		
		boolean isCondensation = (tempRosee >= data.getDHTTemp());
		if (isCondensation != alertCondensation) {
			
			alertCondensation = isCondensation;
			
			notifyAlertCondensation(isCondensation);
		}
	}

	@Override
	public void setTempConsigne(float targetTemp) {
		
		if (targetTemp != this.targetTemp) {
			
			this.targetTemp = targetTemp;
			
			notifyTargetTempChanged(targetTemp);
		}
	}

	@Override
	public void addListener(IActionListener obs) {
		this.listeners.add(obs);
	}

	@Override
	public void removeListener(IActionListener obs) {
		this.listeners.remove(obs);
	}

	@Override
	public void notifyTargetTempChanged(final double targetTemp) {
		System.out.println("[NewAppEvent] New Target Temperature : " + targetTemp);
		this.listeners.forEach(new Consumer<IActionListener>() {
			public void accept(IActionListener obs) {
				obs.onTargetTempChanged(targetTemp);
			}
		});
	}

	
	@Override
	public void notifyAlertCondensation(final boolean state) {
		System.out.println("[AIAlert] Condensation Risk!");
		this.listeners.forEach(new Consumer<IActionListener>() {
			public void accept(IActionListener obs) {
				obs.onAlertCondensationChanged(state);
			}
		});
	}

	@Override
	public void notifyAlertTempAnomaly(final boolean state) {
		System.out.println("[AIAlert] Abnormal Temperature!");
		this.listeners.forEach(new Consumer<IActionListener>() {
			@Override
			public void accept(IActionListener obs) {
				obs.onAlertTempAnomalyChanged(state);
			}
		});
	}

	@Override
	public float getTargetTemp() {
		return targetTemp;
	}


	@Override
	public boolean isAlertCondensation() {
		return alertCondensation;
	}

	@Override
	public boolean isTempAnomaly() {
		return alertTempAnomaly;
	}

	@Override
	public void onPowerStatusChanged(boolean powerOn) {
		// TODO Auto-generated method stub
		
	}

}
