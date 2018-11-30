// Notable Characteristics ///////////////////////////////////////////////////////////////
// - The DHT-22 sampling rate is 0.5Hz, so be sure to delay by 2000ms between measures
// - The Next Big Thing Is This Fridge
// - 100% HACK PROOF !
//////////////////////////////////////////////////////////////////////////////////////////


// Libraries Includes ////////////////////////////////////////////////////////////////////
// #include <PimpMyFridge.h> // Best library in that project, ha ! Includes everything needed
#include <DHT.h> // Include the DHT Library
//////////////////////////////////////////////////////////////////////////////////////////


// I/O Pin Setup /////////////////////////////////////////////////////////////////////////
// #define fridgePower 0 // Power On/Off the fridge
#define pinDHT 1 // Read the values coming from the DHT on that pin (Digital !)
#define pinTemperaturePeltier 3 // Read the values from the thermistor (Analog !)
#define pinTemperatureOutside 4 // Read the values from the thermistor (Analog !)
#define pinFan 5 // Control the fan on that pin (0/1)
#define pinPeltier 6 // Control the Peltier Module on that pin (PWM !)
#define pinOnboardLED 13 // Onboard LED
#define pinOpenDetector 7 // Pin to detect whether door is open or not

//////////////////////////////////////////////////////////////////////////////////////////


// Constants Setup ///////////////////////////////////////////////////////////////////////


double coeffAinside = 0;
double coeffBinside = 0;
double coeffCinside = 0;
double termistorInside = 0;
double coeffAoutside = 0;
double coeffBoutside = 0;
double coeffCoutside = 0;
double termistorOutside = 0;


//////////////////////////////////////////////////////////////////////////////////////////



// Working Variables /////////////////////////////////////////////////////////////////////

double DHT_Temperature = 0; // Value of the Temperature (°C) from the DHT-22
double DHT_Humidity = 0; // Value of the Humidity (%) from the DHT-22
double peltierTemperature = 0; // Value of the temperature (°C) from the thermistor on the Peltier Module
double outsideTemperature = 0;
double targetTemperature = 0;

double peltierValue = 0;


char inputs [20];
char oldInputs[20];

//////////////////////////////////////////////////////////////////////////////////////////


void setup()
{
  Serial.begin(9600); // Init Serial Monitor

  // Set the pinModes
  pinMode(pinDHT,INPUT); //  Mesure sur le module DHT
  pinMode(pinTemperaturePeltier,INPUT); // Mesure à la thermistance
  pinMode(pinOpenDetector, INPUT); //  Detect if door is open
  pinMode(pinOnboardLED, OUTPUT);
  pinMode(pinPeltier, OUTPUT);
  pinMode(pinFan, OUTPUT);
  
}

void loop()
{
  
}


// Get all the inputs on the Arduno (Sensor, ...)
void getInputs(){   
    sprintf(inputs, "SS:%03X:%c", // sprintf the string in the inputs array
        analogRead(pinTemperaturePeltier), // Analog Read on pin...
        digitalRead(pinOpenDetector) // Digital Read on pin...
      // ...
    );
}



// Send data to the serial monitor
// OK !
void sendSerialData(){
  if( strcmp(inputs, oldInputs) != 0) // If the data isn't new
    {
      strcpy(oldInputs, inputs); // Add the new inputs to the oldInputs array
      Serial.println(inputs); // Send to the Serial port
    }
}


// Get data from the Java Program
// TO ADAPT
void getSerialData()
{
  
  // If new data available from the serial port
  if(Serial.available()){
        int ind=0;
        char buff[5];
        while(Serial.available()){
            unsigned char c = Serial.read();
            buff[ind] = c;
            if(ind++ > 6) break;
        }
        buff[2]=0; 
}
}


void setNewVariables()
{
  
}


// Command the parts of the Fridge
void actionableIntelligence()
{
  
}
