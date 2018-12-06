package com.example.vanh1200.galleryex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context mContext;
    private RequestOptions mOptions;
    private ArrayList<Image> mImages;

    public ImageAdapter(ArrayList<Image> images) {
        mImages = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_item_layout,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(mImages.get(i));
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageSmall;
        private TextView mTextDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View itemView) {
            mImageSmall = itemView.findViewById(R.id.image_crop);
            mTextDate = itemView.findViewById(R.id.text_create_date);
        }

        public void bindData(Image image) {
            mOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.image_holder)
                    .error(R.drawable.image_holder);
            Glide.with(mContext)
                    .load(image.getPath())
                    .apply(mOptions)
                    .into(mImageSmall);
            mTextDate.setText(image.getDate());
        }
    }

    public void update(ArrayList<Image> images){
        mImages.addAll(images);
        notifyDataSetChanged();
    }
}
