package com.example.pharmapp.ui.slideshow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pharmapp.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static androidx.navigation.Navigation.findNavController;

public class SlideshowFragment extends Fragment {




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_slideshow, container,false);

        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String user=preferences.getString("user","No exite la informacion");

        //traer usuario
        String URLUSUARIO="http://192.168.0.87/medicamentos_android/buscarusuario.php?usuario="+user;

        StringRequest stringRequ = new StringRequest(Request.Method.GET, URLUSUARIO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String usuario, contraseña, nombre, apellido,nombrecompleto,foto, url, os,localidad, calle,altura, direccion,dpto, numos;
                TextView nombreperfil= v.findViewById(R.id.nomperfil);
                TextView apellidoperfil = v.findViewById(R.id.apeperfil);
                TextView usuarioperfil = v.findViewById(R.id.usuperfil);
                TextView obrasocialperfil = v.findViewById(R.id.obraperfil);
                TextView numeroafiliadoperfil = v.findViewById(R.id.numperfil);
                TextView localidadperfil = v.findViewById(R.id.locperfil);
                TextView calleperfil= v.findViewById(R.id.caperfil);
                TextView alturaperfil = v.findViewById(R.id.altperfil);
                TextView dptoperfil = v.findViewById(R.id.dpperfil);

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

        Button editar = v.findViewById(R.id.buttoneditar);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findNavController(v).navigate(R.id.action_nav_slideshow_to_editarPerfilUsuario);
            }
            });



        return v;
    }



}