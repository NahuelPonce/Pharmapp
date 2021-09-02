package com.example.pharmapp.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.pharmapp.R;
import com.example.pharmapp.db.DbHelper;

import java.io.ByteArrayInputStream;

public class VisorRecetaFragment extends Fragment {

    ImageView recetaImagen;
    ImageButton recetaDelete;
    DbHelper dbHelper;

    public VisorRecetaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_visor_receta, container, false);

        dbHelper = new DbHelper(view.getContext());
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        recetaImagen = view.findViewById(R.id.visorReceta);
        recetaDelete = view.findViewById(R.id.btndeleteimage);

        Bundle bundle = getArguments();
        int imagenid = bundle.getInt("recetaid");

        Cursor c = db.rawQuery("SELECT imagen FROM t_receta where recetaId=?", new String[]{String.valueOf(imagenid)});
        if(c.moveToFirst()) {
            byte[] receta = c.getBlob(0);
            ByteArrayInputStream bais = new ByteArrayInputStream(receta);
            Bitmap bitmap = BitmapFactory.decodeStream(bais);

            recetaImagen.setImageBitmap(bitmap);
        }
        recetaDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new DbHelper(view.getContext());
                final SQLiteDatabase db = dbHelper.getWritableDatabase();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("¿Desea eliminar la receta del carrito?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                db.delete("t_receta", "recetaId=?", new String[] {String.valueOf(imagenid)});
                                Navigation.findNavController(view).navigate(R.id.action_nav_visor_receta_to_nav_gallery);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }

        });


        return view;
    }
}