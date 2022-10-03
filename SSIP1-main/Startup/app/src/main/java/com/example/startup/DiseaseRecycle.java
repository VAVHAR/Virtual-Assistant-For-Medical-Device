package com.example.startup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.startup.Models.Infoandtreat;
import com.example.startup.Models.Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DiseaseRecycle extends RecyclerView.Adapter<Holder> {


    Context context;
    ArrayList<Model> enums;


    public DiseaseRecycle(Context context, ArrayList<Model> enums) {

        this.context = context;
        this.enums = enums;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drecycl,null);
        Holder holder=new Holder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {

        final String content = position + 1 + ")" + "Probable Illness : - <br>" + enums.get(position).getDisease()+"<br>";
            holder.Disease.setTextColor(Color.BLACK);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.Disease.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));

            } else {
                holder.Disease.setText(Html.fromHtml(content));

            }


            holder.Disease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Information.class);
                    intent.putExtra("diseaseid",enums.get(position).getDisease_Id());
                    intent.putExtra("symptoms",enums.get(position).getSymptoms());
                    intent.putExtra("disease",enums.get(position).getDisease());


                    //About
//                    intent.putExtra("information",enums1.get(position).getInformation());
//                    intent.putExtra("spread",enums1.get(position).getSpread());


                    //treatment




                    context.startActivity(intent);
                }
            });





    }

    @Override
    public int getItemCount() {
        return enums.size();
    }



}


class Holder extends RecyclerView.ViewHolder
{
    TextView Disease;
    public Holder(@NonNull View itemView) {
        super(itemView);

        Disease=itemView.findViewById(R.id.disease);



    }
}


