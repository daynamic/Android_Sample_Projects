package com.test.asharm93.smartprix.Model;

/**
 * Created by asharm93 on 5/10/2016.
 */
public class NearByShopModel {
    String Store_logo,price;
    public NearByShopModel(String Store_logo,String price) {
        super();
        this.Store_logo=Store_logo;
        this.price=price;

    }
    public String getStore_logo() {
        return this.Store_logo;
    }
    public String getprice() {
        return this.price;
    }
    public String toString()
    {
        return price;
    }
}
