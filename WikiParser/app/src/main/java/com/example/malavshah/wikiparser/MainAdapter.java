package com.example.malavshah.wikiparser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by malavshah on 1/16/18.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    TreeMap<String, String> sorted;

    public MainAdapter(TreeMap<String, String> sorted) {
        this.sorted = sorted;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        List<String> keys = new ArrayList<String>(sorted.keySet());
        List<String> values = new ArrayList<String>(sorted.values());
        holder.title.setText(keys.get(position));
        holder.description.setText(values.get(position));

        //for (Map.Entry<String, String> entry : sorted.entrySet()) {
            //holder.title.setText(entry.getKey());
            //holder.description.setText(entry.getValue());

            /*ArrayList<String> cityList=   (ArrayList<String>)entry.get(sorted.get(position));
            for(String cityName : cityList){
                Log.e("City : ",cityName);
            }*/

            //holder.description.setText(sorted.get(position)); // value for the given key
            //sorted.get(position);


        //Log.i("MyApp", entry.getKey() + entry.getValue());
        //tv.append(entry.getKey() + " " + entry.getValue() + "\n\n");

    }

    @Override
    public int getItemCount() {
        return sorted.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }
}
