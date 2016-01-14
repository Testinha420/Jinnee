package br.com.smartside.jinnee.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.smartside.jinnee.R;

/**
 * Created by smartside on 23/11/15.
 */
public class ProdutosListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    View rootView;

    public static ProdutosListFragment newInstance(String param1) {
        ProdutosListFragment fragment = new ProdutosListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.produtos_list_fragment, container, false);

        return rootView;
    }

}

