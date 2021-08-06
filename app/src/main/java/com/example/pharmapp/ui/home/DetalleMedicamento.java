package com.example.pharmapp.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pharmapp.R;
import com.example.pharmapp.db.DbHelper;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

public class DetalleMedicamento extends Fragment {

    ImageView medicamentoImagen;
    TextView medicamentoNombre;
    TextView medicamentoCantidad;
    TextView medicamentoPrecioTotal;
    TextView medicamentoPrecio;
    TextView medicamentoId;
    ImageButton mas;
    ImageButton menos;
    ImageButton agregar;
    NumberFormat formatter;

    DbHelper dbHelper;

    public DetalleMedicamento() {
        // Required empty public constructor
        formatter = new DecimalFormat("$ ###,###,###.00");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detalle_medicamento, container, false);

        medicamentoImagen = v.findViewById(R.id.imagen_medicamento);
        medicamentoNombre = v.findViewById(R.id.nombre_medicamento);
        medicamentoCantidad = v.findViewById(R.id.cantidad_medicamento);
        medicamentoPrecioTotal = v.findViewById(R.id.preciototal_medicamento);
        medicamentoPrecio = v.findViewById(R.id.precio_medicamento);
        medicamentoId = v.findViewById(R.id.medicamento_id);

        Bundle bundle = getArguments();
        String nombre = bundle.getString("nombre");
        Double precio = bundle.getDouble("precio");
        int imagen = bundle.getInt("imagen");
        int idmedicamento = bundle.getInt("medicamentoid");

        medicamentoNombre.setText(nombre);
        medicamentoPrecio.setText(String.valueOf(precio));
        medicamentoImagen.setImageResource(imagen);
        medicamentoId.setText(String.valueOf(idmedicamento));

        /*Bundle objetoMedicamento = getArguments();
        Medicamento medio = null;
cament
        if(objetoMedicamento!=null){
            medicamento = (Medicamento) objetoMedicamento.getSerializable("objeto");

            medicamentoImagen.setImageResource(medicamento.getImagen());
            medicamentoNombre.setText(medicamento.getNombre());
            medicamentoPrecio.setText(medicamento.getPrecio());
        }*/

        agregar = v.findViewById(R.id.agregar_medicamento);
        mas = v.findViewById(R.id.mas);
        menos = v.findViewById(R.id.menos);
        dbHelper = new DbHelper(v.getContext());
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        medicamentoCantidad.setText("1");
        medicamentoPrecioTotal.setText(String.valueOf(precio));


        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _cantidad= medicamentoCantidad.getText().toString();
                _cantidad=_cantidad.replace(",","").replace("#","").replace(".","");
                int Incremento =Integer.parseInt(_cantidad)+1;
                medicamentoCantidad.setText(String.valueOf(Incremento));
                calculaTotal(Incremento);
            }
        });

        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _cantidad= medicamentoCantidad.getText().toString();
                _cantidad=_cantidad.replace(",","").replace("#","").replace(".","");
                int Incremento =Integer.parseInt(_cantidad)-1;
                if(Incremento < 1)
                    Incremento=1;
                medicamentoCantidad.setText(String.valueOf(Incremento));
                calculaTotal(Incremento);
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT id, cantidad, total FROM t_carrito where medicamentoId=?", new String[]{(String) medicamentoId.getText().toString()});
                ContentValues values = new ContentValues();

                if(!c.moveToFirst()) {
                    values.put("medicamentoId", Integer.parseInt(medicamentoId.getText().toString()));
                    values.put("nombre", medicamentoNombre.getText().toString());
                    values.put("precio", Float.parseFloat(medicamentoPrecio.getText().toString().replace("$","")));
                    //values.put("imagen", Integer.parseInt(String.valueOf(medicamentoImagen)));
                    values.put("cantidad", Integer.parseInt(medicamentoCantidad.getText().toString()));
                    values.put("total", Float.parseFloat(medicamentoPrecioTotal.getText().toString().replace("$", "")));
                    db.insert("t_carrito", null, values);
                }
                if (c.moveToFirst()) {

                    int nuevaCantidad= c.getInt(1) + Integer.parseInt(medicamentoCantidad.getText().toString());
                    float nuevototal= c.getFloat(2) + Float.parseFloat(medicamentoPrecioTotal.getText().toString().replace("$",""));
                    values.put("cantidad", nuevaCantidad);
                    values.put("total", nuevototal);
                    db.update("t_carrito",values,"medicamentoId=?",new String[]{medicamentoId.getText().toString()});

                }

                findNavController(v).navigate(R.id.action_nav_detalle_to_nav_home2);

            }
        });



        return v;
    }

    void calculaTotal (int cantidad) {

        Bundle bundle = getArguments();
        Double precio = bundle.getDouble("precio");


        double resultado = 0;
        double medicamentop= precio;
        resultado=cantidad*medicamentop;
        //medicamentoPrecioTotal.setText(formatter.format(resultado));
        medicamentoPrecioTotal.setText(String.valueOf(resultado));
    }
}