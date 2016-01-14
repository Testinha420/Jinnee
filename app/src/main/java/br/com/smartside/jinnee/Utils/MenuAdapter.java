package br.com.smartside.jinnee.Utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.smartside.jinnee.R;
import br.com.smartside.jinnee.model.MenuItem;

/**
 * Created by smartside on 27/10/15.
 */
public class MenuAdapter extends ArrayAdapter<MenuItem> {

    Context context;
    int layoutResourceId;
    MenuItem data[] = null;
    private ImageView mSelectedIcon;
    int position;

    public MenuAdapter(Context context, int layoutResourceId, MenuItem[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    public int selectedPos = 0;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuHolder viewHolder;
        View rowView = convertView;
        this.position = position;

        if (rowView == null) {

            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            rowView = layoutInflater.inflate(layoutResourceId, null, true);
            viewHolder = new MenuHolder();

            viewHolder.menuTitle = (TextView) rowView.findViewById(R.id.title);
            viewHolder.menuIcon = (ImageView) rowView.findViewById(R.id.icon_button);
            viewHolder.circle = rowView.findViewById(R.id.circle);

            rowView.setTag(viewHolder);

        }else {
            viewHolder = (MenuHolder) rowView.getTag();
        }

        setItemState(viewHolder.menuIcon, viewHolder.circle, position == selectedPos);


        MenuItem menu_item = data[position];
        viewHolder.menuTitle.setText(menu_item.title);
        viewHolder.menuIcon.setImageResource(menu_item.image);

        return rowView;
    }

    public void setItemState(ImageView ic, View circle, Boolean selected){

        if (selected) {
            ic.setColorFilter(0xff261f53, PorterDuff.Mode.MULTIPLY);
            circle.setBackgroundResource(R.drawable.circle_white);
        } else {
            ic.clearColorFilter();
            circle.setBackgroundResource(R.drawable.circle);
        }
    }

    static class MenuHolder{
        ImageView menuIcon;
        TextView menuTitle;
        View circle;
    }

}
