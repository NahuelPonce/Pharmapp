package com.example.pharmapp.ui.home;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pharmapp.R;

public class DetalleMedicamento extends Fragment {


    public DetalleMedicamento() {
        // Required empty public constructor
    }

    ImageView medicamentoImagen;
    TextView medicamentoNombre;
    TextView medicamentoCantidad;
    TextView medicamentoPrecioTotal;
    TextView medicamentoPrecio;
    TextView medicamentoId;
    ImageButton mas;
    ImageButton menos;
    ImageButton agregar;

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

        Bundle objetoMedicamento = getArguments();
        Medicamento medicamento = null;

        if(objetoMedicamento!=null){
            medicamento = (Medicamento) objetoMedicamento.getSerializable("objeto");

            medicamentoImagen.setImageResource(medicamento.getImagen());
            medicamentoNombre.setText(medicamento.getNombre());
            medicamentoPrecio.setText(medicamento.getPrecio());
        }

        agregar = v.findViewById(R.id.agregar_medicamento);
        mas = v.findViewById(R.id.mas);
        menos = v.findViewById(R.id.menos);

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _cantidad= medicamentoCantidad.getText().toString();
                _cantidad=_cantidad.replace(",","").replace("#","").replace(".","");
                int Incremento =Integer.parseInt(_cantidad)+1;
                medicamentoCantidad.setText(String.valueOf(Incremento));
            }
        });

        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _cantidad= medicamentoCantidad.getText().toString();
                _cantidad=_cantidad.replace(",","").replace("#","").replace(".","");
                int Incremento =Integer.parseInt(_cantidad)-1;
                if(Incremento < 0)
                    Incremento=0;
                medicamentoCantidad.setText(String.valueOf(Incremento));
            }
        });


        return v;
    }

    /*void CalculaTotal (int cantidad) {

        double resultado = 0;
        resultado = cantidad*medicamentoPrecio.getPrecio();
        medicamentoPrecioTotal
    }*/
}