package com.flickr.photoalbum.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flickr.photoalbum.PhotoAlbumViewModel;
import com.flickr.photoalbum.R;
import com.flickr.photoalbum.service.model.Image;
import com.squareup.picasso.Callback;
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
        holder.bind(position,image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class PhotoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_image_link;
        ImageView iv_thumb;
        onPhotoItemClickListener onPhotoItemClickListener;

        private Details details;

        public PhotoItemViewHolder(@NonNull View itemView, onPhotoItemClickListener onPhotoItemClickListener) {
            super(itemView);
            details = new Details();
            tv_image_link = itemView.findViewById(R.id.tv_image_link);
            iv_thumb = itemView.findViewById(R.id.iv_thumb);
            this.onPhotoItemClickListener = onPhotoItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPhotoItemClickListener.onPhotoClick(getAdapterPosition());
        }

        Details getItemDetails() {
            return details;
        }

        public void bind(int position, Image image){
            String imageURL ="";
            if(image.getImageUrls().thumb!=null && !image.getImageUrls().thumb.isEmpty()) {
                imageURL = image.getImageUrls().thumb;
            }
            else{
                imageURL = image.getImageUrls().large;
            }

            Picasso.with(iv_thumb.getContext().getApplicationContext()).load(imageURL)
                    .fit()
                    .centerCrop()
                    .error(iv_thumb.getContext().getResources().getDrawable(R.drawable.ic_image))
                    .into(iv_thumb);
        }

    }

    public interface onPhotoItemClickListener{
        public void onPhotoClick(int position);
    }

    static class Details extends ItemDetailsLookup.ItemDetails<Long> {

        long position;

        Details() {
        }

        @Override
        public int getPosition() {
            return (int) position;
        }

        @Nullable
        @Override
        public Long getSelectionKey() {
            return position;
        }

        @Override
        public boolean inSelectionHotspot(@NonNull MotionEvent e) {
            return true;
        }
    }

    static class KeyProvider extends ItemKeyProvider<Long> {

        KeyProvider(RecyclerView.Adapter adapter) {
            super(ItemKeyProvider.SCOPE_MAPPED);
        }

        @Nullable
        @Override
        public Long getKey(int position) {
            return (long) position;
        }

        @Override
        public int getPosition(@NonNull Long key) {
            long value = key;
            return (int) value;
        }
    }

    static class DetailsLookup extends ItemDetailsLookup<Long> {

        private RecyclerView recyclerView;

        DetailsLookup(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Nullable
        @Override
        public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                if (viewHolder instanceof PhotoItemViewHolder) {
                    return ((PhotoItemViewHolder) viewHolder).getItemDetails();
                }
            }
            return null;
        }
    }

    static class Predicate extends SelectionTracker.SelectionPredicate<Long> {

        @Override
        public boolean canSetStateForKey(@NonNull Long key, boolean nextState) {
            return true;
        }

        @Override
        public boolean canSetStateAtPosition(int position, boolean nextState) {
            return true;
        }

        @Override
        public boolean canSelectMultiple() {
            return true;
        }
    }


}
