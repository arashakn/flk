package com.flickr.photoalbum.service.model;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("ImageUrls")
    ImageUrls imageUrls;

    @SerializedName("OriginalHeight")
    int originalHeight;

    @SerializedName("OriginalWidth")
    int originalWidth;

    public ImageUrls getImageUrls() {
        return imageUrls;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }
}
