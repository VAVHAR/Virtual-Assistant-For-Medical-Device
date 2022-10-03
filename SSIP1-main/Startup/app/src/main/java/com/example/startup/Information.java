package com.example.startup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.IDNA;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.startup.INformation.About;
import com.example.startup.INformation.Symptoms;
import com.example.startup.INformation.Treatment;
import com.example.startup.Models.Infoandtreat;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Information extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    String id;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =1 ;
    public static final String MyPREFERENCES = "MyPrefs" ;



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);


        id = getIntent().getStringExtra("diseaseid");
        Log.d("ewf",id);


        RequestQueue queue1 = Volley.newRequestQueue(Information.this);
        String url1 ="https://vamd.000webhostapp.com/forsms.php?Disease_Id="+ id;

        Log.d("erfwe",url1);
// Request a string response from the provided URL.
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse( String response) {
                        try {

                            Type collectionType = new TypeToken<ArrayList<Infoandtreat>>() {}.getType();

                            ArrayList<Infoandtreat>  enums1 = new Gson().fromJson(response, collectionType);



                                       sharedpreferences =getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();


                                        editor.putString("information" , enums1.get(0).getInformation());
                                        editor.putString("spread" , enums1.get(0).getSpread());


                                        editor.putString("name", enums1.get(0).getName());
                                        editor.putString("address", enums1.get(0).getAddress());
                                        editor.putString("contact", enums1.get(0).getContact_No());
                                        editor.putString("medicine", enums1.get(0).getMedicine_Name());
                                        editor.commit();




                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })

        {
            @Override
            protected Map<String, String> getParams () throws
                    AuthFailureError {
                Map<String, String> stringMap = new HashMap<String, String>();





                stringMap.put("Disease_Id",id);


                return stringMap;
            }
        };

// Add the request to the RequestQueue.
        queue1.add(stringRequest1);



        TabLayout tabLayout=findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);


        tabLayout.addTab(tabLayout.newTab().setText("INFORMATION"));
        tabLayout.addTab(tabLayout.newTab().setText("SYMPTOMS"));
        tabLayout.addTab(tabLayout.newTab().setText("TREATMENT"));




        FragmentStatePagerAdapter fragmentStatePagerAdapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {

                if (position==0)
                {
                    return new About();
                }
                if (position==1)
                {

                    return new Symptoms();

                }
                if (position==2)
                {

                    return new Treatment();

                }

                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        viewPager.setAdapter(fragmentStatePagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));






        checkForSmsPermission();




        String disease = getIntent().getStringExtra("disease");
        String symptoms = getIntent().getStringExtra("symptoms");



        TextView textView  = findViewById(R.id.diseases);
        textView.setText(disease);




        String TAG = "sfieu;";
//get number
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        final String getSimSerialNumber = tManager.getSimSerialNumber();
        final String getSimNumber = tManager.getLine1Number();

        Log.v(TAG, "getSimSerialNumber : " + getSimSerialNumber +" ,getSimNumber : "+ getSimNumber);






        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String contactno = prefs.getString("contact", "");
        Log.d("ewf",contactno);

        String name = prefs.getString("name", "");
        Log.d("ewf",name);

        String address = prefs.getString("address", "");
        Log.d("ewf",address);


        String medicine = prefs.getString("medicine", "");
        Log.d("ewf",medicine);




        final ImageView sms= findViewById(R.id.sms);
        final String contact =    "Disease Name"  + disease + " <br>" +
                                  "Symptoms :-"  + symptoms +" <br>" +
                                  "Doctor Name : -" + name +" <br>" +
                                    "Doctor Contact :- " + contactno +" <br>" +
                                    "Doctor Address :- " + address +" <br>" +
                                    "Medicine :- " + medicine;






        final String sms1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sms1 = String.valueOf(Html.fromHtml(contact, Html.FROM_HTML_MODE_LEGACY));

        } else {
            sms1 = String.valueOf(Html.fromHtml(contact, Html.FROM_HTML_MODE_LEGACY));


        }

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS(getSimNumber,sms1);
                Toast.makeText(Information.this,sms1,Toast.LENGTH_LONG).show();
            }
        });



    }




    //send sms

    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the SMS button.
        }
    }



        private void  sendSMS(String phoneNumber, String message)
        {
            String SENT = "SMS_SENT";
            String DELIVERED = "SMS_DELIVERED";

            PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                    new Intent(SENT), 0);

            PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                    new Intent(DELIVERED), 0);

            //---when the SMS has been sent---
            registerReceiver(new BroadcastReceiver(){
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    switch (getResultCode())
                    {
                        case Activity.RESULT_OK:
                            Toast.makeText(getBaseContext(), "SMS sent",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(getBaseContext(), "Generic failure",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Toast.makeText(getBaseContext(), "No service",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Toast.makeText(getBaseContext(), "Null PDU",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText(getBaseContext(), "Radio off",
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(SENT));

            //---when the SMS has been delivered---
            registerReceiver(new BroadcastReceiver(){
                @Override
                public void onReceive(Context arg0, Intent arg1) {
                    switch (getResultCode())
                    {
                        case Activity.RESULT_OK:
                            Toast.makeText(getBaseContext(), "SMS delivered",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(getBaseContext(), "SMS not delivered",
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }, new IntentFilter(DELIVERED));

            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
        }


    @Override
    protected void onResume() {
        super.onResume();

    }

}


