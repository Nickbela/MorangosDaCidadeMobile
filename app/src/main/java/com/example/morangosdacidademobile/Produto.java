package com.example.morangosdacidademobile;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.morangosdacidademobile.adapters.CarrinhoAdapter;

import java.util.Arrays;
import java.util.List;

public class Produto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_produto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewProdutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Produto> produtos = Arrays.asList(
                new Produto("Morango Albion", 150.00, R.mipmap.ic_albion_foreground),
                new Produto("Morango Capri", 185.00, R.mipmap.ic_capri_foreground),
                new Produto("Morango Diamante", 190.00, R.mipmap.ic_diamante_foreground),
                new Produto("Morango Bourbon", 210.00, R.mipmap.ic_bourbon_foreground)
        );

        CarrinhoAdapter adapter = new CarrinhoAdapter(produtos);
        recyclerView.setAdapter(adapter);

    }
}