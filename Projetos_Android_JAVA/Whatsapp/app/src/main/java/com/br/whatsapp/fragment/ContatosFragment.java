package com.br.whatsapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.br.whatsapp.R;
import com.br.whatsapp.activity.ConversaActivity;
import com.br.whatsapp.adapter.ContatoAdapter;
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
    private ArrayList<Contato> contatos;
    private DatabaseReference referenciaFirebase;
    private ValueEventListener valueEventListenerContatos;


    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() { //Chamado antes do Fragment iniciar
        super.onStart();
        referenciaFirebase.addValueEventListener(valueEventListenerContatos);
        Log.i("ValueEventListener", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        referenciaFirebase.removeEventListener(valueEventListenerContatos);
        Log.i("ValueEventListener", "onStop");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contatos = new ArrayList<>();

        // Inflate the layout for this fragment
        View meuView = inflater.inflate(R.layout.fragment_contatos, container, false);

        meuListView = meuView.findViewById(R.id.listViewContatosID);

        /*meuAdapter = new ArrayAdapter(getActivity(), R.layout.lista_contato, contatos);*/
        //a ideia do adapter é montar os itens que serao exibidos na lista
        meuAdapter = new ContatoAdapter(getActivity(), contatos);
        meuListView.setAdapter(meuAdapter);

        //Recuperar contatos do firebase
        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUsuariologado = preferencias.getIdentificador();

        referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("contatos")
                                                                .child(identificadorUsuariologado);

        //Listener para recuperar contatos
        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Limpar contatos
                contatos.clear();

                //Listar contatos
                for(DataSnapshot dados: dataSnapshot.getChildren()) {
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato );
                }

                meuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Clicando em um contato da lista, abrir a Activity de conversa
        meuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);
                startActivity(intent);
            }
        });

        return meuView;
    }

}
