package com.flickr.photoalbum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.flickr.photoalbum.adapter.PhotoListAdapter;
import com.flickr.photoalbum.databinding.ActivityPhotoAlbumMainBinding;

public class PhotoAlbumActivity extends AppCompatActivity implements PhotoListAdapter.onPhotoItemClickListener {
    public static final String TAG = PhotoAlbumActivity.class.getSimpleName();
    PhotoAlbumViewModel photoAlbumViewModel ;
    private RecyclerView rv_photos;
    private PhotoListAdapter photoListAdapter;
    //data binding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoAlbumViewModel = ViewModelProviders.of(this).get(PhotoAlbumViewModel.class);
        setUpViews();
        observeViewModel();
    }

    public void setUpViews(){
        rv_photos = findViewById(R.id.rv_photos);
        photoListAdapter = new PhotoListAdapter(photoAlbumViewModel, this, this);
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

    @Override
    public void onPhotoClick(int position) {
        Log.d(TAG, "onPhotoClick: ->"+position);
    }
}
