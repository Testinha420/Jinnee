package br.com.smartside.jinnee.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.smartside.jinnee.MainActivity;
import br.com.smartside.jinnee.R;
import br.com.smartside.jinnee.activity.PlaceInfos;
import br.com.smartside.jinnee.model.Promotions;

/**
 * Created by smartside on 23/11/15.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PromotionViewHolder>{

    List<Promotions> promotions;
    Context context;

    public RVAdapter(Context context, List<Promotions> promotions){
        this.context = context;
        this.promotions = promotions;
    }

    @Override
    public int getItemCount() {
        return promotions.size();
    }

    @Override
    public void onBindViewHolder(final PromotionViewHolder promotionViewHolder, int i) {
        promotionViewHolder.promotionName.setText(promotions.get(i).name_promotion);
        promotionViewHolder.promotionDescription.setText(promotions.get(i).description_promotion);
        promotionViewHolder.promotionPhoto.setImageResource(promotions.get(i).photoId);
        promotionViewHolder.promotionOldPrice.setText(promotions.get(i).old_price);
        promotionViewHolder.promotionOldPrice.setPaintFlags(promotionViewHolder.promotionOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        promotionViewHolder.promotionNewPrice.setText(promotions.get(i).new_price_promotion);

        promotionViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, promotionViewHolder.promotionName.getText().toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, PlaceInfos.class);
                intent.putExtra("name_product", promotionViewHolder.promotionName.getText().toString());
                context.startActivity(intent);

            }
        });


    }

    public static class PromotionViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView promotionName;
        TextView promotionDescription;
        TextView promotionOldPrice;
        TextView promotionNewPrice;
        ImageView promotionPhoto;

        PromotionViewHolder(View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);
            promotionName = (TextView) itemView.findViewById(R.id.promotion_name);
            promotionDescription = (TextView) itemView.findViewById(R.id.promotion_description);
            promotionOldPrice = (TextView) itemView.findViewById(R.id.promotion_old_price);
            promotionNewPrice = (TextView) itemView.findViewById(R.id.promotion_new_price);
            promotionPhoto = (ImageView) itemView.findViewById(R.id.promotion_photo);

        }
    }

    @Override
    public PromotionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.promotion_item, viewGroup, false);
        PromotionViewHolder pvh = new PromotionViewHolder(v);
        return pvh;
    }

}