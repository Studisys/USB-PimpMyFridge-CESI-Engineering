package fr.exia.pimpmyfridge.main;

import org.jfree.ui.RefineryUtilities;

import fr.exia.pimpmyfridge.model.Model;
import fr.exia.pimpmyfridge.view.View;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 System.out.println("Hello World ! Starting the fridge app...");
		 
		 
		 // Subject to change !
		 // =============================
		 
		 // Create the model
		 Model model = new Model();
		 View chart = new View("Pimp My Fridge","Évolution des températures");
	     chart.pack( );
		 RefineryUtilities.centerFrameOnScreen( chart );
		 chart.setVisible( true );
		 
		 
		 // Create the view
		 //View view = new View(model, model);
		 
		 // Create the controller
		 // Controller controller = new Controller(view, model);
	}

}
