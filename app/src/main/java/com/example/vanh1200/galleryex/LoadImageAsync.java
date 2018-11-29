package com.example.vanh1200.galleryex;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoadImageAsync extends AsyncTask<Void, Void, ArrayList<Image>> {
    private ArrayList<Image> mImages;
    private static final String DATE_FORMAT = "dd/MM/yyyy HH : MM : SS";
    private LoadImageListener mListener;
    private Context mContext;

    public LoadImageAsync(Context context, LoadImageListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    protected ArrayList<Image> doInBackground(Void... voids) {
        mImages = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(uri,
                null, null, null, null);
        cursor.moveToFirst();
        int indexPath = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        int indexCreatedDate = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
        if(cursor == null)
            return mImages;
        while(!cursor.isAfterLast()){
            String path = cursor.getString(indexPath);
            long createdDate = cursor.getLong(indexCreatedDate);
            Image image = new Image(getDate(createdDate), path);
            mImages.add(image);
            cursor.moveToNext();
        }
        cursor.close();
        return mImages;
    }

    @Override
    protected void onPostExecute(ArrayList<Image> images) {
        super.onPostExecute(images);
        mListener.loadImageResult(mImages);
    }

    public String getDate(long milliSeconds) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(new Date(milliSeconds));
    }

    public interface LoadImageListener{
        void loadImageResult(ArrayList<Image> images);
    }
}
