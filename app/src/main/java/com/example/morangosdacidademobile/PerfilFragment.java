package com.example.morangosdacidademobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.morangosdacidademobile.Services.ClienteApiService;

import RegrasDeNegocio.Entity.Cliente;

public class PerfilFragment extends Fragment {

    private TextView tvNome, tvEmail, tvTelefone, tvCpf, tvEndereco, tvWelcomeUser;
    private static final String TAG = "PerfilFragment"; // Definir um TAG para os logs
    Button btn_editar, btnExcluir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout do fragmento
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Inicializar as TextViews
        tvWelcomeUser = rootView.findViewById(R.id.tvWelcomeUser);
        tvNome = rootView.findViewById(R.id.Nome1);
        tvEmail = rootView.findViewById(R.id.Email1);
        tvTelefone = rootView.findViewById(R.id.Telefone1);
        tvCpf = rootView.findViewById(R.id.Cpf1);
        tvEndereco = rootView.findViewById(R.id.Endereco1);

        // Log inicializando o fragment
        Log.d(TAG, "Iniciando PerfilFragment...");

        new Thread(() -> {
            try {
                SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("ClientePreferences", Context.MODE_PRIVATE);
                String emailCliente = sharedPreferences2.getString("emailCliente", null); // Recupera o email

                // Log para verificar o email recuperado
                Log.d(TAG, "Email recuperado do SharedPreferences: " + emailCliente );

                if (emailCliente  != null) {
                    // Agora você pode buscar os dados do cliente com esse email

                    Cliente cliente = ClienteApiService.getClienteDataByEmail(emailCliente );

                    Log.d(TAG, "Dados do cliente1: "
                            + "Nome=" + cliente.getNome()
                            + ", Email=" + cliente.getEmail()
                            + ", Telefone=" + cliente.getTelefone()
                            + ", CPF=" + cliente.getCpf()
                            + ", Rua=" + cliente.getRua()
                            + ", Numero=" + cliente.getNumero()
                            + ", Cidade=" + cliente.getCidade()
                            + ", Estado=" + cliente.getEstado());

                    // Log para verificar os dados do cliente
                    if (cliente != null) {
                        Log.d(TAG, "Dados do cliente encontrados: " + cliente.getNome() + ", " + cliente.getEmail());

                        Log.d(TAG, "Dados do cliente2: "
                                + "Nome=" + cliente.getNome()
                                + ", Email=" + cliente.getEmail()
                                + ", Telefone=" + cliente.getTelefone()
                                + ", CPF=" + cliente.getCpf()
                                + ", Rua=" + cliente.getRua()
                                + ", Numero=" + cliente.getNumero()
                                + ", Cidade=" + cliente.getCidade()
                                + ", Estado=" + cliente.getEstado());

                        // Preencher os campos com os dados do cliente
                        getActivity().runOnUiThread(() -> {
                            tvWelcomeUser.setText("Bem Vindo(a), "+ cliente.getNome());
                            tvNome.setText("Nome: " + cliente.getNome());
                            tvEmail.setText("Email: "+cliente.getEmail());
                            tvTelefone.setText("Telefone: "+cliente.getTelefone());
                            tvCpf.setText("CPF: "+cliente.getCpf());

                            // Exibir o endereço completo
                            String enderecoCompleto = "Endereço: "+cliente.getRua() + ", " + cliente.getNumero() + ", "
                                    + cliente.getCidade() + ", " + cliente.getEstado();
                            tvEndereco.setText(enderecoCompleto);

                            // Log para confirmar o preenchimento dos dados
                            Log.d(TAG, "Dados preenchidos na UI: " + enderecoCompleto);
                        });

                    } else {
                        // Caso não encontre o cliente, exibe uma mensagem
                        Log.d(TAG, "Cliente não encontrado para o email: " + emailCliente);

                        getActivity().runOnUiThread(() -> tvNome.setText("Cliente não encontrado"));
                    }
                } else {
                    Log.d(TAG, "Email não encontrado nos SharedPreferences.");
                }
            } catch (Exception e) {
                Log.e(TAG, "Erro ao carregar os dados do cliente: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();

        // Dentro do método onCreateView, adicione isso logo após inicializar os TextViews:
        Button btnEditar = rootView.findViewById(R.id.btn_editar);
        btnExcluir = rootView.findViewById(R.id.btn_excluir);

        // Configurar o clique no botão
        btnEditar.setOnClickListener(v -> {
            // Transição para a tela de edição do perfil
            openEditProfileScreen();
        });

        btnExcluir.setOnClickListener(v -> {
            // Confirmação antes de excluir
            new android.app.AlertDialog.Builder(requireContext())
                    .setTitle("Excluir Cadastro")
                    .setMessage("Tem certeza de que deseja excluir seu cadastro?")
                    .setPositiveButton("Sim", (dialog, which) -> excluirCadastro())
                    .setNegativeButton("Não", null)
                    .show();
        });

        return rootView;
    }

    private void excluirCadastro() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ClientePreferences", Context.MODE_PRIVATE);
        String emailCliente = sharedPreferences.getString("emailCliente", null);

        if (emailCliente != null) {
            new Thread(() -> {
                try {
                    // Faz a requisição para excluir o cliente e recebe a resposta como texto
                    String resposta = ClienteApiService.deleteCliente(emailCliente);

                    getActivity().runOnUiThread(() -> {
                        // Verifica se a resposta indica sucesso ou erro
                        if (resposta != null && resposta.equals("sucesso")) {
                            Toast.makeText(requireContext(), "Cadastro excluído com sucesso.", Toast.LENGTH_SHORT).show();
                            // Limpar SharedPreferences e redirecionar para a tela inicial
                            sharedPreferences.edit().clear().apply();
                            startActivity(new Intent(requireContext(), Login.class));
                            getActivity().finish();
                        } else {
                            // Exibe a mensagem de erro recebida do servidor
                            Toast.makeText(requireContext(), "Erro ao excluir cadastro: " + resposta, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Erro ao excluir cadastro: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();
        }
    }


    private void openEditProfileScreen() {
        // Aqui você pode iniciar uma nova Activity para a edição de perfil
        Intent intent = new Intent(requireContext(), Gerenciamento.class);
        startActivity(intent);
    }


}
