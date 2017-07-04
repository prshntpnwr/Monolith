package com.example.prashant.monolith.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.prashant.monolith.R;
import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AboutView view = AboutBuilder.with(this)
                .setPhoto(R.mipmap.profile_picture)
                .setCover(R.mipmap.profile_cover)
                .setName("Prashant Panwar")
                .setSubTitle("Mobile Developer")
                .setBrief("I'm warmed of mobile technologies. Ideas maker, curious and nature lover.")
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .addGooglePlayStoreLink("8002078663318221363")
                .addGitHubLink("user")
                .addFacebookLink("user")
                .addDribbbleLink("dribble")
                .addBitbucketLink("bucket")
                .addTwitterLink("")
                .addFiveStarsAction()
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .build();

//        setContentView(view);
//        this.setTitle("About");

        addContentView(view, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }
}
