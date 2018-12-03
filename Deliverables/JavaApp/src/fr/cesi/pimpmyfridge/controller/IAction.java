package fr.cesi.pimpmyfridge.controller;

public interface IAction extends IDataLinkListener {

	public void addListener(IActionListener obs);

	public void removeListener(IActionListener obs);
	
	public void notifyTargetTempChanged(double targetTemp);
		
	public void notifyAlertCondensation(boolean state);	

	public void notifyAlertTempAnomaly(boolean state);
	
	public void setTempConsigne(float targetTemp);
	
	public float getTargetTemp();


	public boolean isAlertCondensation();

	public boolean isTempAnomaly();
	
}
