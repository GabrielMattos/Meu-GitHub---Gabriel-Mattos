package com.br.whatsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.br.whatsapp.R;
import com.br.whatsapp.model.Conversa;

import java.util.ArrayList;
import java.util.List;

public class ConversaAdapter  extends ArrayAdapter<Conversa> {

    private ArrayList<Conversa> conversas;
    private Context context;

    public ConversaAdapter(Context c, ArrayList<Conversa> objects) {
        super(c, 0, objects);
        this.conversas = objects;
        this.context = c;
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        //verifica se a lista está preenchida
        if(conversas != null) {

            //Inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //monta a view a partir do xml
            view = inflater.inflate(R.layout.lista_conversas, parent, false);

            //recupera elemento para exibição
            TextView nome = view.findViewById(R.id.textview_Titulo);
            TextView ultimaMensagem = view.findViewById(R.id.textview_Subtitulo);

            Conversa conversa = conversas.get(position);
            nome.setText(conversa.getNome());
            ultimaMensagem.setText(conversa.getMensagem());
        }

        return view;
    }*/
}
