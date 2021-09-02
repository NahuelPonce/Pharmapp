package com.example.pharmapp.ui.gallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.navigation.Navigation;

import com.example.pharmapp.R;
import com.example.pharmapp.db.DbHelper;
import com.example.pharmapp.ui.gallery.Receta;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static androidx.navigation.Navigation.findNavController;

public class GridViewAdapter extends BaseAdapter {

    Context context;
    List<Receta> listaImagenes;
    LayoutInflater layoutInflater;
    DbHelper dbHelper;

    public GridViewAdapter(Context context, List<Receta> listaImagenes) {
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

        byte [] blob = listaImagenes.get(position).getImagen();
        ByteArrayInputStream bais = new ByteArrayInputStream(blob);
        Bitmap bitmap = BitmapFactory.decodeStream(bais);

        ivImagen.setImageBitmap(bitmap);

        ivImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("recetaid", listaImagenes.get(position).getRecetaID());

                findNavController(v).navigate(R.id.action_nav_gallery_to_nav_visor_receta, bundle);
            }
        });




        ibtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new DbHelper(view.getContext());
                final SQLiteDatabase db = dbHelper.getWritableDatabase();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("¿Desea eliminar la receta del carrito?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db.delete("t_receta", "recetaId=?", new String[] {String.valueOf(listaImagenes.get(position).getRecetaID())});
                                Navigation.findNavController(view).navigate(R.id.action_nav_gallery_self);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }




        });

        return convertView;
    }

}
