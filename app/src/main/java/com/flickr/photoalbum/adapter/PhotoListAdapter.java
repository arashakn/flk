package com.flickr.photoalbum.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.flickr.photoalbum.PhotoAlbumViewModel;
import com.flickr.photoalbum.R;
import com.flickr.photoalbum.service.model.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoItemViewHolder> {
    List<Image> data = new ArrayList<>();

    public PhotoListAdapter(PhotoAlbumViewModel photoAlbumViewModel , LifecycleOwner lifecycleOwner){
        photoAlbumViewModel.getImagesList().observe(lifecycleOwner, images -> {
            if(images!=null){
                data.clear();
                data.addAll(images);
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public PhotoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent,false);
        return new PhotoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoItemViewHolder holder, int position) {
        Image image = data.get(position);

        if(image.getImageUrls().thumb!=null && !image.getImageUrls().thumb.isEmpty()) {
            Picasso.with(holder.iv_thumb.getContext()).load(image.getImageUrls().thumb).into(holder.iv_thumb);
        }
        else{
            Picasso.with(holder.iv_thumb.getContext()).load(image.getImageUrls().large).into(holder.iv_thumb);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class PhotoItemViewHolder extends RecyclerView.ViewHolder{
        TextView tv_image_link;
        ImageView iv_thumb;
        public PhotoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_image_link = itemView.findViewById(R.id.tv_image_link);
            iv_thumb = itemView.findViewById(R.id.iv_thumb);
        }
    }



}
