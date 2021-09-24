package com.example.pharmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pharmapp.databinding.FragmentGalleryBinding;
import com.example.pharmapp.ui.gallery.GalleryFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView imagen;
    EditText editTextTextPersonName,editTextTextPassword;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        imagen = findViewById(R.id.logo);
        imagen.setImageResource(R.drawable.logo);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUsuario("http://192.168.0.87/medicamentos_android/validarusuario.php");
            }
        });
    }

    private void validarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent intent= new Intent(getApplicationContext(), Main2Activity.class);
                    //Bundle parametros = new Bundle();
                    //parametros.putString("usuario",editTextTextPersonName.getText().toString());



                    //intent.putExtra(Main2Activity.nombres,editTextTextPersonName.getText());
                    String valor = editTextTextPersonName.getText().toString();
                    intent.putExtra("usuario",valor);
                    startActivity(intent);


                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("usuario",editTextTextPersonName.getText().toString());
                parametros.put("contraseña",editTextTextPassword.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //metodo boton siguiente
    public void Siguiente(View view) {
        Intent siguiente = new Intent(this, registrarse.class);
        startActivity(siguiente);
    }
}