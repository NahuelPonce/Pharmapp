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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmapp.R;
import com.example.pharmapp.db.DbHelper;
import com.example.pharmapp.ui.home.Medicamento;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.widget.LinearLayout;
import android.widget.TextView;


public class GalleryFragment<Total> extends Fragment {

    Context context;
    ArrayList<Medicamento> medicamentoArrayList;
    RecyclerView recyclerViewMedicamentos;
    AdapterMedicamento2 adapter;
    DbHelper dbHelper;
    Button este, continuar;
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
        if (cursorMedicamento.moveToFirst()){
            do {
                medicamento = new Medicamento();
                medicamento.setMedicamentoID(cursorMedicamento.getInt(1));
                medicamento.setNombre(cursorMedicamento.getString(2));
                medicamento.setCantidad(cursorMedicamento.getInt(5));
                medicamento.setPrecio(cursorMedicamento.getDouble(3));
                medicamento.setTotal(cursorMedicamento.getDouble(6));

                total = cursorMedicamento.getDouble(6) + total;


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

            tot.setVisibility(getView().VISIBLE);
            tot2.setVisibility(getView().VISIBLE);
            pesos.setVisibility(getView().VISIBLE);
            vacio.setVisibility(getView().GONE);
            gvImagenes.setVisibility(getView().VISIBLE);
            tot.setText(String.valueOf(total));


        }

       /* if (recetas.isEmpty()){
            gvImagenes.setVisibility(getView().GONE);


        } else{
            gvImagenes.setVisibility(getView().VISIBLE);
        }
*/

        return v;
    }

    /*public void actualizar(List<Receta> recetas, View view) {
        if (recetas.isEmpty()){
            gvImagenes.setVisibility(getView().GONE);
            Navigation.findNavController(view).navigate(R.id.action_nav_gallery_self);

        } else{
            gvImagenes.setVisibility(getView().VISIBLE);
        }


    }
    */

}