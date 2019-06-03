package com.br.whatsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.br.whatsapp.R;
import com.br.whatsapp.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoAdapter extends ArrayAdapter<Contato> {

    private ArrayList<Contato> contatos;
    private Context context;

    public ContatoAdapter(@androidx.annotation.NonNull Context c, @androidx.annotation.NonNull ArrayList<Contato> objects) {
        super(c, 0, objects);
        this.contatos = objects;
        this.context = c;
    }

    @androidx.annotation.NonNull
    @Override
    public View getView(int position, @androidx.annotation.Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {
        //Criando do zero uma View
        View view = null;

        //verifica se a lista está vazia
        if(contatos != null) {
            //Inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //monta view a partir do XML
            view = inflater.inflate(R.layout.lista_contato, parent, false);

            //recuperar elemento para exibição
            TextView nomeContato = view.findViewById(R.id.textview_Nome);
            TextView emailContato = view.findViewById(R.id.textview_Email)
            Contato contato = contatos.get(position);
            nomeContato.setText(contato.getNome());
            emailContato.setText(contato.getEmail());
        }

        return view;
    }







































}
