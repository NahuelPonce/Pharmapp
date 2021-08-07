package com.example.pharmapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterMedicamento extends RecyclerView.Adapter<AdapterMedicamento.ViewHolder> implements View.OnClickListener  {

    LayoutInflater inflater;
    ArrayList<Medicamento> medicamentos;

    private View.OnClickListener listener;

    public AdapterMedicamento(Context context, ArrayList<Medicamento> medicamentos) {
        this.inflater = LayoutInflater.from(context);
        this.medicamentos = medicamentos;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = inflater.inflate((R.layout.elemento_lista), parent, false);
        v.setOnClickListener(this);

        return new ViewHolder(v);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String nombre = medicamentos.get(position).getNombre();
        String descripcion = medicamentos.get(position).getDescripcion();
        String comprimido = medicamentos.get(position).getComprimido().toString();
        String precio = medicamentos.get(position).getPrecio().toString();
        int imagen = medicamentos.get(position).getImagen();

        holder.nombre.setText(nombre);
        holder.descripcion.setText(descripcion);
        holder.comprimido.setText(comprimido);
        holder.precio.setText(precio);
        holder.imagen.setImageResource(imagen);


    }

    @Override
    public int getItemCount() {
        return medicamentos.size();
    }

    @Override
    public void onClick(View v) {
        if (listener!=null) {
            listener.onClick(v);
        }

    }

    public void filterList (ArrayList<Medicamento> filteredList) {
        medicamentos = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, descripcion, comprimido, precio;
        ImageView imagen;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.lvTitulo);
            comprimido = itemView.findViewById(R.id.textComprimido);
            descripcion = itemView.findViewById(R.id.textDescripcion);
            precio = itemView.findViewById(R.id.lvPrecio);
            imagen = itemView.findViewById(R.id.ivImagen);
        }
    }
}
