package com.bignerdranch.android.PhotoGallery;

/**
 * Created with IntelliJ IDEA.
 * User: gd452
 * Date: 2013. 11. 18.
 * Time: 오후 4:17
 * To change this template use File | Settings | File Templates.
 */
public class GalleryItem {
    private String mCaption;
    private String mId;

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    private String mUrl;

    public String toString(){
        return mCaption;
    }
}
