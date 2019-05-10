package com.br.whatsapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.br.whatsapp.R;
import com.br.whatsapp.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Toolbar myToolBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolBar = findViewById(R.id.toolbarPrincipal);
        myToolBar.setTitle("WhatsApp");
        setSupportActionBar(myToolBar); //método de suporte para funcionar corretamente - necessário

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Pegar o menu e colocar no objeto menu

        MenuInflater myInflater = getMenuInflater();
        myInflater.inflate(R.menu.menu_main, menu);



        return true;
    }
}
