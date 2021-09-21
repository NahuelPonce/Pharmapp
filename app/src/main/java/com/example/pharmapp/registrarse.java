package com.example.pharmapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                        //foto.setImageURI(uri);

                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri );
                            //ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            //bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                            //byte[] bArray = bos.toByteArray();
                            foto.setImageBitmap(bitmap);
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
                final String nom;
                final String ape;
                final String usu;
                final String con;
                final String cal;
                final String dp;
                final String os;
                final String ft;
                final String dn;
                final String alt;
                final String na;
                nom = String.valueOf(nombre.getText());
                ape = String.valueOf(apellido.getText());
                usu = String.valueOf(usuario.getText());
                con = String.valueOf(contraseña.getText());
                dn = String.valueOf(dni.getText());
                ft=  String.valueOf(foto.getResources());
                cal = String.valueOf(calle.getText());
                alt = String.valueOf(altura.getText());
                dp = String.valueOf(depto.getText());
                os = String.valueOf(obrasocial.getText());
                na = String.valueOf(numafiliado.getText());

                if (nom.length() == 0 || ape.length() == 0 || usu.length() == 0 || con.length() == 0 || dn.length() == 0 || cal.length() == 0 || alt.length() == 0 || os.length() == 0 || na.length() == 0){
                      Toast.makeText(registrarse.this,"Hay campos requeridos incompletos", Toast.LENGTH_SHORT).show();

                } else {
                    registrarusuario(nom,ape,usu,con,dn,ft,cal,alt,dp,os,na);
                    Intent intent = new Intent(registrarse.this, MainActivity.class);
                    startActivity(intent);

                }


            }

        });


    }

    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodeImage = Base64.getEncoder().encodeToString(imageBytes);
        return encodeImage;
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

                String foto= getStringImagen(bitmap);

                parametros.put("usuario",usu);
                parametros.put("contraseña",con);
                parametros.put("nombre",nom);
                parametros.put("apellido",ape);
                parametros.put("dni",dn);
                parametros.put("foto",foto);
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