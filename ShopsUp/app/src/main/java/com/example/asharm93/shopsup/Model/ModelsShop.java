package com.example.asharm93.shopsup.Model;

/**
 * Created by asharm93 on 3/5/17.
 */

public class ModelsShop {
    private String title;
    private String description;
    private String thumbnail_image;
    private String url;

    public ModelsShop(String title, String description, String thumbnail_image, String url) {
        this.title = title;
        this.description = description;
        this.thumbnail_image=thumbnail_image;
        this.url=url;
    }
    public ModelsShop() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
