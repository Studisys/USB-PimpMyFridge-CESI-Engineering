// Libraries Includes ////////////////////////////////////////////////////////////////////

#include <DHT.h>
#include <DHT_U.h>
//////////////////////////////////////////////////////////////////////////////////////////


// I/O Pin Setup /////////////////////////////////////////////////////////////////////////
// #define fridgePower 0 // Power On/Off the fridge
#define DHTPININ 9
#define DHTPINOUT 10

#define pinTemperaturePeltier 0 // Read the values from the thermistor (Analog !)
#define pinPeltier 2 // Control the Peltier Module on that pin (PWM !)

#define DHTTYPE DHT22 // The DHT sensor is DHT22


//////////////////////////////////////////////////////////////////////////////////////////


// Constants Setup ///////////////////////////////////////////////////////////////////////

// Termistor Inside Fridge
double coeffAinside = 1.12902 * pow(10, -3);
double coeffBinside = 2.3418 * pow(10, -4);
double coeffCinside = 8.7264 * pow(10, -8);
double resistanceInside = 9910;
double voltageInside = 1023;

float targetTemperature = 18;

String Data;

//////////////////////////////////////////////////////////////////////////////////////////



// Working Variables /////////////////////////////////////////////////////////////////////

double DHT_Temperature = 0; // Value of the Temperature (°C) from the DHT-22
double DHT_Humidity = 0; // Value of the Humidity (%) from the DHT-22
double peltierTemperature = 0; // Value of the temperature (°C) from the thermistor on the Peltier Module
double outsideTemperature = 0;
//double targetTemperature = 0;
double Dewpoint = 0;

//////////////////////////////////////////////////////////////////////////////////////////

DHT dhtIn(DHTPININ, DHTTYPE); // Init DHT Sensor
DHT dhtOut(DHTPINOUT, DHTTYPE); // Init DHT Sensor
void setup()
{
  Serial.begin(9600); // Init Serial Monitor

  // Set the pinModes
  pinMode(DHTPININ, INPUT); //  Mesure sur le module DHT Intérieur
  pinMode(DHTPINOUT, INPUT); //  Mesure sur le module DHT Extérieur
  pinMode(pinTemperaturePeltier, INPUT); // Mesure à la thermistance
  pinMode(pinPeltier, OUTPUT);

  dhtIn.begin(); // Start DHT Sensor
  dhtOut.begin(); // Start DHT Sensor
}

void loop()
{

  readSerialData(); // Read data from serial incoming from Java app
  readSensors(); // Read values from sensors
  getAtmoDewpoint(); // Calculate the Dewpoint (condensation temperature)
  delay(500); // Delay to slow things down
  sendSerialData(); // Send data over Serial
  actionableIntelligence(); // Peltier Module Control according to target temperature
}




// Read values from sensors
// OK !
void readSensors()
{
  peltierTemperature = readInsideTemperature(); // Read Temperature from Termistor on Peltier Module
  outsideTemperature = readOutsideTemperature(); // Read Temperature from Termistor outside Fridge
  DHT_Temperature = readDHTTemperature(); // Read Temperature from DHT Module;
  DHT_Humidity = readDHTHumidity(); // Read Humidity from DHT Module
  Dewpoint = getAtmoDewpoint();
}


// Convert value from Termistor to Celsius
// OK !
double convertRawToCelsius (double tempRaw, double coeffA, double coeffB, double coeffC, double resistance, double voltage)
{
  long RTH = 0;
  RTH = (tempRaw * resistance) / (voltage - tempRaw);
  double RSLT = 0;
  RSLT = (1 / (coeffA + coeffB * log(RTH) + coeffC * (pow(log(RTH), 3)))) - 273.15;
  return RSLT;
}


// Send data to the serial monitor
// OK !
void sendSerialData() {
  Serial.print("D:");
  Serial.print(peltierTemperature);
  Serial.print(";"); // Separator
  Serial.print(DHT_Temperature);
  Serial.print(";");
  Serial.print(outsideTemperature);
  Serial.print(";"); // Separator
  Serial.print(Dewpoint);
  Serial.print(";");
  Serial.println(DHT_Humidity); // New line = new measurements
}


// Get data from the Java Program
// OK
void readSerialData()
{
  // If new data available from the serial port
  if (Serial.available()) {
    int ind = 0;
    char buff[3];
    while (Serial.available()) {
      unsigned char c = Serial.read();
      buff[ind] = c;
      if (ind++ > 3) break;
    }
    Data = buff; // We get the input from the Serial monitor as String first
    targetTemperature = Data.toFloat();
    Serial.print("[ArduinoOutput] Confirmation of new target temperature : ");
    Serial.println(targetTemperature); // We convert it to float
  }
}




// Command the parts of the Fridge
// OK !
void actionableIntelligence()
{
  float differenceOfTemperature = peltierTemperature - targetTemperature;
  float pourcentError = (5 / 100) * targetTemperature;

  if (abs(pourcentError) < abs(differenceOfTemperature)) {
    if (pourcentError < differenceOfTemperature) {
      //Temperature trop elevee
      //Refroidir
      digitalWrite(pinPeltier, HIGH);
      Serial.print("[ArduinoOutput] Too hot. Starting cooling. ");
      Serial.print("Current Internal Temperature (");
      Serial.print(DHT_Temperature);
      Serial.print(" C) is above the target temperature (");
      Serial.print(targetTemperature);
      Serial.println(" C).");
      //Serial.println();



    }
    else if (pourcentError > differenceOfTemperature) {
      //Temperature trop basse
      //Couper alim
      digitalWrite(pinPeltier, LOW);
      Serial.print("[ArduinoOutput] Too cold. Stopping cooling. ");
      Serial.print("Current Internal Temperature (");
      Serial.print(DHT_Temperature);
      Serial.print(" C) is below the target temperature (");
      Serial.print(targetTemperature);
      Serial.println(" C).");
      //Serial.println();
    }
  }
  else if (abs(pourcentError) > abs(differenceOfTemperature)) {
    //Don't touch it bro all is right
  }
}


// Get Dewpoint
// OK
double getAtmoDewpoint()
{
  double Tr = (237.7 * (((17.27 * DHT_Temperature) / (237.7 + DHT_Temperature)) + log(DHT_Humidity / 100))) / (17.27 - (((17.27 * DHT_Temperature) / (237.7 + DHT_Temperature)) + log(DHT_Humidity / 100)));
  return Tr;
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
float readOutsideTemperature()
{
  float t = dhtOut.readTemperature();
  return t;
}


// Read Temperature from DHT Module
// OK !
float readDHTTemperature()
{
  float t = dhtIn.readTemperature();
  return t;
}


// Read Humidity from DHT Module
// OK !
float readDHTHumidity()
{
  float h = dhtIn.readHumidity();
  return h;
}
