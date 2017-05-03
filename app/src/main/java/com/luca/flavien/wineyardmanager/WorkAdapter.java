package com.luca.flavien.wineyardmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.luca.flavien.wineyardmanager.db.object.Job;

import java.util.List;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: Main
 *
 * Description: We create this class for manage the list view in the Fragment Work, it is use for have 2 lines in a list view
 */

public class WorkAdapter extends ArrayAdapter<Job>{
    public WorkAdapter(Context context, List<Job> jobs) {
        super(context, 0, jobs);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_double_line,parent, false);
        }

        WorkViewHolder viewHolder = (WorkViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new WorkViewHolder();
            viewHolder.action = (TextView) convertView.findViewById(R.id.top_line_action);
            viewHolder.employee = (TextView) convertView.findViewById(R.id.second_line_employe);
            convertView.setTag(viewHolder);
        }

        Job job = getItem(position);

        if (job != null) {
            viewHolder.action.setText(job.getDescription());
        }
        viewHolder.employee.setText(job.getWorker().getLastName()+" "+job.getWorker().getFirstName());
        return convertView;
    }


    private class WorkViewHolder{
        public TextView action;
        public TextView employee;
    }
}
