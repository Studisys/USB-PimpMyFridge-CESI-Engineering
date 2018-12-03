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
	
	
	public static final int TIME_OUT = 2000;
	
	
	public static final int DATA_RATE = 9600;
	
	
	private static final String DataIdentifier = "D:";
	
	private static final String EmptyBufferErrorMessage = "Underlying input stream returned zero bytes";
	
	//private static final int FIELD_NUMBER = 4;
	
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
	public void start() {
		try {
			System.out.println("[ArduinoInput] Link Start !");
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		}
		catch (Throwable e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public synchronized void stop() {
		if (serialPort != null) {
			System.out.println("[ArduinoInput] Link Stopped !");
			serialPort.removeEventListener();
			serialPort.close();
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
			if (inputLine.startsWith(DataIdentifier)) {
				
				String[] tokens = inputLine.substring(2).split(";", 4);
				
				if (tokens.length != 4) {
					System.err.println("[Arduino] Invalid data message (message does not have " + "4" + "fields.");
					return;
				}
				
				float[] values = parseFloatArray(tokens);
				
				
				// We notify for 4 values : Peltier, DHT Temp, Outside Temp and DHT Humidity
				notifyListeners(new Model(values[0], values[1], values[2], values[3]));
			}
			
			
			else {
				System.err.println("[ArduinoInput] Message received is unknown (Invalid)");
			}
		}
		catch (IOException e) {
			if (e.getMessage().equals(EmptyBufferErrorMessage)) {
				
			}
			else {
				System.err.println(String.format("[ArduinoInput] Error %s", e.toString()));
			}
		}
		catch (Throwable e) {
			System.err.println(String.format("[ArduinoInput] Error %s", e.toString()));
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