#include <SoftwareSerial.h>
#include <Streaming.h>

const int bluetoothTx = 7;
const int bluetoothRx = 8;
int ledCucina = 7;
int ledBagno = 11;
int ledCamera = 10;
int ledStudio = 9;
int ledSalone = 8;
int i=0;
char dataFromBt;
boolean lightBlink = false;
SoftwareSerial bluetooth(bluetoothTx, bluetoothRx);


  void setup()
  {
    Serial.begin(115200);
    bluetooth.begin(115200);
  }

  void loop()
  {
    bluetooth << "a";
    Serial << i<<endl;
    delay(1000);
    i=i+1;
    if (bluetooth.available() > 0)
    {
   
    dataFromBt = (char)bluetooth.read();
    Serial<<"byte coming "<<dataFromBt<<endl;

   
 }
 
 
 }

