package com.example.pharmapp.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pharmapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

public class HomeFragment extends Fragment {

    ArrayList<Medicamento> medicamentos;
    ArrayList<MedicamentoBD> medicamentosBD;
    RecyclerView recyclerViewMedicamentos;
    AdapterMedicamentoBD adapterBD;
    EditText svSearch;
    TextView bajoreceta;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerViewMedicamentos = v.findViewById(R.id.lvLista);
        svSearch = v.findViewById(R.id.svSearch);
        bajoreceta = v.findViewById(R.id.textView5);


        svSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        medicamentos = new ArrayList<>();
        medicamentosBD = new ArrayList<>();


        llenar();


        //mostrar();

        return v;
    }
    public void filter(String text) {
        ArrayList<MedicamentoBD> filteredList = new ArrayList<>();

        for (MedicamentoBD item: medicamentosBD) {
            if (item.getNombre().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapterBD.filterList(filteredList);
        adapterBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("nombre", filteredList.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getNombre());
                bundle.putDouble("precio", filteredList.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getPrecio());
                bundle.putInt("comprimido",filteredList.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getComprimido());
                bundle.putString("imagen", filteredList.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getImagen());
                bundle.putInt("medicamentoid", filteredList.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getMedicamentoID());
                bundle.putInt("receta",filteredList.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getReceta());
                bundle.putInt("stock",filteredList.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getStock());

                findNavController(v).navigate(R.id.action_nav_home_to_nav_detalle, bundle);
            }
        });
    }


    public void llenar() {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getResources().getString(R.string.URL_medicamentos),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            //JSONObject jsonObject = new JSONObject(response);
                            //JSONArray jsonArray = jsonObject.getJSONArray("MedicamentosBD");

                            for (int i = 0; i < array.length(); i++) {
                                //JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject jsonObject1 = array.getJSONObject(i);
                                medicamentosBD.add(
                                        new MedicamentoBD(
                                                jsonObject1.getInt("medicamentoID"),
                                                jsonObject1.getString("nombre"),
                                                jsonObject1.getInt("comprimido"),
                                                jsonObject1.getInt("stock"),
                                                jsonObject1.getDouble("precio"),
                                                jsonObject1.getString("imagen"),
                                                jsonObject1.getInt("receta")
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
                }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
                );
        requestQueue.add(stringRequest);






    }
        /*Medicamento item = new Medicamento();
        item.setMedicamentoID(1);
        item.setNombre("Ibuprofeno");
        item.setComprimido(800);
        item.setPrecio(500.00);
        item.setImagen(R.drawable.ibuprofeno);

        medicamentos.add(item);

         */

    public void mostrar() {


        recyclerViewMedicamentos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterBD = new AdapterMedicamentoBD(getContext(), medicamentosBD);
        recyclerViewMedicamentos.setAdapter(adapterBD);



        adapterBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("nombre", medicamentosBD.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getNombre());
                bundle.putDouble("precio", medicamentosBD.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getPrecio());
                bundle.putInt("comprimido",medicamentosBD.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getComprimido());
                bundle.putString("imagen", medicamentosBD.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getImagen());
                bundle.putInt("medicamentoid", medicamentosBD.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getMedicamentoID());
                bundle.putInt("receta", medicamentosBD.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getReceta());
                bundle.putInt("stock",medicamentosBD.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getStock());
                findNavController(v).navigate(R.id.action_nav_home_to_nav_detalle, bundle);
            }
        });


    }

}