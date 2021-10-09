package com.example.pharmapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pharmapp.db.DbHelper;
import com.example.pharmapp.ui.gallery.GridViewAdapter;
import com.example.pharmapp.ui.gallery.Receta;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.navigation.Navigation.findNavController;


public class EditarPerfilUsuario extends Fragment {
    ImageView fotoperfil;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_editar_perfil_usuario, container, false);
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String user=preferences.getString("user","No exite la informacion");

        ImageView fotoperfil = v.findViewById(R.id.imagperfil);

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri> () {
                    @Override
                    public void onActivityResult(Uri uri) {
                        //foto.setImageURI((Uri) result);
                            try {

                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri );
                                fotoperfil.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                    }
                });
        Button foto = v.findViewById(R.id.buttonperfil);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pass in the mime type you'd like to allow the user to select
                // as the input
                mGetContent.launch("image/*");
            }
        });




        EditText nombreperfil= v.findViewById(R.id.nomperfil);
        EditText apellidoperfil = v.findViewById(R.id.apeperfil);
        TextView usuarioperfil = v.findViewById(R.id.usuperfil);
        EditText contraperfil = v.findViewById(R.id.contraperf);
        EditText obrasocialperfil = v.findViewById(R.id.obraperfil);
        EditText numeroafiliadoperfil = v.findViewById(R.id.numperfil);
        EditText dniperfil = v.findViewById(R.id.dniperf);

        EditText localidadperfil = v.findViewById(R.id.locperfil);
        EditText calleperfil= v.findViewById(R.id.caperfil);
        EditText alturaperfil = v.findViewById(R.id.altperfil);
        EditText dptoperfil = v.findViewById(R.id.dpperfil);




        //traer usuario
        String URLUSUARIO="http://192.168.0.39/medicamentos_android/buscarusuario.php?usuario="+user;


        StringRequest stringRequ = new StringRequest(Request.Method.GET, URLUSUARIO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String usuario, contraseña, nombre, apellido, dni,nombrecompleto,foto, url, os,localidad, calle,altura, direccion,dpto, numos;


                //Toast.makeText(Main2Activity.this,response,Toast.LENGTH_SHORT).show();
                try {

                    JSONObject obj = new JSONObject(response);
                    usuario = obj.getString("usuario");
                    contraseña =obj.getString("contraseña");
                    nombre = obj.getString("nombre");
                    apellido = obj.getString("apellido");
                    dni= obj.getString("dni");
                    localidad = obj.getString("localidad");
                    calle = obj.getString("calle");
                    altura = obj.getString("altura");
                    dpto = obj.getString("dpto");
                    os = obj.getString("obrasocial");
                    numos = obj.getString("numafiliado");
                    foto= obj.getString("foto");

                    nombreperfil.setText(nombre);
                    apellidoperfil.setText(apellido);
                    usuarioperfil.setText(usuario);
                    contraperfil.setText(contraseña);
                    obrasocialperfil.setText(os);
                    localidadperfil.setText(localidad);
                    dniperfil.setText(dni);
                    calleperfil.setText(calle);
                    alturaperfil.setText(altura);
                    numeroafiliadoperfil.setText(numos);
                    dptoperfil.setText(dpto);

                    if(foto.isEmpty()){
                        fotoperfil.setImageResource(R.drawable.blue_modern_icons_maternity_doctor_logo);
                    }else {
                        url = "http://192.168.0.39/medicamentos_android/drawable/"+usuario+".png";
                        Picasso.with(getActivity())
                                .load(url)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(fotoperfil);
                    }


                } catch (JSONException e) {
                    Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQue= Volley.newRequestQueue(getContext());
        requestQue.add(stringRequ);


        Button editar = v.findViewById(R.id.editarcontendio);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*nombre_p= v.findViewById(R.id.nomperfil);
                apellido_p = v.findViewById(R.id.apeperfil);
                dni_p= v.findViewById(R.id.dniperf);
                contra_p = v.findViewById(R.id.contraperf);
                obrasocial_p = v.findViewById(R.id.obraperfil);
                numeroafiliado_p = v.findViewById(R.id.numperfil);
                localidad_p = v.findViewById(R.id.locperfil);
                calle_p= v.findViewById(R.id.caperfil);
                altura_p = v.findViewById(R.id.altperfil);
                //EditText dptoperfil = v.findViewById(R.id.dpperfil);

                 */

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
                final String lo;


                nom = String.valueOf(nombreperfil.getText());
                usu =String.valueOf(usuarioperfil.getText());

                ape = String.valueOf(apellidoperfil.getText());
                con = String.valueOf(contraperfil.getText());
                dn = String.valueOf(dniperfil.getText());
                //ft=  String.valueOf(fotoperfil.getResources());
                lo= String.valueOf(localidadperfil.getText());
                cal = String.valueOf(calleperfil.getText());
                alt = String.valueOf(alturaperfil.getText());
                //dp = String.valueOf(dptoperfil.getText());
                os = String.valueOf(obrasocialperfil.getText());
                na = String.valueOf(numeroafiliadoperfil.getText());


                if (nom.length() == 0 || ape.length() == 0 || con.length() == 0 || dn.length() == 0 || os.length() == 0 || lo.length() == 0 || cal.length() == 0 || alt.length() == 0 || na.length() == 0){
                    Toast.makeText(getActivity(),"Hay campos requeridos incompletos", Toast.LENGTH_SHORT).show();

                } else {


                    registrarusuario(usu,nom,ape,con,dn,lo,cal,alt,os,na,fotoperfil);


                    findNavController(v).navigate(R.id.action_editarPerfilUsuario_to_nav_home);



                    //Intent intent= new Intent(getActivity(), Main2Activity.class);
                    //startActivity(intent);

                }




            }
        });
        return  v;
    }

    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodeImage = Base64.getEncoder().encodeToString(imageBytes);
        return encodeImage;
    }

    private void registrarusuario(String usu, String nom, String ape, String con, String dn, String lo, String cal, String alt, String os, String na, ImageView fotoperfil){
        String URL = "http://192.168.0.39/medicamentos_android/actualizarusuario.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"hay error",Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();

                Bitmap bitmap = ((BitmapDrawable)fotoperfil.getDrawable()).getBitmap();

                String foto= getStringImagen(bitmap);

                parametros.put("usuario",usu);
                parametros.put("contraseña",con);
                parametros.put("nombre",nom);
                parametros.put("apellido",ape);
                parametros.put("dni",dn);
                parametros.put("foto",foto);
                parametros.put("localidad",lo);
                parametros.put("calle",cal);
                parametros.put("altura",alt);
                //parametros.put("dpto",dp);
                parametros.put("obrasocial",os);
                parametros.put("numafiliado",na);

                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


}