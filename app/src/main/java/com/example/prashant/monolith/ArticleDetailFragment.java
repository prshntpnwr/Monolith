package com.example.prashant.monolith;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.prashant.monolith.articleData.ArticleLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener;
import com.hlab.fabrevealmenu.view.FABRevealMenu;

public class ArticleDetailFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> , OnFABMenuSelectedListener {

    private static final String TAG = ImageDetailFragment.class.getSimpleName();

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_POSITION = "item_position";
    private static final String Monolith_SHARE_HASHTAG = " #MonolithApp";

    public ArticleDetailFragment() {
    }

    private ColorDrawable mStatusBarColorDrawable;

    private View mPhotoContainerView;
    private ImageView mPhotoView;
    private boolean mIsCard = false;
    private int mStatusBarFullOpacityBottom;

    private NestedScrollView mScrollView;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private int mItemPosition;
    private long mItemId;
    private Cursor mCursor;
    private FloatingActionButton fab;
    private FABRevealMenu fabMenu;

    public View mRootView;
    public Toolbar toolbar;
    int defaultColor;
    int color;

    public static ArticleDetailFragment newInstance(long itemId, int position) {
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_ITEM_ID, itemId);
        arguments.putInt(ARG_ITEM_POSITION, position);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
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

        mIsCard = getResources().getBoolean(R.bool.detail_is_card);
        mStatusBarFullOpacityBottom = getResources().getDimensionPixelSize(
                R.dimen.detail_card_top_margin);

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
        mRootView = inflater.inflate(R.layout.fragment_article_detail, container, false);

        AdView mAdView = (AdView) mRootView.findViewById(R.id.adView);
        MobileAds.initialize(getContext(), getResources().getString(R.string.banner_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        toolbar = (Toolbar) mRootView.findViewById(R.id.detail_toolbar);

        mScrollView = (NestedScrollView) mRootView.findViewById(R.id.scrollview);

        mPhotoView = (ImageView) mRootView.findViewById(R.id.photo);
        mPhotoContainerView = mRootView.findViewById(R.id.photo_container);

        fab = (FloatingActionButton) mRootView.findViewById(R.id.fab);
        fabMenu = (FABRevealMenu) mRootView.findViewById(R.id.fabMenu);

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
        return mRootView;
    }

    private void bindViews() {
        if (mRootView == null) {
            return;
        }

        TextView titleView = (TextView) mRootView.findViewById(R.id.article_title);
        TextView bylineView = (TextView) mRootView.findViewById(R.id.article_byline);
        TextView bodyView = (TextView) mRootView.findViewById(R.id.article_body);

        Intent intent =  getActivity().getIntent();
        if(intent != null && intent.getExtras() != null){

            titleView.setText(intent.getStringExtra("title"));
            bylineView.setText(intent.getStringExtra("date"));
            bodyView.setText(intent.getStringExtra("description"));
            try {
                Glide.with(this.getContext())
                        .load(intent.getStringExtra("image"))
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model,
                                                           Target<GlideDrawable> target,
                                                           boolean isFromMemoryCache, boolean isFirstResource) {
                                Bitmap bitmap = ((GlideBitmapDrawable) resource.getCurrent()).getBitmap();
                                Palette palette = Palette.generate(bitmap);
                                defaultColor = 0xFF333333;
                                color = palette.getMutedColor(defaultColor);
                                mRootView.findViewById(R.id.meta_bar)
                                        .setBackgroundColor(color);
                                //lets try to use palette for fab
//                                fab.setBackgroundTintList(ColorStateList.valueOf(color));
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                    fabMenu.setBackgroundTintList(ColorStateList.valueOf(color));
//                                }
                                return false;
                            }
                        })
                        .into(mPhotoView);
            }catch (Exception e) {
                e.printStackTrace();
            }
        } else if (mCursor != null) {
            mRootView.setAlpha(0);
            mRootView.setVisibility(View.VISIBLE);
            mRootView.animate().alpha(1);

            mCursor.moveToPosition(mItemPosition);
            titleView.setText(mCursor.getString(ArticleLoader.Query.COLUMN_TITLE));
            bylineView.setText(mCursor.getString(ArticleLoader.Query.COLUMN_PUBLISH_DATE));
            bodyView.setText(mCursor.getString(ArticleLoader.Query.COLUMN_DESCRIPTION));

            try {
                Glide.with(this.getContext())
                        .load(mCursor.getString(ArticleLoader.Query.COLUMN_IMAGE_URL))
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model,
                                                           Target<GlideDrawable> target,
                                                           boolean isFromMemoryCache, boolean isFirstResource) {
                                Bitmap bitmap = ((GlideBitmapDrawable) resource.getCurrent()).getBitmap();
                                Palette palette = Palette.generate(bitmap);
                                defaultColor = 0xFF333333;
                                color = palette.getMutedColor(defaultColor);
                                mRootView.findViewById(R.id.meta_bar)
                                        .setBackgroundColor(color);
                                //lets try to use palette for fab
//                                fab.setBackgroundTintList(ColorStateList.valueOf(color));
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                    fabMenu.setBackgroundTintList(ColorStateList.valueOf(color));
//                                }
                                return false;
                            }
                        })
                        .into(mPhotoView);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            mRootView.setVisibility(View.GONE);
            // TODO: add empty layout
            titleView.setText("N/A");
            bylineView.setText("N/A" );
            bodyView.setText("N/A");
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
        return ArticleLoader.newInstanceForItemId(getActivity(), mItemId);
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

    @Override
    public void onMenuItemSelected(View view) {
        int id = (int) view.getTag();
        String url;

        Intent intent1 =  getActivity().getIntent();
        if(intent1 != null && intent1.getExtras() != null) {
            url = intent1.getStringExtra("link");
        } else {
            url = mCursor.getString(ArticleLoader.Query.COLUMN_LINK);
        }

        if (id == R.id.fab_custom_tab) {

            Intent intent = new Intent();
            //Add app as the referrer
            intent.putExtra(Intent.EXTRA_REFERRER,
                    Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + getContext().getPackageName()));

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            //tab color
            builder.setToolbarColor(color);
            //tab animation
            builder.setStartAnimations(this.getContext(), android.R.anim.slide_out_right, android.R.anim.slide_in_left);
            builder.setExitAnimations(this.getContext(), android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            //tab back button
            builder.setCloseButtonIcon(BitmapFactory.decodeResource(
                    getResources(), R.drawable.ic_back));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this.getContext(), Uri.parse(url));

        } else if (id == R.id.fab) {
            try {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(url + "\n\n"
                                + Monolith_SHARE_HASHTAG)
                        .getIntent(), getString(R.string.action_share)));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(getActivity(), "Shared Successfully! ", Toast.LENGTH_SHORT).show();
    }
}
