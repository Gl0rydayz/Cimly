package com.example.cimly_mobile_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Intern> interns;

    public DisplayAdapter(Context ctx, ArrayList<Intern> interns) {
        this.context = ctx;
        this.interns = interns;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return interns.size();
    }

    @Override
    public Object getItem(int i) {
        return interns.get(i); // Return the Intern at the specified position
    }

    @Override
    public long getItemId(int i) {
        return i; // Return the position as an ID
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.customlistview, null);
        TextView tvname = view.findViewById(R.id.tvname);
        TextView tvarrived = view.findViewById(R.id.tvarrived);
        TextView tvleft = view.findViewById(R.id.tvleft);
        TextView tvid = view.findViewById(R.id.tvid_perso);
        ImageView iv_perso = view.findViewById(R.id.profile_image);

        // Get the current Intern at position i
        Intern currentIntern = interns.get(i);

        tvname.setText(currentIntern.getName());
        tvarrived.setText(String.format("Arrived At: %s", currentIntern.getArrivetime().toString()));
        tvleft.setText(String.format("Left At: %s", currentIntern.getLeftime().toString()));
        tvid.setText(String.format("id: %s", currentIntern.getId()));

        return view;
    }
}
