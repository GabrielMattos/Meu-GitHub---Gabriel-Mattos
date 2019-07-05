package com.br.whatsapp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.br.whatsapp.fragment.ContatosFragment;
import com.br.whatsapp.fragment.ConversasFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {"CONVERSAS", "CONTATOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) { //retorna os fragments

        Fragment myFragment = null;

        switch (position) {
            case 0:
                myFragment = new ConversasFragment();
                break;
            case 1 :
                myFragment = new ContatosFragment();
                break;
        }

        return myFragment;
    }

    @Override
    public int getCount() { //retorna quantidade de abas

        return tituloAbas.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}
