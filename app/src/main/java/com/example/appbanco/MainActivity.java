package com.example.appbanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText email = findViewById(R.id.etemail);
        EditText password = findViewById(R.id.etpassword);
        Button startsession = findViewById(R.id.btnstartsesion);
        TextView reglink = findViewById(R.id.tvregister);

        reglink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
        startsession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sEmail = email.getText().toString();
                String sPass = password.getText().toString();
                //chequear si email y password se digitaron correctamente
                if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    //conexion a la base de datos
                    sqlBanco ohBanco = new sqlBanco(getApplicationContext(), "dbbanco", null, 1);
                    SQLiteDatabase dbs = ohBanco.getReadableDatabase();
                    String query = "SELECT name, rol FROM customer WHERE email = '" + sEmail + "' and password = '" + sPass + "'";
                    Cursor cCust = dbs.rawQuery(query, null);
                    if (cCust.moveToFirst()) {
                        String rol = cCust.getString(1);
                        String name = cCust.getString(0);
                        if (rol.equals("Administrador")) {
                            //Invocar la actividad de cuenta con el parametro del nombre
                            Intent iCuenta = new Intent(getApplicationContext(), Cuenta.class);
                            iCuenta.putExtra("sname", name);//parametro enviado a la actividad de cuenta
                            iCuenta.putExtra("srol", rol);
                            startActivity(new Intent(getApplicationContext(), Cuenta.class));
                            startActivity(iCuenta);//Inicia la llamada de la actividad
                        } else {
                            startActivity(new Intent(getApplicationContext(), Usuario.class));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Email y/o contraseña INVALIDOS", Toast.LENGTH_SHORT).show();
                    }
                }
               else {
                   Toast.makeText(getApplicationContext(), "Debe ingresar email y contraseña", Toast.LENGTH_SHORT).show();
                    }

                }
        });

    }
}






