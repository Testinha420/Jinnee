package br.com.smartside.jinnee.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import br.com.smartside.jinnee.R;
import br.com.smartside.jinnee.fragment.ProdutosListFragment;
import br.com.smartside.jinnee.fragment.PromocoesFragment;

/**
 * Created by smartside on 23/11/15.
 */
public class PlaceInfos extends AppCompatActivity {

    static final int NUM_ITEMS = 2;

    PlaceInfoAdapter mAdapter;
    ViewPager vp;
    TabLayout tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_place_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detalhes do lugar");

        vp = (ViewPager) findViewById(R.id.view_pager_product);
        tablayout = (TabLayout) findViewById(R.id.tabs_product);

        mAdapter = new PlaceInfoAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);

        tablayout.setupWithViewPager(vp);
        tablayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));

    }

    public static class PlaceInfoAdapter extends FragmentPagerAdapter {

        public SparseArray<Fragment> registeredFragments = new SparseArray<>();

        public PlaceInfoAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    Fragment frag1 = PromocoesFragment.newInstance("promocoes_fragment");
                    registeredFragments.put(position, frag1);
                    return frag1;
                case 1:
                    Fragment frag2 = ProdutosListFragment.newInstance("produtos_list_fragment");
                    registeredFragments.put(position, frag2);
                    return frag2;
            }

            return null;

        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "PROMOÇÕES";
                case 1:
                    return "PRODUTOS";
            }
            return "";
        }
    }

}
