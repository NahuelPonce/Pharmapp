package com.example.pharmapp.ui.gallery;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.pharmapp.R;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    Context context;
    List<Uri> listaImagenes;
    LayoutInflater layoutInflater;

    public GridViewAdapter(Context context, List<Uri> listaImagenes) {
        this.context = context;
        this.listaImagenes = listaImagenes;
    }

    @Override
    public int getCount() {
        return listaImagenes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaImagenes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_gv_imagenes,null);
        }

        ImageView ivImagen = convertView.findViewById(R.id.ivImagen);
        ImageButton ibtnEliminar = convertView.findViewById(R.id.ibtnEliminar);

        ivImagen.setImageURI(listaImagenes.get(position));

        ibtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaImagenes.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}
