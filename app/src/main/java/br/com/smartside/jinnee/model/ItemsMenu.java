package br.com.smartside.jinnee.model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import br.com.smartside.jinnee.R;

/**
 * Created by smartside on 27/07/15.
 */
public class ItemsMenu {

    private static LinkedHashMap<String, Integer> codeHash;

    public static LinkedHashMap<String, Integer> getAllItems() {

        if(codeHash == null){

            codeHash  = new LinkedHashMap<String, Integer>();

            codeHash.put("Mapa", R.drawable.ic_buy);
            codeHash.put("Promoções", R.drawable.ic_buy);
            codeHash.put("Lugares", R.drawable.ic_buy);
            codeHash.put("Perfil", R.drawable.ic_buy);
            codeHash.put("Configurações", R.drawable.ic_buy);

        }

        return codeHash;
    }

    public static int getItemIndex(String item_code){

        List keys = new ArrayList(getAllItems().keySet());

        for (int i = 0; i < keys.size(); i++) {
            if( keys.get(i) == item_code){
                return i;
            }
        }

        return 1;
    }
}
