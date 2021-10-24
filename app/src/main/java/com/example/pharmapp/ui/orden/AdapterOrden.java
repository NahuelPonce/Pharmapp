package com.example.pharmapp.ui.orden;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

//extends RecyclerView.Adapter<AdapterMedicamento.ViewHolder> implements View.OnClickListener
public class AdapterOrden extends RecyclerView.Adapter<AdapterOrden.ViewHolder> { //implements  View.OnClickListener {
    LayoutInflater inflater;
    ArrayList<OrdenBD> ordenBD;
    Context context;
    //private View.OnClickListener listener;


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
        //v.setOnClickListener(this);

        return new ViewHolder(v);
    }



    /*public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }*/

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String  idorden = String.valueOf(ordenBD.get(position).getIdorden());
        String  idpedido = String.valueOf(ordenBD.get(position).getIdpedido());
        //String usuario = ordenBD.get(position).getUsuario();
        String estado = ordenBD.get(position).getEstado();
        String fecha = ordenBD.get(position).getFecha();
        String medicamentosid = ordenBD.get(position).getMedicamentosid();
        String descripcion = ordenBD.get(position).getMotivo();
        //String cantidades = ordenBD.get(position).getCantidades();

        Double preciototal = ordenBD.get(position).getPreciototal();
        String mostrar = new String();
        String[] output = medicamentosid.split(",");
        for (int i = 0; i < output.length; i++) {

          mostrar=output[i].replace(" ","")+","+mostrar;

        }
        mostrar=mostrar.substring(0, mostrar.length()-1);


        holder.idpedido.setText(idpedido);

        if(estado.equals("Cancelado")){
            holder.estado.setTextColor(Color.parseColor("#FF0054"));
            holder.estado.setText(estado);
            holder.descripcion.setVisibility(View.VISIBLE);
            holder.descripcion.setText(descripcion);

        }

        else{
            if(estado.equals("Confirmado")){
                Log.i("tutorial","holahola");
                holder.estado.setTextColor(Color.parseColor("#008f39"));
                holder.estado.setText(estado);
                holder.descripcion.setVisibility(View.GONE);
            }else {
                holder.estado.setText(estado);
                holder.descripcion.setVisibility(View.GONE);
                holder.estado.setTextColor(Color.parseColor("#e5be01"));
            }

       }




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

    /*@Override
    public void onClick(View v) {
        if (listener!=null) {
            listener.onClick(v);
        }

    }*/

    public class ViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener {

        TextView idpedido,medicamentos, precio, estado,fecha,idorden,descripcion;
        //ImageButton btnEliminar;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            idpedido = itemView.findViewById(R.id.lvpedidoid);
            precio = itemView.findViewById(R.id.preciolv);
            estado = itemView.findViewById(R.id.estadoid);
            descripcion = itemView.findViewById(R.id.descripcionlv);

            medicamentos =itemView.findViewById(R.id.medicamentoslist);
            fecha=itemView.findViewById(R.id.textView8);
            idorden=itemView.findViewById(R.id.idorden);

            //btnEliminar = itemView.findViewById(R.id.btnEliminar2);
            //btnEliminar.setOnClickListener(this);

        }

        /*
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

         */




    }

}
