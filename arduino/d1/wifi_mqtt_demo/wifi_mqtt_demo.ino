#include <PubSubClient.h>
#include <ESP8266WiFi.h>
#include <Servo.h> // 舵机控制
#include <DHT.h> // 温湿度传感器
#include <ArduinoJson.h>
#include <stdio.h>
#include <string.h>

const char* ssid = "ChinaNet-xEDd";       // wifi热点名称
const char* passwd = (char*)"ektj699s";  // wifi热点密码
const char* mqtt_server = "192.168.1.28"; // mqtt服务器地址
const int port = 1883; // mqtt服务器端口号
const char* MESSAGE_TOPIC = "SENSOR_DATA"; // 发送消息mqtt topic
const char* CONTROL_TOPIC = "SENSOR_CONTROL";
const char* client_id = "WeMosD1-01"; //这个是板子的编号

//定义
#define DuoPIN D5   // 定义D5管脚为舵机控制引脚
#define DHTPIN D4            // 定义D5管脚为温湿度传感器数据传入引脚
#define DHTTYPE DHT11        // 指定传感器类型


DHT dht(DHTPIN, DHTTYPE);
long lastSend;
WiFiClient espclient;
PubSubClient client(espclient);
Servo myservo;  //创建一个舵机控制对象

//获取传感器数据进行发送
void gettemp() {
  //上传的字符串进行拼接转义
  float h = dht.readHumidity();//读湿度
  float t = dht.readTemperature();//读温度，默认为摄氏度

  char attributes[100];
  sprintf(attributes, "{\"chipType\":\"%s\",\"sensorType\":\"DHT11\",\"data\":[{\"key\":\"温度\",\"value\":\"%.2f\"},{\"key\":\"湿度\",\"value\":\"%.2f\"}]}", client_id, t, h);
  client.publish(MESSAGE_TOPIC, attributes );

}
//初始化wifi
void init_wifi() {
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
      // 连接成功时订阅主题
      client.subscribe(CONTROL_TOPIC);
    } else {
      Serial.print("failed,rc=");
      Serial.print(client.state());
      Serial.print("try 5 seconds later");
      delay(5000);
    }
  }
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);   // 打印主题信息
  Serial.print("]");
  char controlData[255];
  for (int i = 0; i < length; i++) {
    controlData[i] = (char)payload[i];
    Serial.print((char)payload[i]); // 打印主题内容
  }
  StaticJsonDocument<255> jsonBuffer; //声明一个JsonDocument对象，长度200
  // 反序列化JSON
  DeserializationError error = deserializeJson(jsonBuffer, controlData);
  if (error)
  {
    Serial.print(F("deserializeJson() failed: "));
    Serial.println(error.f_str());
    return;
  }
  // 解析JSON {"chipType":"WeMosD1-01","sensorType":"DUO","data":10}
  const char* chipType = jsonBuffer["chipType"];
  const char* sensorType = jsonBuffer["sensorType"];
  long data1  = jsonBuffer["data"];
  if (strcmp(chipType, client_id) == 0) {
    if (strcmp(sensorType, "DUO") == 0) {
      myservo.write(data1);
    }
  }
}

//设备初始化
void setup() {
  Serial.begin(9600);
  init_wifi();
  client.setServer(mqtt_server, port);
  client.setCallback(callback);
  dht.begin();            //DHT开始工作
  myservo.attach(DuoPIN);
  lastSend = 0;
}
void loop() {
  reconnect();  //1.首先进行服务器的连接  因为这是循环函数 所以执行一次后判断就行了
  delay(20000);
  gettemp();
  client.loop();
}
