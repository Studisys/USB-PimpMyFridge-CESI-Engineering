package fr.cesi.pimpmyfridge.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import fr.cesi.pimpmyfridge.controller.IDataLink;
import fr.cesi.pimpmyfridge.controller.IDataLinkListener;
import fr.cesi.pimpmyfridge.model.Model;


public class ArduinoSourceInput implements IDataLink, IDataLinkListener {
	
	private IDataLink source;
	
	private List<Double> values;

	private int length;
	
	private ArrayList<IDataLinkListener> listeners;

	public ArduinoSourceInput(IDataLink source, int length) {
		this.source = source;
		this.length = length;
		values = new ArrayList<Double>();
		listeners = new ArrayList<IDataLinkListener>();
	}
	
	protected double add(double value) {
		values.add(value);
		while (values.size() > length) values.remove(0);
		float sum = 0;
		for (double val : values) sum += val;
		return sum / (double)values.size();
	}
	
	@Override
	public void notifyListeners(Model data) {
		
		final Model newData = new Model(
				data.getHumidityPercent(),
				data.getDHTTemp(),
				data.getPeltierTemp(),
				data.getOutsideTemp()
		);
		listeners.forEach(new Consumer<IDataLinkListener>() {
			public void accept(IDataLinkListener observer) {
				observer.onNewDataRead(newData);
			}
		});
	}

	@Override
	public void init() throws Throwable {
		this.source.init();
	}

	@Override
	public void start() {
		
		this.source.addListener(this);
		
		this.source.start();
	}

	@Override
	public void stop() {
		
		this.source.removeListener(this);
		
		this.source.stop();
	}

	@Override
	public void addListener(IDataLinkListener obs) {
		this.listeners.add(obs);
	}

	@Override
	public void removeListener(IDataLinkListener obs) {
		this.listeners.remove(obs);
	}

	@Override
	public void notifyListeners(final boolean powerOn) {
		listeners.forEach(new Consumer<IDataLinkListener>() {
			public void accept(IDataLinkListener observer) {
				observer.onPowerStatusChanged(powerOn);
			}
		});
	}


	@Override
	public boolean isPowerEnabled() {
		return this.source.isPowerEnabled();
	}

	@Override
	public long getPowerUptime() {
		return this.source.getPowerUptime();
	}

	@Override
	public void onNewDataRead(Model data) {
		
		notifyListeners(data);
	}

	@Override
	public void onPowerStatusChanged(boolean powerOn) {
		notifyListeners(powerOn);
	}

}
