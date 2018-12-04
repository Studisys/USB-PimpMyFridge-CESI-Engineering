package fr.cesi.pimpmyfridge.controller;

import fr.cesi.pimpmyfridge.model.Model;

public interface IDataLink {
	
	
	public void init() throws Throwable;
	
	
	public void start();
	
	
	public void stop();
	
	
	public void addListener(IDataLinkListener observer);
	
	
	public void removeListener(IDataLinkListener observer);
	
	
	public void notifyListeners(Model data);
	
	public void notifyListeners(boolean powerOn);

}
