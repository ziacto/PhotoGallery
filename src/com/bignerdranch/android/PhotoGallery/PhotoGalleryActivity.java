package com.bignerdranch.android.PhotoGallery;

import android.support.v4.app.Fragment;

public class PhotoGalleryActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new PhotoGalleryFragment();
    }
}
