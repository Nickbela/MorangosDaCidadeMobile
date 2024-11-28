    package com.example.morangosdacidademobile;

    import static android.content.ContentValues.TAG;
    import static RegrasDeNegocio.Métodos.CadastroLogin.isEmailValido;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;
    import com.example.morangosdacidademobile.Services.ClienteApiService;

    import RegrasDeNegocio.Entity.Cliente;
    import RegrasDeNegocio.Métodos.CadastroLogin;
    import com.example.morangosdacidademobile.Services.CadastroService;

    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.sql.Date;

    public class Cadastro extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_cadastro);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            new Thread(() -> {
                try {
                    URL url = new URL("http://192.168.228.16:8085/api/clientes/login"); // URL do endpoint da API.
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Abre conexão HTTP.
                    connection.setRequestMethod("GET"); // Define o método HTTP como GET.
                    connection.connect(); // Conecta ao servidor.

                    int responseCode = connection.getResponseCode(); // Código de resposta da conexão.
                    if (responseCode == 200) {
                        Log.d(TAG, "Conexão HTTP com 192.168.228.16 bem-sucedida.");
                    } else {
                        Log.e(TAG, "Conexão HTTP falhou. Código de resposta: " + responseCode);
                    }
                    connection.disconnect(); // Fecha a conexão.
                } catch (Exception e) {
                    Log.e(TAG, "Erro ao verificar a conexão HTTP com 192.168.228.16: " + e.getMessage());
                }
            }).start();

                EditText input_nome = findViewById(R.id.nome);
                EditText input_email = findViewById(R.id.email);
                EditText input_senha = findViewById(R.id.senha);
                EditText input_cpf = findViewById(R.id.cpf);
                Button btn_salvar = findViewById(R.id.btn_registrar);
                EditText input_telefone = findViewById(R.id.telefone);
                EditText input_rua = findViewById(R.id.rua);
                EditText input_cidade = findViewById(R.id.cidade);
                EditText input_estado = findViewById(R.id.estado);
                EditText input_cep = findViewById(R.id.cep);
                EditText input_numero = findViewById(R.id.numero_rua);

                btn_salvar.setOnClickListener(v -> {
                    String nome = input_nome.getText().toString();
                    String email = input_email.getText().toString();
                    String cpf = input_cpf.getText().toString();
                    String senha = input_senha.getText().toString();
                    String telefone = input_telefone.getText().toString();
                    String rua = input_rua.getText().toString();
                    String cidade = input_cidade.getText().toString();
                    String estado = input_estado.getText().toString();
                    String cep = input_cep.getText().toString();
                    int numero;
                    java.sql.Date dataNascimento = java.sql.Date.valueOf("2000-10-10");  // Data correta
                    String complemento="0";
                    String bairro="0";

                    // Verifica o campo de número
                    try {
                        numero = Integer.parseInt(input_numero.getText().toString());
                    } catch (NumberFormatException e) {
                        input_numero.setError("Número inválido");
                        input_numero.requestFocus();
                        return;
                    }


                    Log.d("Cadastro", "Nome: " + nome);
                    Log.d("Cadastro", "Email: " + email);
                    Log.d("Cadastro", "CPF: " + cpf);
                    Log.d("Cadastro", "Telefone: " + telefone);
                    Log.d("Cadastro", "Rua: " + rua);
                    Log.d("Cadastro", "Cidade: " + cidade);
                    Log.d("Cadastro", "Estado: " + estado);
                    Log.d("Cadastro", "CEP: " + cep);
                    Log.d("Cadastro", "Número: " + numero);

                    // Validações dos campos
                    if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty() || telefone.isEmpty()
                            || rua.isEmpty() || cidade.isEmpty() || estado.isEmpty() || cep.isEmpty()) {
                        Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!isEmailValido(email)) {
                        input_email.setError("Email inválido");
                        input_email.requestFocus();
                        return;
                    }

                    // Cria o cliente
                    Cliente novoCliente = new Cliente(
                            nome, cpf, email, telefone, dataNascimento, senha, rua, complemento, bairro, numero, cidade, estado, cep
                    );

                    // Thread para chamada à API via CadastroService
                    new Thread(() -> {
                        try {
                            String response = CadastroService.cadastrarCliente(novoCliente);

                            Log.d("Cadastro", "Resposta da API: " + response);

                            // Toast de sucesso na UI
                            runOnUiThread(() -> {
                                Toast.makeText(Cadastro.this, response, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Cadastro.this, Login.class);
                                startActivity(intent);
                                finish();
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                            // Toast de erro na UI
                            runOnUiThread(() -> Toast.makeText(Cadastro.this, "Erro ao adicionar cliente", Toast.LENGTH_SHORT).show());
                        }
                    }).start();
                });
            }
                }


