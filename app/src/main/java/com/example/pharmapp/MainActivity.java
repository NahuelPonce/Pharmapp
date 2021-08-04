package com.example.pharmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView imagen;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagen = findViewById(R.id.logo);
        imagen.setImageResource(R.drawable.blue_modern_icons_maternity_doctor_logo);
    }

    //metodo boton siguiente
    public void Siguiente(View view) {
        Intent siguiente = new Intent(this, Main2Activity.class);
        startActivity(siguiente);

    }
}