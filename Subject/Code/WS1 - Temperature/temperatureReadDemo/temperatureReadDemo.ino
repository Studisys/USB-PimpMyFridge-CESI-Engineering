////////////////////////////////////////////////////////////////////////////////////
////     Project Pimp My Fridge - Smart Mini Fridge                             ////
////     CESI Engineering School | IT Engineering Curriculum - Exia (Year 2/5)  ////
////     Group X | Joël DIDIER / Vicente VAZ / Louis MARJOLET                   ////
////     19/11/2018 -> 05/12/2018                                               ////
////     GitHub ->                                                              ////
////     License -> MIT ()                                                      ////
////////////////////////////////////////////////////////////////////////////////////

#include <math.h>



////////////////////////////////////////////////////////////////////////////////////
////     Global Variables                                                       ////
////////////////////////////////////////////////////////////////////////////////////

float temperature = 0; // Value of the temperature



int temperaturePin = 0; // Pin Number for the temperature sensor (default : "0" - A0)
long valRes = 0;
int Uth = 0;
long Ug = 1023; // Voltage, 1023 = 5V (be sure to measure the effective value and input it)
long R = 10000; // Ohms, approximative (be sure to measure the effective value and input it)
int temperature = 0;





long coeffA = 0;
long coeffB = 0;
long coeffC = 0;


// Info on thermistor B57861S0103F040 :
// At 25°C, thermistor value is 10kOhms

void setup() {
  // put your setup code here, to run once:
  pinMode(temperaturePin, INPUT);
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  // Rth = ( Uth / (Ug-Uth) ) * R
  // (Ug est en bits, ex 1023 à 5V), R à 10000 (ohms), ...
  Uth = digitalRead(A0);
  valRes = (Uth * R)/(Ug - Uth);
  temperature = (1/((coeffA+coeffB*log(valRes)+coeffC*pow(log(valRes)))-273.15
  Serial.println(valRes);
  Serial.println(temperature);
}
