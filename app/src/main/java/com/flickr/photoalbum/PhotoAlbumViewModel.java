package com.flickr.photoalbum;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.flickr.photoalbum.service.PhotoManager;
import com.flickr.photoalbum.service.model.Image;

import java.util.ArrayList;

public class PhotoAlbumViewModel extends ViewModel {

    MutableLiveData<ArrayList<Image>> imagesList = new MutableLiveData<>();
    MutableLiveData<Boolean> loading = new MutableLiveData<>();
    MutableLiveData<Boolean> error = new MutableLiveData<>();

    public PhotoAlbumViewModel(){
         ArrayList<Image> images = PhotoManager.getInstance().getImages();
         if(images.size() == 0){
             error.setValue(true);
         }
         else{
             error.setValue(false);
             imagesList.setValue(images);
         }
    }

    public MutableLiveData<ArrayList<Image>> getImagesList() {
        return imagesList;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }
}
