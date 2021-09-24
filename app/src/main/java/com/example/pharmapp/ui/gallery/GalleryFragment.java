package com.example.pharmapp.ui.gallery;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pharmapp.R;
import com.example.pharmapp.db.DbHelper;
import com.example.pharmapp.ui.home.Medicamento;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class GalleryFragment<Total> extends Fragment {


    Context context;
    ArrayList<Medicamento> medicamentoArrayList;
    RecyclerView recyclerViewMedicamentos;
    AdapterMedicamento2 adapter;
    DbHelper dbHelper;
    Button este, continuar,comprar;
    ImageView foto;
    GridView gvImagenes;
    GridViewAdapter baseAdapter;
    TextView tot,tot2,pesos, vacio;


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetMultipleContents(),
            new ActivityResultCallback<List<Uri>> () {
                @Override
                public void onActivityResult(List<Uri> result) {
                    //foto.setImageURI((Uri) result);
                    dbHelper =new DbHelper(getContext());
                    final SQLiteDatabase db = dbHelper.getWritableDatabase();


                    for (Uri cadena : result) {
                        ContentValues values = new ContentValues();
                        try {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), cadena );
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG,100, bos);
                            byte[] bArray = bos.toByteArray();
                            values.put("imagen", bArray);
                            db.insert("t_receta", null, values );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Cursor cursorReceta;
                    cursorReceta = db.rawQuery("SELECT * FROM t_receta", null);
                    List<Receta> recetas = new ArrayList<>();
                    Receta receta = null;

                    if (cursorReceta.moveToFirst()){
                        do {
                            receta = new Receta();
                            receta.setRecetaID(cursorReceta.getInt(0));
                            receta.setImagen(cursorReceta.getBlob(1));

                            recetas.add(receta);
                        } while (cursorReceta.moveToNext());
                    }

                    baseAdapter = new GridViewAdapter(getContext(),recetas);
                    gvImagenes.setAdapter(baseAdapter);


                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gallery, container,false);



        //SharedPreferences preferences = context.getSharedPreferences("usuario",Context.MODE_PRIVATE);
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String user=preferences.getString("user","No exite la informacion");

        recyclerViewMedicamentos = v.findViewById(R.id.lvLista2);
        este = v.findViewById(R.id.button4);
        tot = v.findViewById(R.id.textView10);
        pesos = v.findViewById(R.id.textView12);
        tot2= v.findViewById(R.id.textView11);
        vacio = v.findViewById(R.id.textView3);
        continuar = v.findViewById(R.id.button2);

        gvImagenes = v.findViewById(R.id.gvImagenes);

        dbHelper = new DbHelper(v.getContext());
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Medicamento> medicamentoArrayList = new ArrayList<>();
        Medicamento medicamento = null;
        Cursor cursorMedicamento;
        cursorMedicamento = db.rawQuery("SELECT * FROM  t_carrito",null);
        double total = 0;
        int tieneokreceta=0;
        if (cursorMedicamento.moveToFirst()){
            do {
                medicamento = new Medicamento();
                medicamento.setMedicamentoID(cursorMedicamento.getInt(1));
                medicamento.setNombre(cursorMedicamento.getString(2));
                medicamento.setCantidad(cursorMedicamento.getInt(5));
                medicamento.setPrecio(cursorMedicamento.getDouble(3));
                medicamento.setTotal(cursorMedicamento.getDouble(6));
                medicamento.setComprimido(cursorMedicamento.getInt(7));
                medicamento.setReceta(cursorMedicamento.getInt(8));

                total = cursorMedicamento.getDouble(6) + total;
                tieneokreceta = cursorMedicamento.getInt(8) + tieneokreceta;
                medicamentoArrayList.add(medicamento);
            } while (cursorMedicamento.moveToNext());
        }

        cursorMedicamento.close();
        recyclerViewMedicamentos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterMedicamento2(getContext(),medicamentoArrayList);
        recyclerViewMedicamentos.setAdapter(adapter);



        Cursor cursorReceta;
        cursorReceta = db.rawQuery("SELECT * FROM t_receta", null);
        List<Receta> recetas = new ArrayList<>();
        Receta receta = null;

        if (cursorReceta.moveToFirst()){
            do {
                receta = new Receta();
                receta.setRecetaID(cursorReceta.getInt(0));
                receta.setImagen(cursorReceta.getBlob(1));

                recetas.add(receta);
            } while (cursorReceta.moveToNext());
        }

        baseAdapter = new GridViewAdapter(getContext(),recetas);
        gvImagenes.setAdapter(baseAdapter);


        este.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");

            }

        });

        if (total == 0.0 && medicamentoArrayList.isEmpty()) {
            tot.setVisibility(getView().INVISIBLE);
            tot2.setVisibility(getView().INVISIBLE);
            pesos.setVisibility(getView().INVISIBLE);
            este.setVisibility(getView().INVISIBLE);
            continuar.setVisibility(getView().INVISIBLE);
            vacio.setVisibility(getView().VISIBLE);
            gvImagenes.setVisibility(getView().GONE);

            //dbHelper =new DbHelper(getContext());
            db.delete("t_receta",null,null);
        } else {

            if ( tieneokreceta == 0) {

                este.setEnabled(false);

            }else {
                este.setEnabled(true);
            }
            tot.setVisibility(getView().VISIBLE);
            tot2.setVisibility(getView().VISIBLE);
            pesos.setVisibility(getView().VISIBLE);
            vacio.setVisibility(getView().GONE);
            gvImagenes.setVisibility(getView().VISIBLE);
            tot.setText(String.valueOf(total));
        }




        //REALIZAR COMPRA
        comprar= v.findViewById(R.id.button2);


        comprar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dbHelper = new DbHelper(v.getContext());
                final SQLiteDatabase db = dbHelper.getWritableDatabase();

                Cursor cursorMedicamento;
                cursorMedicamento = db.rawQuery("SELECT * FROM  t_carrito",null);
                hacercompra(cursorMedicamento, user);
            }

        });
        return v;
    }


    public void hacercompra(Cursor cursorMedicamento, String user){


        //traer usuario
        String URLUSUARIO="http://192.168.0.87/medicamentos_android/buscarusuario.php?usuario="+user;

        StringRequest stringRequ = new StringRequest(Request.Method.GET, URLUSUARIO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String usuario, contraseña, nombre, apellido,nombrecompleto,foto, url, os,obr, calle,altura, direccion;

                //Toast.makeText(Main2Activity.this,response,Toast.LENGTH_SHORT).show();
                try {

                    JSONObject obj = new JSONObject(response);
                    usuario = obj.getString("usuario");
                    nombre = obj.getString("nombre");
                    apellido = obj.getString("apellido");
                    calle = obj.getString("calle");
                    altura = obj.getString("altura");
                    direccion=calle+" "+altura;
                    os = obj.getString("obrasocial");
                    nombrecompleto = nombre+" "+apellido+" "+os;
                    shop(nombrecompleto,cursorMedicamento,usuario,direccion);

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




    }

    public void shop(String nombrecompleto, Cursor cursorMedicamento,  String usuario, String direccion){

        String nombre = new String();
        Double preciototal,total;
        total = 0.0;
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        String URL = "http://192.168.0.87/medicamentos_android/insertarpedidocliente.php";


        if (cursorMedicamento.moveToFirst()){
            do {

                String idmedicamento = String.valueOf(cursorMedicamento.getInt(1));
                String nombremedicamento = String.valueOf(cursorMedicamento.getString(2));
                String comprimido = String.valueOf(cursorMedicamento.getInt(7));
                String cantidad = String.valueOf(cursorMedicamento.getInt(5));
                String precio = String.valueOf(cursorMedicamento.getDouble(3));

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //nada
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parametros=new HashMap<String, String>();
                        parametros.put("nombrecomprador",nombrecompleto);
                        parametros.put("usuariocomprador",usuario);
                        parametros.put("idmedicamento",idmedicamento);
                        parametros.put("nombremedicamento",nombremedicamento);
                        parametros.put("comprimido",comprimido);
                        parametros.put("cantidad",cantidad);
                        parametros.put("fecha",currentDateTimeString);
                        parametros.put("direccion",direccion);
                        parametros.put("precio",precio);

                        return parametros;

                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
                    } while (cursorMedicamento.moveToNext());
                }

        /*
        String URL = "http://192.168.0.87/medicamentos_android/insertarpedido.php";
        String finalNombre = nombre;
        String comprador = "violeta";
        String direccion = "prueba";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //nada
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros=new HashMap<String, String>();

                parametros.put("comprador",nombrecompleto);
                parametros.put("direccion",direccion);
                parametros.put("descripcion", finalNombre);


                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

         */



    }



}