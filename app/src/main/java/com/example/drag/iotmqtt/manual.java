package com.example.drag.iotmqtt;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class manual extends AppCompatActivity {


    private RecyclerView.LayoutManager ImData;

    private EditText catatan;





    Button btn2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MqttAndroidClient client;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pakanmanual);
        catatan = (EditText) findViewById(R.id.editText);


       String clientID= MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.mqtt-dashboard.com:1883", clientID);
        try {
            client.connect();
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(manual.this, "connected", Toast.LENGTH_LONG).show();
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
                Toast.makeText(manual.this, new String(message.getPayload()), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }

        });

        final Spinner dropdown = findViewById(R.id.spinner);
        btn2 = (Button) findViewById(R.id.button8);

        mqtt_connectx();
    }



        public void mqtt_connectx(){
            final Spinner dropdown = findViewById(R.id.spinner);
            btn2 = (Button) findViewById(R.id.button8);

            String clientId = MqttClient.generateClientId();
            MqttConnectOptions options = new MqttConnectOptions();
            final MqttAndroidClient client =
                    new MqttAndroidClient(manual.this, "tcp://broker.mqtt-dashboard.com:1883",
                            clientId);
            try {
                options.setKeepAliveInterval(5);


                IMqttToken token = client.connect(options);
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {

                        btn2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String pakanGram = dropdown.getSelectedItem().toString();
                                if (pakanGram.equals("200")) {
                                    String topic = "lele1";
                                    String payload = "2";
                                    byte[] encodedPayload = new byte[0];
                                    try {
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {

                                        e.printStackTrace();
                                    }

                                } else if (pakanGram.equals("400")) {
                                    String topic = "lele1";
                                    String payload = "3";
                                    byte[] encodedPayload = new byte[0];
                                    try {
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {

                                        e.printStackTrace();
                                    }

                                } else if (pakanGram.equals("600")) {
                                    String topic = "lele1";
                                    String payload = "4";
                                    byte[] encodedPayload = new byte[0];
                                    try {
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {

                                        e.printStackTrace();
                                    }

                                } else if (pakanGram.equals("800")) {
                                    String topic = "lele1";
                                    String payload = "5";
                                    byte[] encodedPayload = new byte[0];
                                    try {
                                        encodedPayload = payload.getBytes("UTF-8");
                                        MqttMessage message = new MqttMessage(encodedPayload);
                                        client.publish(topic, message);
                                    } catch (UnsupportedEncodingException | MqttException e) {

                                        e.printStackTrace();
                                    }

                                }

                                final String cacatan = catatan.getText().toString().trim();
                                final String jumlahpakan = dropdown.getSelectedItem().toString();
                                class addData extends AsyncTask<Void, Void, String> {
                                    ProgressDialog loading;

                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();
                                        loading = ProgressDialog.show(manual.this, "Adding", "tunggu", false, false);
                                    }

                                    @Override
                                    protected void onPostExecute(String s) {
                                        super.onPostExecute(s);
                                        loading.dismiss();
                                        Toast.makeText(manual.this,s, Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    protected String doInBackground(Void... voids) {
                                        HashMap<String, String> params = new HashMap<>();
                                        params.put(konfigurasi.KEY_EMP_catatan, cacatan);
                                        params.put(konfigurasi.KEY_EMP_jumlahpakan, jumlahpakan);

                                        RequestHandler rh = new RequestHandler();
                                        String res = rh.sendPostRequest(konfigurasi.URL_ADD, params);
                                        return res;



                                    }



                                }
                                addData ae = new addData();
                                ae.execute();
                            }




                        });

                        String[] items = new String[]{"200","400","600","800"};
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,items);
                        dropdown.setAdapter(adapter);

                    }




                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                    }


                    });





            }catch (Exception e){

            }

        }

        }







