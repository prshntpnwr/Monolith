package com.example.prashant.monolith;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment{

    public ImageFragment() {

    }

    public ImageView imageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        imageView = (ImageView)rootView.findViewById(R.id.image);


        return rootView;
    }

//    public void bindView() {
//
//        Intent intent = getActivity().getIntent();
//
//        if (intent.hasExtra("image")) {
//
//            Picasso.with(getActivity())
//                    .load()
//                    .into(imageView);
//
//        }
//    }
}
