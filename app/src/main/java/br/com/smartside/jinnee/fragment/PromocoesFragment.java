package br.com.smartside.jinnee.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.smartside.jinnee.R;
import br.com.smartside.jinnee.adapter.RVAdapter;
import br.com.smartside.jinnee.model.Promotions;

/**
 * Created by smartside on 23/11/15.
 */
public class PromocoesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    View rootView;
    private List<Promotions> promotions;
    private RecyclerView rv;


    public static PromocoesFragment newInstance(String param1) {
        PromocoesFragment fragment = new PromocoesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.promocoes_fragment, container, false);

        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        loadPromotionList();

        return rootView;
    }

    private void loadPromotionList() {

        promotions = new ArrayList<>();
        promotions.add(new Promotions("Óculos Azul", "Descrição do produto ou serviço com detalhe...","R$260,00","R$220,00", R.drawable.produto1));

        RVAdapter adapter = new RVAdapter(getActivity(), promotions);
        rv.setAdapter(adapter);

    }

}
