package com.example.pharmapp.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pharmapp.R;
import com.example.pharmapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Activity activity;
    private ArrayList<Medicamento> medicamentos;
    private ListView lista;
    private Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        activity = getActivity();
        medicamentos = new ArrayList<Medicamento>();

        llenar();

        adapter = new Adapter(activity,medicamentos);

        lista = (ListView) root.findViewById(R.id.lvLista);

        lista.setAdapter(adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void llenar() {

        Medicamento item = new Medicamento();
        item.setNombre("ibuprofeno");
        item.setComprimido(800);
        item.setDescripcion("blalbalbal");
        item.setImagen(R.drawable.blue_modern_icons_maternity_doctor_logo);

        medicamentos.add(item);
        //
        item = new Medicamento();
        item.setNombre("Tafirol");
        item.setComprimido(400);
        item.setDescripcion("blalbalbal2");
        item.setImagen(R.drawable.blue_modern_icons_maternity_doctor_logo);

        medicamentos.add(item);
        //
        item = new Medicamento();
        item.setNombre("ibupirac");
        item.setComprimido(600);
        item.setDescripcion("blalbalbal3");
        item.setImagen(R.drawable.blue_modern_icons_maternity_doctor_logo);

        medicamentos.add(item);


    }

}