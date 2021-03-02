package com.example.drag.iotmqtt;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class manuals extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextCatatan;
    private EditText editTextJumlahPakan;

    private Button buttonAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputman);

        editTextCatatan = (EditText) findViewById(R.id.editTextx);
        editTextJumlahPakan = (EditText) findViewById(R.id.editTextz);

        buttonAdd = (Button) findViewById(R.id.button10);

        buttonAdd.setOnClickListener(this);
    }
        private void addData(){
        final String catatan = editTextCatatan.getText().toString().trim();
        final String jumlahpakan = editTextJumlahPakan.getText().toString().trim();

        class AddData extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(manuals.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(manuals.this,s,Toast.LENGTH_LONG).show();
            }



            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(konfigurasi.KEY_EMP_catatan,catatan);
                params.put(konfigurasi.KEY_EMP_jumlahpakan,jumlahpakan);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD,params);
                return res;
            }


        }
            AddData ae = new AddData();
            ae.execute();
        }

    @Override
    public void onClick(View v) {
                if (v == buttonAdd){
                    addData();

                }
    }
}
