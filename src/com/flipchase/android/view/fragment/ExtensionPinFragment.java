package com.flipchase.android.view.fragment;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flipchase.android.R;
import com.flipchase.android.extlibpro.extension.views.PinView;
import com.flipchase.android.view.activity.ImageDisplayActivity;

public class ExtensionPinFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.imagedisplay_large_fragment, container, false);
        ImageDisplayActivity ida =  (ImageDisplayActivity)getActivity();
        String selectedImageName = ida.getSelectedImageName();
        
        PinView imageView = (PinView)rootView.findViewById(R.id.imageView);
        imageView.setImageFile(selectedImageName);
        imageView.setPin(new PointF(600f, 600f));
        /*
        PinView imageView = (PinView)rootView.findViewById(R.id.imageView);
        imageView.setImageAsset("squirrel.jpg");
        //imageView.setPin(new PointF(1718f, 581f));
        imageView.setPin(new PointF(600f, 600f));
        */
        return rootView;
    }

}
