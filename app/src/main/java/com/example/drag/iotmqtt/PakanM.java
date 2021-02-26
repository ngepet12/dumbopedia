package com.example.drag.iotmqtt;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import com.firebase.client.*;

public class PakanM extends AppCompatActivity {
    Button btn200, btn400, btn600, btn800;
    private Firebase fconn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        MqttAndroidClient client;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pakanm);

        String clientID = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.mqtt-dashboard.com:1883", clientID);
        fconn = new Firebase("https://iotmqtt-9fd39.firebaseio.com/pakanGram");
        try {
            client.connect();
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(PakanM.this, "connected", Toast.LENGTH_LONG).show();
                    setSubscription();

                }

                private void setSubscription() {

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();


        }
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String time = new Timestamp(System.currentTimeMillis()).toString();

                System.out.println("Time:\t" + time +
                        "Topic:\t" + topic +
                        "Message:\t" + new String(message.getPayload()) +
                        "Qos:\t" + message.getQos());
                Toast.makeText(PakanM.this, new String(message.getPayload()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        btn200 = (Button) findViewById(R.id.button2);
        btn400 = (Button) findViewById(R.id.button3);
        btn600 = (Button) findViewById(R.id.button4);
        btn800 = (Button) findViewById(R.id.button5);
        int btn200Gram = 200;
        int btn400Gram = 400;
        int btn600Gram = 600;
        int btn800Gram = 800;

        mqtt_connectx();
    }

        public void mqtt_connectx(){
            String clientId = MqttClient.generateClientId();
            MqttConnectOptions options = new MqttConnectOptions();
            final MqttAndroidClient client =
                    new MqttAndroidClient(PakanM.this, "tcp://broker.mqtt-dashboard.com:1883",
                            clientId);
            try {
                options.setKeepAliveInterval(5);


                IMqttToken token = client.connect(options);
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        btn200.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String topic = "lele1";
                                String payload = "2";
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                }catch (UnsupportedEncodingException | MqttException e){

                                    e.printStackTrace();
                                }

                            }
                        });
                        btn400.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String topic = "lele1";
                                String payload = "3";
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                }catch (UnsupportedEncodingException | MqttException e){

                                    e.printStackTrace();
                                }

                            }
                        });
                        btn600.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String topic = "lele1";
                                String payload = "4";
                                byte[] encodedPayload = new byte[0];
                                try {
                                    encodedPayload = payload.getBytes("UTF-8");
                                    MqttMessage message = new MqttMessage(encodedPayload);
                                    client.publish(topic, message);
                                }catch (UnsupportedEncodingException | MqttException e){

                                    e.printStackTrace();
                                }

                            }
                        });



                        };


                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                    }
                });
            }
            catch (MqttException e) {
                e.printStackTrace();
            }
        }










    }

