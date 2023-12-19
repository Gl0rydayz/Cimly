package com.example.cimly_mobile_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DisplayAdapter extends ArrayAdapter<Intern> {
    Context context;
    int rsc;

    public DisplayAdapter(Context ctx, int resource, List<Intern> objects) {
        super(ctx, resource, objects);
        this.context = ctx;
        this.rsc = resource;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(rsc,viewGroup,false);
        TextView tvname = view.findViewById(R.id.tvname);
        TextView tvarrived = view.findViewById(R.id.tvarrived);
        TextView tvleft = view.findViewById(R.id.tvleft);
        TextView tvid = view.findViewById(R.id.tvid_perso);
        TextView tvemail = view.findViewById(R.id.tvemail);
        TextView tvnumero = view.findViewById(R.id.tvnum);
        ImageView iv_perso = view.findViewById(R.id.profile_image);

        Intern intern=getItem(i);
        String imageId = intern.getImageId();

        tvname.setText(intern.getName());
        tvemail.setText(String.format("Email: %s",intern.getEmail()));
        tvnumero.setText(String.format("Phone Number: %s",intern.getNumero()));
        tvarrived.setText(String.format("Arrived At: %s", intern.getArrivetime()));
        tvleft.setText(String.format("Left At: %s", intern.getLeftime()));
        tvid.setText(String.format("id: %s", intern.getId()));
        try {
            String encodedId = URLEncoder.encode(imageId, "UTF-8");
            String directImageUrl = "https://drive.google.com/uc?id=" + encodedId;
            // Load image using Picasso
            Picasso.get().load(directImageUrl).placeholder(R.drawable.profiledefault).into(iv_perso);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return view;
    }
}
