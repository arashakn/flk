package com.flickr.photoalbum.adapter;

import android.util.Log;
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
    onPhotoItemClickListener onPhotoItemClickListener;

    public PhotoListAdapter(PhotoAlbumViewModel photoAlbumViewModel , LifecycleOwner lifecycleOwner,onPhotoItemClickListener onPhotoItemClickListener ){
        photoAlbumViewModel.getImagesList().observe(lifecycleOwner, images -> {
            if(images!=null){
                data.clear();
                data.addAll(images);
                notifyDataSetChanged();
            }
        });
        this.onPhotoItemClickListener = onPhotoItemClickListener;
    }

    @NonNull
    @Override
    public PhotoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent,false);
        return new PhotoItemViewHolder(view,onPhotoItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoItemViewHolder holder, int position) {
        Image image = data.get(position);
        String imageURL ="";
        if(image.getImageUrls().thumb!=null && !image.getImageUrls().thumb.isEmpty()) {
            imageURL = image.getImageUrls().thumb;
            Log.d("Adapter + small", imageURL);

        }
        else{
            imageURL = image.getImageUrls().large;
            Log.d("Adapter + large", imageURL);

        }



        Picasso.with(holder.iv_thumb.getContext()).load(imageURL)
                .fit()
                .centerCrop()
                .into(holder.iv_thumb)
        ;

//        Picasso.with(holder.iv_thumb.getContext()).load(imageURL)
//                .fit()
//                .centerCrop()
//                .placeholder(holder.iv_thumb.getContext()
//                        .getResources().getDrawable(R.drawable.ic_image)).
//                error(holder.iv_thumb.getContext().getResources().getDrawable(R.drawable.ic_image)).into(holder.iv_thumb);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class PhotoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_image_link;
        ImageView iv_thumb;
        onPhotoItemClickListener onPhotoItemClickListener;

        public PhotoItemViewHolder(@NonNull View itemView, onPhotoItemClickListener onPhotoItemClickListener) {
            super(itemView);
            tv_image_link = itemView.findViewById(R.id.tv_image_link);
            iv_thumb = itemView.findViewById(R.id.iv_thumb);
            this.onPhotoItemClickListener = onPhotoItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPhotoItemClickListener.onPhotoClick(getAdapterPosition());
        }
    }

    public interface onPhotoItemClickListener{
        public void onPhotoClick(int position);
    }



}
