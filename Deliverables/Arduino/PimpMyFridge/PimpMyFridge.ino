

// Notable Characteristics ///////////////////////////////////////////////////////////////
// - The DHT-22 sampling rate is 0.5Hz, so be sure to delay by 2000ms between measures
// - The Next Big Thing Is This Fridge
// - 100% HACK PROOF !
//////////////////////////////////////////////////////////////////////////////////////////


// Libraries Includes ////////////////////////////////////////////////////////////////////
// #include "Libraries/PimpMyFridge/PimpMyFridge.h" // Best library in that project, ha ! Includes everything needed

#include <DHT.h>
#include <DHT_U.h>
//////////////////////////////////////////////////////////////////////////////////////////


// I/O Pin Setup /////////////////////////////////////////////////////////////////////////
// #define fridgePower 0 // Power On/Off the fridge
#define DHTPIN 9
#define pinTemperaturePeltier 3 // Read the values from the thermistor (Analog !)
#define pinTemperatureOutside 4 // Read the values from the thermistor (Analog !)
#define pinFan 5 // Control the fan on that pin (0/1)
#define pinPeltier 6 // Control the Peltier Module on that pin (PWM !)
#define pinOnboardLED 13 // Onboard LED
#define pinOpenDetectorReceiver 7 // Pin to detect whether door is open or not
#define pinOpenDetectorEmitter 8
#define DHTTYPE DHT22 // The DHT sensor is DHT22

#define TWOFACED (1000UL * 2) // Define "2 seconds" in Arduino language, without RTC Module
unsigned long rolltime = millis() + TWOFACED;

//////////////////////////////////////////////////////////////////////////////////////////


// Constants Setup ///////////////////////////////////////////////////////////////////////

// Termistor Inside Fridge
double coeffAinside = 1.12902*pow(10,-3);
double coeffBinside = 2.3418*pow(10,-4);
double coeffCinside = 8.7264*pow(10,-8);
double resistanceInside = 9910;
double voltageInside = 1023;

// Termistor Outside Fridge
double coeffAoutside = 1.12902*pow(10,-3);
double coeffBoutside = 2.3418*pow(10,-4);
double coeffCoutside = 8.7264*pow(10,-8);
double resistanceOutside = 9910;
double voltageOutside = 1023;


//////////////////////////////////////////////////////////////////////////////////////////



// Working Variables /////////////////////////////////////////////////////////////////////

double DHT_Temperature = 0; // Value of the Temperature (°C) from the DHT-22
double DHT_Humidity = 0; // Value of the Humidity (%) from the DHT-22
double peltierTemperature = 0; // Value of the temperature (°C) from the thermistor on the Peltier Module
double outsideTemperature = 0;
double targetTemperature = 0;
boolean isDoorOpen = 0;


char inputs [20];
char oldInputs[20];

//////////////////////////////////////////////////////////////////////////////////////////

DHT dht(DHTPIN, DHTTYPE); // Init DHT Sensor


void setup()
{
  Serial.begin(9600); // Init Serial Monitor

  // Set the pinModes
  pinMode(DHTPIN,INPUT); //  Mesure sur le module DHT
  pinMode(pinTemperaturePeltier,INPUT); // Mesure à la thermistance
  pinMode(pinOpenDetectorEmitter, OUTPUT); // Send signal for Door Detector
  pinMode(pinOpenDetectorReceiver, INPUT); // Receive signal from Emitter for Door Detector
  pinMode(pinOnboardLED, OUTPUT);
  pinMode(pinPeltier, OUTPUT);
  pinMode(pinFan, OUTPUT);


  dht.begin(); // Start DHT Sensor
}

void loop()
{
  readSerialData();
  readSensors(); // Read values from sensors
  delay(2000);
  sendSerialData();
}




// Read values from sensors
// OK !
void readSensors()
{
  peltierTemperature = readInsideTemperature(); // Read Temperature from Termistor on Peltier Module
  outsideTemperature = readOutsideTemperature(); // Read Temperature from Termistor outside Fridge
  isDoorOpen = readOpenSensor(); // Check if Door is Open
  DHT_Temperature = readDHTTemperature(); // Read Temperature from DHT Module
  DHT_Humidity = readDHTHumidity(); // Read Humidity from DHT Module
  // Every 2 seconds, read values from DHT Module (as values need to be read in a 2 sec interval)
  /*if((millis() - rolltime) >= 0) {
    DHT_Temperature = readDHTTemperature(); // Read Temperature from DHT Module
    DHT_Humidity = readDHTHumidity(); // Read Humidity from DHT Module
    rolltime += TWOFACED;
  }*/
}


// Convert value from Termistor to Celsius
// OK !
double convertRawToCelsius (double tempRaw, double coeffA, double coeffB, double coeffC, double resistance, double voltage)
{
  long RTH=0;
  RTH=(tempRaw*resistance)/(voltage-tempRaw);
  double RSLT=0;
  RSLT=(1/(coeffA+coeffB*log(RTH)+coeffC*(pow(log(RTH),3))))-273.15;
  return RSLT;
}

// Check if Door is Open or Not
// TESTING REQUIRED !
boolean readOpenSensor()
{
  digitalWrite(pinOpenDetectorEmitter, HIGH); // Emit Signal
  if (digitalRead(pinOpenDetectorReceiver) == 0)
  {
    return true; // Door is open
  }
  else {
    return false; // Door is closed
  }
  digitalWrite(pinOpenDetectorEmitter, LOW); // Stop Signal Emission
}


// Send data to the serial monitor
// OK !
void sendSerialData(){
  Serial.print(peltierTemperature);
  Serial.print(":"); // Separator
  Serial.print(outsideTemperature);
  Serial.print(":"); // Separator
  Serial.print(DHT_Temperature);
  Serial.print(":"); // Separator
  Serial.print(DHT_Humidity);
  Serial.print(":"); // Separator
  Serial.print(isDoorOpen);
  Serial.println(); // New line = new measurements
}


// Get data from the Java Program
// TO ADAPT
void readSerialData()
{
  // If new data available from the serial port
  if(Serial.available()){
        int ind=0;
        char buff[6];
        while(Serial.available()){
            unsigned char c = Serial.read();
            buff[ind] = c;
            if(ind++ > 6) break;
        }
      String Data = buff; // We get the input from the Serial monitor as String first
      float target = Data.toFloat(); // We convert it to float
}
}


void setNewVariables()
{
  //target = target2;
}



// Command the parts of the Fridge
void actionableIntelligence()
{
  
}



// Read Temperature from Termistor on Peltier Module
// OK !
double readInsideTemperature()
{
  double insideTempRaw = analogRead(pinTemperaturePeltier);
  double insideTempConverted = convertRawToCelsius(insideTempRaw, coeffAinside, coeffBinside, coeffCinside, resistanceInside, voltageInside);
  return insideTempConverted;
}




// Read Temperature from Outside Termistor
// OK !
double readOutsideTemperature()
{
  double outsideTempRaw = analogRead(pinTemperatureOutside);
  double outsideTempConverted = convertRawToCelsius(outsideTempRaw, coeffAoutside, coeffBoutside, coeffCoutside, resistanceOutside, voltageOutside);
  return outsideTempConverted;
}

// Read Temperature from DHT Module
// OK !
double readDHTTemperature()
{
  float t = dht.readTemperature();
  return t;
}


// Read Humidity from DHT Module
// OK !
double readDHTHumidity()
{
  float h = dht.readHumidity();
  return h;
}
