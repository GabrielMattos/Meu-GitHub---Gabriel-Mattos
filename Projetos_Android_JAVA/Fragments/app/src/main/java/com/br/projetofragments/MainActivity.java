package com.br.projetofragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogar;
    private boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogar = findViewById(R.id.buttonLogar);

        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager myFragmentManager = getSupportFragmentManager();
                FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();

                if(status) {
                    LoginFrament myLoginFragment = new LoginFrament();
                    myFragmentTransaction.add(R.id.RL_Conteiner_Fragment, myLoginFragment); //Adicionar a um container
                    myFragmentTransaction.commit(); //Confirma
                    buttonLogar.setText("Cadastre-se");
                    status = false;
                } else {
                    CadastroFragment myCadastroFragment = new CadastroFragment();
                    myFragmentTransaction.add(R.id.RL_Conteiner_Fragment, myCadastroFragment);
                    myFragmentTransaction.commit();
                    buttonLogar.setText("Logar");
                    status = true;
                }
            }
        });
    }





























}
