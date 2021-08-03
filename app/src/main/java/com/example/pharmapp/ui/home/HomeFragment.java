package com.example.pharmapp.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmapp.R;
import com.example.pharmapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import static androidx.navigation.Navigation.findNavController;

public class HomeFragment extends Fragment {

    private ArrayList<Medicamento> medicamentos;
    RecyclerView recyclerViewMedicamentos;
    private AdapterMedicamento adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home,container,false);
        recyclerViewMedicamentos = v.findViewById(R.id.lvLista);

        medicamentos = new ArrayList<>();

        llenar();

        mostrar();

        return v;
    }


    public void llenar() {

        Medicamento item = new Medicamento();
        item.setNombre("ibuprofeno");
        item.setComprimido(800);
        item.setDescripcion("blalbalbal");
        item.setPrecio(500);
        item.setImagen(R.drawable.ibuprofeno);

        medicamentos.add(item);
        //
        item = new Medicamento();
        item.setNombre("Tafirol");
        item.setComprimido(400);
        item.setDescripcion("blalbalbal2");
        item.setPrecio(500);
        item.setImagen(R.drawable.fb8_deva);

        medicamentos.add(item);
        //
        item = new Medicamento();
        item.setNombre("ibupirac");
        item.setComprimido(600);
        item.setDescripcion("blalbalbal3");
        item.setPrecio(500);
        item.setImagen(R.drawable.ibupirac_600_60b660d67d7c0);

        medicamentos.add(item);

        //
        item = new Medicamento();
        item.setNombre("Adermicina");
        item.setComprimido(600);
        item.setDescripcion("blalbalbal4");
        item.setPrecio(500);
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

                findNavController(v).navigate(R.id.action_nav_home_to_detalleeFragment);
                //String nombre = medicamentos.get(recyclerViewMedicamentos.getChildAdapterPosition(v)).getNombre();
                //Toast.makeText(getContext(), "Selecciono el medicamento " + nombre, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
