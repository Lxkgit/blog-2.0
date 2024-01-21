#include <ESP8266WiFi.h>
#include <PubSubClient.h>
#include <DHT.h>

const char* ssid = "TP-LINK_424D";       //“wifi热点名称”
const char* passwd = (char*)"tplink96012";  //”wifi热点密码”
const char* mqtt_server = "192.168.0.106"; // 服务器地址
const int port = 1883;//服务器端口号
const char* topic_name = "test";
const char*client_id = "wemos001";//这个是板子的编号  我没有懂 但是不影响使用

//定义针脚
#define DHTPIN 4            //定义类型，DHT11或者其它
#define DHTTYPE DHT11        //进行初始设置 
DHT dht(DHTPIN, DHTTYPE);

unsigned long lastSend;
WiFiClient espclient;
PubSubClient client(espclient);

//获取传感器数据进行发送
void gettemp()
{
  //上传的字符串进行拼接转义
  float h = dht.readHumidity();//读湿度
  float t = dht.readTemperature();//读温度，默认为摄氏度

  char attributes[100];
  sprintf(attributes, "{\"h\":%.2f, \"t\":%.2f}", h, t); // 将num转换为字符串并赋值给str

//  payload.toCharArray( attributes, 100 );
  client.publish(topic_name, attributes );

}
//初始化wifi
void setup_wifi() {
  delay(10);
  WiFi.begin(ssid, passwd);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print("...");
  }
}
//连接服务器
void reconnect() {
  while (!client.connected()) { //如果没有连上
    if (client.connect(client_id)) { //接入时本设备id
      Serial.println("connect success");
    } else {
      Serial.print("failed,rc=");
      Serial.print(client.state());
      Serial.print("try 5 seconds later");
      delay(5000);
    }
  }
}
//设备初始化
void setup() {
  Serial.begin(9600);
  setup_wifi();
  client.setServer(mqtt_server, port);
  dht.begin();            //DHT开始工作
  lastSend = 0;
}
void loop() {
  reconnect();  //1.首先进行服务器的连接  因为这是循环函数 所以执行一次后判断就行了
  if (millis() - lastSend > 10000) {
    gettemp();
    lastSend = millis();
  }
  client.loop();
}
