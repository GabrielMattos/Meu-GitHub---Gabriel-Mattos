package com.br.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.br.whatsapp.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextNome, editTextCodPais, editTextCodArea, editTextTelefone;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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




































}


