package cs478.project3c.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Phone implements Serializable {

    // Properties for main activity list
    private String name;

    // Properties for phone pictures
    private Integer pictureResource;

    // Properties for website
    private String url;

    public Phone(String name, Integer pictureResource, String url) {
        this.name = name;
        this.pictureResource = pictureResource;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public Integer getPictureResource() {
        return pictureResource;
    }

    public String getUrl() {
        return url;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
