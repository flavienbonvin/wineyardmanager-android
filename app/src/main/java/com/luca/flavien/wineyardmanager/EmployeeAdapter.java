package com.luca.flavien.wineyardmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.luca.flavien.wineyardmanager.DB.Object.Job;
import com.luca.flavien.wineyardmanager.DB.Object.Worker;

import java.util.List;

/**
 * Created by Cento on 25.04.2017.
 */

    public class EmployeeAdapter extends ArrayAdapter<Worker> {
        public EmployeeAdapter(Context context, List<Worker> workers) {
            super(context, 0, workers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_employee,parent, false);
            }

            WorkViewHolder viewHolder = (WorkViewHolder) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new WorkViewHolder();
                viewHolder.avatar = (ImageView) convertView.findViewById(R.id.icon);
                viewHolder.name = (TextView) convertView.findViewById(R.id.Itemname);

                convertView.setTag(viewHolder);
            }

            Worker worker = getItem(position);

            viewHolder.name.setText(worker.getLastName()+" "+worker.getFirstName());

            //TODO Ajouter une image dans le Worker

            String pathImg = null;
            if(pathImg== null || pathImg==""){
                viewHolder.avatar.setImageResource(R.drawable.ic_contact);
            }/*else{
                Bitmap bMap = BitmapFactory.decodeFile(vin.getImg());
                viewHolder.avatar.setImageBitmap(bMap);
            }*/


            return convertView;
        }


        private class WorkViewHolder{
            public TextView name;
            public ImageView avatar;
        }
}
