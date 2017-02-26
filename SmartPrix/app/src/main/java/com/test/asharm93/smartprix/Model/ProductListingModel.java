package com.test.asharm93.smartprix.Model;

/**
 * Created by asharm93 on 5/9/2016.
 */
public class ProductListingModel {
    String Id,Name,Category,Price,Brand,Img_Url;
    public ProductListingModel(String Id,String Name,String Category,String Price,String Brand,String Img_Url) {
        super();
        this.Id=Id;
        this.Name=Name;
        this.Category=Category;
        this.Price=Price;
        this.Brand=Brand;
        this.Img_Url=Img_Url;

    }

    public String getId(){
        return this.Id;
    }
    public String getName() {
        return this.Name;
    }
    public String getCategory(){
        return this.Category;
    }
    public String getPrice(){
        return this.Price;
    }
    public String getBrand(){
        return this.Brand;
    }
    public String getImg_Url(){
        return  this.Img_Url;
    }
    public String toString()
    {
        return Name;
    }
}
