package com.bignerdranch.android.PhotoGallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created with IntelliJ IDEA.
 * User: gd452
 * Date: 2013. 11. 16.
 * Time: 오전 10:47
 * To change this template use File | Settings | File Templates.
 */
public class PhotoGalleryFragment extends Fragment {
    private GridView mGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_photo_gallery, container, false);
        mGridView = (GridView) v.findViewById(R.id.gridView);

        return v;
    }
}
