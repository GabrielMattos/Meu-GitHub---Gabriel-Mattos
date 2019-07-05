package com.br.projetofragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFrament extends Fragment {

    private TextView textViewLogin;


    public LoginFrament() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, //Chamado para a criação do fragment
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_login_frament, container, false);

        textViewLogin = myView.findViewById(R.id.textViewTelaLogin);
        textViewLogin.setText("Tela de Login alterada");

        return  myView;//Converte XML em View
    }

}
