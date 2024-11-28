package com.example.morangosdacidademobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.morangosdacidademobile.Services.ClienteApiService;
import com.example.morangosdacidademobile.ui.Gerenciamento;

import RegrasDeNegocio.Entity.Cliente;

public class PerfilFragment extends Fragment {

    private TextView tvWelcomeUser, etNome, etEmail, etTelefone, etCpf, etEndereco;
    private Button btnEditProfile, btnDeleteProfile;
    private Cliente clienteAtual; // Objeto para armazenar os dados do cliente

    private String login = "user_login"; // Substitua pelo login do cliente atual
    private String senha = "user_password"; // Substitua pela senha do cliente atual

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Inicializa os componentes da interface
        tvWelcomeUser = view.findViewById(R.id.tvWelcomeUser);
        etNome = view.findViewById(R.id.etNome);
        etEmail = view.findViewById(R.id.etEmail);
        etTelefone = view.findViewById(R.id.etTelefone);
        etCpf = view.findViewById(R.id.etCpf);
        etEndereco = view.findViewById(R.id.etEndereco);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnDeleteProfile = view.findViewById(R.id.btnDeleteProfile);

        // Carrega os dados do cliente
        carregarDadosCliente();

        // Configura os botões
        btnEditProfile.setOnClickListener(v -> editarPerfil());
        btnDeleteProfile.setOnClickListener(v -> confirmarExclusao());

        return view;
    }

    private void carregarDadosCliente() {
        // Chama o método assíncrono para buscar os dados do cliente
        new BuscarClienteTask().execute(login, senha);
    }

    private void atualizarUI(Cliente cliente) {
        if (cliente == null) {
            Toast.makeText(getActivity(), "Erro ao carregar dados do cliente.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Atualiza os TextViews com os dados do cliente
        tvWelcomeUser.setText("Bem-vindo, " + cliente.getNome() + "!");
        etNome.setText("Nome: " + cliente.getNome());
        etEmail.setText("E-mail: " + cliente.getEmail());
        etTelefone.setText("Telefone: " + cliente.getTelefone());
        etCpf.setText("CPF: " + cliente.getCpf());
        etEndereco.setText("Endereço: " + cliente.getRua() + ", " + cliente.getNumero() + ", " + cliente.getCidade() + ", " + cliente.getEstado());
    }

    private void editarPerfil() {
        // Navega para a tela de edição de perfil
        Intent intent = new Intent(getActivity(), Gerenciamento.class);
        intent.putExtra("cliente", (CharSequence) clienteAtual); // Passa os dados do cliente para a próxima tela
        startActivity(intent);
    }

    private void confirmarExclusao() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Excluir Cadastro")
                .setMessage("Tem certeza de que deseja excluir seu cadastro? Esta ação é irreversível.")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluirPerfil();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void excluirPerfil() {
        // Chama o método assíncrono para excluir o cliente
        new ExcluirClienteTask().execute(Long.valueOf(clienteAtual.getCpf()));
    }

    // Task para buscar o cliente
    private class BuscarClienteTask extends AsyncTask<String, Void, Cliente> {
        @Override
        protected Cliente doInBackground(String... params) {
            try {
                return ClienteApiService.getClienteByLogin(params[0], params[1]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cliente cliente) {
            clienteAtual = cliente; // Salva os dados do cliente
            atualizarUI(cliente); // Atualiza a interface com os dados
        }
    }

    // Task para excluir o cliente
    private class ExcluirClienteTask extends AsyncTask<Long, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Long... params) {
            try {
                String resposta = ClienteApiService.deleteCliente(params[0]);
                return resposta.equals("Cliente deletado com sucesso");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean sucesso) {
            if (sucesso) {
                Toast.makeText(getActivity(), "Perfil excluído com sucesso!", Toast.LENGTH_SHORT).show();
                // Redireciona para a tela de login
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                requireActivity().finish();
            } else {
                Toast.makeText(getActivity(), "Erro ao excluir perfil.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
