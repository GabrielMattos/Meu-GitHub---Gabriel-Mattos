package com.br.whatsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.br.whatsapp.R;
import com.br.whatsapp.helper.Preferencias;
import com.br.whatsapp.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;

    //dados do destinatario
    private String nomeUsuarioDestinatario;

    //dados do remetente
    private String idUsuarioRemetente;

    private EditText ediTextMensagem;
    private ImageButton imageButtonEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        findViews();

        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRemetente = preferencias.getIdentificador();

        Bundle extra =  getIntent().getExtras();

        if(extra != null) {
            nomeUsuarioDestinatario = extra.getString("nome");
        }

        //Configura toolbar
        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //enviar mensagem
        imageButtonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoMensagem = ediTextMensagem.getText().toString();

                if(textoMensagem.isEmpty()) {
                    Toast.makeText(ConversaActivity.this, "Digite uma mensagem para enviar.", Toast.LENGTH_SHORT).show();
                } else {
                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioRemetente);
                    mensagem.setMensagem(textoMensagem);

                    salvarMensagem("id remetente", "id destinatario", "Mensagem");
                }
            }
        });
    }

    private boolean salvarMensagem(String idRemetente, String idUsuario, Mensagem mensagem) {

        try {

            firebase

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


public void findViews() {

    toolbar = findViewById(R.id.toolbar_conversa);
    ediTextMensagem = findViewById(R.id.editText_mensagem);
    imageButtonEnviar = findViewById(R.id.imageButton_enviar);
}




























































}
