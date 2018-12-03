package fr.cesi.pimpmyfridge.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

import fr.cesi.pimpmyfridge.model.Model;

public abstract class ArduinoConnection implements IDataLink {

	private ArrayList<IDataLinkListener> listeners;
	

	protected boolean powerEnabled = false;
	
	
	private long tempsAllumage = 0;
	private Date powerOnTime;
	
	public ArduinoConnection() {
		listeners = new ArrayList<IDataLinkListener>();
	}
	
	@Override
	public void addListener(IDataLinkListener observer) {
		listeners.add(observer);
	}
	
	@Override
	public void removeListener(IDataLinkListener observer) {
		listeners.remove(observer);
	}

	@Override
	public void notifyListeners(final Model data) {
		listeners.forEach(new Consumer<IDataLinkListener>() {
			public void accept(IDataLinkListener observer) {
				observer.onNewDataRead(data);
			}
		});
	}
	
	@Override
	public void notifyListeners(final boolean powerOn) {
		
		if (powerOn) {
			this.powerOnTime = new Date();
		}
		else {
			this.tempsAllumage = getPowerUptime();
			this.powerOnTime = null;
		}
		listeners.forEach(new Consumer<IDataLinkListener>() {
			public void accept(IDataLinkListener observer) {
				//observer.onPowerStatusChanged(powerOn);
			}
		});
	}

	protected void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			System.exit(-2);
		}
	}
	
	@Override
	public boolean isPowerEnabled() {
		return this.powerEnabled;
	}
	
	
	public long getPowerUptime() {
		long time = tempsAllumage;
		if (powerOnTime != null) {
			time += (new Date().getTime() - powerOnTime.getTime()) / 1000;
		}
		return time;
	}
}
