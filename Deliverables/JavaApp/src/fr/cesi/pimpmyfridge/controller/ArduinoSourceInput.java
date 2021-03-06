package fr.cesi.pimpmyfridge.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import fr.cesi.pimpmyfridge.controller.IDataLink;
import fr.cesi.pimpmyfridge.controller.IDataLinkListener;
import fr.cesi.pimpmyfridge.model.Model;


public class ArduinoSourceInput implements IDataLink, IDataLinkListener {
	
	private IDataLink source;
	
	@SuppressWarnings("unused")
	private List<Double> values;

	@SuppressWarnings("unused")
	private int length;
	
	private ArrayList<IDataLinkListener> listeners;

	// List sources for Serial Link
	public ArduinoSourceInput(IDataLink source, int length) {
		this.source = source;
		this.length = length;
		values = new ArrayList<Double>();
		listeners = new ArrayList<IDataLinkListener>();
	}
	
	// Listener notifier when there's new data
	@Override
	public void notifyListeners(Model data) {
		
		final Model newData = new Model(
				data.getPeltierTemp(),
				data.getDHTTemp(),
				data.getOutsideTemp(),
				data.getDewTemp(),
				data.getHumidityPercent()
		);
		listeners.forEach(new Consumer<IDataLinkListener>() {
			public void accept(IDataLinkListener observer) {
				observer.onNewDataRead(newData);
			}
		});
	}

	// Init Data Source
	@Override
	public void init() throws Throwable {
		this.source.init();
	}

	// Start listening for changes 
	@Override
	public void start() {
		
		this.source.addListener(this);
		
		this.source.start();
	}

	// Stop listening for changes
	@Override
	public void stop() {
		
		this.source.removeListener(this);
		
		this.source.stop();
	}

	// Add Listener
	@Override
	public void addListener(IDataLinkListener observer) {
		this.listeners.add(observer);
	}

	// Remove Listener
	@Override
	public void removeListener(IDataLinkListener observer) {
		this.listeners.remove(observer);
	}


	// When there's new incoming data from the Arduino
	@Override
	public void onNewDataRead(Model data) {
		
		notifyListeners(data);
	}
	
	// Notify the listeners when there's a change
	@Override
	public void notifyListeners(final boolean powerOn) {
		listeners.forEach(new Consumer<IDataLinkListener>() {
			public void accept(IDataLinkListener observer) {
			//observer.onPowerStatusChanged(powerOn);
			}
		});
	}

}
