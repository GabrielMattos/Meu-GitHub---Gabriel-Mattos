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
import com.br.whatsapp.config.ConfiguracaoFirebase;
import com.br.whatsapp.helper.Base64Custom;
import com.br.whatsapp.helper.Preferencias;
import com.br.whatsapp.model.Mensagem;
import com.google.firebase.database.DatabaseReference;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;

    //dados do destinatario
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;

    //dados do remetente
    private String idUsuarioRemetente;

    private EditText ediTextMensagem;
    private ImageButton imageButtonEnviar;

    private DatabaseReference referenciaFirebase;

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
            String emailDestinatario = extra.getString("email");
            idUsuarioDestinatario = Base64Custom.codificarBase64(emailDestinatario);
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
                    salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);
                    ediTextMensagem.setText("");
                }
            }
        });
    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem) {

        try {

            referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("mensagens");
            referenciaFirebase.child(idRemetente).child(idDestinatario).push().setValue(mensagem);

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
