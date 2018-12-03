package fr.cesi.pimpmyfridge.main;


import java.awt.EventQueue;

import javax.swing.JOptionPane;

import fr.cesi.pimpmyfridge.view.View;
import fr.cesi.pimpmyfridge.controller.Controller;
	import fr.cesi.pimpmyfridge.controller.ArduinoDataSource;
	import fr.cesi.pimpmyfridge.controller.IDataLink;
	import fr.cesi.pimpmyfridge.controller.IAction;
	import fr.cesi.pimpmyfridge.controller.ArduinoSourceInput;
	import fr.cesi.pimpmyfridge.controller.ActionApp;


public class Main {

	public static void main(String[] args) {
	
		final IDataLink datalink = getDataLinkImplementation();
		
		final IAction action = new ActionApp();		
		
		final Controller controller = new Controller();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				View view = new View();
			
				try {
					controller.start(view, datalink, action);
				} catch (Throwable ex) {
					JOptionPane.showMessageDialog(null, "Erreur au lancement de l'application."
							+ "\n" + ex.getClass().getSimpleName() + " : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}

			}
		});

	}

	private static IDataLink getDataLinkImplementation() {
		
		IDataLink implementation = getImplementation(
				new ArduinoSourceInput(new ArduinoDataSource(), 15)); 

	
		if (implementation == null) {
			System.err.println("[Implementation] Can't get data from Source.");
			System.exit(-1);
		}
		
		return implementation;
	}
	
	private static IDataLink getImplementation(IDataLink... implementations) {
		
		for (IDataLink implementation : implementations) {
			
			try {
				implementation.init();
			}
			
			catch (Throwable e) {
				System.err.println(String.format("[Implementation] ERROR - Can't get data from Arduino. Terminating...",
						implementation.getClass().getSimpleName(),
						e.getMessage(), e.getClass().getSimpleName()));
				continue;
			}
			
			return implementation;
		}
		
		return null;
	}

}
