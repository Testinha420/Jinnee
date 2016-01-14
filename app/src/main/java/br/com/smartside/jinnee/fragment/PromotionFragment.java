package br.com.smartside.jinnee.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.smartside.jinnee.R;

/**
 * Created by smartside on 27/10/15.
 */
public class PromotionFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_promotion, container, false);

        return rootView;
    }

}
