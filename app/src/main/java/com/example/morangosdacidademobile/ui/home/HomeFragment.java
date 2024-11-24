package com.example.morangosdacidademobile.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.morangosdacidademobile.R;
import com.example.morangosdacidademobile.databinding.FragmentHomeBinding;
import com.example.morangosdacidademobile.ui.CarrinhoFragment;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Definir o click do botão "Comprar Agora"
        binding.btnShopNow.setOnClickListener(view -> navigateToCarrinhoFragment());

        return root;
    }

    private void navigateToCarrinhoFragment() {
        // Criação da instância do fragmento de destino
        CarrinhoFragment carrinhoFragment = new CarrinhoFragment();

        // Iniciar a transação para substituir o fragmento atual
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, carrinhoFragment);  // R.id.fragment_container é o contêiner onde o fragmento será inserido
        transaction.addToBackStack(null);  // Adiciona à pilha de retrocesso, permitindo voltar ao fragmento anterior
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
