package com.corona.coronazp20t;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<Corona> data;

    // create constructor to initialize context and data sent from SearchActivity
    public Adapter(Context context, List<Corona> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when ViewHolder created/viewholder- kortele i kuria dedam duomenis
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_corona, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data - cia pildys kortele kiekvienu atveju, kai iskviesim, irasu
    //cia sitoj vietoj issitraukiam is saraso konkretu irasa ir uzpildom kiekviena elementa
    //cia persirasyti reikia kokius duomenis norim issitraukti
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        Corona current = data.get(position);
        myHolder.textKeyId.setText(current.getKeyId());
        myHolder.textLastUpdate.setText("Last update: "+current.getLastUpdate());
        myHolder.textConfirmed.setText("Confirmed: "+current.getConfirmed());
        myHolder.textDeaths.setText("Deaths: "+current.getDeaths());
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textKeyId;
        TextView textLastUpdate;
        TextView textConfirmed;
        TextView textDeaths;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textKeyId = (TextView) itemView.findViewById(R.id.textKeyId);
            textLastUpdate = (TextView) itemView.findViewById(R.id.textLastUpdate);
            textConfirmed = (TextView) itemView.findViewById(R.id.textConfirmed);
            textDeaths = (TextView) itemView.findViewById(R.id.textDeaths);
            itemView.setOnClickListener(this);
        }

        // Click event for all items
        //cia pereinam i new entry activity, kadanmgi adapteris neturi atskiro lango, tai rasom contex
        @Override
        public void onClick(View v) {
            Intent goToNewEntryActivity = new Intent(context,NewEntryActivity.class);
            context.startActivity(goToNewEntryActivity);
        }
    }
}
