package com.example.pharmapp.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmapp.R;
import com.example.pharmapp.databinding.FragmentGalleryBinding;
import com.example.pharmapp.ui.home.AdapterMedicamento;
import com.example.pharmapp.ui.home.Medicamento;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    ArrayList<Medicamento> medicamentos;
    RecyclerView recyclerViewMedicamentos;
    AdapterMedicamento adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_gallery, container,false);

        recyclerViewMedicamentos = v.findViewById(R.id.lvLista2);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("medicamento", null);
        Type type = new TypeToken<ArrayList<Medicamento>>() {}.getType();
        medicamentos = gson.fromJson(json, type);

        if (medicamentos == null) {
            medicamentos = new ArrayList<>();
        }

        recyclerViewMedicamentos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterMedicamento(getContext(), medicamentos);
        recyclerViewMedicamentos.setAdapter(adapter);

        return v;
    }

}