package com.example.startup.INformation;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.startup.Information;
import com.example.startup.Models.Infoandtreat;
import com.example.startup.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class About extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences prefs;


    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.about, null);



        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == -1) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 356);
        }

        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "", "Please wait...");
        new Thread() {
            public void run() {
                try{
                    //your code here.....

                    prefs = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                    String information = prefs.getString("information", "");
                    String sprea = prefs.getString("spread", "");


                    TextView info = view.findViewById(R.id.info);
                    info.setText(information);


                    TextView spread = view.findViewById(R.id.spread);
                    spread.setText(sprea);


                }
                catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
                // dismiss the progress dialog
                progressDialog.dismiss();
            }
        }.start();







        return view;
    }
}
