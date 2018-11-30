// Notable Characteristics ///////////////////////////////////////////////////////////////
// - The DHT-22 sampling rate is 0.5Hz, so be sure to delay by 2000ms between measures
// - The Next Big Thing Is This Fridge
// - 100% HACK PROOF !
//////////////////////////////////////////////////////////////////////////////////////////


// Libraries Includes ////////////////////////////////////////////////////////////////////
// #include <PimpMyFridge.h> // Best library in that project, ha ! Includes everything needed
//////////////////////////////////////////////////////////////////////////////////////////


// I/O Pin Setup /////////////////////////////////////////////////////////////////////////
#define fridgePower 0 // Power On/Off the fridge
#define pinDHT 1 // Read the values coming from the DHT on that pin
#define pinTemperature 3 // Read the values from the thermistor
#define pinPeltierAirflow 4 // Control the Peltier Module (Fan) on that pin
#define pinPeltierPower 5 // Control the Peltier Module (heating resistance) on that pin
#define pinOboardLED 13 // Onboard LED

//////////////////////////////////////////////////////////////////////////////////////////


// Constants Setup ///////////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////////////////////////////////



// Working Variables /////////////////////////////////////////////////////////////////////

double DHT_Temperature = 0; // Value of the Temperature (°C) from the DHT-22
double DHT_Humidity = 0; // Value of the Humidity (%) from the DHT-22

double Temperature = 0; // Value of the temperature (°C) from the thermistor
//////////////////////////////////////////////////////////////////////////////////////////


void setup()
{
  Serial.begin(9600); // Init Serial Monitor

  pinMode(pinDHT,INPUT); //  Mesure sur le module DHT
  pinMode(pinTemperature,INPUT); // Mesure à la thermistance

  // pinMode(doorOpeningDetector, INPUT); // Feature - Open
  // ...
}

void loop()
{
  pinMode(fridgePower, OUTPUT); // BitPoweeeeeeeeeeeeeeeeeeeeeeeeeeeer (powering the fridge on)
  
  // ...
}
