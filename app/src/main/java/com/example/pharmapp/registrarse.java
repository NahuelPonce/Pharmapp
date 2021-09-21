package com.example.pharmapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class registrarse extends AppCompatActivity {

    ImageView foto;
    Button registrar;
    EditText nombre;
    EditText apellido;
    EditText usuario;
    EditText contraseña;
    EditText dni;
    EditText calle;
    EditText altura;
    EditText depto;
    EditText obrasocial;
    EditText numafiliado;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                        foto.setImageURI(uri);
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(registrarse.this.getContentResolver(), uri );
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                            byte[] bArray = bos.toByteArray();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });



        Button selectButton = findViewById(R.id.button7);
        foto = findViewById(R.id.imageView5);
        registrar = findViewById(R.id.button6);

        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        usuario = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.contraseña);
        dni = findViewById(R.id.dni);
        calle = findViewById(R.id.calle);
        altura = findViewById(R.id.altura);
        depto = findViewById(R.id.depto);
        obrasocial = findViewById(R.id.obrasocial);
        numafiliado = findViewById(R.id.numeroafiliado);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass in the mime type you'd like to allow the user to select
                // as the input
                mGetContent.launch("image/*");
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nom, ape,usu,con,cal,dp,os,ft;
                final String dn;
                final String alt;
                final String na;
                nom = String.valueOf(nombre.getText());
                ape = String.valueOf(apellido.getText());
                usu = String.valueOf(usuario.getText());
                con = String.valueOf(contraseña.getText());
                dn = String.valueOf(dni.getText());
                ft= getResources()
                cal = String.valueOf(calle.getText());
                alt = String.valueOf(altura.getText());
                dp = String.valueOf(depto.getText());
                os = String.valueOf(obrasocial.getText());
                na = String.valueOf(numafiliado.getText());



                registrarusuario(nom,ape,usu,con,dn,ft,cal,alt,dp,os,na);



                Intent intent = new Intent(registrarse.this, MainActivity.class);
                startActivity(intent);
            }

        });





    }


    private void registrarusuario(String nom, String ape, String usu, String con, String dn, String ft, String cal, String alt, String dp, String os, String  na){
        String URL = "http://192.168.0.87/medicamentos_android/insertar.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Successfully Registered")){
                    Intent intent= new Intent(getApplicationContext(), Main2Activity.class);
                    startActivity(intent);
                } else {
                        Toast.makeText(registrarse.this,response,Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(registrarse.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();

                parametros.put("usuario",usu);
                parametros.put("contraseña",con);
                parametros.put("nombre",nom);
                parametros.put("apellido",ape);
                parametros.put("dni",dn);
                parametros.put("foto",ft);
                parametros.put("calle",cal);
                parametros.put("altura",alt);
                parametros.put("dpto",dp);
                parametros.put("obrasocial",os);
                parametros.put("numafiliado",na);

                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void volverinicio(View view) {
        Intent siguiente = new Intent(this, MainActivity.class);
        startActivity(siguiente);
    }
}