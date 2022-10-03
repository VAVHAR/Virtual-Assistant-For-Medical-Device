package com.example.startup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.startup.Models.Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {



    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    LinearLayout linearLayout,Doctorlayout;
    TextView textView;
    Button button;
    private static final int REQUEST_LOCATION = 0;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);






        ageerroe();






        getTheUserPermission();









        txtSpeechInput = findViewById(R.id.txtSpeechInput);
        btnSpeak = findViewById(R.id.btnSpeak);

        if (!txtSpeechInput.equals(null))
        {
            linearLayout = findViewById(R.id.conditions_layout);
            linearLayout.setVisibility(LinearLayout.INVISIBLE);



        }
        button = findViewById(R.id.start_again);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("fwefw","dfslh");
                txtSpeechInput.setText("");

                txtSpeechInput.setVisibility(TextView.INVISIBLE);
                if (!txtSpeechInput.equals(null))
                {
                    linearLayout = findViewById(R.id.conditions_layout);
                    linearLayout.setVisibility(LinearLayout.INVISIBLE);

                    textView.setText(" Hey Give me a Symptoms");

                }

            }
        });

        // hide the action bar
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE}, 1);
            return;
        } else {
            // Write you code here if permission already given.
        }

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });



        findViewById(R.id.menu_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Help.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "tel:" + "108" ;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);

            }
        });


    }


    public void ageerroe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Only For 18+");

        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //perform any action
               dialog.dismiss();
            }
        });


        //creating alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



        private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String url;
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {


                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                    textView = findViewById(R.id.SymptomsText);
                    textView.setText(result.get(0));


                    txtSpeechInput.setVisibility(TextView.VISIBLE);


                    txtSpeechInput.setText(result.get(0));


                    Log.d("ddd", String.valueOf(result.get(result.size() - 1)));

                    RequestQueue queue = Volley.newRequestQueue(this);

                    url = "https://vamd.000webhostapp.com/newsymptoms.php?Symptoms=" + result.get(0);



                    Log.d("de", url);




                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            Type collectionType = new TypeToken<ArrayList<Model>>() {
                                            }.getType();

                                            ArrayList<Model> enums = new Gson().fromJson(response, collectionType);


                                            Log.d("f", enums.get(0).getSymptoms());


                                            RecyclerView recyclerView2 = findViewById(R.id.recycler1);

                                            linearLayout.setVisibility(LinearLayout.VISIBLE);

                                            DiseaseRecycle recycleView = new DiseaseRecycle(MainActivity.this, enums);
                                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                                            recyclerView2.setLayoutManager(layoutManager);

                                            recyclerView2.setHasFixedSize(true);

                                            recyclerView2.setAdapter(recycleView);
                                        }
                                catch (Exception e)
                                            {
                                                AlertDialog();
                                            }
                                        button = findViewById(R.id.start_again);

                                        button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Log.d("fwefw","dfslh");
                                                txtSpeechInput.setText("");

                                                txtSpeechInput.setVisibility(TextView.INVISIBLE);
                                                if (!txtSpeechInput.equals(null))
                                                {
                                                    linearLayout = findViewById(R.id.conditions_layout);
                                                    linearLayout.setVisibility(LinearLayout.INVISIBLE);

                                                    textView.setText(" Hey Give me a Symptoms");

                                                }

                                            }
                                        });
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                textView.setText("That didn't work!");
                            }
                        })

                    {
                        @Override
                        protected Map<String, String> getParams () throws
                                AuthFailureError {
                            Map<String, String> stringMap = new HashMap<String, String>();


                            String name2 = txtSpeechInput.getText().toString();


                            stringMap.put("Symptoms", name2);


                            return stringMap;
                        }
                        };

// Add the request to the RequestQueue.
                    queue.add(stringRequest);

                    //Fetch Sysmptoms



                    }

            }
            button = findViewById(R.id.start_again);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("fwefw","dfslh");
                    txtSpeechInput.setText("");

                    txtSpeechInput.setVisibility(TextView.INVISIBLE);

                    linearLayout = findViewById(R.id.conditions_layout);
                    linearLayout.setVisibility(LinearLayout.INVISIBLE);



                }
            });
        }
    }

    public void AlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Enter Valid Symptoms");

        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //perform any action
                txtSpeechInput.setText("");
                textView.setText("");
            }
        });


        //creating alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    // Location getter
    private void getTheUserPermission() {
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE}, REQUEST_LOCATION);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationGetter locationGetter = new LocationGetter(MainActivity.this, REQUEST_LOCATION, locationManager);


        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            locationGetter.OnGPS();
        } else {

            locationGetter.getLocation();
        }

    }
    public class LocationGetter {

        private int REQUEST_LOCATION;
        private MainActivity mContext;
        private LocationManager locationManager;
        private Geocoder geocoder;

        public LocationGetter(MainActivity mContext, int requestLocation, LocationManager locationManager) {
            this.mContext = mContext;
            this.locationManager = locationManager;
            this.REQUEST_LOCATION = requestLocation;
        }


        public void getLocation() {

            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,

                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            } else {
                Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                if (LocationGps != null) {
                    double lat = LocationGps.getLatitude();
                    double longi = LocationGps.getLongitude();
                    getTheAddress(lat, longi);
                } else if (LocationNetwork != null) {
                    double lat = LocationNetwork.getLatitude();
                    double longi = LocationNetwork.getLongitude();
                    getTheAddress(lat, longi);
                } else if (LocationPassive != null) {
                    double lat = LocationPassive.getLatitude();
                    double longi = LocationPassive.getLongitude();
                    getTheAddress(lat, longi);
                } else {
                    Toast.makeText(MainActivity.this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
                }

            }

        }

        private void getTheAddress(double latitude, double longitude) {
            List<Address> addresses;
            geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getSubAdminArea();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                Log.d("city", addresses.get(0).getSubAdminArea());
                Log.d("city", addresses.get(0).getAdminArea());
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        public void OnGPS() {

            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }
}


