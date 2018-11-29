package com.example.vanh1200.galleryex;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 1;
    private static final int TWO_COLUMNS = 2;
    private static final String DATE_FORMAT = "dd/MM/yyyy HH : MM : SS";
    private String[] mPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private RecyclerView mRecyclerImage;
    private ArrayList<Image> mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermission(mPermissions)) {
            initViews();
        }
    }

    private void initViews() {
        mRecyclerImage = findViewById(R.id.recycler_image);
        ImageAdapter imageAdapter = new ImageAdapter(getImagePath(this));
        mRecyclerImage.setLayoutManager(new GridLayoutManager(this, TWO_COLUMNS));
        mRecyclerImage.setAdapter(imageAdapter);
    }

    private boolean checkPermission(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            {
                for (String permission : permissions) {
                    if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(permissions, PERMISSION_READ_EXTERNAL_STORAGE);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE:
                if (checkPermission(permissions)) {
                    initViews();
                } else {
                    finish();
                }
                break;
            default:
                break;
        }
    }

    public ArrayList<Image> getImagePath(Context context) {
        mImages = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(uri,
                null, null, null, null);
        cursor.moveToFirst();
        int indexPath = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        int indexCreatedDate = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
        if (cursor != null) {
            do {
                String path = cursor.getString(indexPath);
                long createdDate = cursor.getLong(indexCreatedDate);
                Image image = new Image(getDate(createdDate), path);
                mImages.add(image);
            } while (cursor.moveToNext());
        }
        return mImages;
    }

    public String getDate(long milliSeconds) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(new Date(milliSeconds));
    }

}
