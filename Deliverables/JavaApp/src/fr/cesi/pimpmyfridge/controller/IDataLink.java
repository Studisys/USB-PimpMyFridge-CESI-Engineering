package fr.cesi.pimpmyfridge.controller;

import fr.cesi.pimpmyfridge.model.Model;

public interface IDataLink {
	
	
	public void init() throws Throwable;
	
	
	public void start();
	
	
	public void stop();
	
	
	public void addListener(IDataLinkListener obs);
	
	
	public void removeListener(IDataLinkListener obs);
	
	
	public void notifyListeners(Model data);
	
	
	public void notifyListeners(boolean powerOn);
	
	
	public boolean isPowerEnabled();

	
	public long getPowerUptime();
	
}
