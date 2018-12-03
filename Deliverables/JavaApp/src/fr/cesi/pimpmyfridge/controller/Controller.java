package fr.cesi.pimpmyfridge.controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import fr.cesi.pimpmyfridge.controller.IDataLink;
import fr.cesi.pimpmyfridge.controller.IDataLinkListener;
import fr.cesi.pimpmyfridge.controller.IAction;
import fr.cesi.pimpmyfridge.controller.IActionListener;
import fr.cesi.pimpmyfridge.model.Model;
import fr.cesi.pimpmyfridge.view.View;
import fr.cesi.pimpmyfridge.controller.ArduinoDataSource;

public class Controller implements IDataLinkListener, IActionListener {
	
	
	
	private View view;
	private IDataLink datalink;
	private IAction action;

	
	public void start(View view, final IDataLink datalink, final IAction action) throws Throwable {
		
		
		this.view = view;
		this.datalink = datalink;
		this.action = action;
		
		
		this.view.labelTargetTemp.setText(String.format("%.1f �C", action.getTargetTemp()));
		this.view.alertCondensation.setVisible(false);
		this.view.alertTempAnomaly.setVisible(false);
		this.view.chart.mark.setValue(action.getTargetTemp());

		
		view.buttonTargetPlus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Controller.this.action.setTempConsigne(action.getTargetTemp() + 0.5f);
				String data = Float.toString(action.getTargetTemp());
				ArduinoDataSource.writeData(data);
				// AJOUTER ICI EVENEMENT ENVOI DATA ARDUINO
			}
		});
		view.buttonTargetMinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionListener) {
				Controller.this.action.setTempConsigne(action.getTargetTemp() - 0.5f);
				String data = Float.toString(action.getTargetTemp());
				ArduinoDataSource.writeData(data);
				// AJOUTER ICI EVENEMENT ENVOI DATA ARDUINO
			}
		});


		view.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event0) {

				datalink.stop();
			}
		});
		

		this.datalink.addListener(action);


		this.datalink.addListener(this);
		this.action.addListener(this);
		

		this.view.setVisible(true);
		
		this.datalink.start();
		
	}

	
	@Override
	public void onNewDataRead(final Model data) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				view.labelTempOutside.setText(String.format("%.1f �C", data.getOutsideTemp()));
				view.labelTempDHT.setText(String.format("%.1f �C", data.getDHTTemp()));
				view.labelTempPeltier.setText(String.format("%.1f �C", data.getPeltierTemp()));
				view.labelHumidity.setText(String.format("%.1f", data.getHumidityPercent()) + "%");
				

				view.chart.addData((float)data.getDHTTemp(), (float)data.getOutsideTemp(), (float)data.getPeltierTemp() );
			}
		});
	}
	
	
	@Override
	public void onTargetTempChanged(final double temp) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				view.labelTargetTemp.setText(String.format("%.1f �C", temp));
				Controller.this.view.chart.mark.setValue(temp);
			}
		});
	}
	
		
	@Override
	public void onAlertCondensationChanged(final boolean state) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				view.alertCondensation.setVisible(state);
			}
		});
	}

	
	@Override
	public void onAlertTempAnomalyChanged(final boolean state) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				view.alertTempAnomaly.setVisible(state);
			}
		});
	}

	
	@Override
	public void onPowerStatusChanged(final boolean powerOn) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				// Peltier Module
				
			}
		});
	}

}
