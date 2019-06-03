package com.br.whatsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
            LayoutInflater inflater = context.getSystemService()
        }

        return view;

    }
}
