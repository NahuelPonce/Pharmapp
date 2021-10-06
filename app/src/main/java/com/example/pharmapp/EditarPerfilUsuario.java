package com.example.pharmapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static androidx.navigation.Navigation.findNavController;


public class EditarPerfilUsuario extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_editar_perfil_usuario, container, false);
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String user=preferences.getString("user","No exite la informacion");

        //traer usuario
        String URLUSUARIO="http://192.168.0.87/medicamentos_android/buscarusuario.php?usuario="+user;


        StringRequest stringRequ = new StringRequest(Request.Method.GET, URLUSUARIO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String usuario, contraseña, nombre, apellido,nombrecompleto,foto, url, os,localidad, calle,altura, direccion,dpto, numos;
                EditText nombreperfil= v.findViewById(R.id.nomperfil);
                EditText apellidoperfil = v.findViewById(R.id.apeperfil);
                EditText usuarioperfil = v.findViewById(R.id.usuperfil);
                EditText obrasocialperfil = v.findViewById(R.id.obraperfil);
                EditText numeroafiliadoperfil = v.findViewById(R.id.numperfil);
                EditText localidadperfil = v.findViewById(R.id.locperfil);
                EditText calleperfil= v.findViewById(R.id.caperfil);
                EditText alturaperfil = v.findViewById(R.id.altperfil);
                EditText dptoperfil = v.findViewById(R.id.dpperfil);

                ImageView fotoperfil = v.findViewById(R.id.imagperfil);

                //Toast.makeText(Main2Activity.this,response,Toast.LENGTH_SHORT).show();
                try {

                    JSONObject obj = new JSONObject(response);
                    usuario = obj.getString("usuario");
                    nombre = obj.getString("nombre");
                    apellido = obj.getString("apellido");
                    localidad = obj.getString("localidad");
                    calle = obj.getString("calle");
                    altura = obj.getString("altura");
                    dpto = obj.getString("dpto");
                    os = obj.getString("obrasocial");
                    numos = obj.getString("numafiliado");
                    foto=obj.getString("foto");

                    nombreperfil.setText(nombre);
                    apellidoperfil.setText(apellido);
                    usuarioperfil.setText(usuario);
                    obrasocialperfil.setText(os);
                    localidadperfil.setText(localidad);
                    calleperfil.setText(calle);
                    alturaperfil.setText(altura);
                    numeroafiliadoperfil.setText(numos);
                    dptoperfil.setText(dpto);

                    if(foto.isEmpty()){
                        fotoperfil.setImageResource(R.drawable.blue_modern_icons_maternity_doctor_logo);
                    }else {
                        url = "http://192.168.0.87/medicamentos_android/drawable/"+usuario+".png";
                        Picasso.with(getActivity())
                                .load(url)
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



        return  v;
    }
}