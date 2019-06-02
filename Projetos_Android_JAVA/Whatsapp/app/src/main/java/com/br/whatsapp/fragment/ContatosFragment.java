package com.br.whatsapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.br.whatsapp.R;
import com.br.whatsapp.config.ConfiguracaoFirebase;
import com.br.whatsapp.helper.Preferencias;
import com.br.whatsapp.model.Contato;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */


public class ContatosFragment extends Fragment {

    private ListView meuListView;
    private ArrayAdapter meuAdapter;
    private ArrayList<String> contatos;
    private DatabaseReference referenciaFirebase;


    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contatos = new ArrayList<>();

        // Inflate the layout for this fragment
        View meuView = inflater.inflate(R.layout.fragment_contatos, container, false);

        meuListView = meuView.findViewById(R.id.listViewContatosID);

        meuAdapter = new ArrayAdapter(getActivity(), R.layout.lista_contato, contatos);
        meuListView.setAdapter(meuAdapter);

        //Recuperar contatos do firebase
        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUsuariologado = preferencias.getIdentificador();

        referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("contatos")
                                                                .child(identificadorUsuariologado);

        //Listener para recuperar contatos
        referenciaFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Limpar contatos
                contatos.clear();

                //Listar contatos
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato.getNome());
                }

                meuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return meuView;
    }

}
