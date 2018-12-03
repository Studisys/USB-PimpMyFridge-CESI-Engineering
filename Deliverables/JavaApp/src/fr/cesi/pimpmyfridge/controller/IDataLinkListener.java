package fr.cesi.pimpmyfridge.controller;

import fr.cesi.pimpmyfridge.model.Model;

public interface IDataLinkListener {
	
	
	public void onNewDataRead(Model data);

	
	public void onPowerStatusChanged(boolean powerOn);
	
}
