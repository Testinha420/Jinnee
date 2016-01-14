package br.com.smartside.jinnee.model;

/**
 * Created by smartside on 23/11/15.
 */
public class Promotions {

    public String name_promotion;
    public String description_promotion;
    public String old_price;
    public String new_price_promotion;
    public int photoId;

    public Promotions(String name_promotion, String description_promotion, String old_price, String new_price_promotion, int photoId) {
        this.name_promotion = name_promotion;
        this.description_promotion = description_promotion;
        this.old_price = old_price;
        this.new_price_promotion = new_price_promotion;
        this.photoId = photoId;
    }

}
