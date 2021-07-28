package com.example.pharmapp.ui.home;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pharmapp.R;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private ArrayList<Medicamento> medicamentos;
    private Activity activity;

    public Adapter(Activity activity, ArrayList<Medicamento> medicamentos){
        this.activity = activity;
        this.medicamentos = medicamentos;
    }

    @Override
    public int getCount() {
        return medicamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return medicamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null) v = activity.getLayoutInflater().inflate(R.layout.elemento_lista,parent,false);

        Medicamento item = medicamentos.get(position);

        TextView nombre = (TextView) v.findViewById(R.id.lvTitulo);
        TextView comprimido =(TextView) v.findViewById(R.id.textComprimido);
        TextView descripcion =(TextView) v.findViewById(R.id.textView3);
        ImageView imagen = (ImageView) v.findViewById(R.id.ivImagen);

        nombre.setText(item.getNombre());
        comprimido.setText(item.getComprimido().toString());
        descripcion.setText(item.getDescripcion());
        imagen.setImageResource(item.getImagen());

        return v;
    }
}
