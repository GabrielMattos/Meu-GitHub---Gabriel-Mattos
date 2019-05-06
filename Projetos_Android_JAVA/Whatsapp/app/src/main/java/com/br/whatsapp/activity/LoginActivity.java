package com.br.whatsapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.whatsapp.R;
import com.br.whatsapp.helper.Permissao;
import com.br.whatsapp.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    /*private EditText editTextNome, editTextCodPais, editTextCodArea, editTextTelefone;
    private Button btnCadastrar;
    private String[] permissoesNecessarias = new String[] {Manifest.permission.SEND_SMS, Manifest.permission.INTERNET};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    public void abrirCadastroUsuario(View view) {

        Intent intent = new Intent(LoginActivity.this, ActivityCadastroUsuario.class);
        startActivity(intent);
    }



































}


