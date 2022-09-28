package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Cuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);
        TextView usuarioc = findViewById(R.id.tvusuarioc);
        //Mostrar el nombre y el rol enviados desde MainActivity.java
        usuarioc.setText(usuarioc.getText().toString()+"Usuario: "+getIntent().getStringExtra("sname")+"rol"+getIntent().getStringExtra("srol"));

        EditText acemail = findViewById(R.id.etemail);
        EditText acfecha = findViewById(R.id.etdate);
        EditText acbalancecuenta = findViewById(R.id.etaccount);
        Button acagregar = findViewById(R.id.btnagregar);
        acagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serchAccount(acemail.getText().toString(),acfecha.getText().toString(),acbalancecuenta.getText().toString());
            }

            private void serchAccount(String semail, String sfecha, String sbalancecuenta) {
                sqlBanco ohBanco = new sqlBanco(getApplicationContext(),"dbbanco",null,1);
                SQLiteDatabase db = ohBanco.getReadableDatabase();
                String sql = "Select email From customer Where email = '"+semail+"'";
                Cursor cAccount = db.rawQuery(sql,null);
                if(!cAccount.moveToFirst()) {
                    SQLiteDatabase dbadd = ohBanco.getWritableDatabase();
                    try {
                        ContentValues cvAccount = new ContentValues();
                        cvAccount.put("email", semail);
                        cvAccount.put("fecha", sfecha);
                        cvAccount.put("balancecuenta", sbalancecuenta);
                        dbadd.insert("account", null, cvAccount);
                        dbadd.close();
                        Toast.makeText(getApplicationContext(), "Cliente agregado correctamente", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Error:  "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Este email ya tiene una cuenta",Toast.LENGTH_SHORT).show();

                }
            }


        });



    }


}
