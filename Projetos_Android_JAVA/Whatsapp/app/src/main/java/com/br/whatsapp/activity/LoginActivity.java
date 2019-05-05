package com.br.whatsapp.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.whatsapp.R;
import com.br.whatsapp.helper.Permissao;
import com.br.whatsapp.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextNome, editTextCodPais, editTextCodArea, editTextTelefone;
    private Button btnCadastrar;
    private String[] permissoesNecessarias = new String[] {Manifest.permission.SEND_SMS, Manifest.permission.INTERNET};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1,this, permissoesNecessarias);

        findViews();
        simplesMaskFormatter();

        buttonCadastro();

    }

    //Botão cadastro
    public void buttonCadastro() {

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Pegar às Strings
                String nomeUsuario = editTextNome.getText().toString();
                String telefoneCompleto = editTextCodPais.getText().toString() + editTextCodArea.getText().toString() + editTextTelefone.getText().toString();

                //replace - substitui uma informação, por outra desejada.
                String telefoneSemFormatacao = telefoneCompleto.replace("+", "");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-", "");

                //Gerar TOKEN - NÃO RECOMENDADO - apenas para estudo!
                Random randomico = new Random();
                int numeroRandimico = randomico.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroRandimico);  //Converter String para o que deseja
                String mensagemEnvio =  "Olá " + nomeUsuario + ",  Whatsapp Código de confirmação: " + token;

                //salvar dados para validação
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarPreferenciasUsuario(nomeUsuario, telefoneSemFormatacao, token);

                //Envio de SMS
                //telefoneSemFormatacao = "5554"; //Apenas se usar emulador
                boolean enviadoSMS = enviaSMS("+" + telefoneSemFormatacao, mensagemEnvio);
                
                if(enviadoSMS) {
                    Intent intent = new Intent(LoginActivity.this, ValidadorActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Problema ao enviar o SMS, tente novamente", Toast.LENGTH_LONG).show();
                }
/*
                HashMap<String, String> usuario = preferencias.getDataUsuario();
                Log.i("TOKEN", "T:" + usuario.get("token"));
*/
            }
        });
    }

    //Buscar os Views pelo ID
    public void findViews() {

        editTextNome = findViewById(R.id.editTextNomeID);
        editTextTelefone = findViewById(R.id.editTextTelefoneID);
        editTextCodPais = findViewById(R.id.editTextCodPais);
        editTextCodArea = findViewById(R.id.editTextCodArea);
        btnCadastrar = findViewById(R.id.buttonCadastrar);
    }

    //Simples máscara de formatação

    public void simplesMaskFormatter() {

        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        SimpleMaskFormatter simpleMaskPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskArea = new SimpleMaskFormatter("NN");

        MaskTextWatcher maskTelefone = new MaskTextWatcher(editTextTelefone, simpleMaskTelefone);
        MaskTextWatcher maskPais = new MaskTextWatcher(editTextCodPais, simpleMaskPais);
        MaskTextWatcher maskArea = new MaskTextWatcher(editTextCodArea, simpleMaskArea);

        editTextTelefone.addTextChangedListener(maskTelefone);
        editTextCodPais.addTextChangedListener(maskPais);
        editTextCodArea.addTextChangedListener(maskArea);

    }//simplesMaskFormatter


    public boolean enviaSMS(String telefone, String mensagem) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }//enviaSMS

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) { //Verifica se alguma permissao foi negada

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int resultado : grantResults) {
            if(resultado == PackageManager.PERMISSION_DENIED) {  //se alguma foi negada
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() { //Alerta para quer só possa continuar se aceitar as permissoes

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar o app, é necessário aceitar aos permissoes");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



































}


