package com.example.pharmapp.ui.orden;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pharmapp.R;
import com.example.pharmapp.ui.home.AdapterMedicamento;
import com.example.pharmapp.ui.home.AdapterMedicamentoBD;
import com.example.pharmapp.ui.home.MedicamentoBD;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

public class OrdenFragment extends Fragment {
    RecyclerView recyclerViewOrden;
    AdapterOrden adapterBD;
    ArrayList<OrdenBD> ordenBD;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_orden, container,false);
        recyclerViewOrden = v.findViewById(R.id.lvLista3);
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String user = preferences.getString("user","No exite la informacion");

        ordenBD = new ArrayList<>();
        llenar(user);

        return v;
    }

    private void llenar(String user) {
        String URL_orden = "http://192.168.0.87/medicamentos_android/orden.php?usuario="+user;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_orden,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            //JSONObject jsonObject = new JSONObject(response);
                            //JSONArray jsonArray = jsonObject.getJSONArray("MedicamentosBD");
                            String medicamentos;
                            for (int i = 0; i < array.length(); i++) {
                                //JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject jsonObject1 = array.getJSONObject(i);

                                ordenBD.add(
                                        new OrdenBD(
                                                jsonObject1.getInt("idorden"),
                                                jsonObject1.getInt("idpedido"),
                                                jsonObject1.getString("usuario"),
                                                jsonObject1.getString("estado"),
                                                jsonObject1.getString("fecha"),
                                                jsonObject1.getString("medicamentosid"),
                                                jsonObject1.getString("cantidades"),
                                                jsonObject1.getDouble("preciototal")

                                        )

                                );
                               /* for(int i = 0; i < medicamentosBD.size(); i++) {
                                    if (medicamentosBD.get(i).getReceta() == 1){
                                        bajoreceta.setVisibility(getView().GONE);
                                    }

                                */



                            }

                            mostrar();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );
        requestQueue.add(stringRequest);






    }



    private void mostrar() {

        recyclerViewOrden.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterBD = new AdapterOrden(getContext(), ordenBD);
        recyclerViewOrden.setAdapter(adapterBD);
    }

}