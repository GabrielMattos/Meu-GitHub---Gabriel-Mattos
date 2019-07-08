package com.br.whatsapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.whatsapp.R;
import com.br.whatsapp.config.ConfiguracaoFirebase;
import com.br.whatsapp.helper.Base64Custom;
import com.br.whatsapp.helper.Preferencias;
import com.br.whatsapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class ActivityCadastroUsuario extends AppCompatActivity {

    private EditText editTextNome, editTextEmail, editTextSenha;
    private Button btnCadastrar;
    private Usuario usuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        findViews();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = new Usuario();
                usuario.setNome(editTextNome.getText().toString());
                usuario.setEmail(editTextEmail.getText().toString());
                usuario.setSenha(editTextSenha.getText().toString());
                cadastrarUsuario();

            }
        });
    }

    private void cadastrarUsuario() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAntenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(ActivityCadastroUsuario.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) { //RETORNO

                if(task.isSuccessful()) {
                    Toast.makeText(ActivityCadastroUsuario.this, "Sucesso ao cadastrar usuario", Toast.LENGTH_LONG).show();
                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(identificadorUsuario);
                    usuario.salvar();
                    abrirLoginUsuario();

                    Preferencias preferencias = new Preferencias(ActivityCadastroUsuario.this);
                    preferencias.salvarDados(identificadorUsuario, usuario.getNome());

                } else {

                    String erroExcecao = "";

                    try{
                        throw task.getException();  //lançar excessao
                    } catch (FirebaseAuthWeakPasswordException e) { //SENHA
                        erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letras e números.";
                    } catch (FirebaseAuthInvalidCredentialsException e) { //EMAIL
                        erroExcecao = "O e-mail digitado é inválido, digite um novo e-mail.";
                    } catch (FirebaseAuthUserCollisionException e) { //EMAIL SENDO UTILIZADO
                        erroExcecao = "Esse e-mail já está sendo usado.";
                    } catch (Exception e) {
                        erroExcecao = "Ao efetuar o cadastro.";
                        e.printStackTrace();
                    }

                    Toast.makeText(ActivityCadastroUsuario.this, "Erro :" + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void abrirLoginUsuario() {

        Intent intent = new Intent(ActivityCadastroUsuario.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void findViews() {

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        btnCadastrar = findViewById(R.id.buttonCadastrar);
    }



































}
