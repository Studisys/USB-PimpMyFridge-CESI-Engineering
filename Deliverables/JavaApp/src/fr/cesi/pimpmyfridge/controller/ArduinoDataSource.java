package fr.cesi.pimpmyfridge.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Consumer;


import javax.swing.SwingUtilities;

import fr.cesi.pimpmyfridge.controller.ArduinoConnection;
import fr.cesi.pimpmyfridge.model.Model;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ArduinoDataSource extends ArduinoConnection implements SerialPortEventListener {
	
	// Timeout value
	public static final int TIME_OUT = 2000;
	
	// Baud Rate (Bit/s transmitted, default is 9600)
	public static final int DATA_RATE = 9600;
	
	// Message if there's nothing in the incoming frame
	private static final String EmptyBufferErrorMessage = "Underlying input stream returned zero bytes";
	
	// Identifier in our incoming frame
	private static final String DataID = "D:";
	
	// Number of fields in the frame
	private static final int FIELD_NUMBER = 4;
	
	
	public SerialPort serialPort;
	
	public BufferedReader input;
	
	public static OutputStream output;

	
	@Override
	public void init() throws Throwable {
		
		final List<CommPortIdentifier> availablePorts = new ArrayList<>();
		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
		
		final PlaceHolder<CommPortIdentifier> selectedPort = new PlaceHolder<>();

		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			if (currPortId.getPortType() == CommPortIdentifier.PORT_SERIAL)
			{
				availablePorts.add(currPortId);
			}
		}
		
		if (availablePorts.size() > 0) {
			final StringBuilder ports = new StringBuilder();
			availablePorts.forEach(new Consumer<CommPortIdentifier>() {
				public void accept(CommPortIdentifier port) {
					ports.append(" " + port.getName());
				}
			});
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
						availablePorts.forEach(new Consumer<CommPortIdentifier>() {
						
						public void accept(CommPortIdentifier port) {
								selectedPort.set(port);
						}
					});
				}
			});
		}
		
		if (selectedPort.isNull()) {
			throw new IOException("[ArduinoDataSource] Could not find COM port.");
		}
		
		System.out.println("[ArduinoDataSource] Arduino found on serial port: " + selectedPort.get().getName());

		
		serialPort = (SerialPort) selectedPort.get().open(this.getClass().getName(), TIME_OUT);

		
		serialPort.setSerialPortParams(DATA_RATE,
				SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);

		
		input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		output = serialPort.getOutputStream();
	}

	@Override
	// Method to start the data link
	public void start() {
		try {
			System.out.println("[ArduinoInput] Link Start !");
			serialPort.addEventListener(this); // We listen on it
			serialPort.notifyOnDataAvailable(true); // Notify if there's data available on the serial link
		}
		catch (Throwable e) {
			throw new RuntimeException(e); // If an error occurs
		}
		
	}

	@Override
	// Method to properly close the Serial link on program close
	public synchronized void stop() {
		if (serialPort != null) {
			System.out.println("[ArduinoInput] Link Stopped !");
			serialPort.removeEventListener(); // We don't listen anymore
			serialPort.close(); // We close the data link
		}
	}

	@Override
	public synchronized void serialEvent(SerialPortEvent event0) {
		
		
		if (event0.getEventType() != SerialPortEvent.DATA_AVAILABLE) {
			System.out.println("[ArduinoInput] Event : " + event0.getEventType());
			return;
		}
		
		
		try {
			String inputLine = input.readLine();
			// If we can read a special identifier from the string (to identify it)
			if (inputLine.startsWith(DataID)) {
				
				// We split, in an array of strings, the received frame intro 4 fields (delimited with ";")
				String[] tokens = inputLine.substring(2).split(";", FIELD_NUMBER);
				
				// If there's not 4 fields, then the message is probably garbage
				if (tokens.length != FIELD_NUMBER) {
					System.err.println("[ArduinoInput] Invalid data message (message does not have " + "4 " + "fields. Received message is : " + inputLine + ")");
					return;
				}
				// We convert all the values into Float values and put them in an array of Float
				float[] values = parseFloatArray(tokens);
				
				// Display the received frame in console
				System.out.println("Received the following frame : " + inputLine);
				
				// We notify for 4 new values : Peltier, DHT Temp, Outside Temp and DHT Humidity
				notifyListeners(new Model(values[0], values[1], values[2], values[3]));
			}
			
			
			else {
				// If the frame is not valid
				System.err.println("[ArduinoInput] Message received is unknown (Invalid)");
			}
		}
		catch (IOException e) {
			if (e.getMessage().equals(EmptyBufferErrorMessage)) {
				// We don't have to display any error in cause because there's nothing on the serial link,
				// so we remove any System.out/err here.
			}
			else {
				
			}
		}
		catch (Throwable e) {
			System.err.println(String.format("[ArduinoInput] Error %s", e.toString())); // Show the error
		}
		

	}
	private float[] parseFloatArray(String[] tokens) {
		float[] r = new float[tokens.length];
		int i = 0;
		for (String tok : tokens) {
			r[i++] = tok.toLowerCase().equals("nan") ? -1 : Float.parseFloat(tok);
		}
		return r;
	}

	public static void writeData(String data) {
		try {
			output.write(data.getBytes());
		}
		catch (Exception e) {
			System.err.println(String.format("[SerialWrite] Could not write data to serial, %s : %s", e.getClass().getSimpleName(), e.getMessage()));
		}
	}
	

}