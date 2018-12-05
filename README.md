
# Pimp My Fridge !
## A USB Mini-Fridge with a touch of intelligence.
![enter image description here](https://raw.githubusercontent.com/Studisys/USB-PimpMyFridge-CESI-Engineering/master/Deliverables/Photos/p2.jpg)![enter image description here](https://raw.githubusercontent.com/Studisys/USB-PimpMyFridge-CESI-Engineering/master/Deliverables/Photos/p4.png)
# Context
In a school context, we had to add an "intelligence" to a Mini-USB Fridge that can be bought for 5 or 10$ on eBay or AliExpress. We had to make it possible to reach a setpoint temperature and keep it stable. This is part of our thermodynamic lessons.

We added an Arduino and various sensors to the fridge, and created a Java application, communicating with the Arduino, aiming at displaying the sensor values and choosing a setpoint temperature.

# Hardware
We used :
- 1 Mini-USB Fridge as on the picture (that uses a Peltier Module)
- 1 Arduino UNO
- 1 Breadboard
- 2 DHT22 sensors
- 1 termistor
- 1 transistor MOSFET (NPN or PNP) or relay
- A few resistances

# How to use this
1) Do the required wiring (requires to unsolder the Peltier module located under the USB Fridge to use it in the schematic)
![](https://raw.githubusercontent.com/Studisys/USB-PimpMyFridge-CESI-Engineering/master/Deliverables/Schematic/schematic.png)

2) Adapt the Arduino code (sensors pins, ...) and upload it to the Arduino
3) Plug the Arduino in the computer (important ! If the Arduino is not plugged in, the Java App won't launch)
3) Run the PimpMyFridge.jar located in /Deliverables/JavaApp/JAR_EXECUTABLE (be sure to run it in the same folder as rxtxSerial.dll) or add the /src folder to a Java editor (e.g : Eclipse), build and run it.
4) Done

# Group Members
- Joël DIDIER (@Studisys)
- Vicente VAZ (@Trapadore)
- Louis MARJOLET (@LoLouis10)

