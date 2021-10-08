package com.example.pharmapp.ui.gallery;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.navigation.Navigation;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
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
            //db.delete("t_receta",null,null);
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

                Cursor cursorReceta;
                cursorReceta = db.rawQuery("SELECT * FROM t_receta", null);

                hacercompra(cursorMedicamento, user,db,cursorReceta);

                Toast.makeText(getActivity(),"Compra realizada con exito, espere la confirmacion",Toast.LENGTH_SHORT).show();


            }

        });
        return v;
    }


    public void hacercompra(Cursor cursorMedicamento, String user, SQLiteDatabase db, Cursor cursorReceta){


        //traer usuario
        String URLUSUARIO="http://192.168.0.87/medicamentos_android/buscarusuario.php?usuario="+user;

        StringRequest stringRequ = new StringRequest(Request.Method.GET, URLUSUARIO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String usuario, contraseña, nombre, apellido,nombrecompleto,foto, url, os,localidad, calle,altura, direccion;

                //Toast.makeText(Main2Activity.this,response,Toast.LENGTH_SHORT).show();
                try {

                    JSONObject obj = new JSONObject(response);
                    usuario = obj.getString("usuario");
                    nombre = obj.getString("nombre");
                    apellido = obj.getString("apellido");
                    localidad = obj.getString("localidad");
                    calle = obj.getString("calle");
                    altura = obj.getString("altura");
                    direccion=calle+" "+altura+" "+localidad;
                    os = obj.getString("obrasocial");
                    nombrecompleto = nombre+" "+apellido;
                    //shop(nombrecompleto,cursorMedicamento,usuario,direccion);
                    shop2(nombrecompleto,os,cursorMedicamento,usuario,direccion,db,cursorReceta);

                } catch (JSONException e) {
                    Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (InterruptedException e) {
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


/*
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



    }*/

    public void shop2(String nombrecompleto,String os, Cursor cursorMedicamento, String usuario, String direccion, SQLiteDatabase db, Cursor  cursorReceta) throws InterruptedException {

        String identificadormedicamento = new String();
        String cantidades = new String();


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time


        if (cursorMedicamento.moveToFirst()) {
            do {
                String idmedicamento = String.valueOf(cursorMedicamento.getInt(1));
                String cantidad = String.valueOf(cursorMedicamento.getInt(5));

                identificadormedicamento = idmedicamento + ";" + identificadormedicamento;
                cantidades = cantidad + ";" + cantidades;

            } while (cursorMedicamento.moveToNext());
        }
        String URL = "http://192.168.0.87/medicamentos_android/insertarpedido.php";
        String finalIdentificadormedicamento = identificadormedicamento;

        String finalCantidades = cantidades;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //nada
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();

                parametros.put("fecha", formattedDate);
                parametros.put("nombrecomprador", nombrecompleto);
                parametros.put("usuariocomprador", usuario);
                parametros.put("obrasocial", os);
                parametros.put("direccion", direccion);
                parametros.put("medicamentosid", finalIdentificadormedicamento);
                parametros.put("cantidades", finalCantidades);

                return parametros;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        Thread.sleep(1000L);
        traeridpedido(formattedDate,usuario,db,cursorReceta);


    }

    private void traeridpedido(String formattedDate, String usuario, SQLiteDatabase db, Cursor cursorReceta) {

        /// persito recetas
        String URLpedido = "http://192.168.0.87/medicamentos_android/traerpedido.php?usuario="+usuario+"&fecha="+formattedDate;

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, URLpedido, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String idped, fech;

                try {

                    JSONObject obj = new JSONObject(response);
                    idped = obj.getString("idpedido");
                    fech = obj.getString("fecha");
                    Toast.makeText(getActivity(),idped+"ACAAAA",Toast.LENGTH_SHORT).show();
                    receta(idped,fech,db,cursorReceta);

                } catch (JSONException e) {
                    Toast.makeText(getActivity(),e.toString()+"ACA",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue2= Volley.newRequestQueue(getContext());
        requestQueue2.add(stringRequest2);



        //elimino lo que queda en pantalla
        db.delete("t_carrito",null,null);
        Navigation.findNavController(getView()).navigate(R.id.action_nav_gallery_self);

    }

    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodeImage = Base64.getEncoder().encodeToString(imageBytes);
        return encodeImage;
    }

    public void receta(String idped, String fech, SQLiteDatabase db, Cursor cursorReceta){

        String URLreceta = "http://192.168.0.87/medicamentos_android/insertarreceta.php";
        Toast.makeText(getActivity(),idped,Toast.LENGTH_SHORT).show();


        Integer i = 0;
        if (cursorReceta.moveToFirst() && cursorReceta != null){
            do {
                byte[] receta = cursorReceta.getBlob(1);
                ByteArrayInputStream bais = new ByteArrayInputStream(receta);
                Bitmap bitmap = BitmapFactory.decodeStream(bais);

                //recetaImagen.setImageBitmap(bitmap);
                //ByteArrayOutputStream boas = new ByteArrayOutputStream();
                //byte[] byteArray = cursorReceta.getBlob(1);
                //Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
                //byte[] byteArray = cursorReceta.getBlob(1);
                //Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
                String foto= getStringImagen(bitmap);

                i=i+1;
                Integer finalI = i;
                String num= String.valueOf(finalI);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLreceta, new Response.Listener<String>() {
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

                        parametros.put("idpedido",idped);
                        parametros.put("foto",foto);
                        parametros.put("fecha",fech);
                        parametros.put("num",num);

                        return parametros;

                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
            } while (cursorReceta.moveToNext());

        }



    }





}