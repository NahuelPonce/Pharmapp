package com.example.pharmapp.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmapp.R;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

public class HomeFragment extends Fragment {

    ArrayList<Medicamento> medicamentos;
    RecyclerView recyclerViewMedicamentos;
    AdapterMedicamento adapter;
    EditText svSearch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerViewMedicamentos = v.findViewById(R.id.lvLista);
        svSearch = v.findViewById(R.id.svSearch);
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

        llenar();

        mostrar();

        return v;
    }

    public void filter(String text) {
        ArrayList<Medicamento> filteredList = new ArrayList<>();

        for (Medicamento item: medicamentos) {
            if (item.getNombre().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }


    public void llenar() {

        Medicamento item = new Medicamento();
        item.setMedicamentoID(1);
        item.setNombre("ibuprofeno");
        item.setComprimido(800);
        item.setDescripcion("blalbalbal");
        item.setPrecio(500.00);
        item.setImagen(R.drawable.ibuprofeno);

        medicamentos.add(item);
        //
        item = new Medicamento();
        item.setMedicamentoID(2);
        item.setNombre("Tafirol");
        item.setComprimido(400);
        item.setDescripcion("blalbalbal2");
        item.setPrecio(500.00);
        item.setImagen(R.drawable.fb8_deva);

        medicamentos.add(item);
        //
        item = new Medicamento();
        item.setMedicamentoID(3);
        item.setNombre("ibupirac");
        item.setComprimido(600);
        item.setDescripcion("blalbalbal3");
        item.setPrecio(500.00);
        item.setImagen(R.drawable.ibupirac_600_60b660d67d7c0);

        medicamentos.add(item);

        //
        item = new Medicamento();
        item.setMedicamentoID(4);
        item.setNombre("Adermicina");
        item.setComprimido(600);
        item.setDescripcion("blalbalbal4");
        item.setPrecio(500.00);
        item.setImagen(R.drawable.ibupirac_600_60b660d67d7c0);

        medicamentos.add(item);
    }

    public void mostrar() {
        recyclerViewMedicamentos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterMedicamento(getContext(), medicamentos);
        recyclerViewMedicamentos.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("nombre", medicamentos.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getNombre());
                bundle.putDouble("precio", medicamentos.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getPrecio());
                bundle.putInt("imagen", medicamentos.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getImagen());
                bundle.putInt("medicamentoid", medicamentos.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getMedicamentoID());

                findNavController(v).navigate(R.id.action_nav_home_to_nav_detalle, bundle);
            }
        });
    }

}