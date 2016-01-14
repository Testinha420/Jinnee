package br.com.smartside.jinnee;


import android.app.FragmentManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.com.smartside.jinnee.Utils.MenuAdapter;
import br.com.smartside.jinnee.fragment.MapFragment;
import br.com.smartside.jinnee.fragment.PromotionFragment;
import br.com.smartside.jinnee.model.MenuItem;

public class MainActivity extends AppCompatActivity {

    private MenuAdapter adapter;
    private View mActionBarView, circle, view, circle_white, circle_selected, menu_view;
    private ImageButton open_menu;
    private GridView menu;
    private RelativeLayout layout_buttons;
    private float scale = 0;
    private Boolean isOpen = false;
    private TextView toolbar_title, menu_title;
    private String title;
    private ImageView icon, mSelectedIcon;
    final int sdk = android.os.Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        mActionBarView = getLayoutInflater().inflate(R.layout.my_toolbar, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setElevation(0);

        icon = (ImageView) findViewById(R.id.icon_button);

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        menu_title = (TextView) findViewById(R.id.menu_title);

        menu = (GridView) findViewById(R.id.gridview);
        layout_buttons = (RelativeLayout) findViewById(R.id.layout_buttons);


        createMenu();

        open_menu = (ImageButton) findViewById(R.id.open_menu);
        open_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpandOrRetractMenu();
            }
        });

        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int len = menu.getChildCount();
                adapter.selectedPos = position;
                for (int i = 0; i < len; i++) {
                    View v = menu.getChildAt(i);
                    ImageView ic = (ImageView) v.findViewById(R.id.icon_button);
                    circle = v.findViewById(R.id.circle);
                    adapter.setItemState(ic, circle, i == position);
                }

                ExpandOrRetractMenu();

                switch (position) {
                    case 0:
                        // MAPA
                        setFragment(new MapFragment());
                        toolbar_title.setText("Mapa");
                        break;
                    case 1:
                        // PROMOÇÕES
                        setFragment(new PromotionFragment());
                        toolbar_title.setText("Todas as promoções");
                        break;
                    case 2:
                        // LUGARES
                        toolbar_title.setText("Lista de Lugares");
                        break;
                    case 3:
                        // PERFIL
                        toolbar_title.setText("Perfil");
                        break;
                    case 4:
                        // CONFIGURAÇÕES
                        toolbar_title.setText("Configurações");
                        break;
                }
            }
        });
    }

    private void ExpandOrRetractMenu() {

        if (isOpen) {
            toolbar_title.setVisibility(View.VISIBLE);
            menu_title.setVisibility(View.GONE);

            SlideToAbove();
            isOpen = false;
        } else {
            toolbar_title.setVisibility(View.GONE);
            menu_title.setVisibility(View.VISIBLE);

            SlideToDown();
            isOpen = true;
        }

    }

    private void createMenu() {

        MenuItem menu_data[] = new MenuItem[]{
            new MenuItem("Mapa", R.drawable.ic_map),
            new MenuItem("Promoções", R.drawable.ic_buy),
            new MenuItem("Lugares", R.drawable.ic_places),
            new MenuItem("Perfil", R.drawable.ic_profile),
            new MenuItem("Configurações", R.drawable.ic_config)
        };

        adapter = new MenuAdapter(this, R.layout.menu_layout, menu_data);
        menu.setAdapter(adapter);

        setFragment(new MapFragment());
        toolbar_title.setText("Mapa");

        int leg = adapter.getCount();

        if(leg == 0){

            View v = menu.getChildAt(0);

            ImageView ic = (ImageView) v.findViewById(R.id.icon_button);
            ic.setColorFilter(0xff261f53, PorterDuff.Mode.MULTIPLY);

            circle = v.findViewById(R.id.circle);
            circle.setBackgroundResource(R.drawable.circle_white);

        }


    }

    public void setFragment(android.support.v4.app.Fragment fragment){
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        /*MenuItem open_menu = menu.add("Open Menu");
        open_menu.setIcon(R.drawable.open_menu);
        open_menu.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);

        open_menu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Toast.makeText(MainActivity.this, "teste", Toast.LENGTH_SHORT).show();

                return true;
            }
        });*/
        return true;
    }

    private float getScale() {

        if (scale == 0)
            scale  = getResources().getDisplayMetrics().densityDpi / 160f;
        return scale;
    }

    public float dpToPx(float dp) {
        return dp * getScale();
    }

    public void SlideToAbove() {

        final int initialHeight = layout_buttons.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    layout_buttons.setVisibility(View.GONE);
                }else{
                    layout_buttons.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    layout_buttons.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / layout_buttons.getContext().getResources().getDisplayMetrics().density));
        layout_buttons.startAnimation(a);

    }

    public void SlideToDown() {

        isOpen = true;

        layout_buttons.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = layout_buttons.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        layout_buttons.getLayoutParams().height = 1;
        layout_buttons.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                layout_buttons.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                layout_buttons.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / layout_buttons.getContext().getResources().getDisplayMetrics().density));
        layout_buttons.startAnimation(a);

    }

}
