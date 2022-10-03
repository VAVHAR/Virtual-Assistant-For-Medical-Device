package com.example.startup.INformation;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.startup.R;

public class Symptoms extends Fragment {
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.symptoms, null);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == -1) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 356);
        }

        //get disease id
        String s = getActivity().getIntent().getStringExtra("symptoms");

       s =  s.replace(",",",\n \u2022");
        Log.d("ewf",s);
        Log.d("ewf",s);


        TextView  symptoms = view.findViewById(R.id.symptoms);
        symptoms.setText("\u2022" + s + ".");
        return view;
    }
}