// Package Name
package fr.cesi.pimpmyfridge.main;

// AWT
import java.awt.EventQueue;

// Swing
import javax.swing.JOptionPane;

// Project Packages
import fr.cesi.pimpmyfridge.view.View;
import fr.cesi.pimpmyfridge.controller.Controller;
	import fr.cesi.pimpmyfridge.controller.ArduinoDataSource;
	import fr.cesi.pimpmyfridge.controller.IDataLink;
	import fr.cesi.pimpmyfridge.controller.IAction;
	import fr.cesi.pimpmyfridge.controller.ArduinoSourceInput;
	import fr.cesi.pimpmyfridge.controller.ActionApp;


// Main Class, run at startup
public class Main {

	public static void main(String[] args) {
	
		// Get a Data Implementation (get COM Port) and store it in a IDataLink Object
		final IDataLink datalink = getDataLinkImplementation();
		
		// Instantiate "Action" (work) Functions in an IAction Object
		final IAction action = new ActionApp();		
		
		// Instantiate the Controller in a Controller Object
		final Controller controller = new Controller();
		
		// Application Thread
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				// Instantiate the view (window)
				View view = new View();
			
				try {
					// Try to start the app w/ controller (with the objects view, datalink and action)
					controller.start(view, datalink, action);
				} catch (Throwable ex) {
					// If there's an error :
					// Open a window with "Fatal Error" as title, "Error during application initialization !" + error as text inside window
					JOptionPane.showMessageDialog(null, "Error during application initialization !"
							+ "\n" + ex.getClass().getSimpleName() + " : " + ex.getMessage(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}

			}
		});

	}

	// Method to get the Data Implementation (the COM port for the Arduino)
	private static IDataLink getDataLinkImplementation() {
		
		IDataLink implementation = getImplementation(
				// Max 15 COM ports
				new ArduinoSourceInput(new ArduinoDataSource(), 15)); 

	// If no Arduino Detected (no COM port)
		if (implementation == null) {
			System.err.println("[Implementation] Can't get data from Source.");
			// Exit
			System.exit(-1);
		}
		// Return the null implementation
		return implementation;
	}
	
	// Method to init the implementation (establish a data link over Serial port)
	private static IDataLink getImplementation(IDataLink... implementations) {
		
		// For each COM port available
		for (IDataLink implementation : implementations) {
			
			try {
				// Try to start a data link with the current implementation
				implementation.init();
			}
			// If an error occurs
			catch (Throwable e) {
				System.err.println(String.format("[Implementation] ERROR - Can't get data from Arduino. Terminating...",
						implementation.getClass().getSimpleName(),
						e.getMessage(), e.getClass().getSimpleName()));
				continue;
			}
			// Return the implementation
			return implementation;
		}
		
		// Exit
		return null;
	}

}
