package com.example.prashant.monolith;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.prashant.monolith.data.GalleryLoader;
import com.hlab.fabrevealmenu.model.FABMenuItem;
import com.hlab.fabrevealmenu.view.FABRevealMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = ImageDetailFragment.class.getSimpleName();

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_POSITION = "item_position";

    private ArrayList<FABMenuItem> items;
    //private String[] mDirectionStrings = {"Direction - LEFT", "Direction - UP"};
   // private Direction currentDirection = Direction.LEFT;

    public ImageDetailFragment() {
    }

    private int mItemPosition;
    private long mItemId;
    private Cursor mCursor;
    private String image_url;

   // public ImageView imageView;
    public View mRootView;
    public Toolbar toolbar;

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

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItemId = getArguments().getLong(ARG_ITEM_ID);
            mItemPosition = getArguments().getInt(ARG_ITEM_POSITION);
        }

        setHasOptionsMenu(true);
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
        final FABRevealMenu fabMenu = (FABRevealMenu) mRootView.findViewById(R.id.fab_menu);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: add image/details to db
            }
        });

        bindViews();
        setupToolbar();
        return mRootView;
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        mCursor.close();
//    }

    private void bindViews() {
        if (mRootView == null) {
            return;
        }

        ImageView imageView = (ImageView) mRootView.findViewById(R.id.image);

        if (mCursor != null) {
            mRootView.setVisibility(View.VISIBLE);

            //cause screen refresh
//            mRootView.setAlpha(0);
//            mRootView.animate().alpha(1);

//            for explicit intent
//            Picasso.with(this.getContext()).load(getActivity().getIntent().getStringExtra("URL"))
//                    .placeholder(R.color.accent)
//                    .error(R.color.primary_dark)
//                    .into(imageView);

            mCursor.moveToPosition(mItemPosition);
            Picasso.with(this.getContext()).load(mCursor.getString(GalleryLoader.Query.COLUMN_IMAGE_PATH))
                    .placeholder(R.color.accent)
                    .error(R.color.primary_dark)
                    .into(imageView);

            Log.d("image goes here", mCursor.getString(GalleryLoader.Query.COLUMN_IMAGE_PATH));
        } else {
            mRootView.setVisibility(View.GONE);
            //cause a purplish hue
//           imageView.setColorFilter(R.color.photo_placeholder);
        }
    }

    private void setupToolbar() {
        if (toolbar != null) {
            toolbar.setNavigationIcon(ContextCompat
                    .getDrawable(getActivity(), R.drawable.ic_back));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            toolbar.setTitle("");
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return GalleryLoader.newInstanceForItemId(getActivity(), mItemId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
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
