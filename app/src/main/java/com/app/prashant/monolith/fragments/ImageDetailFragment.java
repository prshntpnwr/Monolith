package com.app.prashant.monolith.fragments;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.prashant.monolith.R;
import com.app.prashant.monolith.galleryData.GalleryLoader;
import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener;
import com.hlab.fabrevealmenu.view.FABRevealMenu;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

public class ImageDetailFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, OnFABMenuSelectedListener {

    private static final String TAG = ImageDetailFragment.class.getSimpleName();

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_POSITION = "item_position";
    private static final String Monolith_SHARE_HASHTAG = " #MonolithApp";

    private int mItemPosition;
    private long mItemId;
    private Cursor mCursor;

    public ImageView imageView;
    public View mRootView;
    public Toolbar toolbar;

    public final int PERMISSION_REQUEST_CODE = 1000;

    public static ImageDetailFragment newInstance(long itemId, int position) {
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_ITEM_ID, itemId);
        arguments.putInt(ARG_ITEM_POSITION, position);
        ImageDetailFragment fragment = new ImageDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (!checkAppPermission()) {
                requestAppPermission();
            }
        }

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItemId = getArguments().getLong(ARG_ITEM_ID);
            mItemPosition = getArguments().getInt(ARG_ITEM_POSITION);
        }

        setHasOptionsMenu(true);
    }

    private boolean checkAppPermission() {
        int result = ContextCompat.checkSelfPermission(getContext().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestAppPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.SET_WALLPAPER,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE
        );
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_image_detail, container, false);
        toolbar = (Toolbar) mRootView.findViewById(R.id.detail_toolbar);

        final FloatingActionButton fab = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        final FABRevealMenu fabMenu = (FABRevealMenu) mRootView.findViewById(R.id.fabMenu);

        try {
            if (fabMenu != null) {
                fabMenu.bindAncherView(fab);
                fabMenu.setOnFABMenuSelectedListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        bindViews();
        setupToolbar();
        setSharedAnimation();
        return mRootView;
    }

    @Override
    public void onMenuItemSelected(View view) {
        int id = (int) view.getTag();
        if (id == R.id.fab_wallpaper) {

            String wallpaper = mCursor.getString(GalleryLoader.Query.COLUMN_IMAGE_PATH);
            final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());

            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    try {
                        wallpaperManager.setBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }

            };

            Picasso.with(getContext()).load(wallpaper).into(target);

            Toast.makeText(getActivity(), getResources().getString(R.string.wallpaper_set_successfully), Toast.LENGTH_SHORT).show();

        } else if (id == R.id.fab_image_share) {
            try {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(mCursor.getString(GalleryLoader.Query.COLUMN_IMAGE_PATH) + "\n\n"
                                + Monolith_SHARE_HASHTAG)
                        .getIntent(), getString(R.string.action_share)));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void bindViews() {
        if (mRootView == null) {
            return;
        }

        imageView = (ImageView) mRootView.findViewById(R.id.image);

        if (mCursor != null) {
            mRootView.setVisibility(View.VISIBLE);

            mCursor.moveToPosition(mItemPosition);
            Picasso.with(this.getContext()).load(mCursor.getString(GalleryLoader.Query.COLUMN_IMAGE_PATH))
                    .placeholder(R.color.photo_placeholder)
                    .error(R.color.primary_dark)
                    .into(imageView);

            Log.d("image goes here", mCursor.getString(GalleryLoader.Query.COLUMN_IMAGE_PATH));
        } else {
            mRootView.setVisibility(View.GONE);
        }
    }

    private void setupToolbar() {
        if (toolbar != null) {
            toolbar.setNavigationIcon(ContextCompat
                    .getDrawable(getActivity(), R.drawable.ic_back));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().supportFinishAfterTransition();
                }
            });

            toolbar.setTitle("");
        }
    }

    public void setSharedAnimation() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(imageView,
                    getResources().getString(R.string.transition_gallery_photo) + String.valueOf(mItemPosition));
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return GalleryLoader.newInstanceForItemId(getActivity(), mItemId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                // Start the postponed transition here
                ActivityCompat.startPostponedEnterTransition(getActivity());
                Log.d("HERE GOES TRANSITION---", "Starting transition");
                return true;
            }
        });

        if (!isAdded()) {
            if (cursor != null) {
                cursor.close();
            }
            return;
        }

        mCursor = cursor;
        if (mCursor != null && !mCursor.moveToFirst()) {
            Log.e(TAG, "Error reading item detail cursor");
            mCursor.close();
            mCursor = null;
        }

        bindViews();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursor = null;
        bindViews();
    }
}
