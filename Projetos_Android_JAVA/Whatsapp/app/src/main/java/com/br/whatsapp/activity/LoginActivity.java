package com.br.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.br.whatsapp.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextNome, editTextCodPais, editTextCodArea, editTextTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        simplesMaskFormatter();

    }

    //Buscar os Views pelo ID
    public void findViews() {

        editTextNome = findViewById(R.id.editTextNomeID);
        editTextTelefone = findViewById(R.id.editTextTelefoneID);
        editTextCodPais = findViewById(R.id.editTextCodPais);
        editTextCodArea = findViewById(R.id.editTextCodArea);
    }

    //Simples máscara de formatação

    public void simplesMaskFormatter() {

        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("N NNNN-NNNN");
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


