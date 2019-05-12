package com.br.whatsapp.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.br.whatsapp.R;
import com.br.whatsapp.adapter.TabAdapter;
import com.br.whatsapp.config.ConfiguracaoFirebase;
import com.br.whatsapp.helper.SlidingTabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Toolbar myToolBar;
    private FirebaseAuth usuarioAtenticacao;

    private SlidingTabLayout mySlidingTabLayout;
    private ViewPager myViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioAtenticacao = ConfiguracaoFirebase.getFirebaseAntenticacao();

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
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void deslogarUsuario() {

        usuarioAtenticacao.signOut();
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        Toast.makeText(this, "Sucesso ao deslogar usuário", Toast.LENGTH_SHORT).show();
        startActivity(myIntent);
        finish();
    }































}
