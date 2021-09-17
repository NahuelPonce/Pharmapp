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
//import androidx.appcompat.widget.AbsActionBarView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pharmapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterMedicamentoBD extends RecyclerView.Adapter<AdapterMedicamentoBD.ViewHolder> implements View.OnClickListener  {


    LayoutInflater inflater;
    ArrayList<MedicamentoBD> medicamentosBD;
    Context context;
    private View.OnClickListener listener;


    public AdapterMedicamentoBD(Context context, ArrayList<MedicamentoBD> medicamentosBD) {
        this.inflater = LayoutInflater.from(context);
        this.medicamentosBD = medicamentosBD;
        //this.medicamentosBD = medicamentosBD;
        this.context = context;
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
        String nombre = medicamentosBD.get(position).getNombre();
        String comprimido = medicamentosBD.get(position).getComprimido().toString();
        String precio = medicamentosBD.get(position).getPrecio().toString();
        //String imagen = medicamentosBD.get(position).getImagen();
        Integer receta = medicamentosBD.get(position).getReceta();


        Glide.with(context)
                .load(medicamentosBD.get(position).getImagen())
                .into(holder.imagen);


        holder.nombre.setText(nombre);
        holder.comprimido.setText(comprimido);
        holder.precio.setText(precio);


        if (receta == 0){
            holder.bajoreceta.setVisibility(View.INVISIBLE);
        }


        /*for(int i = 0; i < medicamentosBD.size(); i++) {
            if (medicamentosBD.get(i).getReceta() == 1){
                bajoreceta.setVisibility(getView().GONE);
            }

         */
        //holder.imagen.setImageResource(imagen);

    }

    @Override
    public int getItemCount() {
        return medicamentosBD.size();
    }

   @Override
    public void onClick(View v) {
        if (listener!=null) {
            listener.onClick(v);
        }

    }


    public void filterList (ArrayList<MedicamentoBD> filteredList) {
        medicamentosBD = filteredList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, comprimido, precio, bajoreceta;
        ImageView imagen;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.lvTitulo);
            comprimido = itemView.findViewById(R.id.textComprimido);
            precio = itemView.findViewById(R.id.lvPrecio);
            imagen = itemView.findViewById(R.id.ivImagen);
            bajoreceta = itemView.findViewById(R.id.textView5);
        }
    }
}