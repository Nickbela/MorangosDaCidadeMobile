package com.example.morangosdacidademobile;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.morangosdacidademobile.ui.CarrinhoFragment;
import com.example.morangosdacidademobile.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<Integer, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loadHomeFragment(); // Carrega o HomeFragment inicialmente
        }

        // Inicializar BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Criar o mapeamento entre IDs e Fragments
        fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.nav_home, new HomeFragment());
        fragmentMap.put(R.id.nav_produtos, new ProdutoFragment());
        fragmentMap.put(R.id.nav_carrinho, new CarrinhoFragment());
        fragmentMap.put(R.id.nav_perfil, new PerfilFragment());

        // Configurar o listener de navegação
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Verifica se o item foi mapeado e obtém o fragment
                Fragment selectedFragment = fragmentMap.get(item.getItemId());

                // Se o fragment não for nulo, substitui o fragment atual
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }

    private void loadHomeFragment() {
        // Cria uma nova instância do HomeFragment
        HomeFragment homeFragment = new HomeFragment();

        // Adiciona o fragmento no layout
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, homeFragment) // Substitui o fragmento atual pelo HomeFragment
                .commit();
    }
}
