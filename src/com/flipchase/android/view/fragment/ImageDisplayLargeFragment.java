
package com.flipchase.android.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flipchase.android.R;
import com.flipchase.android.extlibpro.subscaleview.SubsamplingScaleImageView;
import com.flipchase.android.view.activity.ImageDisplayActivity;

public class ImageDisplayLargeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.imagedisplay_large_fragment, container, false);
        ImageDisplayActivity ida =  (ImageDisplayActivity)getActivity();
        String selectedImageName = ida.getSelectedImageName();
        /*
        rootView.findViewById(com.example.testapp.R.id.next).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageDisplayActivity)getActivity()).next();
            }
        });
        */
        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)rootView.findViewById(R.id.imageView);
        /** What ever the method you call it will treat this as url **/
        imageView.setImageFile(selectedImageName);
        return rootView;
    }

}
