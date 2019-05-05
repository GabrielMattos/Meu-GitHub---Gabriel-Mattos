package com.br.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.whatsapp.R;
import com.br.whatsapp.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ValidadorActivity extends AppCompatActivity {

    private EditText ediTextCodigoValidacao;
    private Button btnValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        findViews();
        buttonValidarToken();
        simplesMaskFormatter();
    }

    public void findViews() {

        ediTextCodigoValidacao = findViewById(R.id.editTextCodValidacao);
        btnValidar = findViewById(R.id.buttonValidar);
    }

    private void buttonValidarToken() {

        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recuperar dados das preferencias do usuario
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String, String> usuario = preferencias.getDadosUsuario();

                String tokenGerado = usuario.get("token");
                String tokenDigitado = ediTextCodigoValidacao.getText().toString();

                if(tokenDigitado.equals(tokenGerado)) {
                    Toast.makeText(ValidadorActivity.this, "Token VALIDADO", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ValidadorActivity.this, "Token NÃO VALIDADO", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void simplesMaskFormatter() {

        SimpleMaskFormatter simpleMaskCodigoValidacao = new SimpleMaskFormatter("NNNN");
        final MaskTextWatcher mascaraCodigoValidacao = new MaskTextWatcher(ediTextCodigoValidacao, simpleMaskCodigoValidacao);
        ediTextCodigoValidacao.addTextChangedListener(mascaraCodigoValidacao);
    }

























}
