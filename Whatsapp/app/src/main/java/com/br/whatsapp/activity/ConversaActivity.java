package com.br.whatsapp.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.br.whatsapp.R;
import com.br.whatsapp.adapter.MensagemAdapter;
import com.br.whatsapp.config.ConfiguracaoFirebase;
import com.br.whatsapp.helper.Base64Custom;
import com.br.whatsapp.helper.Preferencias;
import com.br.whatsapp.model.Conversa;
import com.br.whatsapp.model.Mensagem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;

    //dados do destinatario
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;

    //dados do remetente
    private String idUsuarioRemetente;
    private  String nomeUsuarioRemetente;

    private EditText ediTextMensagem;
    private ImageButton imageButtonEnviar;

    private DatabaseReference referenciaFirebase;

    private ListView listViewConversa;

    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> meuAdapter;

    private ValueEventListener valueEventListenerMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        findViews();

        //dados usuario logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRemetente = preferencias.getIdentificador();
        nomeUsuarioRemetente = preferencias.getNome();

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

        //Monta listview e adapter
        mensagens = new ArrayList<>();
        meuAdapter = new MensagemAdapter(ConversaActivity.this, mensagens);
        //meuAdapter = new ArrayAdapter(ConversaActivity.this, android.R.layout.simple_list_item_1, mensagens);

        listViewConversa.setAdapter(meuAdapter);

        //recuperar mensagens no Firebase
        referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("mensagens").child(idUsuarioRemetente).child(idUsuarioDestinatario);

        //Cria listener para mensagens
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Limpar mensagens
                mensagens.clear();

                //recuperar mensagens
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    Mensagem mensagem = dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }

                meuAdapter.notifyDataSetChanged(); //Notifica que os dados mudaram
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        referenciaFirebase.addValueEventListener(valueEventListenerMensagem);

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
                    //Salvamos mensagem para o remetente
                    Boolean retornoMensagemRemetente = salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);

                    if(!retornoMensagemRemetente) {
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar mensagem, tente novamente.", Toast.LENGTH_LONG).show();
                    } else {
                        //savamos mensagem para o destinatario
                        Boolean retornoMensagemDestinatario = salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem);
                        if(!retornoMensagemDestinatario) {
                            Toast.makeText(ConversaActivity.this, "Problema ao enviar mensagem para o destinatario, tente novamente.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //salvamos conversa para o remetente
                    Conversa conversaRemetente = new Conversa();
                    conversaRemetente.setIdUsuario(idUsuarioDestinatario);
                    conversaRemetente.setNome(nomeUsuarioDestinatario);
                    conversaRemetente.setMensagem(textoMensagem);
                    Boolean retornoConversaRemetente = salvarConversa(idUsuarioRemetente, idUsuarioDestinatario, conversaRemetente);
                    if(!retornoConversaRemetente) {
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa, tente novamente.", Toast.LENGTH_SHORT).show();
                    } else {
                        //salvamos conversa para o destinatario
                        Conversa conversaDestinatario = new Conversa();
                        conversaDestinatario.setIdUsuario(idUsuarioRemetente);
                        conversaDestinatario.setNome(nomeUsuarioRemetente);
                        conversaDestinatario.setMensagem(textoMensagem);
                        salvarConversa(idUsuarioDestinatario, idUsuarioRemetente, conversaDestinatario);

                        Boolean retornoConversaDestinatario = salvarConversa(idUsuarioDestinatario, idUsuarioRemetente, conversaDestinatario);
                        if(!retornoConversaDestinatario) {
                            Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa para o destinatario, tente novamente.", Toast.LENGTH_SHORT).show();
                        }

                    }

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
        listViewConversa = findViewById(R.id.listview_conversas);
    }

    private Boolean salvarConversa(String idRemetente, String idDestinatario, Conversa conversa) {

        try {
            referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("conversas");
            referenciaFirebase.child(idUsuarioRemetente).child(idUsuarioDestinatario).setValue(conversa);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        referenciaFirebase.removeEventListener(valueEventListenerMensagem); //Economiza recursos, se o usuario nao estiver na activity conversa
    }







































}
