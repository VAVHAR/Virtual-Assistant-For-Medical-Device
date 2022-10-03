package com.example.startup.INformation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.startup.R;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class Treatment extends Fragment {


    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences prefs;


    public View onCreateView(final LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.treatment, null);


         prefs = getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String name = prefs.getString("name", "");
         String address = prefs.getString("address", "");
         String contact = prefs.getString("contact", "");
        String medicine = prefs.getString("medicine", "");

        TextView doctorname = view.findViewById(R.id.name);
        doctorname.setText(name);


        TextView Addre = view.findViewById(R.id.address);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Addre.setText(Html.fromHtml(address, Html.FROM_HTML_MODE_LEGACY));
        }


        TextView doctorcontact = view.findViewById(R.id.cont);
        doctorcontact.setText("Contact No:- " + contact);
        doctorcontact.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                String uri = "tel:" +prefs.getString("contact", "");
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse(uri));
                                startActivity(intent);
                             return true;
                            }
                        });

        ImageView doctorloca = view.findViewById(R.id.doctorlocation);
        doctorloca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                 Uri.parse("http://maps.google.com/maps?daddr=" + prefs.    getString("address", "")));
              startActivity(intent);
            }
        });



        TextView medicine_name = view.findViewById(R.id.medicinename);
        medicine_name.setText(medicine);

        ImageView medicala = view.findViewById(R.id.medicallocation);
        medicala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.google.com/maps/daddr=Pharmarcy$h1=en";
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                        startActivity(intent);
            }
        });

//
//
//        RequestQueue queue = Volley.newRequestQueue(getContext());
//        String url ="https://vamd.000webhostapp.com/treatment.php?Disease_Id=" + id;
//
//
//        Log.d("fwef",id);
//
//// Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//                    @RequiresApi(api = Build.VERSION_CODES.N)
//                    @Override
//                    public void onResponse(String response) {
//
//                        Type collectionType = new TypeToken<ArrayList<Model>>() {}.getType();
//
//                        final ArrayList<Model> enums = new Gson().fromJson(response, collectionType);
//
//
//                        final TextView textView = view.findViewById(R.id.name);
//                        textView.setText(enums.get(0).getName());
//                        Log.d("ewrfwef", String.valueOf(response));
//
//
//
//
//
//
//
//
//                        String cont =enums.get(0).getAddress();
//                        TextView textView1 = view.findViewById(R.id.address);
//                        textView1.setText(Html.fromHtml(cont, Html.FROM_HTML_MODE_LEGACY));
//
//
//
//
//                        final TextView textView2 = view.findViewById(R.id.cont);
//                        textView2.setText("Contact No:- " + enums.get(0).getContact_No());
//                        textView2.setOnLongClickListener(new View.OnLongClickListener() {
//                            @Override
//                            public boolean onLongClick(View v) {
//                                String uri = "tel:" + enums.get(0).getContact_No();
//                                Intent intent = new Intent(Intent.ACTION_CALL);
//                                intent.setData(Uri.parse(uri));
//                                startActivity(intent);
//                             return true;
//                            }
//                        });
//
//                        ImageView hospital = view.findViewById(R.id.doctorlocation);
//                        hospital.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                final Intent intent = new Intent(Intent.ACTION_VIEW,
//                                        Uri.parse("http://maps.google.com/maps?daddr=" + enums.get(0).getAddress()));
//                                startActivity(intent);
//                            }
//                        });
//
//                        ImageView medical = view.findViewById(R.id.medicallocation);
//                        String url = "http://maps.google.co.uk/maps?q=Pharmacy&hl=en";
//                        final Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
//                        medical.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                startActivity(intent);
//                            }
//                        });
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        })
//
//        {
//            @Override
//            protected Map<String, String> getParams () throws
//                    AuthFailureError {
//                Map<String, String> stringMap = new HashMap<String, String>();
//
//
//
//
//
//                stringMap.put("Disease_Id", id);
//
//
//                return stringMap;
//            }
//        };
//
//// Add the request to the RequestQueue.
//        queue.add(stringRequest);
//
//
//
//
//        RequestQueue queue1 = Volley.newRequestQueue(getContext());
//        String url1 ="https://vamd.000webhostapp.com/medicine.php?Disease_Id=" + id;
//
//
//        Log.d("fwef",id);
//
//// Request a string response from the provided URL.
//        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1,
//                new Response.Listener<String>() {
//                    @RequiresApi(api = Build.VERSION_CODES.N)
//                    @Override
//                    public void onResponse(String response) {
//
//                        Type collectionType = new TypeToken<ArrayList<Medicine>>() {}.getType();
//
//                        ArrayList<Medicine> enums = new Gson().fromJson(response, collectionType);
//
//                        Log.d("aeaf", String.valueOf(enums));
//
//
//                        Log.d("ref",response);
//                        TextView textView11 = view.findViewById(R.id.medical);
//                        textView11.setText("Medicine");
//                        Log.d("ewrfwef", String.valueOf(response));
//
//
//
//                        TextView textView12 = view.findViewById(R.id.medicallocation1);
//                        String cont1 = enums.get(0).getMedicine_Name();
//
//                        cont1 =  cont1.replace(",",",\n");
//
//                        textView12.setText(cont1);
//
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        })
//
//        {
//            @Override
//            protected Map<String, String> getParams () throws
//                    AuthFailureError {
//                Map<String, String> stringMap = new HashMap<String, String>();
//
//
//
//
//
//                stringMap.put("Disease_Id", id);
//
//
//                return stringMap;
//            }
//        };
//
//// Add the request to the RequestQueue.
//        queue1.add(stringRequest1);
//



//
//        TextView doctorname = view.findViewById(R.id.name);
//        doctorname.setText(getActivity().getIntent().getStringExtra("doctorname"));
//
//
//          TextView address = view.findViewById(R.id.address);
//        address.setText(getActivity().getIntent().getStringExtra("address"));
//
//
//          TextView contact = view.findViewById(R.id.cont);
//        contact.setText("Contact No:- " + getActivity().getIntent().getStringExtra("contact"));
//        contact.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                String uri = "tel:" + getActivity().getIntent().getStringExtra("contact");
//                                Intent intent = new Intent(Intent.ACTION_CALL);
//                                intent.setData(Uri.parse(uri));
//                                startActivity(intent);
//                return true;
//            }
//        });
//
//          TextView medicine = view.findViewById(R.id.medicinename);
//        medicine.setText(getActivity().getIntent().getStringExtra("medicine"));
//
//        ImageView hospital = view.findViewById(R.id.doctorlocation);
//                        hospital.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                final Intent intent = new Intent(Intent.ACTION_VIEW,
//                                        Uri.parse("http://maps.google.com/maps?daddr=" + getActivity().getIntent().getStringExtra("address")));
//                                startActivity(intent);
//                            }
//                        });
//
//                        ImageView medical = view.findViewById(R.id.medicallocation);
//                        String url = "http://maps.google.co.uk/maps?q=Pharmacy&hl=en";
//                        final Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
//                        medical.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                startActivity(intent);
//                            }
//                        });


        return view;
    }
}
