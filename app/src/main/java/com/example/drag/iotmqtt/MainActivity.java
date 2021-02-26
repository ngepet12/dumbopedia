package com.example.drag.iotmqtt;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;

public class MainActivity extends AppCompatActivity {
    //init textview
    private TextView nilai;

    //Connection for Firebase cloud
    private Firebase fconn;

    Button on1, off1, Status, setTime,onx4;
    TextView txv1, ttx, thx, status, xxs;
    EditText editx;

    private static final String DB_URL = "jdbc:mysql://192.168.1.6/coba";
    private static final String USER = "root";
    private static final String PASS = "";


    int t1Hour, t1Minute, t2Hour, t2Minute;


    MqttAndroidClient clients;


    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        //generating token

        thx = (TextView) findViewById(R.id.txt3);


        String clientId = MqttClient.generateClientId();
        clients = new MqttAndroidClient(this.getApplicationContext(), "tcp://broker.mqtt-dashboard.com:1883", clientId);

        try {

            clients.connect();
            IMqttToken token = clients.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(MainActivity.this, "connected", Toast.LENGTH_LONG).show();
                    setSubscription();
                }

                private void setSubscription() {
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(MainActivity.this, "Connection Failed", Toast.LENGTH_LONG);
                }
            });


        } catch (MqttException e) {
            e.printStackTrace();
        }

        clients.setCallback(new MqttCallback() {
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
                Toast.makeText(MainActivity.this, new String(message.getPayload()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


        //read value from textview

        nilai = (TextView) findViewById(R.id.nilai);
        status = (TextView) findViewById(R.id.status);
        //open connection
        fconn = new Firebase("https://iotmqtt-9fd39.firebaseio.com/value");
        //realtime process

        fconn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //ambil nilai field value
                String value = dataSnapshot.getValue(String.class);
                value = value.substring(0, value.indexOf('.'));
                nilai.setText(value);
                System.out.println(value);
                if (Integer.parseInt(value) >= 11) {
                    status.setBackgroundColor(Color.RED);
                    status.setText("Pakan Ikan Habis");
                    status.setTextColor(Color.BLACK);

                    try {


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    status.setBackgroundColor(Color.GREEN);
                    status.setText("Pakan Ikan Masih");


                }


                //show nilai Component


            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        txv1 = findViewById(R.id.ttx);

        onx4 = findViewById(R.id.onx4);
        Button btn = (Button) findViewById(R.id.onx4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this ,manual.class));

            }
        });
        setTime = findViewById(R.id.button7);
        Button btnx = (Button) findViewById(R.id.button7);
        btnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this ,pakan.class));

            }
        });



        /*txv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Initialize Hour
                                t1Hour=hourOfDay;
                                t1Minute=minute;

                                Calendar calendar = Calendar.getInstance();

                                calendar.set(0,0,0,t1Hour,t1Minute);
                                txv1.setText(DateFormat.format("hh:mm aa",calendar));
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(t1Hour,t1Minute);
                timePickerDialog.show();

            }
        });*/
        on1 = (Button) findViewById(R.id.onx);
        off1 = (Button) findViewById(R.id.button6);
       setTime = (Button) findViewById(R.id.button7);
        thx = (TextView) findViewById(R.id.txt3);


        mqtt_connectx();
    }


    public void mqtt_connectx() {
        String clientId = MqttClient.generateClientId();
        MqttConnectOptions options = new MqttConnectOptions();
        final MqttAndroidClient client =
                new MqttAndroidClient(MainActivity.this, "tcp://broker.mqtt-dashboard.com:1883",
                        clientId);

        try {
            options.setKeepAliveInterval(5);
            options.setUserName("drago");
            options.setPassword("1234567".toCharArray());

            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(final IMqttToken asyncActionToken) {

                    on1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String topic = "lele1";
                            String payload = "1";
                            byte[] encodedPayload = new byte[0];
                            try {
                                encodedPayload = payload.getBytes("UTF-8");
                                MqttMessage message = new MqttMessage(encodedPayload);
                                client.publish(topic, message);

                            } catch (UnsupportedEncodingException | MqttException e) {
                                e.printStackTrace();

                            }


                        }


                    });
                    ttx = findViewById(R.id.ttx);


                    off1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String topic = "lele1";
                            String payload = "2";
                            byte[] encodedPayload = new byte[0];
                            try {
                                encodedPayload = payload.getBytes("UTF-8");
                                MqttMessage message = new MqttMessage(encodedPayload);
                                client.publish(topic, message);
                                Toast.makeText(MainActivity.this, new String(message.getPayload()), Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException | MqttException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    //Toast.makeText(MainActivity.this,"Connected",Toast.LENGTH_LONG).show();

                    try {
                        client.subscribe("lele2", 2);


                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    client.setCallback(new MqttCallback() {
                        private Object MqttCallback;

                        @Override
                        public void connectionLost(Throwable cause) {
                            Log.d(TAG, "Connection Lost");
                        }

                        @Override
                        public void messageArrived(String lele2, MqttMessage message) throws Exception {
                            Log.d(TAG, "topic:" + lele2 + "msg:" + new String(message.getPayload()));
                            Toast.makeText(MainActivity.this, new String(message.getPayload()), Toast.LENGTH_SHORT).show();
                            String CHANNEL_ID = null;


                        }

                        @Override
                        public void deliveryComplete(IMqttDeliveryToken token) {

                        }
                    });
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                    Toast.makeText(MainActivity.this, "Check Network", Toast.LENGTH_LONG).show();

                }

            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /*public void on1(View view) {
        Send ObjSend = new Send();
        ObjSend.execute("");
    }

    private class Send extends AsyncTask<String, String, String> {
        String msg = "";

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                if (conn == null) {
                    msg = "Wrong";

                } else {
                    String query = "INSERT INTO col_here values ('200')";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(query);


                }
                conn.close();
            } catch (Exception e) {

            }

            return msg;
        }*/


    public void subscribeTopic(final String lele2) {
        try {
            Log.d("tag", "lele2" + lele2);
            final MqttClient client = null;
            Log.d("tag", "client.isConnected()>>>>" + client.isConnected());
            if (client.isConnected()) {
                client.subscribe(lele2, 2);
                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {

                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception {
                        Log.d("tag", "message" + new String(message.getPayload()));
                        Log.d("tag", "topic" + lele2);
                        String time = new Timestamp(System.currentTimeMillis()).toString();
                        System.out.println("Time:\t" + time +
                                "Topic:\t" + topic +
                                "Message:\t" + new String(message.getPayload()) +
                                "Qos:\t" + message.getQos());
                        Toast.makeText(MainActivity.this, new String(message.getPayload()), Toast.LENGTH_SHORT).show();


                        Object parseMqttMessage;
                        parseMqttMessage(new String(message.getPayload()));


                    }

                    private void parseMqttMessage(String s) {
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {

                    }
                });

            }
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    public void onx2(View view) {

        Send ObjSend = new Send();
        ObjSend.execute("");

    }

    private class Send extends AsyncTask<String, String, String> {
        String msg = "";
        String text = editx.getText().toString();

        @Override
        protected void onPreExecute() {
            editx.setText("iNSERTING");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(DB_URL);
                if (conn == null) {
                    msg = "fail";

                } else {
                    String query = "INSERT INTO col_here (row_here,volume) VALUES ('"+text+"','200')";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(query);
                    msg = "Success";
                }
                conn.close();
            } catch (Exception e) {


            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            return;
        }
    }
}


