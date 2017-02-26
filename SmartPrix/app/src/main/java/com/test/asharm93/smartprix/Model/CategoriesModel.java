package com.test.asharm93.smartprix.Model;

/**
 * Created by asharm93 on 5/8/2016.
 */
public class CategoriesModel {
    String Name;
    public CategoriesModel(String Name) {
        super();
        this.Name=Name;

    }
    public String getName() {
        return this.Name;
    }
    public String toString()
    {
        return Name;
    }

}
