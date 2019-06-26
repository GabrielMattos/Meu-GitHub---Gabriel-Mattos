package com.br.whatsapp.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.whatsapp.R;
import com.br.whatsapp.adapter.TabAdapter;
import com.br.whatsapp.config.ConfiguracaoFirebase;
import com.br.whatsapp.helper.Base64Custom;
import com.br.whatsapp.helper.Preferencias;
import com.br.whatsapp.helper.SlidingTabLayout;
import com.br.whatsapp.model.Contato;
import com.br.whatsapp.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Toolbar myToolBar;
    private FirebaseAuth usuarioFirebase;

    private SlidingTabLayout mySlidingTabLayout;
    private ViewPager myViewPager;

    private String identificadorContato;
    private DatabaseReference referenciaFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAntenticacao();

        myToolBar = findViewById(R.id.toolbarPrincipal);
        myToolBar.setTitle("WhatsApp");
        setSupportActionBar(myToolBar); //método de suporte para funcionar corretamente - necessário

        mySlidingTabLayout = findViewById(R.id.STL_TABS);
        myViewPager = findViewById(R.id.VP_Pagina);

        //Configurar sliding tabs
        mySlidingTabLayout.setDistributeEvenly(true); //Vai distribuir as tabs pelo espaço do layout
        mySlidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));

        //Configurar adapter
        TabAdapter myTabAdapter = new TabAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabAdapter);
        mySlidingTabLayout.setViewPager(myViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Pegar o menu e colocar no objeto menu

        MenuInflater myInflater = getMenuInflater();
        myInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sair:
                deslogarUsuario();
                return true;
            case R.id.action_configuracoes:
                return true;
            case R.id.action_adicionar:
                abrirCadastroContato();
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void abrirCadastroContato() {

        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(MainActivity.this);

        //Configura a dialog
        myAlertDialog.setTitle("Novo Contato");
        myAlertDialog.setMessage("Digite o E-mail do usuário.");
        myAlertDialog.setCancelable(false);

        final EditText myEditText = new EditText(MainActivity.this);
        myAlertDialog.setView(myEditText);

        myAlertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String emailContato = myEditText.getText().toString();

                if(emailContato.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, digite o E-mail do contato", Toast.LENGTH_SHORT).show();
                } else {
                    identificadorContato = Base64Custom.codificarBase64(emailContato);
                    referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(identificadorContato);
                    referenciaFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() != null) { //Caso tenha algum dado

                                //Recuperar dados do contato a ser adicionado
                                Usuario usuarioContato = dataSnapshot.getValue(Usuario.class);

                                //Recuperar identificador usuario logado(Base64)
                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String identificadorUsuarioLogado = preferencias.getIdentificador();

                                referenciaFirebase = ConfiguracaoFirebase.getFirebase();
                                referenciaFirebase = referenciaFirebase.child("contatos")
                                                                        .child(identificadorUsuarioLogado)
                                                                        .child(identificadorContato);

                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario(identificadorContato);
                                contato.setEmail(usuarioContato.getEmail());
                                contato.setNome(usuarioContato.getNome());

                                referenciaFirebase.setValue(contato);

                                Toast.makeText(MainActivity.this, "Usuário salvo com sucesso.", Toast.LENGTH_SHORT).show();

                            } else { //Caso não exista o contato cadastrato no app
                                Toast.makeText(MainActivity.this, "Usuário não possui cadastro no aplicativo.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    }); //Consulta única no FB, se o dado for alterado, o usuário não é informado


                }
            }
        });
        myAlertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        myAlertDialog.create();
        myAlertDialog.show();

    }

    private void deslogarUsuario() {

        usuarioFirebase.signOut();
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        Toast.makeText(this, "Sucesso ao deslogar usuário", Toast.LENGTH_SHORT).show();
        startActivity(myIntent);
        finish();
    }































}
