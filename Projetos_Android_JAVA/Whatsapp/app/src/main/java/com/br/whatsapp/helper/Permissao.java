package com.br.whatsapp.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes) {

        if(Build.VERSION.SDK_INT >= 23) {

            List<String> listaPermissoes = new ArrayList<String>();

            /*percorre as permissoes passadas, verificando uma a uma se já tem a permissao liberada
             */
            for(String permissao : permissoes) {
                Boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED; //verifica se pra activity tem a permissao necessaria ----- // Se é igual ao nível do pacote que instá instalado o aplicativo

                if(!validaPermissao) {
                    listaPermissoes.add(permissao);
                }
            }

            if(listaPermissoes.isEmpty()) { //Se a lista estiver vazia, não é necessário solicitar permissão
                return true;
            }

            String[] novasPermissoes = new String[listaPermissoes.size()]; //array de strings
            listaPermissoes.toArray(novasPermissoes);

            //solicitar permissão
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
        }

        return true;
    }














}
