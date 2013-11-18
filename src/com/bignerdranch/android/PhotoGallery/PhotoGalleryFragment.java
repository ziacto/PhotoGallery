package com.bignerdranch.android.PhotoGallery;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: gd452
 * Date: 2013. 11. 16.
 * Time: 오전 10:47
 * To change this template use File | Settings | File Templates.
 */
public class PhotoGalleryFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";
    ArrayList<GalleryItem> mItems;
    private GridView mGridView;

    ThumbnailDownloader<ImageView> mThumbnailThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate is called~~~~~~~~~~~~~~~");
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setRetainInstance(true);
        new FetchItemsTask().execute();

        mThumbnailThread = new ThumbnailDownloader<ImageView>(new Handler());
        mThumbnailThread.setListener(new ThumbnailDownloader.Listener<ImageView>() {
            @Override
            public void onThumbnailDownloaded(ImageView imageView, Bitmap thumbnail) {
                if(isVisible()){
                    imageView.setImageBitmap(thumbnail);
                }
            }
        });
        mThumbnailThread.start();
        mThumbnailThread.getLooper();
        Log.i(TAG, "Background Thread started");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView is called~~~~~~~~~~~~~~~~~~");
        View v = inflater.inflate(R.layout.activity_photo_gallery, container, false);
        mGridView = (GridView) v.findViewById(R.id.gridView);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        mThumbnailThread.quit();
        Log.i(TAG, "Background thread destroyed.................");
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestoyView is called~~~~~~~~~~~~~~~~~~");
        super.onDestroyView();    //To change body of overridden methods use File | Settings | File Templates.
        mThumbnailThread.clearQueue();
    }

    void setupAdapter() {
        if (getActivity() == null || mGridView == null) return;

        if (mItems != null) {
//            mGridView.setAdapter(new ArrayAdapter<GalleryItem>(getActivity(), android.R.layout.simple_gallery_item, mItems));
            mGridView.setAdapter(new GalleryItemAdapter(mItems));
        } else {
            mGridView.setAdapter(null);
        }
    }

    /**
     * Third parameter is the type of result produced by your AsyncTask.
     * This is the value returned by doInBackground, as well as the value of onPostExecute's input parameter.
     */
    private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<GalleryItem>> {
        @Override
        protected ArrayList<GalleryItem> doInBackground(Void... params) {
//            try{
//                String result = new FlickrFetchr().getUrl("http://www.google.com");
//                Log.i(TAG, "Fetched contents on URL: "+ result);
//            }catch(IOException ioe){
//                Log.e(TAG, "Failed to fetch URL: ", ioe);
//            }

            return new FlickrFetchr().fetchItems();
        }

        /**
         * This method is run on the main thread, not the background thread, so it's safe to update UI within it.
         *
         */
        @Override
        protected void onPostExecute(ArrayList<GalleryItem> items) {
            mItems = items;
            setupAdapter();
        }
    }

    private class GalleryItemAdapter extends ArrayAdapter<GalleryItem> {
        public GalleryItemAdapter(ArrayList<GalleryItem> items) {
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "getView is called~~~~~~");
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.gallery_item, parent, false);
            }

            ImageView imageView = (ImageView) convertView.findViewById(R.id.gallery_item_imageView);
            imageView.setImageResource(R.drawable.brian_up_close);

            GalleryItem item = getItem(position);
            mThumbnailThread.queueThumbnail(imageView, item.getUrl());

            return convertView;
        }
    }
}
