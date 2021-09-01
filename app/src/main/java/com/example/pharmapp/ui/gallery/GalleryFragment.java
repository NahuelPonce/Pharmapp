package com.example.pharmapp.ui.gallery;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmapp.R;
import com.example.pharmapp.db.DbHelper;
import com.example.pharmapp.ui.home.Medicamento;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.widget.LinearLayout;


public class GalleryFragment extends Fragment {

    Context context;
    ArrayList<Medicamento> medicamentoArrayList;
    RecyclerView recyclerViewMedicamentos;
    AdapterMedicamento2 adapter;
    DbHelper dbHelper;
    Button este;
    ImageView foto;
    GridView gvImagenes;
    GridViewAdapter baseAdapter;


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetMultipleContents(),
            new ActivityResultCallback<List<Uri>> () {
                @Override
                public void onActivityResult(List<Uri> result) {
                    //foto.setImageURI((Uri) result);
                    dbHelper =new DbHelper(getContext());
                    final SQLiteDatabase db = dbHelper.getWritableDatabase();


                    for (Uri cadena : result) {
                        ContentValues values = new ContentValues();
                        //System.out.println(cadena);
                        String x = cadena.getPath();
                        //System.out.println(x);
                        values.put("uri", x);
                        db.insert("t_receta", null, values );
                        //Uri prueba = Uri.parse(x);
                        //System.out.println("content://com.google.android.apps.photos.contentprovider"+ prueba);
                        /*try {
                            FileInputStream fs = new FileInputStream(x);
                            byte[] imgbyte = new byte[fs.available()];
                            fs.read(imgbyte);



                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/


                    }
                    Cursor cursorReceta;
                    cursorReceta = db.rawQuery("SELECT * FROM t_receta", null);
                    List<Uri> recetas = new ArrayList<>();

                    if (cursorReceta.moveToFirst()){
                        do {
                            String imag = cursorReceta.getString(0);
                            Uri imagenuri = Uri.parse("content://com.google.android.apps.photos.contentprovider"+imag);
                            recetas.add(imagenuri);
                        } while (cursorReceta.moveToNext());
                    }

                    baseAdapter = new GridViewAdapter(getContext(),recetas);
                    gvImagenes.setAdapter(baseAdapter);

                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gallery, container,false);

        recyclerViewMedicamentos = v.findViewById(R.id.lvLista2);
        este = v.findViewById(R.id.button4);

        gvImagenes = v.findViewById(R.id.gvImagenes);

        dbHelper = new DbHelper(v.getContext());
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Medicamento> medicamentoArrayList = new ArrayList<>();
        Medicamento medicamento = null;
        Cursor cursorMedicamento;
        cursorMedicamento = db.rawQuery("SELECT * FROM  t_carrito",null);

        if (cursorMedicamento.moveToFirst()){
            do {
                medicamento = new Medicamento();
                medicamento.setMedicamentoID(cursorMedicamento.getInt(1));
                medicamento.setNombre(cursorMedicamento.getString(2));
                medicamento.setCantidad(cursorMedicamento.getInt(5));
                medicamento.setPrecio(cursorMedicamento.getDouble(3));
                medicamento.setTotal(cursorMedicamento.getDouble(6));


                medicamentoArrayList.add(medicamento);
            } while (cursorMedicamento.moveToNext());
        }

        cursorMedicamento.close();
        recyclerViewMedicamentos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterMedicamento2(getContext(),medicamentoArrayList);
        recyclerViewMedicamentos.setAdapter(adapter);


        este.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }

        });





        return v;
    }

}