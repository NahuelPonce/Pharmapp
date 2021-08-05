package com.example.pharmapp.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmapp.R;
import com.example.pharmapp.ui.gallery.AdapterMedicamento2;
import com.example.pharmapp.ui.home.AdapterMedicamento;
import com.example.pharmapp.ui.home.Medicamento;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
//extends RecyclerView.Adapter<AdapterMedicamento.ViewHolder> implements View.OnClickListener
public class AdapterMedicamento2 extends RecyclerView.Adapter<AdapterMedicamento2.ViewHolder> implements  View.OnClickListener {
    LayoutInflater inflater;
    ArrayList<Medicamento> medicamentos;

    private View.OnClickListener listener;

    public AdapterMedicamento2(Context context, ArrayList<Medicamento> medicamentos) {
        this.inflater = LayoutInflater.from(context);
        this.medicamentos = medicamentos;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterMedicamento2.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = inflater.inflate((R.layout.elemento_lista), parent, false);
        v.setOnClickListener(this);

        return new AdapterMedicamento2.ViewHolder(v);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterMedicamento2.ViewHolder holder, int position) {
        String nombre = medicamentos.get(position).getNombre();
        String precio = medicamentos.get(position).getPrecio().toString();


        holder.nombre.setText(nombre);
        holder.precio.setText(precio);


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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre,precio;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.lvTitulo);
            precio = itemView.findViewById(R.id.lvPrecio);

        }
    }

}
