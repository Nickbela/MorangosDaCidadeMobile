package com.example.morangosdacidademobile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.morangosdacidademobile.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (savedInstanceState == null) {
            // Criar uma instância do HomeFragment (em Java, não é necessário o 'val')
            HomeFragment homeFragment = new HomeFragment();  // Cria a instância do HomeFragment

            // Inicia a transação de fragmentos para adicionar o HomeFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)  // Substitui o fragment no contêiner
                    .commit();
        }


}}