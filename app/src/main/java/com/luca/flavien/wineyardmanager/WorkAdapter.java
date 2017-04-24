package com.luca.flavien.wineyardmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.luca.flavien.wineyardmanager.DB.Object.Job;

import java.util.List;

/**
 * Created by Flavien on 24.04.2017.
 */

public class WorkAdapter extends ArrayAdapter<Job>{
    public WorkAdapter(Context context, List<Job> jobs) {
        super(context, 0, jobs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_double_line,parent, false);
        }

        WorkViewHolder viewHolder = (WorkViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new WorkViewHolder();
            viewHolder.action = (TextView) convertView.findViewById(R.id.top_line_action);
            viewHolder.employe = (TextView) convertView.findViewById(R.id.second_line_employe);
            //viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        Job job = getItem(position);

        viewHolder.action.setText(job.getDescription());
        viewHolder.employe.setText(job.getWorker().getLastName()+" "+job.getWorker().getFirstName());
            /*String pathImg = vin.getImg();
            if(pathImg== null || pathImg==""){
                viewHolder.avatar.setImageResource(R.drawable.wine_default);
            }else{
                Bitmap bMap = BitmapFactory.decodeFile(vin.getImg());
                viewHolder.avatar.setImageBitmap(bMap);
            }*/


        return convertView;
    }


    private class WorkViewHolder{
        public TextView action;
        public TextView employe;
        //public ImageView avatar;
    }
}
