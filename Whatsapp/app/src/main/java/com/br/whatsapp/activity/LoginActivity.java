package com.br.whatsapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.whatsapp.R;
import com.br.whatsapp.config.ConfiguracaoFirebase;
import com.br.whatsapp.helper.Base64Custom;
import com.br.whatsapp.helper.Permissao;
import com.br.whatsapp.helper.Preferencias;
import com.br.whatsapp.model.Usuario;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLogin, editTextSenha;
    private Button buttonLogar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    private DatabaseReference referenciaFirebase;
    private ValueEventListener valueEventListenerUsuario;

    private String identificadorUsuarioLogado;

    /*private EditText editTextNome, editTextCodPais, editTextCodArea, editTextTelefone;
    private Button btnCadastrar;
    private String[] permissoesNecessarias = new String[] {Manifest.permission.SEND_SMS, Manifest.permission.INTERNET};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        findViews();

        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setEmail(editTextLogin.getText().toString());
                usuario.setSenha(editTextSenha.getText().toString());
                validarLogin();
            }
        });
    }

    private void validarLogin() { //fazer autenticacao

        autenticacao = ConfiguracaoFirebase.getFirebaseAntenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());
                    referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(identificadorUsuarioLogado);
                    Preferencias preferencias = new Preferencias(LoginActivity.this);
                    String usuarioRecuperado = usuario.getNome();
                    preferencias.salvarDados(identificadorUsuarioLogado, usuarioRecuperado);

                    /* NÃO FUNCIONOU DE JEITO NENHUM
                    valueEventListenerUsuario = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Usuario usuarioRecuperado = dataSnapshot.getValue(Usuario.class);

                            Preferencias preferencias = new Preferencias(LoginActivity.this);
                            preferencias.salvarDados(identificadorUsuarioLogado, usuarioRecuperado.getNome());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    referenciaFirebase.addListenerForSingleValueEvent(valueEventListenerUsuario);*/

                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Sucesso no login", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro no login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verificarUsuarioLogado() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAntenticacao();
        if(autenticacao.getCurrentUser() != null) {
            abrirTelaPrincipal();
        }
    }

    private void abrirTelaPrincipal() {

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {

        editTextLogin = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonLogar = findViewById(R.id.buttonValidar);
    }


    public void abrirCadastroUsuario(View view) {

        Intent intent = new Intent(LoginActivity.this, ActivityCadastroUsuario.class);
        startActivity(intent);
    }



































}


