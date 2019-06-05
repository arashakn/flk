package com.flickr.photoalbum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.flickr.photoalbum.adapter.PhotoListAdapter;

public class PhotoAlbumActivity extends AppCompatActivity {
    PhotoAlbumViewModel photoAlbumViewModel ;
    private RecyclerView rv_photos;
    private PhotoListAdapter photoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album_main);
        photoAlbumViewModel = ViewModelProviders.of(this).get(PhotoAlbumViewModel.class);
        setUpViews();
        observeViewModel();
    }

    public void setUpViews(){
        rv_photos = findViewById(R.id.rv_photos);
        photoListAdapter = new PhotoListAdapter(photoAlbumViewModel, this);
        rv_photos.setAdapter(photoListAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_photos.setLayoutManager(mLayoutManager);

    }

    public void observeViewModel(){
        photoAlbumViewModel.getImagesList().observe(this, images -> {
            if(images!=null){
            }
        });
    }

    public  static Intent getIntent(Context context){
        return new Intent(context, PhotoAlbumActivity.class);
    }
}
