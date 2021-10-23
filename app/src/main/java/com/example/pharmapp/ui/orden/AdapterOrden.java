package com.example.pharmapp.ui.orden;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.pharmapp.R;
import com.example.pharmapp.db.DbHelper;
import com.example.pharmapp.ui.home.Medicamento;
import com.example.pharmapp.ui.home.MedicamentoBD;
import com.example.pharmapp.ui.orden.OrdenBD;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

//extends RecyclerView.Adapter<AdapterMedicamento.ViewHolder> implements View.OnClickListener
public class AdapterOrden extends RecyclerView.Adapter<AdapterOrden.ViewHolder> implements  View.OnClickListener {
    LayoutInflater inflater;
    ArrayList<OrdenBD> ordenBD;
    Context context;
    private View.OnClickListener listener;


    public AdapterOrden(Context context, ArrayList<OrdenBD> ordenBD) {
        this.inflater = LayoutInflater.from(context);
        this.ordenBD = ordenBD;
        this.context = context;

    }


    @NonNull
    @NotNull
    @Override
    public AdapterOrden.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = inflater.inflate((R.layout.elemento_lista_orden), parent, false);
        v.setOnClickListener(this);

        return new ViewHolder(v);
    }



    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String  idorden = String.valueOf(ordenBD.get(position).getIdorden());
        String  idpedido = String.valueOf(ordenBD.get(position).getIdpedido());
        //String usuario = ordenBD.get(position).getUsuario();
        String estado = ordenBD.get(position).getEstado();
        String fecha = ordenBD.get(position).getFecha();
        String medicamentosid = ordenBD.get(position).getMedicamentosid();
        //String cantidades = ordenBD.get(position).getCantidades();

        Double preciototal = ordenBD.get(position).getPreciototal();
        String mostrar = new String();
        String[] output = medicamentosid.split(",");
        for (int i = 0; i < output.length; i++) {

          mostrar=output[i].replace(" ","")+","+mostrar;

        }
        mostrar=mostrar.substring(0, mostrar.length()-1);


        holder.idpedido.setText(idpedido);
        holder.estado.setText(estado);

        DecimalFormat df = new DecimalFormat("#.00");
        holder.precio.setText(String.valueOf(df.format(preciototal)));
        holder.medicamentos.setText(mostrar);
        holder.fecha.setText(fecha);
        holder.idorden.setText(idorden);


    }


    @Override
    public int getItemCount() {
        return ordenBD.size();
    }

    @Override
    public void onClick(View v) {
        if (listener!=null) {
            listener.onClick(v);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView idpedido,medicamentos, precio, estado,fecha,idorden;
        ImageButton btnEliminar;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            idpedido = itemView.findViewById(R.id.lvpedidoid);
            precio = itemView.findViewById(R.id.preciolv);
            estado = itemView.findViewById(R.id.estadoid);
            medicamentos =itemView.findViewById(R.id.medicamentoslist);
            fecha=itemView.findViewById(R.id.textView8);
            idorden=itemView.findViewById(R.id.idorden);

            btnEliminar = itemView.findViewById(R.id.btnEliminar2);
            btnEliminar.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) { ;
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage("¿Desea eliminar el estado de la orden?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Borrar de la bd
                            String orden= String.valueOf(ordenBD.get(getAdapterPosition()).getIdorden());
                            String URLpedido = "http://192.168.0.87/medicamentos_android/borrarorden.php?idorden="+orden;

                            StringRequest stringRequest2 = new StringRequest(Request.Method.GET, URLpedido, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                   //nada

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context.getApplicationContext(), error.toString(),Toast.LENGTH_SHORT).show();
                                }
                            });


                            RequestQueue requestQueue2= Volley.newRequestQueue(context.getApplicationContext());
                            requestQueue2.add(stringRequest2);

                            ///
                            Navigation.findNavController(v).navigate(R.id.action_nav_orden_self);
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }

}
