package com.example.pharmapp.ui.gallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmapp.R;
import com.example.pharmapp.databinding.FragmentGalleryBinding;
import com.example.pharmapp.db.DbHelper;
import com.example.pharmapp.ui.home.AdapterMedicamento;
import com.example.pharmapp.ui.home.Medicamento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import android.database.Cursor;


public class GalleryFragment extends Fragment {
    Context context;
    ArrayList<Medicamento> medicamentoArrayList;
    RecyclerView recyclerViewMedicamentos;
    AdapterMedicamento2 adapter;
    DbHelper dbHelper;
    //ImageButton eliminar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gallery, container,false);

        recyclerViewMedicamentos = v.findViewById(R.id.lvLista2);
        //eliminar = v.findViewById(R.id.imageButton);


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

        /*eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("¿Desea eliminar el medicamento?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String nombre = medicamentoArrayList.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getNombre();
                                db.delete("t_carrito","nombre = ?",new String[]{nombre});
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

            }
        });
        */


        return v;
    }

}