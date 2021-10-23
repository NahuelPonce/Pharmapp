package com.example.pharmapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pharmapp.db.DbHelper;
import com.example.pharmapp.ui.gallery.GalleryFragment;
import com.example.pharmapp.ui.home.AdapterMedicamento;
import com.example.pharmapp.ui.home.DetalleMedicamento;
import com.example.pharmapp.ui.home.HomeFragment;
import com.example.pharmapp.ui.home.Medicamento;
import com.example.pharmapp.ui.home.MedicamentoBD;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmapp.databinding.ActivityMain2Binding;
import com.google.gson.JsonObject;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this);

        DbHelper dbHelper = new DbHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain2.toolbar);
        binding.appBarMain2.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_orden,R.id.nav_historial, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



             String usuario = getIntent().getStringExtra("usuario");
             SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
             SharedPreferences.Editor editor= preferences.edit();
             editor.putString("user",usuario);
             editor.commit();

        cargarusuario("http://192.168.0.87/medicamentos_android/buscarusuario.php?usuario="+usuario);
            //nomreusu.setText(usu);

    }

    private void cargarusuario(String URL) {       final View vistaHeader = binding.navView.getHeaderView(0);
        final TextView obrasocial= vistaHeader.findViewById(R.id.textView);
        final TextView nomreusu = vistaHeader.findViewById(R.id.nombreusu);
        final ImageView perfil = vistaHeader.findViewById(R.id.imageView);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String usuario, contraseña, nombre, apellido,nombrecompleto,foto, url, os,obr;

                //Toast.makeText(Main2Activity.this,response,Toast.LENGTH_SHORT).show();
                try {

                    JSONObject obj = new JSONObject(response);
                    usuario = obj.getString("usuario");
                    nombre = obj.getString("nombre");
                    apellido = obj.getString("apellido");
                    os = obj.getString("obrasocial");
                    foto=obj.getString("foto");
                    nombrecompleto = nombre+"  "+apellido;
                    obr = "Obra Social:  "+os;
                    nomreusu.setText(nombrecompleto);
                    obrasocial.setText(obr);

                    if(foto.isEmpty()){
                        perfil.setImageResource(R.drawable.blue_modern_icons_maternity_doctor_logo);
                    }else {
                        url = "http://192.168.0.87/medicamentos_android/drawable/"+usuario+".png";
                        Picasso.with(getApplicationContext())
                                .load(url)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .into(perfil);
                    }
                } catch (JSONException e) {
                    Toast.makeText(Main2Activity.this,e.toString(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main2Activity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void cambiarfragment(){
        GalleryFragment mFragment = new GalleryFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main2, mFragment).commit();
    };


}