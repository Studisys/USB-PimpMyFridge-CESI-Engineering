package fr.cesi.pimpmyfridge.controller;

public interface IActionListener {


	public void onTargetTempChanged(double temp);
	
	
	public void onAlertCondensationChanged(boolean state);


	public void onAlertTempAnomalyChanged(boolean state);
	
}
