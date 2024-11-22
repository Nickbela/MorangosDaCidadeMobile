package com.example.morangosdacidademobile.ui;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.morangosdacidademobile.R;

public class PerfilFragment extends Fragment {

    // Construtor padrão
    public PerfilFragment() {
        // Não é necessário adicionar código aqui
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout do fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}
