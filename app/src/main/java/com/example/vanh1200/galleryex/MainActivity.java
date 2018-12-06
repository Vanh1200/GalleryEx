package com.example.vanh1200.galleryex;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements LoadImageAsync.LoadImageListener {
    private static final String TAG = "MainActivity";
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 1;
    private static final int TWO_COLUMNS = 2;
    private static final String DATE_FORMAT = "dd/MM/yyyy HH : MM : SS";
    private String[] mPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private RecyclerView mRecyclerImage;
    private ArrayList<Image> mImages;
    private ImageAdapter mImageAdapter;

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
        mImages = new ArrayList<>();
        mImageAdapter = new ImageAdapter(mImages);
        mRecyclerImage.setLayoutManager(new GridLayoutManager(this, TWO_COLUMNS));
        mRecyclerImage.setAdapter(mImageAdapter);
        LoadImageAsync loadImageAsync = new LoadImageAsync(this, this);
        loadImageAsync.execute();
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


    public static Intent getMainIntent(Context context){
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void loadImageResult(ArrayList<Image> images) {
        mImageAdapter.update(images);
    }
}
