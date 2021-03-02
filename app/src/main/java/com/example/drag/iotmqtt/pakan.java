package com.example.drag.iotmqtt;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import android.text.format.DateFormat;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class pakan extends AppCompatActivity {

    private TimePickerDialog timePickerDialog;
    private TimePickerDialog tp2;
    private TimePickerDialog tp3;
    private TimePickerDialog tp4;
    private TextView tvTimeResult,tvTimeResult2;
    private Button button,button2;

    FirebaseDatabase rootNode;
    DatabaseReference reference,refJmlPakan,db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pakan);

        tvTimeResult = (TextView) findViewById(R.id.thour);
        tvTimeResult2 = (TextView) findViewById(R.id.tmin);
        final Spinner dropdown = findViewById(R.id.spinner2);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button9);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ShowTimeDialog();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("alarm");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String thour2 = tvTimeResult.getText().toString();
                thour2 = thour2.replace("\"","");
                int thour = Integer.parseInt(thour2);
                System.out.println(thour2);
                String tmin2 = tvTimeResult2.getText().toString();
                tmin2 = tmin2.replace("\"","");
                int tmin = Integer.parseInt(tmin2);
                System.out.println(tmin);
                String pakanGram2 = dropdown.getSelectedItem().toString();

                pakanGram2 = pakanGram2.replace("\"","");
                int pakanGram = Integer.parseInt(pakanGram2);
                System.out.println(pakanGram);
                UserHelperClass helperClass = new UserHelperClass(thour,tmin,pakanGram);
                reference.setValue(helperClass);
                String id = UUID.randomUUID().toString();
                Map<String,Object> doc = new HashMap<>();
                doc.put("Jam",thour);
                doc.put("Menit",tmin);
                doc.put("JumlahPakan",pakanGram2);
                db.collection("lele").document(id).set(doc);
            }
        });

        String[] items = new String[]{"200","400","600","800"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,items);
        dropdown.setAdapter(adapter);




}
    private void ShowTimeDialog(){
                Calendar calendar = Calendar.getInstance();

                timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvTimeResult.setText(+hourOfDay+"");
                        tvTimeResult2.setText(+minute+"");


                    }
                },
                        calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));
                timePickerDialog.show();
    }




}
