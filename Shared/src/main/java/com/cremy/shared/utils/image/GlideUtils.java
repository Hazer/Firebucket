package com.cremy.shared.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by remychantenay on 20/05/2016.
 */
public class GlideUtils {

    /**
     * Allows to clear a given ImageView
     * Note: Usefull if used in RecyclerViews/ListViews and so on
     * @param _imageView
     */
    public static void clearImageView(final ImageView _imageView) {
        Glide.clear(_imageView);
    }


    /**
     * Allows to load a CIRCLE image with Glide
     * @param _context
     * @param _imageView
     * @param _imageUrl
     * @param _placeHolderResource
     */
    public static void loadCircleImage(final Context _context,
                                       final ImageView _imageView,
                                       final String _imageUrl,
                                       final int _placeHolderResource) {
        getBitmapLoader(_context, _imageUrl, _placeHolderResource).centerCrop().into(
                new BitmapImageViewTarget(_imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(_context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        _imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    /**
     * Allows to load an image with Glide
     * @param _context
     * @param _imageView
     * @param _imageUrl
     * @param _placeHolderResource
     */
    public static void loadImage(final Context _context,
                                 final ImageView _imageView,
                                 final String _imageUrl,
                                 final int _placeHolderResource) {
        getBitmapLoader(_context, _imageUrl, _placeHolderResource)
                .centerCrop()
                .into(_imageView);
    }

    /**
     * Allows to load a RESIZED image with Glide
     * @param _context
     * @param _imageView
     * @param _imageUrl
     * @param _placeHolderResource
     * @param _width
     * @param _height
     */
    public static void loadResizedImage(final Context _context,
                                        final ImageView _imageView,
                                        final String _imageUrl,
                                        final int _placeHolderResource,
                                        final int _width,
                                        final int _height) {
        getBitmapLoader(_context, _imageUrl, _placeHolderResource)
                .override(_width, _height)
                .into(_imageView);
    }



    private static BitmapRequestBuilder<String, Bitmap> getBitmapLoader(Context _context,
                                                                        final String _imageUrl,
                                                                        final int _placeHolderResource) {
        if (_placeHolderResource != 0) {
            return Glide.with(_context).
                    load(_imageUrl).
                    asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.ALL).
                    placeholder(_placeHolderResource).
                    error(_placeHolderResource).
                    dontTransform().
                    dontAnimate();
        } else {
            return Glide.with(_context).
                    load(_imageUrl).
                    asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.ALL).
                    dontTransform().
                    dontAnimate();
        }
    }
}