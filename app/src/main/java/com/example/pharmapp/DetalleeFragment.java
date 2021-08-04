package com.example.pharmapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetalleeFragment extends Fragment {


    public DetalleeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detallee,container,false);

        Bundle bundle = getArguments();
        String nombre = bundle.getString("nombre");

        TextView text = v.findViewById(R.id.idfragmento2);

        text.setText(nombre);


        return v;
    }
}