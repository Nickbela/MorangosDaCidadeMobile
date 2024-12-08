package com.example.morangosdacidademobile.ui.home;

import android.content.Intent;  // Para usar a classe Intent
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;  // Importa o botão diretamente
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.morangosdacidademobile.ProdutoFragment; // Para navegar até a Activity de Produtos
import com.example.morangosdacidademobile.R; // Recursos do projeto

public class HomeFragment extends Fragment {

    // Definição do botão
    private Button btnShopNow;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Infla o layout para o Fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Referencia o botão usando findViewById
        btnShopNow = root.findViewById(R.id.btnShopNow);

        // Configura o comportamento do botão "Comprar Agora"
        btnShopNow.setOnClickListener(view -> {
            // Navegar diretamente para o ProdutoFragment
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ProdutoFragment()) // Substitua o ID pelo container do fragment
                    .addToBackStack(null) // Opcional: permite retornar ao fragment anterior
                    .commit();
        });

        return root;
    }

}
